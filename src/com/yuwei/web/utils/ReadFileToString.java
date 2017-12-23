package com.yuwei.web.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class ReadFileToString {
	
	public static String getHtmlMode(){
		
		StringBuffer sb = new StringBuffer();
		File file = new File(new File("").getAbsolutePath()+"/StuReg/html/index.html");
		
		BufferedReader br = null;
		
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"utf-8");
			br = new BufferedReader(isr);
			
			String temp = "";
			while((temp = br.readLine()) != null){
				sb.append(temp);
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			if(null != br){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	public static String getHtmlQueryMode(){
		
		StringBuffer sb = new StringBuffer();
		File file = new File(new File("").getAbsolutePath()+"/StuReg/html/query.html");
		
		BufferedReader br = null;
		
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"utf-8");
			br = new BufferedReader(isr);
			
			String temp = "";
			while((temp = br.readLine()) != null){
				sb.append(temp);
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			if(null != br){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	public static String getHtmlShengshu(){
		
		StringBuffer sb = new StringBuffer();
		File file = new File(new File("").getAbsolutePath()+"/StuReg/html/shengsu.html");
		
		BufferedReader br = null;
		
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"utf-8");
			br = new BufferedReader(isr);
			
			String temp = "";
			while((temp = br.readLine()) != null){
				sb.append(temp);
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			if(null != br){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	
	public static String getHtmlLoginMode(){
		
		StringBuffer sb = new StringBuffer();
		File file = new File(new File("").getAbsolutePath()+"/StuReg/html/login.html");
		
		BufferedReader br = null;
		
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"utf-8");
			br = new BufferedReader(isr);
			
			String temp = "";
			while((temp = br.readLine()) != null){
				sb.append(temp);
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			if(null != br){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	public static String getHtmlDownload(){
		
		StringBuffer sb = new StringBuffer();
		File file = new File(new File("").getAbsolutePath()+"/StuReg/html/download.html");
		
		BufferedReader br = null;
		
		try {
			
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"utf-8");
			br = new BufferedReader(isr);
			
			String temp = "";
			while((temp = br.readLine()) != null){
				sb.append(temp);
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			if(null != br){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
}
