package com.financial.mapapi.app;

import org.apache.http.message.BasicNameValuePair;


public class AmapAPI extends MapAPI{
	private final String amapUrl = "http://restapi.amap.com/v3/place/text";;
	private final String s = "rsv3";
	private final String key = "cdd2b67444ff6b421e43f7707fd61f15";
	private final String offset = "50";
	private final String page = "1";
	private final String city = "±±¾©";
	
	private final String AMAP_TABLE_NAME = " amapapi_icbc_sh ";
	
	public void AmapKeyWordsAPI(){
		this.mapUrl = amapUrl;
		this.tableName = AMAP_TABLE_NAME;
		
		this.mapParamsList.add(new BasicNameValuePair("s", s));
		this.mapParamsList.add(new BasicNameValuePair("key", key));	
		this.mapParamsList.add(new BasicNameValuePair("offset", offset));	
		this.mapParamsList.add(new BasicNameValuePair("page", page));	
		this.mapParamsList.add(new BasicNameValuePair("city", city));	
		
		this.WebServiceAPI();
	}
	

}
