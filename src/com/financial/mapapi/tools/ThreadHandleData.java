/**
 * 
 */
package com.financial.mapapi.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.financial.mapapi.bean.BrandMapBean;
import com.financial.mapapi.bean.MerchantBean;
import com.financial.mapapi.bean.StaticValue;
import com.financial.mapapi.dao.MapDao;

/**
 * @author user
 *
 */
public class ThreadHandleData extends Thread{

	private final int threadHandleDataBegine;
	private final int threadHandleDataEnd;
	private final String sourceTableName = " baiduplaceapi_icbc_sh ";//" baiduplaceapi_icbc_sh "amapapi_icbc_sh
	private final String destTableName = " xizhi_icbc_sh ";
	private final String brandTableName = " tmail_brand_map ";
	public ThreadHandleData(int begineId, int endId) {
		threadHandleDataBegine = begineId;
		threadHandleDataEnd = endId;
	}
	
	@Override
	public void run() {
		getProvinceByCity();
		
		super.run();
	}
	
	private void getProvinceByCity() {
		Connection connection = MapDao.getConnection();
		ArrayList<BrandMapBean> brandMapBeans = new ArrayList<BrandMapBean>();
		brandMapBeans = MapDao.queryBrandMapListById(brandTableName, threadHandleDataBegine, threadHandleDataEnd);
		
		//System.out.println("处理" + threadHandleDataBegine + "-----" + threadHandleDataEnd);
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE "+ brandTableName  +
				" SET merchantProvince=? " +
				" where id=?";
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			for (BrandMapBean brandMapBean : brandMapBeans) {
				String merchantCity = brandMapBean.getMerchantCity();
				String simpleCityName = merchantCity;
				
				if ( merchantCity.lastIndexOf("市") == merchantCity.length()-1) {
					simpleCityName = merchantCity.substring(0, merchantCity.length()-1);
				}
	
				brandMapBean.setMerchantProvince(StaticValue.citysMap.get(simpleCityName));

				preparedStatement.setString(1, brandMapBean.getMerchantProvince());
				preparedStatement.setInt(2, brandMapBean.getId());
				preparedStatement.addBatch();   
  
			} 
			preparedStatement.executeBatch();
	
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("MYSQL ERROR:" + e.getMessage());
		} finally{
			try {
				if(preparedStatement != null){
					preparedStatement.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.print("MYSQL ERROR:" + e.getMessage());
			}
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("完成处理" + threadHandleDataBegine + "-----" + threadHandleDataEnd);
		
	}
	
	
	//过滤品牌数据，去除商家名称与品牌名不匹配的记录
	private void filterBrandData() {
		Connection connection = MapDao.getConnection();
		ArrayList<BrandMapBean> brandMapBeans = new ArrayList<BrandMapBean>();
		brandMapBeans = MapDao.queryBrandMapListById(brandTableName, threadHandleDataBegine, threadHandleDataEnd);
		
		int deleteCount=0;
		//System.out.println("处理" + threadHandleDataBegine + "-----" + threadHandleDataEnd);
		
		for (BrandMapBean brandMapBean : brandMapBeans) {
			String brandName = brandMapBean.getBrandName().toLowerCase();
			String[] brandNameArrary = brandName.split("/");
			
			String merchantName = brandMapBean.getMerchantName().toLowerCase();
			Pattern pattern = Pattern.compile("\\s|([^a-zA-Z0-9\\u4e00-\\u9fa5])");
			
			Matcher matcher = pattern.matcher(merchantName);
			merchantName = matcher.replaceAll("");
			
			int unmatchedCount = 0;
			for (int i=0; i<brandNameArrary.length; i++) {
				matcher = pattern.matcher(brandNameArrary[i]);
				brandNameArrary[i] = matcher.replaceAll("");
				if (!merchantName.contains(brandNameArrary[i])) {
					unmatchedCount++;
				}
			}
			if (unmatchedCount == brandNameArrary.length) {
				MapDao.deleteRecordById(brandTableName, connection, brandMapBean.getId());//删除记录
				deleteCount++;
			}
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("完成处理" + threadHandleDataBegine + "-----" + threadHandleDataEnd + "共删除"+deleteCount);
		
	}
	
	//合并三大地图数据
	private void combineThreeMapsData() {
		ArrayList<MerchantBean> merchantBeans = new ArrayList<MerchantBean>();
		merchantBeans = MapDao.queryMerchantListById(destTableName, threadHandleDataBegine, threadHandleDataEnd);		
		Connection connection = MapDao.getConnection();

		
		System.out.println(getName()+"开始执行:" + "处理" + threadHandleDataBegine + "-----" + threadHandleDataEnd + "-----" + merchantBeans.size());
		
		for (MerchantBean merchantBean : merchantBeans) {
			
			if ( !"".equals(merchantBean.getAddress()) )  {
				String address = merchantBean.getAddress();
				if (address.indexOf("：")!=-1) {
					String a[] = address.split("：");  
					if (a.length>1) {
						merchantBean.setAddress(a[1]);
					}
					
				}
				
			}
			if ( !"".equals(merchantBean.getTelephone()) )  {
				String tel = merchantBean.getTelephone();
				if (tel.indexOf("：")!=-1) {
					String a[] = tel.split("：");  
					if (a.length>1)
						merchantBean.setTelephone(a[1]);
				}
				
			}
			if ( !"".equals(merchantBean.getType()) )  {
				String type = merchantBean.getType();
				if (type.indexOf("：")!=-1) {
					String a[] = type.split("：");  
					if (a.length>1)
						merchantBean.setType(a[1]);
				}
				
			}
			MapDao.saveMerchant(destTableName, connection, merchantBean);
			//更新数据
			//MerchantBean merchant = new MerchantBean();
			//merchant = HandleData.CombineData( MapDao.queryMerchantById(sourceTableName, merchantBean.getId()), merchantBean);
			
			//MapDao.saveMerchant(destTableName, connection, merchant);
			
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
