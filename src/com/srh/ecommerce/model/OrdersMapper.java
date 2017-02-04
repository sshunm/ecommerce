package com.srh.ecommerce.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class OrdersMapper implements RowMapper<Orders> {

	@Override
	public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Orders orders = new Orders();
		
		orders.setOrderId(rs.getLong("ORDERID"));
		orders.setOrderNumber(rs.getString("ORDER_NUMBER"));
		orders.setProductId(rs.getLong("PRODUCTID"));
		orders.setUserName(rs.getString("USERNAME"));
		orders.setOrderDate(rs.getString("ORDER_DATE"));
		orders.setDeliveryDate(rs.getString("DELIVERY_DATE"));
		orders.setShippingAddress(rs.getString("SHIPPING_ADDRESS"));
		orders.setNumberOfItems(rs.getInt("NUMBER_OF_ITEMS"));
		orders.setOrderStatus(rs.getString("ORDER_STATUS"));
		
		return orders;
	}

}
