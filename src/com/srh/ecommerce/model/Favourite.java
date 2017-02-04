package com.srh.ecommerce.model;

public class Favourite {
	
	private Long favouriteId;
	
	private String userName;
	
	private Long productId;

	public Long getFavouriteId() {
		return favouriteId;
	}

	public String getUserName() {
		return userName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setFavouriteId(Long favouriteId) {
		this.favouriteId = favouriteId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	

	}
