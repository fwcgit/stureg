package com.yuwei.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.TextUtils;

import com.google.gson.Gson;
import com.yuwei.web.db.DataBaseOpt;
import com.yuwei.web.http.BaseResponse;
import com.yuwei.web.utils.ReadFileToString;

/**
 * Servlet implementation class Download
 */
@WebServlet("/Download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Download() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		OutputStream os = response.getOutputStream();
		
		String params = request.getQueryString();
		String key = params;
		
		if(params.indexOf("down=ios") > -1 || params.indexOf("down=.apk") > -1 || params.indexOf("down=p.apk") > -1){
			String[] keyValue = params.split("&");
			key = keyValue[0].split("=")[1];
		}
		
		
		System.out.println("download key = " +key);
		
		if(TextUtils.isEmpty(key)){
			os.write("thanks....".getBytes());
			os.flush();
			os.close();
			return;
		} 
		
		String existKey = DataBaseOpt.getInstance().existDownKey(key);
		
		if(TextUtils.isEmpty(existKey)) {
			os.write("thanks....".getBytes());
			os.flush();
			os.close();
			return;
		}
		
		if(params.indexOf("down=.apk") > -1){
			DataBaseOpt.getInstance().insertDown(existKey, "", System.currentTimeMillis(), 1, "");
			clientDownload(response);
		}else if(params.indexOf("down=ios") > -1){
			DataBaseOpt.getInstance().insertDown(existKey, "", System.currentTimeMillis(), 2, "");
			response.sendRedirect("https://itunes.apple.com/cn/app/jie-zou-da-shi/id1274096438?mt=8");
		}else if(params.indexOf("down=p.apk") > -1){
			DataBaseOpt.getInstance().insertDown(existKey, "", System.currentTimeMillis(), 3, "");
			clientDownload(response);
		}
			}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		OutputStream os = response.getOutputStream();
		
		String action = request.getParameter("action");
		
		if(action.equals("download")){
			
			String key = request.getParameter("key");
			String osType = request.getParameter("os");
			String source = request.getParameter("source");
			
			BaseResponse br = new BaseResponse();
			br.errorCode = 200;
			
			DataBaseOpt.getInstance().insertDown(source, "", System.currentTimeMillis(), Integer.valueOf(osType), "");
			
			if(osType.equals("1") || osType.equals("3")){
				br.result="download?key="+key+"&down=.apk";
			}else{
				br.result="https://itunes.apple.com/cn/app/jie-zou-da-shi/id1274096438?mt=8";
			}
			
			os.write(new Gson().toJson(br).toString().getBytes("UTF-8"));
			os.flush();
			os.close();
		}
	}
	
	private void clientDownload(HttpServletResponse response){
		//处理请求  
        //读取要下载的文件  
		File file = new File(new File("").getAbsolutePath()+"/StuReg/brrowWhite.apk");
        if(file.exists()){  
            FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				 String filename=URLEncoder.encode(file.getName(),"utf-8"); //解决中文文件名下载后乱码的问题  
		            byte[] b = new byte[fis.available()];  
		            fis.read(b);  
		            response.setCharacterEncoding("utf-8");  
		            response.setHeader("Content-Disposition","attachment; filename="+filename+"");  
		            //获取响应报文输出流对象  
		            ServletOutputStream  out =response.getOutputStream();  
		            //输出  
		            out.write(b);  
		            out.flush();  
		            out.close();  
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(null != fis){
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}  
           
        }     
	}

}
