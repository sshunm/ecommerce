package com.srh.ecommerce.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<Users> {


	public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
		Users user = new Users();
		user.setUserId(rs.getLong("USERID"));
		user.setUserName(rs.getString("USERNAME"));
		user.setAddressLine(rs.getString("ADDRESSLINE"));
		user.setTelephone(rs.getString("TELEPHONE"));
		user.setCity(rs.getString("CITY"));
		user.setCountry(rs.getString("COUNTRY"));
		user.setEmail(rs.getString("EMAIL"));
		user.setFirstName(rs.getString("FIRSTNAME"));
		user.setLastName(rs.getString("LASTNAME"));
		user.setState(rs.getString("USERSTATE"));
		user.setZipCode(rs.getString("ZIPCODE"));
		user.setUserType(rs.getString("USERTYPE"));
		user.setStatus(rs.getString("STATUS"));
		user.setCompanyNumber(rs.getString("COMPANYNUMBER"));
		return user;
	}
}