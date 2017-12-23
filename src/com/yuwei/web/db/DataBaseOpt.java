package com.yuwei.web.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.yuwei.web.db.bean.Table;


public class DataBaseOpt {
	private static final String URL = "jdbc:mysql://139.196.104.98:3306/tg?autoReconnect=true";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String user = "root";
	private static final String pwd = "zxc,.888";
	
	private Connection conn = null;
	
	public String tableName = "";
	
	private static DataBaseOpt baseOpt;
	public static DataBaseOpt getInstance(){
		return baseOpt == null ? baseOpt = new DataBaseOpt() : baseOpt;
	}
	
	private DataBaseOpt(){
		if(conn == null){
			connectDataBase();
		}
	}
	public void connectDataBase(){
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, user, pwd);
			
//			createUserTalbe();
//			createDownloadTalbe();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	private void tryConnect(){
		try {
			
			if(null == conn){
				connectDataBase();
			}
			
			if(null != conn && conn.isClosed()){
				connectDataBase();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String query(String sql){
		
		tryConnect();
		
		String result = "";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			result = createJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public JsonArray queryJson(String sql){
		
		tryConnect();
		
		JsonArray array = new JsonArray();
				
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			array = createJsonArray(rs);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return array;
	}
	
	
	public void insertAccess(String ip,String phone,long date,int count){
		
		tryConnect();
		
		String sql = "insert into `tg`.`access` (`phone`, `count`, `ip`, `time`) values (?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, phone);
			ps.setInt(2, count);
			ps.setString(3, ip);
			ps.setLong(4, date);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertRegister(String source,String data,long time,int type,String phone){
		
		tryConnect();
		
		getMonthTable();
		
		String sql = String.format("insert into reg_%s_user ( source , data , time , p_type , phone) values (?,?,?,?,?)",tableName);
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, source);
			ps.setString(2, data);
			ps.setLong(3, time);
			ps.setInt(4, type);
			ps.setString(5, phone);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertDown(String source,String data,long time,int type,String phone){
		
		tryConnect();
		
		getMonthTable();
		
		String sql = String.format("insert into down_%s_user ( source , data , time , p_type , phone) values (?,?,?,?,?)",tableName);
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, source);
			ps.setString(2, data);
			ps.setLong(3, time);
			ps.setInt(4, type);
			ps.setString(5, phone);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateToken(String token,String account){
		
		tryConnect();
		
		String sql = "UPDATE user set token = '"+token+"'"+" where account = '"+account+"'";
		try {
			Statement st = conn.prepareStatement(sql);
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateAccess(int count,String ip,String phone){
		
		tryConnect();
		
		String sql = "UPDATE access set count = '%s',ip='%s',phone='%s' where ip = '%s' or phone = '%s'";
		sql = String.format(sql, count,ip,phone,ip,phone);
		
		try {
			Statement st = conn.prepareStatement(sql);
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String createJson(ResultSet rs){
		String json = "";
		try {
			
			ResultSetMetaData data = rs.getMetaData();
			String col[] = new String[data.getColumnCount()];
			
			for (int i = 1; i <= data.getColumnCount(); i++) {
				col[i-1] = data.getColumnName(i);
			}
			JsonArray ja = new JsonArray();
			while(rs.next()){
				JsonObject jo = new JsonObject();
				for (int i = 1; i <= col.length; i++) {
					Object obj = rs.getObject(i);
					if(obj instanceof Integer){
						jo.addProperty(col[i-1], (int)obj);
					}else if(obj instanceof Long){
						jo.addProperty(col[i-1], (long)obj);
					}else{
						jo.addProperty(col[i-1], rs.getString(i));
					}
				}
				ja.add(jo);
			}
			json = ja.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//rs.getFetchSize()
		return json;
	}
	
	
	private JsonArray createJsonArray(ResultSet rs){
		
		JsonArray ja = new JsonArray();

		try {
	
			ResultSetMetaData data = rs.getMetaData();
			String col[] = new String[data.getColumnCount()];
			
			for (int i = 1; i <= data.getColumnCount(); i++) {
				col[i-1] = data.getColumnName(i);
			}
			while(rs.next()){
				JsonObject jo = new JsonObject();
				for (int i = 1; i <= col.length; i++) {
					Object obj = rs.getObject(i);
					if(obj instanceof Integer){
						jo.addProperty(col[i-1], (int)obj);
					}else if(obj instanceof Long){
						jo.addProperty(col[i-1], (long)obj);
					}else{
						jo.addProperty(col[i-1], rs.getString(i));
					}
				}
				ja.add(jo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//rs.getFetchSize()
		return ja;
	}
	
	public String existRegKey(String key){
		
		tryConnect();
		
		String json = query("select * from platform where html_key='"+key+"'"+" and type='1'");
		List<com.yuwei.web.db.bean.Platform> list = new Gson().fromJson(json,new TypeToken<List<com.yuwei.web.db.bean.Platform>>(){}.getType());
		if(null != list && !list.isEmpty()){
			return list.get(0).name;
		}
		return "";
	}
	
	public String existDownKey(String key){
		
		tryConnect();
		
		String json = query("select * from platform where html_key='"+key+"'"+" and type='3'");
		List<com.yuwei.web.db.bean.Platform> list = new Gson().fromJson(json,new TypeToken<List<com.yuwei.web.db.bean.Platform>>(){}.getType());
		if(null != list && !list.isEmpty()){
			return list.get(0).name;
		}
		return "";
	}
	
	public void createUserTalbe(){
		
		getMonthTable();
		 
		String sql = "create table IF NOT EXISTS reg_%s_user("
				+ "id INT NOT NULL AUTO_INCREMENT,"
				+ "source VARCHAR(50),data text,"
				+ "time BIGINT,"
				+ "p_type INT,"
				+ "phone text,"
				+ "PRIMARY KEY (id))";
		sql = String.format(sql, tableName);
		try {
			Statement st = conn.prepareStatement(sql);
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getMonthTable(){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM");
		tableName = dateFormat.format(new Date());
	}
	
	public void existsTable(){
		
		tryConnect();
		
		getMonthTable();
		
		String json = DataBaseOpt.getInstance().query("select table_name from information_schema.tables where table_schema='tg' and table_type='base table'");
		List<Table> list = new Gson().fromJson(json,new TypeToken<List<Table>>(){}.getType());
		StringBuffer sb = new StringBuffer();
		for (Table table : list) {
			sb.append(table.TABLE_NAME);
		}
		
		if(sb.indexOf("reg_"+tableName+"_user") <= -1){
			createUserTalbe();
		}
		
		if(sb.indexOf("down_"+tableName+"_user") <= -1){
			createDownloadTalbe();
		}
	}
	
	public void createDownloadTalbe(){
		
		getMonthTable();
		 
		String sql = "create table IF NOT EXISTS down_%s_user("
				+ "id INT NOT NULL AUTO_INCREMENT,"
				+ "source VARCHAR(50),data text,"
				+ "time BIGINT,"
				+ "p_type INT,"
				+ "phone text,"
				+ "PRIMARY KEY (id))";
		sql = String.format(sql, tableName);
		try {
			Statement st = conn.prepareStatement(sql);
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createTalbe(String name){
		
		 
		String sql = "create table IF NOT EXISTS %s("
				+ "id INT NOT NULL AUTO_INCREMENT,"
				+ "source VARCHAR(50),data text,"
				+ "time BIGINT,"
				+ "p_type INT,"
				+ "phone text,"
				+ "PRIMARY KEY (id))";
		sql = String.format(sql, name);
		try {
			Statement st = conn.prepareStatement(sql);
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
