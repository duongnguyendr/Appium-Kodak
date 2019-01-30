package com.cinatic;

import java.sql.DriverManager;
import java.sql.Statement;

import com.cinatic.log.Log;
import com.cinatic.object.Device;

public class MySqlDbHelper extends DatabaseHelper {
	private static String DB_DRIVER="com.mysql.cj.jdbc.Driver";
	private static String DB_SERVER="localhost";
	private static String DB_NAME="QADB";
	
	private static String DBO_Setup="Setup";
	private static String DBO_Notificaion="Notification";
	private static String DBO_Stream="Streaming";
	private static String DBO_Account="Account";
	private static String DBO_Video="Video";
	
	private static String DB_user="sa";
	private static String DB_passwd="sa";
	
	public MySqlDbHelper() {
		connect();
	}
	
	public void connect() {
		try {
			Class.forName(DB_DRIVER);
			String url="";
			
			switch (System.getProperty("os.name").toLowerCase()) {
			case "windows":
			case "linux":
				url = "jdbc:mysql://" + DB_SERVER + "/" + DB_NAME;
				break;
			case "mac":
				url = "jdbc:mysql://qa-server.local" + ";databaseName=" + DB_NAME;
				break;				
			}		
			
			conn = DriverManager.getConnection(url, DB_user, DB_passwd);
			Log.info("connected");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void doIn(String username, String password, String server){
		String sql = String.format("INSERT INTO " + DBO_Account + "\r\n" + 
	           "(username, password, server)\r\n"
				+ "     VALUES ('%s', '%s', '%s')", username, password, server);
		try {
			Log.info(sql);
			Statement statement = conn.createStatement();
			int status = statement.executeUpdate(sql);
			System.out.println(String.format("### SQL return %d", status));
		} catch (Exception ex) {

		}
	}
	
	public int doInsertSetupData(Device c_device, long event_duration, String start_time_event, String end_time_event, String platform, String devicelog, String jenkins_url, String account, String test_type) {		
		String sql = String.format(
				"INSERT INTO " + "`" + DB_NAME + "`.`" + DBO_Setup + "`"+ "\r\n" + 
				"           (device_name, \r\n" + 
				"           device_id, \r\n" + 
				"           firmware_version, \r\n" + 
				"           event_duration, \r\n" + 
				"           start_time_event, \r\n" + 
				"           end_time_event, \r\n" + 
				"           platform, \r\n" +
				"           devicelog, \r\n" +
				"           jenkins_url, \r\n" + 
				"           account, \r\n" + 
				"           test_type) \r\n" + 				
				"     VALUES \r\n" + 
				"           ('%s', \r\n" + 
				"            '%s', \r\n" + 
				"            '%s', \r\n" + 
				"            '%s', \r\n" + 
				"            '%s', \r\n" + 
				"            '%s', \r\n" +
				"            '%s', \r\n" +
				"            '%s', \r\n" + 
				"            '%s', \r\n" + 
				"            '%s', \r\n" + 
				"            '%s')", c_device.getName(), c_device.getDevice_id(), c_device.getFirmware_version(), event_duration, start_time_event, end_time_event, platform, devicelog, jenkins_url, account, test_type);		
		
		return executeUpdate(sql);
	}
}
