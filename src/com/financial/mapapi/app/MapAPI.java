package com.financial.mapapi.app;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.NameValuePair;
import com.financial.mapapi.dao.MapDao;
import com.financial.mapapi.http.ThreadHttpRequest;


public class MapAPI {
	
	private ExecutorService executorService = null;
	private final static int POOL_SIZE = 10;//线程池大小
	
	public List<NameValuePair> mapParamsList = new ArrayList<NameValuePair>();
	public String mapUrl = null;
	public String tableName = null;
	
	public MapAPI(){
		executorService = Executors.newFixedThreadPool(POOL_SIZE);
	}
	
	
	//循环调用ThreadHttpRequest，多线程访问API
	public Boolean WebServiceAPI() {
		int totalMerchantNum = MapDao.totalMerchantNum(tableName);
		int threadHandleNum = totalMerchantNum / (POOL_SIZE-1);//每个线程将要处理的数据量
		
		for (int i=0; i<POOL_SIZE; i++) {
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			paramsList.addAll(mapParamsList);
			
			//根据开始、结束id 查询数据库
			if(i != POOL_SIZE-1){
				executorService.execute(new ThreadHttpRequest( i*threadHandleNum + 1, (i+1)*threadHandleNum, mapUrl, paramsList));	
				
			}else {//最后一组的处理
				executorService.execute(new ThreadHttpRequest( i*threadHandleNum + 1, totalMerchantNum, mapUrl, paramsList));	
			}
			
		}
		executorService.shutdown();
		
		return true;
		
	}
	
	public Boolean BrandMapAPI() {
		
		
		for (int i=7115; i<7799; i++) {
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			paramsList.addAll(mapParamsList);
			
			
			
			executorService.execute(new ThreadHttpRequest( i, i, mapUrl, paramsList));	
			
			
		}
		executorService.shutdown();
		
		return true;
		
	}

}
