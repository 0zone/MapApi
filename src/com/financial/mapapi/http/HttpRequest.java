package com.financial.mapapi.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpRequest {

	private RequestConfig globalConfig = RequestConfig.custom()
	        .setCookieSpec(CookieSpecs.BEST_MATCH)
	        .setSocketTimeout(60000)
	        .setConnectTimeout(50000)
	        .setConnectionRequestTimeout(50000)
	        .build();

	private CloseableHttpClient closeableHttpClient = HttpClients.custom()
	        .setDefaultRequestConfig(globalConfig)
	        .build();
	
	
	private RequestConfig localConfig = RequestConfig.copy(globalConfig)
	        .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
	        .build();
	

	/**
	 * Get����
	 * @param url
	 * @param params
	 * @return
	 */
	public String get(String url, List<NameValuePair> params) {
		int status = -1;
		String body = null;
		HttpGet httpget = null;
		String paramsString = null;
		CloseableHttpResponse closeableHttpResponse = null;
		HttpEntity entity = null;
		try {
			// Get����
			httpget = new HttpGet(url);
			httpget.setConfig(localConfig);
			
			// ���ò���
			paramsString = EntityUtils.toString(new UrlEncodedFormEntity(params, "UTF-8"));
			httpget.setURI(new URI(httpget.getURI().toString() + "?" + paramsString));
			
			// ��������
		
			closeableHttpResponse = closeableHttpClient.execute(httpget);
			status = closeableHttpResponse.getStatusLine().getStatusCode();
			
			// ��ȡ��������
			entity = closeableHttpResponse.getEntity();
			body = EntityUtils.toString(entity);
			
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			System.out.println("statusCode: " + status);
			return body;
		} catch (HttpHostConnectException e) {
			e.printStackTrace();
			System.out.println("statusCode: " + status);
			return body;
		} catch (ConnectException e) {
			e.printStackTrace();
			System.out.println("statusCode: " + status);
			return body;
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if (closeableHttpResponse != null) 
					closeableHttpResponse.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (entity != null) 
					EntityUtils.consume(entity);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return body;
	}

	/**
	 * // Post����
	 * @param url
	 * @param params
	 * @return
	 */
	public String post(String url, List<NameValuePair> params) {
		String body = null;
		try {
			// Post����
			HttpPost httppost = new HttpPost(url);
			httppost.setConfig(localConfig);
			// ���ò���
			httppost.setEntity(new UrlEncodedFormEntity(params));
			// ��������
			HttpResponse httpresponse = closeableHttpClient.execute(httppost);
			// ��ȡ��������
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
			if (entity != null) {
				EntityUtils.consume(entity);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
}
