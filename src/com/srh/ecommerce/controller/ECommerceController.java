package com.srh.ecommerce.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.srh.ecommerce.model.AdminSearch;
import com.srh.ecommerce.model.Favourite;
import com.srh.ecommerce.model.OrderSpec;
import com.srh.ecommerce.model.Orders;
import com.srh.ecommerce.model.Product;
import com.srh.ecommerce.model.UserDto;
import com.srh.ecommerce.model.Users;
import com.srh.ecommerce.service.ECommerceServiceImpl;
import com.srh.ecommerce.service.ReportGeneration;

@Controller
@RequestMapping("/myCart")
public class ECommerceController{

	@RequestMapping( value = "/newUser" )
	public String newUser(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		return "newUser";
	}

	@RequestMapping( value = "/logOut" )
	public String logOut(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		return "logOut";
	}



	@RequestMapping( value = "/addRegister" )
	public String addRegister(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String returnPage = session.getAttribute("userName") != null ? "productRegistration" : "loginWithValidation" ;

		return returnPage;
	}

	@RequestMapping( value = "/checkOut" )
	public String checkOut(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String returnPage = session.getAttribute("userName") != null ? "checkout" : "loginWithValidation" ;

		return returnPage;
	}

	@RequestMapping( value = "/loginValid" )
	public String getLoginPageWithValidation(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		return "loginWithValidation";
	}

	@RequestMapping( value = "/dataTable" )
	public String getDataTableWithValidation(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String returnPage = session.getAttribute("userName") != null ? "dataTableWithValidation" : "loginWithValidation" ;

		return returnPage;
	}


	@RequestMapping( value = "/checkOutTable" )
	public String getDataTableWithCart(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String returnPage = session.getAttribute("userName") != null ? "dataTableWithCart" : "loginWithValidation" ;

		return returnPage;
	}

	@RequestMapping( value = "/viewOrdersPage" )
	public String viewOrdersPage(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String returnPage = session.getAttribute("userName") != null ? "viewOrders" : "loginWithValidation" ;

		return returnPage;
	}


	@RequestMapping( value = "/orderConfirmation" , method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String orderConfirmation(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody OrderSpec spec) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		response.setContentType("text/html");

		HttpSession session = request.getSession();

		String userName = session.getAttribute("userName") != null ? session.getAttribute("userName").toString() : "" ;

		String url = service.orderConfirmation(spec, userName);

		return url;

	}	


	@RequestMapping( value = "/insertUser", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String insertUser(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody Users user) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		String message = service.insertHashedPwd(user);

		return message;
	}

	@RequestMapping( value = "/insertVendor", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String insertVendor(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody Users user) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		String message = service.insertHashedPwd(user);

		return message;
	}

	@RequestMapping( value = "/insertProduct", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String insertProduct(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody Product prod) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		HttpSession session = request.getSession();

		prod.setCompanyNumber( session.getAttribute("companyNumber") != null ? session.getAttribute("companyNumber").toString() : "" );

		String message = service.insertProduct(prod);

		return message;
	}

	@RequestMapping( value = "/viewOrders", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody List<Object[]> viewOrders(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		HttpSession session = request.getSession();

		String companyNumber = session.getAttribute("companyNumber") != null ? session.getAttribute("companyNumber").toString() : "" ;

		String userName = session.getAttribute("userName") != null ? session.getAttribute("userName").toString() : "";
		
		if( userName.equalsIgnoreCase("") )
			throw new Exception();

		String userType = session.getAttribute("userType") != null ? session.getAttribute("userType").toString() : "" ;

		List<Object[]> ordeList = service.viewOrders(companyNumber, userName, userType);

		return ordeList;
	}


	@RequestMapping( value = "/exportReport", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String exportReport(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody AdminSearch adminReport) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		HttpSession session = request.getSession();

		String companyNumber = session.getAttribute("companyNumber") != null ? session.getAttribute("companyNumber").toString() : "" ;

		String userName = session.getAttribute("userName") != null ? session.getAttribute("userName").toString() : "" ;

		String userType = session.getAttribute("userType") != null ? session.getAttribute("userType").toString() : "" ;

		List<Orders> orderList = null;

		if( userType.equalsIgnoreCase("A") )
			orderList = service.viewOrdersForAdminReport(adminReport);
		else{
			adminReport.setCompanyName( !companyNumber.equalsIgnoreCase("") ? service.getCompanyNameByNumber(companyNumber) : "");
			adminReport.setUserName(userName);
			orderList = service.viewOrdersForReports(companyNumber, userName, userType);
		}

		ReportGeneration reportGen = new ReportGeneration();

		String fileNameUrl = reportGen.generateDynamicJasperReport(orderList, adminReport, userType);

		return fileNameUrl;
	} 


	@RequestMapping( value = "/generateReport", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody List<Object[]> generateReports(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody AdminSearch adminReport) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		List<Object[]> orderList = service.viewOrdersForAdmin(adminReport);

		return orderList;
	}  


	@RequestMapping( value = "/cancelOrder", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String cancelOrder(HttpServletRequest request, 
			HttpServletResponse response, @RequestParam Long orderId) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		String message = service.cancelOrder(orderId);

		return message;
	}

	@RequestMapping( value = "/modifyProduct", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String modifyProduct(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody Product prod) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		HttpSession session = request.getSession();

		prod.setCompanyNumber( session.getAttribute("companyNumber") != null ? session.getAttribute("companyNumber").toString() : "" );

		String message = service.modifyProduct(prod);

		return message;
	}

	@RequestMapping( value = "/deleteFavourite", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String deleteFavourite(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody Favourite fav) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		HttpSession session = request.getSession();

		String userName = session.getAttribute("userName") != null ? session.getAttribute("userName").toString() : "" ;

		String message = service.deleteFavourite(userName, fav.getProductId());

		return message;
	}

	@RequestMapping( value = "/insertFavourite", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String insertFavourite(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody Favourite fav) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		HttpSession session = request.getSession();

		String userName = session.getAttribute("userName") != null ? session.getAttribute("userName").toString() : "" ;

		String message = service.insertFavourite(userName, fav.getProductId());

		return message;
	}

	@RequestMapping( value = "/deleteProduct", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String deleteProduct(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody Favourite fav) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		String message = service.deleteProduct(fav.getProductId());

		return message;
	}

	@RequestMapping( value = "/getProductList", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody List<Object> getProductList(HttpServletRequest request, 
			HttpServletResponse response, @RequestParam String searchParam) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		HttpSession session = request.getSession();	

		String userType = (String) session.getAttribute("userType");

		String companyNumber = session.getAttribute("companyNumber") != null ? session.getAttribute("companyNumber").toString() : "";

		List<Object> productList = service.getProductList( companyNumber, userType, searchParam );

		return productList;
	}

	@RequestMapping( value = "/getFavouriteList", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody List<Object> getFavouriteList(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		HttpSession session = request.getSession();	

		List<Object> productList = service.getFavouriteList( session.getAttribute("userName") != null ? session.getAttribute("userName").toString() : "");

		return productList;
	}

	@RequestMapping( value = "/downloadPdfFile", method = {RequestMethod.GET, RequestMethod.POST})
	public void downloadFile(HttpServletRequest request, 
			HttpServletResponse response, @RequestParam String url, @RequestParam String fileType) throws IOException{		


		String[] str = url.split("_OR");

		if( fileType.equalsIgnoreCase("pdf")){
			fileType = "application/pdf";
			response.setHeader("Content-disposition","attachment; filename=" + "OrderConfirmation_OR_" + str[1].trim() );
		}else{
			fileType = "text/csv";
			response.setHeader("Content-disposition","attachment; filename=" + "Report" + str[1].trim() );
		}

		response.setContentType(fileType);		

		File file = new File(url);

		OutputStream out = response.getOutputStream();
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[4096];
		int l;
		while ((l = in.read(buffer)) > 0){
			out.write(buffer, 0, l);
		}
		in.close();
		out.flush();
	}

	@RequestMapping( value = "/fetchUserDetails", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Users fetchUserDetails(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {


		String userName = (String) request.getSession().getAttribute("userName");		

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		Users user = service.getUserDetails(userName);

		return user;
	} 

	@RequestMapping( value = "/modifyUser", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String modifyUser(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody Users user) throws Exception {


		ECommerceServiceImpl service = new ECommerceServiceImpl();

		String message = service.updateUserProfile(user);

		return message;
	} 

	@RequestMapping( value = "/checkUserWithValidation", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody int checkUserWithValidation(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody UserDto user) throws Exception {

		ECommerceServiceImpl service = new ECommerceServiceImpl();

		int result = service.checkUser(user.getUserName() , user.getUserPassword(), request.getSession());

		return result;
	} 

	@ExceptionHandler(Exception.class)
	public @ResponseBody String exceptionHandler(Exception ex) {

		System.out.println(ex.getMessage());

		return "Application has encountered an error. Please contact the admin";

	}

}
