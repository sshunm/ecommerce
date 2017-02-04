package com.srh.ecommerce.model;

public class Product {
	
	private Long productId;
	private String productName;
	private String modelNumber;
	private String os;
	private String ram;
	private String hardDisk;
	private String processor;
	private String screenSize;
	private int available;
	private String price;
	private String status;
	private String companyNumber;
	
	
	
	public Long getProductId() {
		return productId;
	}
	public String getProductName() {
		return productName;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public String getOs() {
		return os;
	}
	public String getHardDisk() {
		return hardDisk;
	}
	public String getProcessor() {
		return processor;
	}
	public String getScreenSize() {
		return screenSize;
	}
	
	public String getCompanyNumber() {
		return companyNumber;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public void setHardDisk(String hardDisk) {
		this.hardDisk = hardDisk;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}
	
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	public String getRam() {
		return ram;
	}
	public void setRam(String ram) {
		this.ram = ram;
	}
	public String getPrice() {
		return price;
	}
	public String getStatus() {
		return status;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	

}
