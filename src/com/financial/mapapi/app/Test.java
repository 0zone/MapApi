package com.financial.mapapi.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.NameValuePair;

import com.financial.mapapi.bean.BrandMapBean;
import com.financial.mapapi.bean.StaticValue;
import com.financial.mapapi.dao.MapDao;
import com.financial.mapapi.http.ThreadHttpRequest;
import com.financial.mapapi.tools.HandleData;
import com.financial.mapapi.tools.ThreadHandleData;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BaiduAPI baiduAPI = new BaiduAPI();
		baiduAPI.BaiduPlaceAPI();
		
		/*AmapAPI amapAPI = new AmapAPI();
		System.out.println("amap WebServiceAPI start");
		amapAPI.AmapKeyWordsAPI();
		*/
		/*TencentMap tencentMap = new TencentMap();
		System.out.println("tencent WebServiceAPI start");
		tencentMap.TencentKeyWordsMap();*/
		

	}

}
