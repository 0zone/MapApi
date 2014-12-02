package com.financial.mapapi.tools;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.financial.mapapi.bean.BrandMapBean;
import com.financial.mapapi.bean.MerchantBean;
import com.financial.mapapi.bean.StaticValue;

/**
 * @author user
 *
 */
public class HandleData {

	
	
	public static String initialData(String data) {
		// TODO Auto-generated method stub

		if (data!=null && !("".equals(data)) ){
			Pattern pattern = Pattern.compile("\\s|([^a-zA-Z0-9\\u4e00-\\u9fa5\\s()\\[\\]（）])");
			Matcher matcher = pattern.matcher(data);
			String handleString = matcher.replaceAll("");
			
			return handleString;
		}
		else {
			return data; 
		}
			 
	}
	public static String getResponseStatus(String responseData) {
		String status="";
		try {
			if (responseData!=null && !"".equals(responseData)) {
				JSONObject object = new JSONObject(responseData);
				status = object.getString("status");
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	public static MerchantBean handleGeoData(String merchantStr, String data){
		

		MerchantBean merchantBean = new MerchantBean();
		merchantBean.setMatchedName(merchantStr);
		
		try {
			JSONObject object = new JSONObject(data);
			if ( "0".equals(object.getString("status")) ){
				JSONObject resultObject = object.getJSONObject("result");

				merchantBean.setType(resultObject.getString("level"));
				merchantBean.setLocation(resultObject.getJSONObject("location").getString("lng")
										+","
										+resultObject.getJSONObject("location").getString("lat"));
				merchantBean.setAddress("");
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return merchantBean;//若status不为0则返回默认值
		
		
	}
	
	public static MerchantBean handlePlaceData(String reponseData){
		

		MerchantBean merchantBean = new MerchantBean();
		

		try {
			if (reponseData != null) {
				JSONObject object = new JSONObject(reponseData);
				if ( "0".equals(object.getString("status")) ){
					merchantBean.setResponseData(reponseData);
				}
				if ( ("0".equals(object.getString("status"))) && (object.getInt("total") >= 1) ){
					JSONObject resultsObject = object.getJSONArray("results").getJSONObject(0);
					
					
					if (resultsObject.has("name")) {
						merchantBean.setMatchedName(resultsObject.getString("name"));
					}
					
					if (resultsObject.has("detail_info")) {
						if (resultsObject.getJSONObject("detail_info").has("type")) 
							merchantBean.setType(resultsObject.getJSONObject("detail_info").getString("type"));
						/*if (resultsObject.getJSONObject("detail_info").has("detail_url")) 
							merchantBean.setPlaceDetailUrl(resultsObject.getJSONObject("detail_info").getString("detail_url"));
						if (resultsObject.getJSONObject("detail_info").has("tag")) 
							merchantBean.setPlaceTag(resultsObject.getJSONObject("detail_info").getString("tag"));*/
						
					}
					if (resultsObject.has("address")){ 
						merchantBean.setAddress(resultsObject.getString("address"));
					}
					if (resultsObject.has("location")) {
						merchantBean.setLocation(resultsObject.getJSONObject("location").getString("lng")
												+","
												+resultsObject.getJSONObject("location").getString("lat"));
					}
					
					if (resultsObject.has("telephone")){ 
						merchantBean.setTelephone(resultsObject.getString("telephone"));
					}
					
				}
				
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return merchantBean;//若status不为0则返回默认值
		
		
	}
	
	public static MerchantBean handleGeocodingData(String reponseData){
		

		MerchantBean merchantBean = new MerchantBean();
		
		try {
			if (reponseData != null) {
				JSONObject object = new JSONObject(reponseData);
				/*if ( "0".equals(object.getString("status")) ){
					merchantBean.setResponseData(reponseData);
				}*/
				if ( ("0".equals(object.getString("status"))) ){
					if (object.has("result")) {
						JSONObject resultObject = object.getJSONObject("result");

						if (resultObject.has("location")) {
							merchantBean.setLocation(resultObject.getJSONObject("location").getString("lng")
													+","
													+resultObject.getJSONObject("location").getString("lat"));
						}
					}
					
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return merchantBean;//若status不为0则返回默认值
		
		
	}
	
	public static MerchantBean handleAmapData(String reponseData){
		MerchantBean merchantBean = new MerchantBean();
		

		try {
			if (reponseData != null) {
				JSONObject object = new JSONObject(reponseData);
				if ( "1".equals(object.getString("status")) ){
					merchantBean.setResponseData(reponseData);
				}
	
				if ( ("1".equals(object.getString("status"))) && (object.getInt("count") >= 1) ){
					JSONObject resultsObject = object.getJSONArray("pois").getJSONObject(0);
					
					
					if (resultsObject.has("name")) 
						merchantBean.setMatchedName(resultsObject.getString("name"));
					
					if (resultsObject.has("type")) 
						merchantBean.setType(resultsObject.getString("type"));
					
					if (resultsObject.has("location")) {
						merchantBean.setLocation(resultsObject.getString("location"));
					}
					if (resultsObject.has("address")) 
						merchantBean.setAddress(resultsObject.getString("address"));
					
					if (resultsObject.has("tel")) 
						merchantBean.setTelephone(resultsObject.getString("tel"));
					
					
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return merchantBean;//若status不为0则返回默认值
		
		
	}
	public static MerchantBean handleTencentData(String reponseData){
		MerchantBean merchantBean = new MerchantBean();
		
		try {
			if (reponseData != null) {
				JSONObject object = new JSONObject(reponseData);
				if ( "0".equals(object.getString("status")) ){
					merchantBean.setResponseData(reponseData);
				}
	
				if ( ("0".equals(object.getString("status"))) && (object.getInt("count") >= 1) ){
					JSONObject resultsObject = object.getJSONArray("data").getJSONObject(0);
					
					
					if (resultsObject.has("title")) 
						merchantBean.setMatchedName(resultsObject.getString("title"));
					
					if (resultsObject.has("category")) 
						merchantBean.setType(resultsObject.getString("category"));
					
					if (resultsObject.has("location")) {
						merchantBean.setLocation(resultsObject.getJSONObject("location").getString("lng")
												+","
												+resultsObject.getJSONObject("location").getString("lat"));
					}
					
					if (resultsObject.has("address")) 
						merchantBean.setAddress(resultsObject.getString("address"));
					
					if (resultsObject.has("tel")) 
						merchantBean.setTelephone(resultsObject.getString("tel"));
					
					
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return merchantBean;//若status不为0则返回默认值
		
		
	}
	public static MerchantBean CombineData(MerchantBean sourceMerchant, MerchantBean destmeMerchant) {
		
		if ( "".equals(destmeMerchant.getMatchedName()) ) {
			destmeMerchant.setMatchedName( sourceMerchant.getMatchedName() );
		}
		
		if ( "".equals(destmeMerchant.getType()) ) {
			destmeMerchant.setType( sourceMerchant.getType() );
		}
		if ( "".equals(destmeMerchant.getAddress()) ) {
			destmeMerchant.setAddress( sourceMerchant.getAddress() );
		}
		if ( "".equals(destmeMerchant.getTelephone()) ) {
			destmeMerchant.setTelephone( sourceMerchant.getTelephone() );
		}
		
		return destmeMerchant;
		
	}
	
	public static String handleBaiduBrandData(String reponseData){
		//BrandBean brandBean = new BrandBean();
		String cityList = "";
		try {
			if (reponseData != null) {
				JSONObject object = new JSONObject(reponseData);
	
				if ( object.has("more_city") ){
					
					if (object.has("content")) {
						JSONArray jsonArrayContent = object.getJSONArray("content");
						for (int i=0; i<jsonArrayContent.length(); i++) {
							
							if (jsonArrayContent.getJSONObject(i).has("name")) {
								
								//cityList = cityList + HandleData.citysMap.get( (jsonArrayContent.getJSONObject(i).getString("name")) ) + ",";
								cityList = cityList + jsonArrayContent.getJSONObject(i).getString("name") + ",";
								
							}
						}
					}
					
					
					
					JSONArray jsonArrayMoreCity = object.getJSONArray("more_city");
					
					for (int i=0; i<jsonArrayMoreCity.length(); i++) {
						if ( jsonArrayMoreCity.getJSONObject(i).has("city") ){
							JSONArray jsonArrayCity = new JSONArray();
							jsonArrayCity = jsonArrayMoreCity.getJSONObject(i).getJSONArray("city");
							for (int j=0; j<jsonArrayCity.length(); j++){
								if ( jsonArrayCity.getJSONObject(j).has("name") ){
									cityList = cityList + jsonArrayCity.getJSONObject(j).get("name") + ",";
								}
							}
						}
						
					}// for jsonArrayMoreCity
					if (cityList.indexOf(",")!=-1) {
						cityList = cityList.substring(0, cityList.lastIndexOf(","));
					}
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cityList;//若status不为0则返回默认值
		
		
	}
	public static ArrayList<BrandMapBean> handleBrandPlaceData(String reponseData){
		

		ArrayList<BrandMapBean> brandMapBeanList = new ArrayList<BrandMapBean>();
		try {
			if (reponseData != null && !"".equals(reponseData)) {
				JSONObject object = new JSONObject(reponseData);
				
				if ( "0".equals(object.getString("status")) ){
					if (object.has("results")) {
						JSONArray jsonArrayResults = object.getJSONArray("results");
						
						for (int i=0; i<jsonArrayResults.length(); i++) {
							BrandMapBean brandMapBean = new BrandMapBean();
							if (jsonArrayResults.getJSONObject(i).has("uid")) {
								brandMapBean.setMerchantUid(jsonArrayResults.getJSONObject(i).getString("uid"));
							}
							if (jsonArrayResults.getJSONObject(i).has("name")) {
								brandMapBean.setMerchantName(jsonArrayResults.getJSONObject(i).getString("name"));
							}
							if (jsonArrayResults.getJSONObject(i).has("address")) {
								brandMapBean.setMerchantAddress(jsonArrayResults.getJSONObject(i).getString("address"));
							}
							if (jsonArrayResults.getJSONObject(i).has("location")) {
								brandMapBean.setMerchantLocation(jsonArrayResults.getJSONObject(i).getJSONObject("location").getString("lng")
																+","
																+jsonArrayResults.getJSONObject(i).getJSONObject("location").getString("lat"));
							}
							if (jsonArrayResults.getJSONObject(i).has("detail_info")) {
								if (jsonArrayResults.getJSONObject(i).getJSONObject("detail_info").has("type")) {
									brandMapBean.setMerchantType(jsonArrayResults.getJSONObject(i).getJSONObject("detail_info").getString("type"));
								}
								
							}
							
							brandMapBeanList.add(brandMapBean);
							
						}
					}
				
					
				}
				
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JSONException"+"原始数据"+reponseData);
		}
		return brandMapBeanList;
		
		
	}
	
	
	
	public static <E> Boolean statisticData(ArrayList<E> arrayListBeans) {
		HashMap<String, Integer> listMap = new HashMap<String, Integer>();
		String type = null;
		int count = 0;
		System.out.println("~~开始统计~~");
		
		for (int i = 0; i < arrayListBeans.size(); i++) {
			type = ((BrandMapBean) arrayListBeans.get(i)).getMerchantType();
			if ( listMap.get(type) == null ) {
				listMap.put(type, 1);
			}else {
				listMap.put(type, listMap.get(type).intValue()+1);
			}
		}
		
		Set<String> set = listMap.keySet();
		
		for (String string : set) {
			System.out.println(string + ":" + listMap.get(string));
			count = count + listMap.get(string);
		}
		
		System.out.println("总计:"+count);
		System.out.println("~~统计完成~~");
		return true;
		
	}
	
	
}
