package com.srh.ecommerce.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.srh.ecommerce.model.OrderSpec;
import com.srh.ecommerce.model.Orders;
import com.srh.ecommerce.model.OrdersMapper;
import com.srh.ecommerce.model.Product;
import com.srh.ecommerce.model.ProductMapper;
import com.srh.ecommerce.model.UserDto;
import com.srh.ecommerce.model.UserDtoMapper;
import com.srh.ecommerce.model.UserMapper;
import com.srh.ecommerce.model.Users;

public class ECommerceDaoImpl{
	
	private NamedParameterJdbcTemplate getNamedJdbcTemplate(){
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		NamedParameterJdbcTemplate namedJdbcTemplate = (NamedParameterJdbcTemplate) appContext.getBean("namedParameterJdbcTemplate");
		
		return namedJdbcTemplate;
	}
	
	private JdbcTemplate getJdbcTemplate(){
		

		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		JdbcTemplate jdbcTemplate = (JdbcTemplate) appContext.getBean("jdbcTemplate");
		
		return jdbcTemplate;
	}


	public void insertNewUser(Long userId, Users user, String password) throws Exception {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();		

		String insertQuery = "INSERT INTO USERS VALUES(:userId, :userName, :addressLine, :telephone, "
				+ ":city, :country, :email, :firstName, :lastName, :state,"
				+ ":zipCode, :userType, :companyNumber, :status)";

		Map<String, String> insertMap = new HashMap<String, String>();
		insertMap.put("userId", userId.toString());
		insertMap.put("userName", user.getUserName());
		insertMap.put("addressLine", user.getAddressLine());
		insertMap.put("telephone", user.getTelephone());
		insertMap.put("city", user.getCity());
		insertMap.put("country", user.getCountry());
		insertMap.put("email", user.getEmail());
		insertMap.put("firstName", user.getFirstName());
		insertMap.put("lastName", user.getLastName());
		insertMap.put("state", user.getState());
		insertMap.put("zipCode", user.getZipCode());
		insertMap.put("userType", user.getUserType());
		insertMap.put("companyNumber", user.getCompanyNumber());
		insertMap.put("status", "ACTIVE");


		namedJdbcTemplate.update(insertQuery, insertMap);
		
		String query = "INSERT INTO USERPASSWORDS VALUES(:userName, :userPassword)";

		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", user.getUserName());
		map.put("userPassword", password);

		namedJdbcTemplate.update(query, map);

	}
	
	public void updateUser(Long userId, Users user) throws Exception {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();	
		
		String updateQuery = "UPDATE USERS SET USERNAME = :userName, ADDRESSLINE = :addressLine, TELEPHONE = :telephone, "
									+ "CITY = :city, COUNTRY = :country, EMAIL = :email, FIRSTNAME = :firstName,"
									+ "LASTNAME = :lastName, USERSTATE = :state, ZIPCODE = :zipCode, COMPANYNUMBER = :companyNumber "
									+ "WHERE USERID = :userId";
									

		Map<String, String> insertMap = new HashMap<String, String>();
		insertMap.put("userId", userId.toString());
		insertMap.put("userName", user.getUserName());
		insertMap.put("addressLine", user.getAddressLine());
		insertMap.put("telephone", user.getTelephone());
		insertMap.put("city", user.getCity());
		insertMap.put("country", user.getCountry());
		insertMap.put("email", user.getEmail());
		insertMap.put("firstName", user.getFirstName());
		insertMap.put("lastName", user.getLastName());
		insertMap.put("state", user.getState());
		insertMap.put("zipCode", user.getZipCode());
		insertMap.put("companyNumber", user.getCompanyNumber());
		
		namedJdbcTemplate.update(updateQuery, insertMap);

	}


	public UserDto getUserHash(String userName){


		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		String sql = "SELECT * FROM USERPASSWORDS WHERE USERNAME = :userName";

		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("userName", userName);

		UserDto userDto = namedJdbcTemplate.queryForObject(sql, queryMap , new UserDtoMapper());		

		return userDto;

	}



	public Long getMaxOfUserId(){
		
		JdbcTemplate jdbcTemplate = getJdbcTemplate();

		Long userId = jdbcTemplate.queryForLong("SELECT MAX(USERID) FROM USERS");

		return userId;
	}


	public boolean checkUserExists(String userName) {


		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		String query = "SELECT COUNT(USERID) FROM USERS WHERE USERNAME = :userName AND STATUS = :status";		

		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("userName", userName);
		queryMap.put("status", "ACTIVE");

		int userCount = namedJdbcTemplate.queryForInt(query, queryMap);

		return userCount > 0 ? true : false;
	}


	public Long getMaxOfCompanyId() {

		JdbcTemplate jdbcTemplate = getJdbcTemplate();

		Long companyId = jdbcTemplate.queryForLong("SELECT MAX(COMPANYID) FROM COMPANY");

		return companyId;
	}


	public void insertCompany(Long companyId, String companyName, String companyNumber) {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		String sql = "INSERT INTO COMPANY VALUES(:companyId, :companyName, :companyNumber)";

		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("companyId", companyId.toString());
		queryMap.put("companyName", companyName);
		queryMap.put("companyNumber", companyNumber);


		namedJdbcTemplate.update(sql, queryMap);

	}
	
	public void updateCompany( String earlyCompanyNumber, String companyName, String companyNumber) {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		String sql = "UPDATE COMPANY SET COMPANYNAME = :companyName, COMPANYNUMBER = :companyNumber WHERE COMPANYNUMBER = :earlyCompanyNumber";

		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("companyName", companyName);
		queryMap.put("companyNumber", companyNumber);
		queryMap.put("earlyCompanyNumber", earlyCompanyNumber);


		namedJdbcTemplate.update(sql, queryMap);

	}

	private Long getMaxOfProductId() {

		JdbcTemplate jdbcTemplate = getJdbcTemplate();

		Long productId = jdbcTemplate.queryForLong("SELECT MAX(PRODUCTID) FROM PRODUCT");

		return productId;
	}

	public void insertProduct(Product prod) {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		Long productId = getMaxOfProductId();

		productId++;

		String sql = "INSERT INTO PRODUCT VALUES(:productId, :productName, :companyNumber, :hardDisk, :modelNumber, "
				+ ":os, :processor, :available, :screenSize, :ram, :price, :status )";

		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("productId", productId.toString());
		queryMap.put("productName", prod.getProductName());
		queryMap.put("companyNumber", prod.getCompanyNumber());
		queryMap.put("hardDisk", prod.getHardDisk());
		queryMap.put("modelNumber", prod.getModelNumber());
		queryMap.put("os", prod.getOs());
		queryMap.put("processor", prod.getProcessor());
		queryMap.put("available", String.valueOf(prod.getAvailable()));
		queryMap.put("screenSize", prod.getScreenSize());
		queryMap.put("ram", prod.getRam());
		queryMap.put("price", prod.getPrice());
		queryMap.put("status", "ACTIVE");

		namedJdbcTemplate.update(sql, queryMap);

	}


	public String getUserCompany(String userName) {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		String sql = "SELECT COMPANYNUMBER FROM USERS WHERE USERNAME = :userName";

		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("userName", userName);

		String userCompany = namedJdbcTemplate.queryForObject(sql, queryMap, String.class);

		return userCompany;
	}

	public List<Product> getProductList(
			String companyNumber, String searchParamString) {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		String sql = "SELECT * FROM PRODUCT WHERE STATUS = :status ";

		Map<String, String> queryMap = null;

		List<Product> productList = null;

		if( !companyNumber.equalsIgnoreCase("") ){

			sql = sql + " AND COMPANYNUMBER = :companyNumber";			

			queryMap = new HashMap<String, String>();

			queryMap.put("companyNumber", companyNumber);
			queryMap.put("status", "ACTIVE");

			productList = namedJdbcTemplate.query(sql, queryMap, new ProductMapper());

		}else{		
			
			if( !searchParamString.equalsIgnoreCase("") ) sql = sql + " AND PRODUCTNAME LIKE :searchParam";	
			
			queryMap = new HashMap<String, String>();

			queryMap.put("status", "ACTIVE");
			if( !searchParamString.equalsIgnoreCase("") ) queryMap.put("searchParam", "%" + searchParamString + "%" );

			productList = namedJdbcTemplate.query(sql, queryMap, new ProductMapper());
		}	

		return productList;
	}

	private Long getMaxOfFavouriteId(){

		JdbcTemplate jdbcTemplate = getJdbcTemplate();

		Long favId = jdbcTemplate.queryForLong("SELECT MAX(FAVID) FROM FAVOURITE");

		return favId;
	}


	public void insertFavourite(String userName, Long productId) {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		Long favId = getMaxOfFavouriteId();

		favId++;

		String sql = "INSERT INTO FAVOURITE VALUES(:favId, :productId, :userName)";

		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("favId", favId.toString());
		queryMap.put("productId", productId.toString());
		queryMap.put("userName", userName);

		namedJdbcTemplate.update(sql, queryMap);
	}


	public List<Product> getFavouriteList(String userName) {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();


		String sql = "SELECT * FROM PRODUCT WHERE PRODUCTID IN ( SELECT DISTINCT(PRODUCTID) FROM FAVOURITE WHERE USERNAME = :userName )";

		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("userName", userName);

		List<Product> productList = namedJdbcTemplate.query(sql, queryMap, new ProductMapper());	

		return productList;
	}


	public void deleteFavourite(String userName, Long productId) {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		String sql = "DELETE FROM FAVOURITE WHERE USERNAME = :userName AND PRODUCTID = :productId ";

		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("userName", userName);
		queryMap.put("productId", productId.toString());

		namedJdbcTemplate.update(sql, queryMap);

	}


	public void modifyProduct(Product prod) {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		String sql = "UPDATE PRODUCT SET PRODUCTNAME = :productName, HARD_DISK = :hardDisk, MODEL_NUMBER = :modelNumber,"
				+ "OS = :os, PROCESSOR = :processor, AVAILABILITY = :available,"
				+ "SCREEN_SIZE = :screenSize, RAM = :ram, PRICE = :price "
				+ "WHERE PRODUCTID = :productId";  

		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("productName", prod.getProductName());
		queryMap.put("hardDisk", prod.getHardDisk());
		queryMap.put("modelNumber", prod.getModelNumber());
		queryMap.put("os", prod.getOs());
		queryMap.put("processor", prod.getProcessor());
		queryMap.put("available", String.valueOf(prod.getAvailable()));
		queryMap.put("screenSize", prod.getScreenSize());
		queryMap.put("ram", prod.getRam());
		queryMap.put("price", prod.getPrice());
		queryMap.put("productId", prod.getProductId().toString());

		namedJdbcTemplate.update(sql, queryMap);

	}


	public void deleteProduct(Long productId) {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		String query = "UPDATE PRODUCT SET STATUS = :status WHERE PRODUCTID = :productId";

		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("productId", productId.toString());
		queryMap.put("status", "INACTIVE");

		namedJdbcTemplate.update(query, queryMap);

	}


	public List<Product> fetchProductDetail(List<Long> productId) {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();
		
		String sql = "SELECT * FROM PRODUCT WHERE PRODUCTID IN (:productIds)";

		StringBuilder productIdForQuery = new StringBuilder();


		for ( int i = 0; i< productId.size(); i++){

			productIdForQuery.append(productId.get(i));

			if ( i != productId.size()-1){

				productIdForQuery.append(",");

			}

		}
		
		Map<String, String> queryMap = new HashMap<String, String>();

		List<Product> prod = namedJdbcTemplate.query(sql, queryMap , new ProductMapper());

		return prod;
	}
	
	private Long getMaxOrderId(){		

		JdbcTemplate jdbcTemplate = getJdbcTemplate();

		Long orderId = jdbcTemplate.queryForLong("SELECT MAX(ORDERID) FROM ORDERS");
		
		return orderId;
	}


	public String insertOrders(OrderSpec spec, String userName) {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		Long orderId = getMaxOrderId();

		orderId++;

		String orderNumber = "OR " + orderId;

		String orderStatus = "SHIPPING";

		String shippingAddress = spec.getAddress();

		Calendar cal = Calendar.getInstance();
		Date orderDate = cal.getTime();
		cal.add(Calendar.DATE, 2);
		Date deliveryDate = cal.getTime();
		
		String sql = "INSERT INTO ORDERS VALUES(:orderId, :orderNumber, :productId, :userName, :orderDate,"
											+ " :deliveryDate, :shippingAddress, :noOfItems, :orderStatus)";
		
		Map<String, String> queryMap = new HashMap<String, String>();

		for( String l : spec.getProducts() ){
						
			updateProduct(l);
			
			queryMap.put("orderId", orderId.toString());
			queryMap.put("orderNumber", orderNumber);
			queryMap.put("productId", l.split(":")[0]);
			queryMap.put("userName", userName);
			queryMap.put("orderDate", orderDate.toString());
			queryMap.put("deliveryDate", deliveryDate.toString());
			queryMap.put("shippingAddress", shippingAddress);
			queryMap.put("noOfItems", l.split(":")[1]);
			queryMap.put("orderStatus", orderStatus);
			
			namedJdbcTemplate.update(sql, queryMap);
			
			
			orderId++;
		}

		return orderNumber;



	}


	private void updateProduct(String l) {
		
		String productId = l.split(":")[0];
		String number = l.split(":")[1];
		
		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();
		
		String sql = "UPDATE PRODUCT SET AVAILABILITY = AVAILABILITY - :number WHERE PRODUCTID = :productId";
		
		Map<String, String> queryMap = new HashMap<String, String>();
		
		queryMap.put("productId", productId);
		queryMap.put("number", number);
		
		namedJdbcTemplate.update(sql, queryMap);
		
	}

	public List<Orders> getOrders(String orderNumber) {

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();
		
		String sql = "SELECT * FROM ORDERS WHERE ORDER_NUMBER = :orderNumber";
		
		Map<String, String> queryMap = new HashMap<String, String>();
		
		queryMap.put("orderNumber", orderNumber);

		List<Orders> orders = namedJdbcTemplate.query(sql, queryMap, new OrdersMapper());

		return orders;

	}


	public Product getProductbyId(Long productId) {	

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();
		
		String sql = "SELECT * FROM PRODUCT WHERE PRODUCTID = :productId";
		
		Map<String, String> queryMap = new HashMap<String, String>();
		
		queryMap.put("productId", productId.toString());

		Product product = namedJdbcTemplate.queryForObject(sql, queryMap,new ProductMapper());

		return product;
	}
	
	
	public String cancelOrders(Long orderId) {	

		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();
		
		String sql = "UPDATE ORDERS SET ORDER_STATUS = :orderStatus where orderId = :orderId";
		
		Map<String, String> queryMap = new HashMap<String, String>();
		
		queryMap.put("orderId", orderId.toString());
		queryMap.put("orderStatus", "CANCELLED");

		int i = namedJdbcTemplate.update(sql, queryMap);

		if( i == 1 ) return "Order cancelled successfully";
		
		return "Order could not be cancelled";
		
	}

	public List<Orders> viewOrders(String companyNumber, String userName, String userType ) {
		
		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();
		
		String sql = null;
		
		Map<String, String> queryMap = new HashMap<String, String>();
		
		if( !companyNumber.equalsIgnoreCase("")  ){
			sql = "SELECT * FROM ORDERS WHERE PRODUCTID IN ( SELECT PRODUCTID FROM PRODUCT WHERE COMPANYNUMBER = :companyNumber AND STATUS = :status )";
					//+ " AND ORDER_STATUS != :orderStatus";
			queryMap.put("companyNumber", companyNumber);
			queryMap.put("status", "ACTIVE");
		}else if(  !userName.equalsIgnoreCase("") && !userType.equalsIgnoreCase("A")){
			sql = "SELECT * FROM ORDERS WHERE USERNAME = :userName";
			queryMap.put("userName", userName);
		}else{
			sql = "SELECT * FROM ORDERS";
			queryMap.put("userName", userName);
		}
		

		List<Orders> orders = namedJdbcTemplate.query(sql, queryMap, new OrdersMapper());

		return orders;
	}

	public String getCompanyNumber(String companyName) {
		
		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();
		
		String sql = "SELECT COMPANYNUMBER FROM COMPANY WHERE COMPANYNAME = :companyName";
		
		Map<String, String> queryMap = new HashMap<String, String>();
		
		queryMap.put("companyName", companyName);

		String companyNumber = namedJdbcTemplate.queryForObject(sql, queryMap, String.class);		
		
		return companyNumber;
	}
	
	public String getCompanyName(String companyNumber) {
		
		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();
		
		String sql = "SELECT COMPANYNAME FROM COMPANY WHERE COMPANYNUMBER = :companyNumber";
		
		Map<String, String> queryMap = new HashMap<String, String>();
		
		queryMap.put("companyNumber", companyNumber);

		String companyName = namedJdbcTemplate.queryForObject(sql, queryMap, String.class);		
		
		return companyName;
	}

	public String getUserType(String userName) {
		
		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		String sql = "SELECT USERTYPE FROM USERS WHERE USERNAME = :userName";

		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("userName", userName);

		String userType = namedJdbcTemplate.queryForObject(sql, queryMap, String.class);

		return userType;
	}

	public Users getUserDetails(String userName) {
		
		NamedParameterJdbcTemplate namedJdbcTemplate = getNamedJdbcTemplate();

		String sql = "SELECT * FROM USERS WHERE USERNAME = :userName";

		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("userName", userName);

		Users user = namedJdbcTemplate.queryForObject(sql, queryMap, new UserMapper());

		return user;
	}


}

