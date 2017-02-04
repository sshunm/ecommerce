package com.srh.ecommerce.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FavouriteMapper  implements RowMapper<Favourite>{

	@Override
	public Favourite mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Favourite fav = new Favourite();
		fav.setFavouriteId(rs.getLong("FAVID"));
		fav.setProductId(rs.getLong("PRODUCTID"));
		fav.setUserName(rs.getString("USERNAME"));
		
		return fav;
	}

}
