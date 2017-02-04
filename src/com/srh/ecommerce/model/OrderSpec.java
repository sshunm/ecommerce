package com.srh.ecommerce.model;

import java.util.List;

public class OrderSpec {
	
	private List<String> products;
	
	private String address;
	
	private String phoneNumber;
	
	private String paymentType;

	public List<String> getProducts() {
		return products;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	
	
	

}
