package com.srh.ecommerce.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.pdfbox.exceptions.COSVisitorException;

import com.srh.ecommerce.dao.ECommerceDaoImpl;
import com.srh.ecommerce.model.AdminSearch;
import com.srh.ecommerce.model.OrderSpec;
import com.srh.ecommerce.model.Orders;
import com.srh.ecommerce.model.Product;
import com.srh.ecommerce.model.UserDto;
import com.srh.ecommerce.model.Users;


public class ECommerceServiceImpl {

	public String insertHashedPwd( Users user ) throws Exception {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		boolean exists = daoImpl.checkUserExists( user.getUserName() );

		if( exists ){

			return "This Username already exists.";

		}else{

			String salt =  BCrypt.gensalt(10);

			String hashed = BCrypt.hashpw(user.getUserPassword(), salt);

			if( user.getUserType() != null && user.getUserType().equalsIgnoreCase("V") ){

				try{

					daoImpl.insertCompany( daoImpl.getMaxOfCompanyId() + 1, user.getCompanyName(), user.getCompanyNumber() );

				}catch(Exception exp){

					return "Please enter unique company number and name";
				}
			}

			daoImpl.insertNewUser( daoImpl.getMaxOfUserId() + 1 , user, hashed);

		}

		return "User successfully added";



	}

	public String updateUserProfile( Users user ) throws Exception {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		Users userExists = daoImpl.getUserDetails( user.getUserName() );

		if( user.getUserType() != null && user.getUserType().equalsIgnoreCase("V") ){

			try{

				daoImpl.updateCompany( userExists.getCompanyNumber(), user.getCompanyName(), user.getCompanyNumber() );

			}catch(Exception exp){

				return "Please enter unique company number and name";
			}
		}
		
		System.out.println(" userExists.getUserId()      ::::: " + userExists.getUserId() );

		daoImpl.updateUser( userExists.getUserId() , user);

		return "User profile successfully updated";



	}

	public int checkUser(String userName, String password, HttpSession session) throws Exception {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		boolean exists = daoImpl.checkUserExists(userName);

		if( exists ){

			UserDto userDto = daoImpl.getUserHash(userName);

			String userCompany = daoImpl.getUserCompany(userName);

			String userType = daoImpl.getUserType(userName);

			boolean result = BCrypt.checkpw(password, userDto.getUserPassword());

			if( result ){

				session.setAttribute("companyNumber", userCompany);

				session.setAttribute("userName", userName);

				session.setAttribute("userType", userType);

				return 1;

			}else{

				return 0;
			}

		}else{

			return -1;
		}


	}

	public String insertProduct(Product prod) {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		daoImpl.insertProduct( prod );

		return "Product added Successfully";
	}

	public List<Object> getProductList(String companyNumber, String userType, String searchParamString) {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		List<Product> productList = daoImpl.getProductList(companyNumber, searchParamString);

		List<Object> finalResult = new ArrayList<Object>();

		List<String> arrayList = null;

		if( productList != null ){

			for(Product mapper : productList){

				arrayList = new ArrayList<String>();

				StringBuilder buttonString = new StringBuilder();


				if( userType.equalsIgnoreCase("C") || userType.equalsIgnoreCase("A") ){					
					buttonString.append("<input type='button' class='submit_button top' title='" );
					buttonString.append( mapper.getProductId().toString() );
					buttonString.append("' onclick='callFavourite(this.title);' value='Add to Favourite'>");
					if( mapper.getAvailable() > 0 ){
						buttonString.append("<input type='button' class='submit_button top' title='");
						buttonString.append(mapper.getProductId().toString());
						buttonString.append("' onclick='callFunction(this.title);' value='Add to cart'>");
					}
				}else if( userType.equalsIgnoreCase("V") ){
					buttonString.append("<input type='button' class='submit_button top' title='");
					buttonString.append( mapper.getProductId().toString() );
					buttonString.append("' onclick='deleteProduct(this.title);' value='Delete Product'>");
					buttonString.append("<input type='button' class='submit_button top' title='");
					buttonString.append( mapper.getProductId().toString());
					buttonString.append("' onclick='modifyProduct(this.title);' value='Modify Product'>");
				}
				arrayList.add(mapper.getProductId().toString());
				arrayList.add(mapper.getProductName());
				arrayList.add(mapper.getModelNumber());
				arrayList.add(mapper.getOs());
				arrayList.add(mapper.getRam());
				arrayList.add(mapper.getHardDisk());
				arrayList.add(mapper.getProcessor());
				arrayList.add(mapper.getScreenSize());
				arrayList.add(String.valueOf(mapper.getAvailable()));
				arrayList.add(mapper.getPrice());
				arrayList.add(buttonString.toString());

				finalResult.add(arrayList);

			}
		}



		return finalResult;
	}

	public String insertFavourite(String userName, Long productId) {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		daoImpl.insertFavourite( userName, productId );

		return "Product added to favourites";
	}

	public List<Object> getFavouriteList(String userName) {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		List<Product> productList = daoImpl.getFavouriteList( userName );

		List<Object> finalResult = new ArrayList<Object>();

		List<String> arrayList = null;

		if( productList != null ){

			for(Product mapper : productList){

				StringBuilder buttonString = new StringBuilder("");

				buttonString.append("<input type='button' class='submit_button top' title='");
				buttonString.append( mapper.getProductId().toString() );
				buttonString.append("' onclick='deleteFavourite(this.title);' value='Delete from Favourite'>");
				
				if( mapper.getAvailable() > 0 ){
					buttonString.append("<input type='button' class='submit_button top' title='");
					buttonString.append(mapper.getProductId().toString() );
					buttonString.append("' onclick='callFunction(this.title);' value='Add to cart'>");
				}

				arrayList = new ArrayList<String>();

				arrayList.add(mapper.getProductId().toString());
				arrayList.add(mapper.getProductName());
				arrayList.add(mapper.getModelNumber());
				arrayList.add(mapper.getOs());
				arrayList.add(mapper.getRam());
				arrayList.add(mapper.getHardDisk());
				arrayList.add(mapper.getProcessor());
				arrayList.add(mapper.getScreenSize());
				arrayList.add(String.valueOf(mapper.getAvailable()));
				arrayList.add(mapper.getPrice());
				arrayList.add(buttonString.toString());

				finalResult.add(arrayList);

			}
		}


		return finalResult;
	}

	public String deleteFavourite(String userName, Long productId) {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		daoImpl.deleteFavourite( userName, productId );

		return "Product deleted from favourites";
	}

	public String modifyProduct(Product prod) {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		daoImpl.modifyProduct( prod );

		return "Product modified Successfully";
	}

	public String deleteProduct(Long productId) {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		daoImpl.deleteProduct( productId );

		return "Product deleted Successfully";
	}

	public String orderConfirmation(OrderSpec spec, String userName) throws COSVisitorException, IOException {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		String orderNumber = daoImpl.insertOrders(spec, userName);

		List<Orders> orderList = daoImpl.getOrders( orderNumber );

		CreatePDFServiceImpl createPDFServiceImpl = new CreatePDFServiceImpl();

		String url = createPDFServiceImpl.generateAcknowledgement(orderList, spec);

		return url;
	}

	public String cancelOrder(Long orderId) {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();	

		return daoImpl.cancelOrders(orderId);
	}

	public String getCompanyNumberByName( String companyName ){

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		String companyNumber = daoImpl.getCompanyNumber(companyName);

		return companyNumber;
	}

	public String getCompanyNameByNumber( String companyNumber ){

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		String companyName = daoImpl.getCompanyName(companyNumber);

		return companyName;
	}

	public List<Object[]> viewOrdersForAdmin(AdminSearch adminReport) {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		String companyNumber = "";		

		if( !adminReport.getCompanyName().equalsIgnoreCase("") )		
			companyNumber = daoImpl.getCompanyNumber(adminReport.getCompanyName());

		List<Orders> orderList = daoImpl.viewOrders(companyNumber, adminReport.getUserName(), "");

		List<Object[]> finalResult = getObjectArrayFromList(orderList, "A");

		return finalResult;
	}
	
	
	public List<Orders> viewOrdersForAdminReport(AdminSearch adminReport) {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		String companyNumber = "";		

		if( !adminReport.getCompanyName().equalsIgnoreCase("") )		
			companyNumber = daoImpl.getCompanyNumber(adminReport.getCompanyName());

		List<Orders> orderList = daoImpl.viewOrders(companyNumber, adminReport.getUserName(), "");
		
		for( Orders order : orderList ){
			
			Product prod = daoImpl.getProductbyId(order.getProductId());
			order.setProductName(prod.getProductName());
			order.setPrice(prod.getPrice());
		}

		return orderList;
	}
	
	public List<Orders> viewOrdersForReports(String companyNumber, String userName, String userType) {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		List<Orders> orderList = daoImpl.viewOrders( companyNumber, userName, userType);
		
		for( Orders order : orderList ){
			
			Product prod = daoImpl.getProductbyId(order.getProductId());
			order.setProductName(prod.getProductName());
			order.setPrice(prod.getPrice());
		}

		return orderList;
	}

	public List<Object[]> viewOrders(String companyNumber, String userName, String userType) {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

		List<Orders> orderList = daoImpl.viewOrders( companyNumber, userName, userType);

		List<Object[]> finalResult = getObjectArrayFromList(orderList, userType);

		return finalResult;
	}



	private List<Object[]> getObjectArrayFromList(List<Orders> orderList, String userType){

		List<Object[]> finalResult = new ArrayList<Object[]>();

		List<String> arrayList = null;

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();		

		if( orderList != null ){

			for(Orders mapper : orderList){
				
				StringBuilder button = new StringBuilder("");

				if( userType.equalsIgnoreCase("C") && !mapper.getOrderStatus().equalsIgnoreCase("CANCELLED")){				
					button.append("<input type='button' class='submit_button top' title='");
					button.append(mapper.getOrderId().toString() );
					button.append("' onclick='cancelOrder(this.title);' value='Cancel Order'>");
				}else{
					button.append("N/A");
				}

				arrayList = new ArrayList<String>();

				Product prod = daoImpl.getProductbyId(mapper.getProductId());

				arrayList.add(mapper.getOrderNumber());
				arrayList.add(prod.getProductName());
				arrayList.add(mapper.getOrderDate());
				arrayList.add(mapper.getDeliveryDate());
				arrayList.add(prod.getPrice());
				arrayList.add(String.valueOf(mapper.getNumberOfItems()));
				arrayList.add(mapper.getOrderStatus());
				arrayList.add(button.toString());

				finalResult.add(arrayList.toArray());

			}
		}

		return finalResult;
	}

	public Users getUserDetails(String userName) {

		ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();	

		Users user = daoImpl.getUserDetails(userName);	

		String companyName = "";

		if( user.getCompanyNumber() != null )
			companyName = daoImpl.getCompanyName(user.getCompanyNumber());

		user.setCompanyName(companyName);

		return user;
	}

}
