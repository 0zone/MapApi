/**
 * 
 */
package com.financial.mapapi.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @author user
 * ȫ�ֳ���
 */
public class StaticValue {
	public static final String pageSize = "20";//�ٶ�APIҳ������
	public static HashMap<String, String> citysMap = new HashMap<String, String>();
	
	public static void initialCitysMap() {
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileInputStream = new FileInputStream(new File("").getAbsolutePath() + "\\resource\\city_list.txt");
			inputStreamReader = new InputStreamReader(fileInputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
			
			String cityProvinceString;
			
			try {
				while ( (cityProvinceString = bufferedReader.readLine()) != null) {
					if (cityProvinceString.equals("")){
						continue;
					}
					else{
						if (cityProvinceString.indexOf(" ") != -1) {
							String[] cityProvince = cityProvinceString.split(" ");
							citysMap.put(cityProvince[0], cityProvince[1]);
						}
					}
					
				}
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("��ȡһ������ʱ����");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("�ļ���ȡ·������FileNotFoundException");
		} finally{
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
}
