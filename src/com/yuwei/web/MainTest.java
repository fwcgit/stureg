package com.yuwei.web;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainTest {

	public static int remainTime = 180;
	public static void main(String[] args) {
		
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		
//		JsonArray array = DataBaseOpt.getInstance().queryJson("select * from reg_2017_10_user where source = '572R5piT'");
//		
//		for(int i = 0 ; i < array.size() ; i++){
//			JsonObject jo = array.get(i).getAsJsonObject();
//			System.out.print((i+1)+"	");
//		
//			System.out.print("网易"+"	");
//			System.out.print(jo.get("phone").getAsString()+"	");
//			System.out.print(dateFormat.format(new Date(jo.get("time").getAsLong()))+"	");
//			String data = jo.get("data").getAsString();
//			if(!data.startsWith("{")){
//				System.out.print(data);
//			}
//			System.out.println();
//		}

		List<String> oldList = new ArrayList<String>();
		oldList.add("1");
		oldList.add("2");
		oldList.add("3");
		
		List<String> newList = new ArrayList<String>();
		newList.addAll(oldList);
		
		oldList.clear();
		
		System.out.println(newList.size());
		
	}
	
	public static boolean emailFormat(String email)
    {
        boolean tag = true;
        final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }
	
    public static String secondConvertMinute(){

        int m = remainTime / 60;
        int s = remainTime % 60;

        String timeStr = "";

        if(m < 10){
            timeStr = "0"+m;
        }else{
            timeStr = m+"";
        }

        timeStr+=":";

        if(s < 10){
            timeStr += ("0"+s);
        }else{
            timeStr += (s+"");
        }

        return  timeStr;
    }

}
