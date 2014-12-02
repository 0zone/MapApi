/**
 * 
 */
package com.financial.mapapi.bean;

/**
 * @author user
 *
 */
public class BrandBean {
	private int brandId;
	private String brandName;
	private String brandCitys;
	public BrandBean() {
		brandCitys="";
	}
	
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandCitys() {
		return brandCitys;
	}
	public void setBrandCitys(String brandCitys) {
		this.brandCitys = brandCitys;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
}
