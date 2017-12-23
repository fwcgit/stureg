package com.yuwei.web.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.TextUtils;

import com.google.gson.Gson;
import com.yuwei.web.db.DataBaseOpt;
import com.yuwei.web.http.BaseResponse;
import com.yuwei.web.http.HttpRequest;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String phone = request.getParameter("phone");
		String yzm = request.getParameter("yzm");
		String pwd = request.getParameter("pwd");
		String source = request.getParameter("source").split(",")[0];
		String channel = request.getParameter("source").split(",")[1];
		
		System.err.println("phone:"+phone);
		System.err.println("yzm:"+yzm);
		System.err.println("pwd:"+pwd);
		System.err.println("source:"+source);
		System.err.println("channel:"+channel);
		

		OutputStream os = response.getOutputStream();
		
		String urlFormat = String.format("http://app.yuhuizichan.com:10000/accounts/app/%s/register?platform=web&checkCode=%s&password=%s&invitedBy=%s"
				,phone,yzm,pwd,"");
		
		String json = HttpRequest.getInstance().post(urlFormat, null);
		
		System.err.println(json);
		
		if(!TextUtils.isEmpty(json)){
			BaseResponse baseResponse = new Gson().fromJson(json, BaseResponse.class);
			if(null != baseResponse){
				if(baseResponse.errorCode == 0){
					os.write("1".getBytes("UTF-8"));
					DataBaseOpt.getInstance().insertRegister(source, channel, System.currentTimeMillis(), 1,phone);
				}else{
					os.write(baseResponse.errorMsg.getBytes("UTF-8"));
				}
				os.flush();
				os.close();
			}
		}
	}

}
