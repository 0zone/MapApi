package com.financial.mapapi.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.financial.mapapi.bean.StaticValue;

public class BaiduAPI extends MapAPI{
	
	private final String baiduUrl = "http://api.map.baidu.com/place/v2/search";
	private final String baiduGeocodingUrl = "http://api.map.baidu.com/geocoder/v2/";
	private final String baiduAK ="zDPXFGhyce6U8SPBzS7Fomv5";	
								//"OhYSe2efxLF8aFy2IZAk7UGp";	
								//"XLU5GufCOu99k05fHqiEaPy3";
								//"22b9a75643e6ecbf30bbf0d6fe8c5129";
	private final String outputType = "json";
	//private final String pageSize = "20";
	private final String pageNum = "0";
	private final String scope = "2";
	private final String region = "北京";
	
	public static List<String> baiduAKList = new ArrayList<String>();
	
	private final String baiduBrandUrl = "http://map.baidu.com/";

	
	private final String BAIDU_TABLE_NAME = " baiduplaceapi_icbc_sh ";
	
	//初始化 基类MapAPI的url、参数、表名
	//初始化变量后调用基类的WebServiceAPI
	public void BaiduPlaceAPI() {
		this.mapUrl = baiduUrl;
		this.tableName = BAIDU_TABLE_NAME;
		
		this.mapParamsList.add(new BasicNameValuePair("ak", baiduAK));
		this.mapParamsList.add(new BasicNameValuePair("output", outputType));	
		this.mapParamsList.add(new BasicNameValuePair("page_size", StaticValue.pageSize));
		this.mapParamsList.add(new BasicNameValuePair("scope", scope));	
		this.mapParamsList.add(new BasicNameValuePair("page_num", pageNum));	
		this.mapParamsList.add(new BasicNameValuePair("region", region));
		
		baiduAKList.add("zDPXFGhyce6U8SPBzS7Fomv5");
		
		
		//this.mapParamsList.remove(0);
		
		this.WebServiceAPI();
	}
	
	public void BaiduGeocodingAPI() {
		this.mapUrl = baiduGeocodingUrl;
		this.tableName = BAIDU_TABLE_NAME;

		this.mapParamsList.add(new BasicNameValuePair("ak", baiduAK));
		this.mapParamsList.add(new BasicNameValuePair("callback", ""));
		this.mapParamsList.add(new BasicNameValuePair("output", outputType));	
		this.mapParamsList.add(new BasicNameValuePair("city", region));
		
		
		this.WebServiceAPI();
	}
	
	//品牌列表API
	public void BaiduBrandAPI() {
		this.mapUrl = baiduBrandUrl;
		this.tableName = " brand ";
		
		this.mapParamsList.add(new BasicNameValuePair("newmap", "1"));
		this.mapParamsList.add(new BasicNameValuePair("reqflag", "pcmap"));
		this.mapParamsList.add(new BasicNameValuePair("biz", "1"));
		this.mapParamsList.add(new BasicNameValuePair("from", "webmap"));
		this.mapParamsList.add(new BasicNameValuePair("qt", "s"));
		this.mapParamsList.add(new BasicNameValuePair("da_src", "pcmappg.searchBox.button"));
		this.mapParamsList.add(new BasicNameValuePair("c", "1"));
		this.mapParamsList.add(new BasicNameValuePair("src", "0"));
		this.mapParamsList.add(new BasicNameValuePair("wd2", ""));
		this.mapParamsList.add(new BasicNameValuePair("sug", "0"));
		this.mapParamsList.add(new BasicNameValuePair("l", "5"));
		this.mapParamsList.add(new BasicNameValuePair("b", "(7862611.220000001,4423515.88;16333139.22,6881115.88)"));
		this.mapParamsList.add(new BasicNameValuePair("from", "webmap"));
		this.mapParamsList.add(new BasicNameValuePair("tn", "B_NORMAL_MAP"));
		this.mapParamsList.add(new BasicNameValuePair("nn", "0"));
		this.mapParamsList.add(new BasicNameValuePair("ie", "utf-8"));
		this.mapParamsList.add(new BasicNameValuePair("t", "1404964015063"));
		
	
		this.WebServiceAPI();
	}
	
	//品牌地图API
	public void BaiduBrandMapAPI() {
		this.mapUrl = baiduUrl;
		this.tableName = " brandmap ";
		
		this.mapParamsList.add(new BasicNameValuePair("ak", baiduAK));
		this.mapParamsList.add(new BasicNameValuePair("output", outputType));	
		this.mapParamsList.add(new BasicNameValuePair("page_size", StaticValue.pageSize));
		this.mapParamsList.add(new BasicNameValuePair("scope", scope));	
		this.mapParamsList.add(new BasicNameValuePair("query", ""));//4
		this.mapParamsList.add(new BasicNameValuePair("page_num", pageNum));	
		this.mapParamsList.add(new BasicNameValuePair("region", region));
		
		baiduAKList.add("zDPXFGhyce6U8SPBzS7Fomv5");
		baiduAKList.add("OhYSe2efxLF8aFy2IZAk7UGp");
		baiduAKList.add("XLU5GufCOu99k05fHqiEaPy3");
		baiduAKList.add("22b9a75643e6ecbf30bbf0d6fe8c5129");
		
		this.BrandMapAPI();
	}
	
	
}
