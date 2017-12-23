package com.yuwei.web.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.yuwei.web.db.DataBaseOpt;
import com.yuwei.web.db.bean.Access;
import com.yuwei.web.http.BaseResponse;
import com.yuwei.web.http.HttpRequest;

public class SSendCode extends HttpServlet {
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
		
		OutputStream os = response.getOutputStream();
		
		String phone = request.getParameter("phone");
		
		int count = canAccess(phone,request.getRemoteHost());
		if(count == -1){
			os.write("请求太频繁。。。".getBytes("UTF-8"));
			os.flush();
			os.close();
			return;
		}
		
		if(count == 0 || count == -2){
			DataBaseOpt.getInstance().insertAccess(request.getRemoteHost(), phone, System.currentTimeMillis(), count+1);
		}else{
			DataBaseOpt.getInstance().updateAccess(count+1, request.getRemoteHost(), phone);
		}
		
		String url = "http://139.196.104.98:84/Member/SendSMS/";

	
		JsonObject jo = new JsonObject();
		jo.addProperty("phone", phone);
		jo.addProperty("type", 0);
		
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
				
			}
		}else{
			os.write("发送失败".getBytes("UTF-8"));
		}
		
		os.flush();
		os.close();
	}
	
	
	private int canAccess(String phone,String ip){
		String sql = String.format("select * from access where ip='%s' or phone='%s'", ip,phone);
		String json = DataBaseOpt.getInstance().query(sql);
		if(TextUtils.isEmpty(json) || json.equals("[]")) return 0;
		
		List<Access> list = new Gson().fromJson(json, new TypeToken<List<Access>>(){}.getType());
		if(null != list && list.size() > 0){
			Access access = list.get(0);
			if(access.count >= 3){
				return -1;
			}else{
				return access.count;
			}
		}
		return -2;
	}
}
