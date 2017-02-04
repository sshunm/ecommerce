package com.srh.ecommerce.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProductMapper   implements RowMapper<Product>{

	@Override
	public Product mapRow(ResultSet rs
			, int rowNum) throws SQLException {
		
		Product prod = new Product();

		prod.setProductId(rs.getLong("PRODUCTID"));
		prod.setProductName(rs.getString("PRODUCTNAME"));
		prod.setCompanyNumber(rs.getString("COMPANYNUMBER"));
		prod.setHardDisk(rs.getString("HARD_DISK"));
		prod.setModelNumber(rs.getString("MODEL_NUMBER"));
		prod.setOs(rs.getString("OS"));
		prod.setProcessor(rs.getString("PROCESSOR"));
		prod.setAvailable(rs.getInt("AVAILABILITY"));
		prod.setScreenSize(rs.getString("SCREEN_SIZE"));
		prod.setRam(rs.getString("RAM"));
		prod.setPrice(rs.getString("PRICE"));
		prod.setStatus("STATUS");
		
		return prod;
	}

}
