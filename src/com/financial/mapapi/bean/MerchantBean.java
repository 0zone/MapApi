package com.financial.mapapi.bean;

public class MerchantBean {
	private Integer id;
	private String originalName;
	private String matchedName;
	private String type;
	private String address;
	private String location;
	private String telephone;
	private String responseData;
	
	public  MerchantBean() {
		this.matchedName = "";
		this.type = "";
		this.address = "";
		this.location = "";
		this.telephone = "";
		this.responseData = "";
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getMatchedName() {
		return matchedName;
	}
	public void setMatchedName(String matchedName) {
		this.matchedName = matchedName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getResponseData() {
		return responseData;
	}
	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}
	
}
