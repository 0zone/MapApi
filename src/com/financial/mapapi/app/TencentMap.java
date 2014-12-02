package com.financial.mapapi.app;

import org.apache.http.message.BasicNameValuePair;

public class TencentMap extends MapAPI{
	private final String tecentUrl = "http://apis.map.qq.com/ws/place/v1/search";
	
	private final String output = "json";
	private final String key = "HMNBZ-Q6WRP-A5SDP-V6ESS-MYJUJ-QRFR4";
	private final String page_size = "50";
	private final String boundary = "region(±±¾©,0)";
	
	private final String TENCENT_TABLE_NAME = " tencentmapapi_icbc_sh ";
	
	public void TencentKeyWordsMap(){
		this.mapUrl = tecentUrl;
		this.tableName = TENCENT_TABLE_NAME;
		
		this.mapParamsList.add(new BasicNameValuePair("key", key));	
		this.mapParamsList.add(new BasicNameValuePair("page_size", page_size));	
		this.mapParamsList.add(new BasicNameValuePair("output", output));	
		this.mapParamsList.add(new BasicNameValuePair("boundary", boundary));
		
		this.WebServiceAPI();
	}
}
