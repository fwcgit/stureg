package com.yuwei.web.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpRequest {
	
	private static HttpRequest httpRequest;
	public static HttpRequest getInstance(){
		return httpRequest == null ? httpRequest = new HttpRequest() : httpRequest;
	}
	
	private HttpRequest(){}
	
	public interface IHttpListener{
		public void succeed();
		public void fail(String msg);
	}
	
	public String post(String Url,Map<String,String> params){
		
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(Url);
		post.addHeader("Content-type", "application/json; charset=utf-8");
		post.setHeader("Accept", "application/json;q=1");
	     
		
		if(null !=params){
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				list.add(new BasicNameValuePair(key, params.get(key)));
			}
			try {
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			HttpResponse response=  httpClient.execute(post);
			
			if(response.getStatusLine().getStatusCode() == 200 ){
				String result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	
	public String postJsonBody(String Url,String json){
		
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(Url);
		post.addHeader("Content-type", "application/json; charset=utf-8");
		post.setHeader("Accept", "application/json;q=1");
	     
		StringEntity entity = new StringEntity(json, "UTF-8");
		entity.setContentEncoding("UTF-8");    
		entity.setContentType("application/json"); 
		
		post.setEntity(entity);

		try {
			HttpResponse response=  httpClient.execute(post);
			
			if(response.getStatusLine().getStatusCode() == 200 ){
				String result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
}
