package com.cinatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cinatic.log.Log;
import com.cinatic.object.Camera;
import com.cinatic.object.Device;

public class DatabaseHelper {
	private static final String DB_DRIVER_SQL="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DB_SERVER="192.168.1.197";
	private static final String DB_NAME="QADB";
	
	private static final String DBO_Setup="[dbo].[Setup]";
	private static final String DBO_Notificaion="[dbo].[Notification]";
	private static final String DBO_Stream="[dbo].[Streaming]";
	private static final String DBO_Account="[dbo].[Account]";
	private static final String DBO_Video="[dbo].[Video]";
	
	private static final String DB_user="sa";
	private static final String DB_passwd="sa";
	
	Connection conn;

	public DatabaseHelper() {
		connect();
	}

	public DatabaseHelper(String os) {
			connect();
	}

	public void connect() {
		try {
			Class.forName(DB_DRIVER_SQL);
			String url="";
			
			switch (System.getProperty("os.name").toLowerCase()) {
			case "windows":
			case "linux":
				url = "jdbc:sqlserver://" + DB_SERVER + ";databaseName=" + DB_NAME;
				break;
			case "mac":
				url = "jdbc:sqlserver://qa-server.local" + ";databaseName=" + DB_NAME;
				break;				
			}		
			
			conn = DriverManager.getConnection(url, DB_user, DB_passwd);
			Log.info("connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ResultSet executeQuery(String sql) {
		try {
			Log.info(sql);
			Statement statement = conn.createStatement();
			return statement.executeQuery(sql);
		} catch (Exception ex) {
			return null;
		}
	}

	protected int executeUpdate(String sql) {
		try {
			Log.info(sql);
			Statement statement = conn.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
			return -1;
		}
	}

	public int doInsertStreamData(Camera c_camera, String cameraMode, long streaming_duration, long status_duration,
			String startTimeStatus, String endTimeStatus, String startTimeMode, String endTimeMode, String note, String device_os, String account) {
		if (!startTimeStatus.equals(""))
			return executeUpdate(String.format(
					"insert into Streaming(camera_name, camera_udid, firmware_version, streaming_mode, streaming_duration, status_duration, start_time_status, end_time_status, start_time_mode, end_time_mode, note, device_os, account) values('%s', '%s', '%s', '%s', %d, %d, '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
					c_camera.getName(), c_camera.getRegistration_id(), c_camera.getFirmware_version(), cameraMode,
					streaming_duration, status_duration, startTimeStatus, endTimeStatus, startTimeMode, endTimeMode,
					note, device_os, account));
		else
			return executeUpdate(String.format(
					"insert into Streaming(camera_name, camera_udid, firmware_version, streaming_mode, streaming_duration, start_time_mode, end_time_mode, note, device_os, account) values('%s', '%s', '%s', '%s', %d, '%s', '%s', '%s', '%s', '%s')",
					c_camera.getName(), c_camera.getRegistration_id(), c_camera.getFirmware_version(), cameraMode,
					streaming_duration, startTimeMode, endTimeMode, note, device_os, account));

	}

	public int doInsertStreamData(Camera c_camera, String cameraMode, long streaming_duration, long status_duration,
			String startTimeStatus, String endTimeStatus, String startTimeMode, String endTimeMode, String note,
			String deviceLog, String fileUrl, String device_os, String account) {
		if (!startTimeStatus.equals(""))
			return executeUpdate(String.format(
					"insert into Streaming(camera_name, camera_udid, firmware_version, streaming_mode, streaming_duration, status_duration, start_time_status, end_time_status, start_time_mode, end_time_mode, note, device_log, file_url, device_os, account) values('%s', '%s', '%s', '%s', %d, %d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
					c_camera.getName(), c_camera.getRegistration_id(), c_camera.getFirmware_version(), cameraMode,
					streaming_duration, status_duration, startTimeStatus, endTimeStatus, startTimeMode, endTimeMode,
					note, deviceLog, fileUrl, device_os, account));
		else
			return executeUpdate(String.format(
					"insert into Streaming(camera_name, camera_udid, firmware_version, streaming_mode, streaming_duration, start_time_mode, end_time_mode, note, device_log, file_url, device_os, account) values('%s', '%s', '%s', '%s', %d, '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
					c_camera.getName(), c_camera.getRegistration_id(), c_camera.getFirmware_version(), cameraMode,
					streaming_duration, startTimeMode, endTimeMode, note, deviceLog, fileUrl, device_os, account));

	}
	
	public int doInsertStreamData(Device c_device, String streaming_mode, long streaming_duration, String start_time_mode, String end_time_mode, long status_duration, String start_time_status, String end_time_status, String platform, String devicelog, String jenkins_url, String account, String test_type) {		
		String sql = String.format("INSERT INTO [dbo].[Streaming]\r\n" + 
				"           ([device_name]\r\n" + 
				"           ,[device_id]\r\n" + 
				"           ,[firmware_version]\r\n" + 
				"           ,[streaming_mode]\r\n" + 
				"           ,[streaming_duration]\r\n" + 
				"           ,[start_time_mode]\r\n" + 
				"           ,[end_time_mode]\r\n" + 
				"           ,[status_duration]\r\n" + 
				"           ,[start_time_status]\r\n" + 
				"           ,[end_time_status]\r\n" + 
				"           ,[platform]\r\n" + 
				"           ,[devicelog]\r\n" + 
				"           ,[jenkins_url]\r\n" + 
				"           ,[account]\r\n" + 
				"           ,[test_type])\r\n" + 
				"     VALUES\r\n" + 
				"           ('%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,%d\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,%d\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s')", c_device.getName(), c_device.getDevice_id(), c_device.getFirmware_version(), streaming_mode, streaming_duration, start_time_mode, end_time_mode, status_duration, start_time_status, end_time_status, platform, devicelog, jenkins_url, account, test_type);
		Log.info(sql);
		return executeUpdate(sql);
	}	
	
	public int doInsertVideoData(Device c_device, String streaming_mode, long streaming_duration, String start_time_mode, String end_time_mode, long status_duration, String start_time_status, String end_time_status, String platform, String devicelog, String jenkins_url, String account, String test_type) {		
		String sql = String.format("INSERT INTO [dbo].[Video]\r\n" + 
				"           ([device_name]\r\n" + 
				"           ,[device_id]\r\n" + 
				"           ,[firmware_version]\r\n" + 
				"           ,[streaming_mode]\r\n" + 
				"           ,[streaming_duration]\r\n" + 
				"           ,[start_time_mode]\r\n" + 
				"           ,[end_time_mode]\r\n" + 
				"           ,[status_duration]\r\n" + 
				"           ,[start_time_status]\r\n" + 
				"           ,[end_time_status]\r\n" + 
				"           ,[platform]\r\n" + 
				"           ,[devicelog]\r\n" + 
				"           ,[jenkins_url]\r\n" + 
				"           ,[account]\r\n" + 
				"           ,[test_type])\r\n" + 
				"     VALUES\r\n" + 
				"           ('%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,%d\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,%d\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s')", c_device.getName(), c_device.getDevice_id(), c_device.getFirmware_version(), streaming_mode, streaming_duration, start_time_mode, end_time_mode, status_duration, start_time_status, end_time_status, platform, devicelog, jenkins_url, account, test_type);
		Log.info(sql);
		return executeUpdate(sql);
	}
			
	public int doInsertNotificationData(Device c_device, long event_duration, String start_time_event, String end_time_event, String platform, String devicelog, String jenkins_url, String account, String test_type) {		
		String sql = String.format("INSERT INTO [dbo].[Notification]\r\n" + 
				"           ([device_name]\r\n" + 
				"           ,[device_id]\r\n" + 
				"           ,[firmware_version]\r\n" + 
				"           ,[event_duration]\r\n" + 
				"           ,[start_time_event]\r\n" + 
				"           ,[end_time_event]\r\n" + 
				"           ,[platform]\r\n" +
				"           ,[devicelog]\r\n" +
				"           ,[jenkins_url]\r\n" + 
				"           ,[account]\r\n" + 
				"           ,[test_type])\r\n" + 				
				"     VALUES\r\n" + 
				"           ('%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,%s\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" +
				"           ,'%s'\r\n" +
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s')", c_device.getName(), c_device.getDevice_id(), c_device.getFirmware_version(), event_duration, start_time_event, end_time_event, platform, devicelog, jenkins_url, account, test_type);		
		return executeUpdate(sql);
	}	
	
	public int doInsertSetupData(Device c_device, long event_duration, String start_time_event, String end_time_event, String platform, String devicelog, String jenkins_url, String account, String test_type) {		
		String sql = String.format("INSERT INTO [dbo].[Setup]\r\n" + 
				"           ([device_name]\r\n" + 
				"           ,[device_id]\r\n" + 
				"           ,[firmware_version]\r\n" + 
				"           ,[event_duration]\r\n" + 
				"           ,[start_time_event]\r\n" + 
				"           ,[end_time_event]\r\n" + 
				"           ,[platform]\r\n" +
				"           ,[devicelog]\r\n" +
				"           ,[jenkins_url]\r\n" + 
				"           ,[account]\r\n" + 
				"           ,[test_type])\r\n" + 				
				"     VALUES\r\n" + 
				"           ('%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,%s\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" +
				"           ,'%s'\r\n" +
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s')", c_device.getName(), c_device.getDevice_id(), c_device.getFirmware_version(), event_duration, start_time_event, end_time_event, platform, devicelog, jenkins_url, account, test_type);		
		return executeUpdate(sql);
	}	
	
	public int doInsertAccount(String username, String password, String server) {
		String sql = String.format("INSERT INTO [dbo].[Account]\r\n" + 
				"           ([username]\r\n" + 
				"           ,[password]\r\n" + 
				"           ,[server])\r\n" + 				
				"     VALUES\r\n" + 
				"           ('%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s')", username, password, server);		
		return executeUpdate(sql);
	}
}
