package com.srh.ecommerce.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserDtoMapper  implements RowMapper<UserDto>{

	@Override
	public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		UserDto user = new UserDto();
		user.setUserName(rs.getString("USERNAME"));
		user.setUserPassword(rs.getString("USERPASSWORD"));
		
		return user;
	}
	

}
