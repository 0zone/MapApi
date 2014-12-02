package com.financial.mapapi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import com.financial.mapapi.bean.BrandBean;
import com.financial.mapapi.bean.BrandMapBean;
import com.financial.mapapi.bean.MerchantBean;

public class MapDao {
	
	
	private final static String TABLE_NAME = " amapapi_icbc_sh ";//" baiduplaceapi_icbc_sh ";
	public static Connection getConnection(){
		Connection connection = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/financial", "root", "root");
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
		
	}

	
	public static Boolean saveMerchant(String tableName, Connection connection, MerchantBean merchantBean){
		//Connection connection = getConnection();
		
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE "+ tableName  +
				" SET matchedName=?, type=?, address=?, location=?, telephone=?, responseData=? " +
				" where id=?";
		/*String sql = "UPDATE "+ tableName  +
				" SET matchedName=?, type=?, address=?, location=?, telephone=? " +
				" where id=?";*/
		/*String sql = "UPDATE "+ tableName  +
				" SET location=? " +
				" where id=?";*/
		//不存储responseData
		ResultSet res = null;
		
		try {
			//System.out.println("开始存入数据库");
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, merchantBean.getMatchedName());
			preparedStatement.setString(2, merchantBean.getType());
			preparedStatement.setString(3, merchantBean.getAddress());
			preparedStatement.setString(4, merchantBean.getLocation());
			preparedStatement.setString(5, merchantBean.getTelephone());
			preparedStatement.setString(6, merchantBean.getResponseData());
			preparedStatement.setInt(7, merchantBean.getId());
			preparedStatement.executeUpdate();
			
			//System.out.println(merchantBean.getId());
        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("saveMerchant ERROR:" + e.getMessage());
            return false;
		} finally{
			try {
				if (res != null) {
					res.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("saveMerchant ERROR:" + e.getMessage());
			}
			try {
				if(preparedStatement != null){
					preparedStatement.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.print("saveMerchant ERROR:" + e.getMessage());
				return false;
			}
		}

		return true;
	
	}
	
	public static Boolean saveBrandCity(String tableName, Connection connection, BrandBean brandBean){
		
		PreparedStatement preparedStatement = null;

		String sql = "UPDATE "+ tableName  +
				" SET brandCitys=? " +
				" where brandId=?";
		//不存储responseData
		ResultSet res = null;
		
		try {
			//System.out.println("开始存入数据库");
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, brandBean.getBrandCitys());
			preparedStatement.setInt(2, brandBean.getBrandId());
			preparedStatement.executeUpdate();
			//System.out.println(merchantBean.getId());
        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("saveBrandCity MYSQL ERROR:" + e.getMessage());
            return false;
		} finally{
			try {
				if (res != null) {
					res.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("saveBrandCity MYSQL ERROR:" + e.getMessage());
			}
			try {
				if(preparedStatement != null){
					preparedStatement.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.print("saveBrandCity MYSQL ERROR:" + e.getMessage());
				return false;
			}
		}

		return true;
	
	}

	public static Boolean saveBrandMapList(String tableName, Connection connection, ArrayList<BrandMapBean> brandMapBeanList){
		//Connection connection = getConnection();
		
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO"+ tableName  +
				" (brandId, brandName, merchantUid, merchantName, merchantCity, merchantProvince, merchantAddress, merchantLocation, merchantType) " +
				" values(?,?,?,?,?,?,?,?,?)";

		//不存储responseData
		ResultSet res = null;
		
		try {
			//System.out.println("开始存入数据库");
			preparedStatement = connection.prepareStatement(sql);
			
			for (BrandMapBean brandMapBean : brandMapBeanList) {
				preparedStatement.setInt(1, brandMapBean.getBrandId());
				preparedStatement.setString(2, brandMapBean.getBrandName());
				preparedStatement.setString(3, brandMapBean.getMerchantUid());
				preparedStatement.setString(4, brandMapBean.getMerchantName());
				preparedStatement.setString(5, brandMapBean.getMerchantCity());
				preparedStatement.setString(6, brandMapBean.getMerchantProvince());
				preparedStatement.setString(7, brandMapBean.getMerchantAddress());
				preparedStatement.setString(8, brandMapBean.getMerchantLocation());
				preparedStatement.setString(9, brandMapBean.getMerchantType());
				System.out.println(brandMapBean.getId());
				preparedStatement.addBatch();   
			}
			
			
			preparedStatement.executeBatch();
			
			//System.out.println(merchantBean.getId());
        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("MYSQL ERROR:" + e.getMessage());
            return false;
		} finally{
			try {
				if (res != null) {
					res.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("MYSQL ERROR:" + e.getMessage());
			}
			try {
				if(preparedStatement != null){
					preparedStatement.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.print("MYSQL ERROR:" + e.getMessage());
				return false;
			}
		}

		return true;
	
	}
	public static ArrayList<BrandMapBean> queryBrandMapListById(String tableName, int beginId, int endId) {
		
		ArrayList<BrandMapBean> brandMapBeans = new ArrayList<BrandMapBean>();
		
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
	
		String sql = "select id,merchantCity,merchantProvince from" + tableName + "where id between " + beginId + " and " + endId ;
		//String sql = "select *, count(distinct merchantUid) from brandmap group by merchantUid ORDER BY brandId ";
		ResultSet res = null;
		
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			res = preparedStatement.executeQuery(sql);
			
			while ( res.next() ) {
				BrandMapBean brandMapBean = new BrandMapBean();
				brandMapBean.setId(res.getInt("id"));
				brandMapBean.setBrandId(res.getInt("brandId"));
				brandMapBean.setBrandName(res.getString("brandName"));
				brandMapBean.setMerchantUid(res.getString("merchantUid"));
				brandMapBean.setMerchantName(res.getString("merchantName"));
				brandMapBean.setMerchantCity(res.getString("merchantCity"));
				brandMapBean.setMerchantProvince(res.getString("merchantProvince"));
				brandMapBean.setMerchantAddress(res.getString("merchantAddress"));
				brandMapBean.setMerchantLocation(res.getString("merchantLocation"));
				brandMapBean.setMerchantType(res.getString("merchantType"));
				
				brandMapBeans.add(brandMapBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(res, preparedStatement, connection);
		}
		
		
		return brandMapBeans;
		
	}
	public static Boolean deleteRecordById(String tableName, Connection connection, int deleteId){
		
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM "+ tableName  +
				" WHERE id="+deleteId;
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.execute();
			
        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("MYSQL ERROR:" + e.getMessage());
            return false;
		} finally{
			try {
				if(preparedStatement != null){
					preparedStatement.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.print("MYSQL ERROR:" + e.getMessage());
				return false;
			}
		}
		return true;
	
	}
	public static ArrayList<MerchantBean> queryMerchantListById(String tableName, int beginId, int endId) {
		
		ArrayList<MerchantBean> arrayListBeans= new ArrayList<MerchantBean>();
		
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		//String sql = "select id,originalName,matchedName,type,address,location,telephone from" + tableName + "where responseData is NULL and id between " + beginId + " and " + endId ;
		String sql = "select id,originalName,matchedName,type,address,location,telephone from" + tableName + "where id between " + beginId + " and " + endId ;
		//String sql = "select id,originalName,matchedName,type,address,location,telephone from" + tableName + "where address!='' and id between " + beginId + " and " + endId;
		
		ResultSet res = null;
		
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			/*preparedStatement.setInt(1, beginId);
			preparedStatement.setInt(2, endId);*/
			
			res = preparedStatement.executeQuery(sql);
			while ( res.next() ) {
				MerchantBean resultBean = new MerchantBean();
				resultBean.setId(res.getInt("id"));
				resultBean.setOriginalName(res.getString("originalName"));
				resultBean.setMatchedName(res.getString("matchedName"));
				resultBean.setType(res.getString("type"));
				resultBean.setAddress(res.getString("address"));
				resultBean.setLocation(res.getString("location"));
				resultBean.setTelephone(res.getString("telephone"));
				//resultBean.setResponseData(res.getString("responseData"));
				
				arrayListBeans.add(resultBean);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(res, preparedStatement, connection);
		}
		
		
		return arrayListBeans;
		
	}
	public static MerchantBean queryMerchantById(String tableName, int merchantId) {
		
		MerchantBean merchantBean= new MerchantBean();
		
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		
		String sql = "select * from" + tableName + "where id=" + merchantId;
		
		ResultSet res = null;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			res = preparedStatement.executeQuery(sql);
			while ( res.next() ) {
				merchantBean.setId(res.getInt("id"));
				merchantBean.setOriginalName(res.getString("originalName"));
				merchantBean.setMatchedName(res.getString("matchedName"));
				merchantBean.setType(res.getString("type"));
				merchantBean.setAddress(res.getString("address"));
				merchantBean.setLocation(res.getString("location"));
				merchantBean.setTelephone(res.getString("telephone"));
				//merchantBean.setResponseData(res.getString("responseData"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(res, preparedStatement, connection);
		}
		
		
		return merchantBean;
		
	}
	public static ArrayList<MerchantBean> queryAllMerchant() {
		
		ArrayList<MerchantBean> arrayListBeans= new ArrayList<MerchantBean>();
		
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		/*String sql = "select * from map where map.resultAddress = ''" + 
					"AND map.resultType != '' AND map.resultType != '村庄' AND map.resultType != '交叉路口' AND map.resultType != '工业园区' AND map.resultType != '城市' AND map.resultType != '省' AND map.resultType != '区县' AND map.resultType != '商圈' AND map.resultType != '政府机构' AND map.resultType != '旅游景点' AND map.resultType != '商务大厦' AND map.resultType != '国家' AND map.resultType != '桥'  AND map.resultType != '乡镇' AND map.resultType != '道路'";
		*/
		String sql = "select * from " + TABLE_NAME + " order by id";
		ResultSet res = null;
		
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			res = preparedStatement.executeQuery(sql);
			while ( res.next() ) {
				MerchantBean resultBean = new MerchantBean();
				resultBean.setId(res.getInt("id"));
				resultBean.setOriginalName(res.getString("originalName"));
				resultBean.setMatchedName(res.getString("matchedName"));
				resultBean.setType(res.getString("type"));
				resultBean.setAddress(res.getString("address"));
				resultBean.setLocation(res.getString("location"));
				resultBean.setTelephone(res.getString("telephone"));
				resultBean.setResponseData(res.getString("responseData"));

				arrayListBeans.add(resultBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(res, preparedStatement, connection);
		}

		return arrayListBeans;
		
	}
	public static int totalMerchantNum(String tableName) {
		int totalNum = 0;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		/*String sql = "select * from map where map.resultAddress = ''" + 
					"AND map.resultType != '' AND map.resultType != '村庄' AND map.resultType != '交叉路口' AND map.resultType != '工业园区' AND map.resultType != '城市' AND map.resultType != '省' AND map.resultType != '区县' AND map.resultType != '商圈' AND map.resultType != '政府机构' AND map.resultType != '旅游景点' AND map.resultType != '商务大厦' AND map.resultType != '国家' AND map.resultType != '桥'  AND map.resultType != '乡镇' AND map.resultType != '道路'";
		*/
		String sql = "select count(*) from " + tableName;
		ResultSet res = null;
		
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			res = preparedStatement.executeQuery(sql);
			
			if (res.next()) {
				totalNum = res.getInt(1);
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(res, preparedStatement, connection);
		}

		return totalNum;
	}
	
	public static ArrayList<BrandBean> queryBrandListById(String tableName, int beginId, int endId) {
		
		ArrayList<BrandBean> arrayListBeans= new ArrayList<BrandBean>();
		
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		
		String sql = "select * from" + tableName + "where brandId between " + beginId + " and " + endId;
		
		ResultSet res = null;
		
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			res = preparedStatement.executeQuery(sql);
			while ( res.next() ) {
				BrandBean resultBean = new BrandBean();
				resultBean.setBrandId(res.getInt("brandId"));
				resultBean.setBrandName(res.getString("brandName"));
				resultBean.setBrandCitys(res.getString("brandCitys"));
				
				arrayListBeans.add(resultBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(res, preparedStatement, connection);
		}
		
		
		return arrayListBeans;
		
	}
	
	
	
	public static Boolean closeConnection(ResultSet res, PreparedStatement pst, Connection con) {
		
		try {
			if (res != null) {
				res.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("MYSQL ERROR:" + e.getMessage());
		}
		try {
			if(pst != null){
				pst.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.print("MYSQL ERROR:" + e.getMessage());
			return false;
		}
		
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e2) {
			// TODO: handle exception
			System.out.print("MYSQL ERROR:" + e2.getMessage());
		}
		
		return true;
		
	}
}
