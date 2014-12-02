/**
 * 
 */
package com.financial.mapapi.bean;

/**
 * @author user
 *
 */
public class BrandMapBean {
	private Integer id;
	private Integer brandId;
	private String brandName;
	private String merchantUid;
	private String merchantName;
	private String merchantCity;
	private String merchantProvince;
	private String merchantAddress;
	private String merchantLocation;
	private String merchantType;
	public BrandMapBean() {
		this.brandId = 0;
		this.brandName = "";
		this.merchantUid = "";
		this.merchantName = "";
		this.merchantCity = "";
		this.merchantProvince = "";
		this.merchantAddress = "";
		this.merchantLocation = "";
		this.merchantType = "";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getMerchantUid() {
		return merchantUid;
	}
	public void setMerchantUid(String merchantUid) {
		this.merchantUid = merchantUid;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantCity() {
		return merchantCity;
	}
	public void setMerchantCity(String merchantCity) {
		this.merchantCity = merchantCity;
	}
	public String getMerchantProvince() {
		return merchantProvince;
	}
	public void setMerchantProvince(String merchantProvince) {
		this.merchantProvince = merchantProvince;
	}
	public String getMerchantAddress() {
		return merchantAddress;
	}
	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}
	public String getMerchantLocation() {
		return merchantLocation;
	}
	public void setMerchantLocation(String merchantLocation) {
		this.merchantLocation = merchantLocation;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

}
