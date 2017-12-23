package com.yuwei.web.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.TextUtils;

import com.yuwei.web.db.DataBaseOpt;
import com.yuwei.web.utils.ReadFileToString;
@WebServlet("/Index")
public class Index extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		OutputStream os = resp.getOutputStream();
		
		String key = req.getQueryString();
		
		
		
		if(TextUtils.isEmpty(key)){
			os.write("thanks....".getBytes());
			os.flush();
			os.close();
			return;
		}
		
		
		if(key.equals("shengshuxuedai")){
			
			response(resp,ReadFileToString.getHtmlShengshu());
			
		}else if(key.equals("login")){
			
			response(resp,ReadFileToString.getHtmlLoginMode());
			
		}else{
			
			if(TextUtils.isEmpty(key)){
				os.write("thanks....".getBytes());
				os.flush();
				os.close();
				return;
			}
			
			String[] params = key.split("&");
			
			if(params.length >= 2){
				
				String existKey = DataBaseOpt.getInstance().existRegKey(params[0]);
				if(!TextUtils.isEmpty(existKey)){
					String html = ReadFileToString.getHtmlMode();
					html = html.replaceAll("#####", existKey+","+params[1]);
					os.write(html.getBytes("UTF-8"));
				}else{
					os.write("thanks....".getBytes());
				}
				
				
			}else{
				os.write("thanks....".getBytes());
			}
			
			os.flush();
			os.close();
			
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
}
