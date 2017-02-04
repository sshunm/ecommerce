package com.srh.ecommerce.model;




public class Orders {
	
	private Long orderId;
	
	private Long productId;
	
	private String orderNumber;
	
	private String userName;
	
	private String orderDate;
	
	private String deliveryDate;
	
	private String shippingAddress;
	
	private int numberOfItems;
	
	private String orderStatus;
	
	private String price;
	
	private String productName;
	
	public Long getOrderId() {
		return orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public String getUserName() {
		return userName;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	
}
