package com.yuwei.web.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.yuwei.web.db.DataBaseOpt;
import com.yuwei.web.db.bean.User;
import com.yuwei.web.http.BaseResponse;
import com.yuwei.web.utils.CheckDb;
import com.yuwei.web.utils.ReadFileToString;


/**
 * Servlet implementation class QueryData
 */
@WebServlet("/QueryData")
public class QueryData extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryData() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	
    	DataBaseOpt.getInstance();
    	CheckDb.getInstance().startTimer();
		System.out.println("QueryData init");

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonArray array = DataBaseOpt.getInstance().queryJson("select * from reg_"+DataBaseOpt.getInstance().tableName+"_user");
		response(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if(action.equals("source")){
			
			String startTime = request.getParameter("startTime")+" 00:00:00";
			String stopTime = request.getParameter("stopTime")+" 24:00:00";
			String token = request.getParameter("token");
			String[] cookies = token.split(";");
			token = cookies[0];
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			try {
				
				long sTime = dateFormat.parse(startTime).getTime();
				long endTime = dateFormat.parse(stopTime).getTime();
				JsonArray array = DataBaseOpt.getInstance().
						queryJson("select * from reg_"+
								getMonthTable(sTime)+
						"_user where time >="+sTime+" and time <= "+endTime+" order by time desc");
				response(response, array);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(action.equals("login")){
			login(request,response);
		}else if(action.equals("down")){
			String startTime = request.getParameter("startTime")+" 00:00:00";
			String stopTime = request.getParameter("stopTime")+" 24:00:00";
			String token = request.getParameter("token");
			String[] cookies = token.split(";");
			token = cookies[0];
			System.out.println("token="+cookies[0]);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			try {
				
				long sTime = dateFormat.parse(startTime).getTime();
				long endTime = dateFormat.parse(stopTime).getTime();
				JsonArray array = DataBaseOpt.getInstance().
						queryJson("select * from down_"+
								getMonthTable(sTime)+
						"_user where time >="+sTime+" and time <= "+endTime+" order by time desc");
				response(response, array);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void login(HttpServletRequest request,HttpServletResponse response){
		
		BaseResponse br = new BaseResponse();
		
		String account = request.getParameter("account");
		String pwd = request.getParameter("pwd");
		
		String sql = "select * from user where account = '"+account+"'";
		String json = DataBaseOpt.getInstance().query(sql);
		if(null == json || json.isEmpty()){
			br.errorCode = 0;
			br.errorMsg = "用户不存在";
			response(response,new Gson().toJson(br));
			return;
		}
		List<User> list = new Gson().fromJson(json,new TypeToken<List<User>>(){}.getType());
		
		if(null == list || list.isEmpty()){
			br.errorCode = 0;
			br.errorMsg = "用户不存在";
			response(response,new Gson().toJson(br));
			return;
		}
		
		User user = list.get(0);
		
		if(user.pwd.equals(pwd)){
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("errorCode", 200);
			jsonObject.addProperty("result",ReadFileToString.getHtmlQueryMode());
			
			response(response,jsonObject.toString());
		}else{
			br.errorCode = 0;
			br.errorMsg = "密码错误";
			response(response,new Gson().toJson(br));
		}
	}
	
	
	private void response(HttpServletResponse response,JsonArray array){
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("errorCode", 200);
		jsonObject.addProperty("errorMsg", "成功");
		jsonObject.addProperty("d_size", array.size());
		jsonObject.add("result",array);
		
		String json =jsonObject.toString();
		
		try {
			OutputStream os = response.getOutputStream();
			os.write(json.getBytes("UTF-8"));
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void response(HttpServletResponse response,String json){
		
		try {
			OutputStream os = response.getOutputStream();
			os.write(json.getBytes("UTF-8"));
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getMonthTable(long time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM");
		
		return format.format(new Date(time));
		
	}
}
