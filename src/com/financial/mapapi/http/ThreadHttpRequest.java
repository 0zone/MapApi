package com.financial.mapapi.http;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.financial.mapapi.app.BaiduAPI;
import com.financial.mapapi.bean.BrandBean;
import com.financial.mapapi.bean.BrandMapBean;
import com.financial.mapapi.bean.MerchantBean;
import com.financial.mapapi.bean.StaticValue;
import com.financial.mapapi.dao.MapDao;
import com.financial.mapapi.tools.HandleData;

public class ThreadHttpRequest extends Thread {
	
	
	private final String httpurl;
	private List<NameValuePair> params = new ArrayList<NameValuePair>();
	private String apiName;
	private String tableName;
	private final int threadHandleDataBegine;
	private final int threadHandleDataEnd;
	private List<String> baiduAKList = new ArrayList<String>();
	
	/**
	 * ThreadHttpRequest
	 * @param begineId		当前线程处理数据开始id
	 * @param endId			当前线程处理数据
	 * @param mapUrl		API	url
	 * @param paramsList	API	参数
	 * @return
	 */
	public ThreadHttpRequest(int begineId, int endId, String mapUrl, List<NameValuePair> paramsList) {
		threadHandleDataBegine = begineId;
		threadHandleDataEnd = endId;
		httpurl = mapUrl;
		params = paramsList;
		if("http://api.map.baidu.com/place/v2/search".equals(httpurl)){
			apiName = "baidu";
			tableName = " baiduplaceapi_icbc_sh ";
		}else if ("http://restapi.amap.com/v3/place/text".equals(httpurl)) {
			apiName = "amap";
			tableName = " amapapi_icbc_sh ";
		}else if ("http://apis.map.qq.com/ws/place/v1/search".equals(httpurl)){
			apiName = "tencent";
			tableName = " tencentmapapi_icbc_sh ";
		}else if ("http://api.map.baidu.com/geocoder/v2/".equals(httpurl)){
			apiName = "geocoder";
			tableName = " xizhi_icbc_sh ";
		}else if ("http://map.baidu.com/".equals(httpurl)){
			apiName = "baidubrand";
			tableName = " brand ";
		}
		baiduAKList.add("zDPXFGhyce6U8SPBzS7Fomv5");
		baiduAKList.add("OhYSe2efxLF8aFy2IZAk7UGp");
		baiduAKList.add("XLU5GufCOu99k05fHqiEaPy3");
		baiduAKList.add("22b9a75643e6ecbf30bbf0d6fe8c5129");
	
	}
	@Override
	public void run() {
		requestMerchantData();
		//RequestBrandCitysData();
		//requestBrandMapData();
		
		super.run();
	}
	
	private void requestMerchantData() {
		ArrayList<MerchantBean> merchantBeans = new ArrayList<MerchantBean>();
		merchantBeans = MapDao.queryMerchantListById(tableName, threadHandleDataBegine, threadHandleDataEnd);
		
		Connection connection = MapDao.getConnection();
		HttpRequest httpRequest = new HttpRequest();
		
		System.out.println(getName()+"开始执行:" + "处理" + threadHandleDataBegine + "-----" + threadHandleDataEnd + "-----" + merchantBeans.size());
		
		for (int i=0; i<merchantBeans.size();i++) {
			MerchantBean merchantBean = new MerchantBean();
			merchantBean = merchantBeans.get(i);
			if("baidu".equals(apiName)){
				params.add(new BasicNameValuePair("query", merchantBean.getOriginalName()));
			}else if ("amap".equals(apiName)) {
				params.add(new BasicNameValuePair("keywords", merchantBean.getOriginalName()));
			}else if("tencent".equals(apiName)){
				params.add(new BasicNameValuePair("keyword", merchantBean.getOriginalName()));
			}else if ("geocoder".equals(apiName)) {
				params.add(new BasicNameValuePair("address", merchantBean.getAddress()));
			}
			
			
			String responseString = null;
			MerchantBean merchant = new MerchantBean();
			//执行HttpRequest的get方法
			responseString = httpRequest.get(httpurl, params);
			
			String responseStatus = HandleData.getResponseStatus(responseString);
			if ( responseStatus.startsWith("3") ){
				if ( !this.baiduAKList.isEmpty() ) {//更换密钥
					params.set(0, new BasicNameValuePair("ak", this.baiduAKList.get(0)));
					System.out.println(getName()+"于"+merchantBean.getId()+"更换密钥为:"+this.baiduAKList.get(0));
					this.baiduAKList.remove(0);
					i=i-1;
					continue;
				}else {
					System.out.println(getName()+"请求次数用完，终止于"+merchantBean.getId());
					System.exit(0);
					return;
				}
				
			}
			
			//处理数据 
			if("baidu".equals(apiName)){
				merchant = HandleData.handlePlaceData(responseString);
			}else if ("amap".equals(apiName)) {
				merchant = HandleData.handleAmapData(responseString);
			}else if("tencent".equals(apiName)){
				merchant = HandleData.handleTencentData(responseString);
			}else if ("geocoder".equals(apiName)) {
				merchant = HandleData.handleGeocodingData(responseString);
			}
			merchant.setId(merchantBean.getId());	
			//存储数据
			MapDao.saveMerchant(tableName, connection, merchant);
			
			
			
			//remove  params参数
			if("baidu".equals(apiName)){
				params.remove(6);
			}else if ("amap".equals(apiName)) {
				params.remove(5);
			}else if("tencent".equals(apiName)){
				params.remove(4);
			}else if ("geocoder".equals(apiName)) {
				params.remove(4);
			}
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(getName()+"执行完毕");
	}
	
	//品牌地图
	private void requestBrandMapData() {
		
		ArrayList<BrandBean> brandBeans = new ArrayList<BrandBean>();
		brandBeans = MapDao.queryBrandListById(" brand ", threadHandleDataBegine, threadHandleDataEnd);
		
		Connection connection = MapDao.getConnection();
		HttpRequest httpRequest = new HttpRequest();
		
		//System.out.println(getName()+"开始执行:" + "处理" + threadHandleDataBegine + "-----" + threadHandleDataEnd + "-----" + brandBeans.size());
		
		for (BrandBean brandBean : brandBeans) {
			
			String brandName = brandBean.getBrandName();
			String brandCitys = brandBean.getBrandCitys();
			
			String[] brandNameArrary = brandName.split("/");
			String[] brandCityArrary = brandCitys.split("/");
			
			for (int i = 0; i < brandNameArrary.length; i++) {//循环品牌名
				params.set(4, new BasicNameValuePair("query", brandNameArrary[i]));//4
				
				if (i>brandCityArrary.length-1) {
					break;//如果城市列表较小
				}
				
				//循环当前品牌城市
				String[] brandNameCity = brandCityArrary[i].split(",");
				for (int j=0; j<brandNameCity.length; j++) {
					if ("".equals(brandNameCity[j])) {
						break;
					}
					params.set(5, new BasicNameValuePair( "region", brandNameCity[j]) );//5
					params.set(6, new BasicNameValuePair( "page_num", "0") );//String.valueOf(nowPage))//6 
					
					String responseString = null;
					//执行HttpRequest的get方法
					responseString = httpRequest.get(httpurl, params);
					
					//获取页数
					int totalPage = 0;
					try {
						if (responseString!=null && !"".equals(responseString)) {
							JSONObject object = new JSONObject(responseString);
							if ( "0".equals(object.getString("status")) ){
								if (object.has("total")) {
									int totalMerchantNum = object.getInt("total");//商户总数
									totalPage = totalMerchantNum / Integer.parseInt(StaticValue.pageSize);
								}else {
									continue;
								}
							}else if ( object.getString("status").startsWith("3") ){
								if ( !this.baiduAKList.isEmpty() ) {//更换密钥
									params.set(0, new BasicNameValuePair("ak", this.baiduAKList.get(0)));
									System.out.println("于"+brandBean.getBrandId()+"更换密钥为:"+this.baiduAKList.get(0));
									this.baiduAKList.remove(0);
									j=0;
									continue;
								}else {
									System.out.println("请求次数用完，终止于"+brandBean.getBrandId());
									System.exit(0);
									return;
								}
								
							}
						}else {
							continue;
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//循环页数
					for (int nowPage = 0; nowPage <= totalPage; nowPage++) {
						params.set(6, new BasicNameValuePair( "page_num", String.valueOf(nowPage) ));
						
						
						ArrayList<BrandMapBean> brandMapBeanList = new ArrayList<BrandMapBean>();
						responseString = httpRequest.get(httpurl, params);
						
						brandMapBeanList = HandleData.handleBrandPlaceData(responseString);
						
						for (BrandMapBean brandMapBean : brandMapBeanList) {
							brandMapBean.setBrandId(brandBean.getBrandId());
							brandMapBean.setBrandName(brandBean.getBrandName());
							brandMapBean.setMerchantCity(brandNameCity[j]);
						}
						MapDao.saveBrandMapList(" brandmap ", connection, brandMapBeanList);
						
						
					}
					
				}
			}			

		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(threadHandleDataBegine+"获取完毕");
	}
	private void requestBrandCitysData() {
		
		ArrayList<BrandBean> brandBeans = new ArrayList<BrandBean>();
		brandBeans = MapDao.queryBrandListById(tableName, threadHandleDataBegine, threadHandleDataEnd);
		
		Connection connection = MapDao.getConnection();
		HttpRequest httpRequest = new HttpRequest();
		
		System.out.println(getName()+"开始执行:" + "处理" + threadHandleDataBegine + "-----" + threadHandleDataEnd + "-----" + brandBeans.size());
		
		for (BrandBean brandBean : brandBeans) {
			
			String brandName = brandBean.getBrandName();
			
			String[] brandNameArrary = brandName.split("/");
			String brandCitys = "";
			for (int i = 0; i < brandNameArrary.length; i++) {
				params.add(new BasicNameValuePair("wd", brandNameArrary[i]));
				
				String responseString = null;
				
				//执行HttpRequest的get方法
				responseString = httpRequest.get(httpurl, params);
				//处理数据 
				if ( i==0 ) {
					brandCitys = HandleData.handleBaiduBrandData(responseString);
				}else {
					brandCitys = brandCitys + "/" + HandleData.handleBaiduBrandData(responseString);
				}

				//remove  params参数
				params.remove(17);
			}			
			//存储数据
			brandBean.setBrandCitys(brandCitys);	
			MapDao.saveBrandCity(tableName, connection, brandBean);
	
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(getName()+"执行完毕");
	}
	
	
	
	
}
