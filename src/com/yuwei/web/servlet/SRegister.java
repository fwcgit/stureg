package com.yuwei.web.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yuwei.web.http.BaseResponse;
import com.yuwei.web.http.HttpRequest;

public class SRegister extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String phone = request.getParameter("phone");
		String yzm = request.getParameter("yzm");
		String pwd = request.getParameter("pwd");
		
	
		System.err.println("phone:"+phone);
		System.err.println("yzm:"+yzm);
		System.err.println("pwd:"+pwd);
		
		OutputStream os = response.getOutputStream();
	
		String url = "http://139.196.104.98:84/Member/Register/";
		
		JsonObject jo = new JsonObject();
		jo.addProperty("phone", phone);
		jo.addProperty("yzm", yzm);
		jo.addProperty("pwd", pwd);
		
		String json = HttpRequest.getInstance().postJsonBody(url, jo.toString());
		
		System.err.println(json);
		
		if(!TextUtils.isEmpty(json)){
			BaseResponse baseResponse = new Gson().fromJson(json, BaseResponse.class);
			if(null != baseResponse){
				if(baseResponse.Code == 200){
					os.write("1".getBytes("UTF-8"));
				}else{
					os.write(baseResponse.Msg.getBytes("UTF-8"));
				}
				os.flush();
				os.close();
			}
		}
		
	}
}
