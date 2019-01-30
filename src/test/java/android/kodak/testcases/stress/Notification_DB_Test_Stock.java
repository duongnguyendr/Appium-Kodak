package android.kodak.testcases.stress;

import java.io.IOException;
import java.text.ParseException;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.DatabaseHelper;
import com.cinatic.FileHelper;
import com.cinatic.LucyApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverSetting;
import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.cinatic.object.Event;

import android.kodak.object.PopupNotification;

public class Notification_DB_Test_Stock {
	private static ApplicationContext context;
	private String c_platform, c_username, c_password, c_deviceid, c_comport, tc_name;
	private String start_event, end_event, eventid, deviceLog;
	private Device c_device;

	private DriverSetting driverSetting;

	DatabaseHelper db = new DatabaseHelper();
	Random ran = new Random();
	LucyApiHelper api;

	@Parameters({ "platform", "server", "username", "password", "deviceid", "comport" })
	@BeforeSuite
	public void berforeSuite(String platform, String server, String username, String password, String deviceid,
			String comport) throws IOException, InterruptedException {
		context = new ClassPathXmlApplicationContext("Bean.xml");
		String systemPath = StringHelper.getSystemPath();
		FileHelper.clearFolder(systemPath + "/html/");
		FileHelper.clearFolder(systemPath + "/allure-results/");

		this.c_platform = platform;
		this.c_username = username;
		this.c_password = password;
		this.c_deviceid = deviceid;
		this.c_comport = comport;

		if (System.getProperty("suitePlatform") != null) {
			this.c_platform = System.getProperty("suitePlatform");
		}

		if (System.getProperty("suiteUsername") != null) {
			this.c_username = System.getProperty("suiteUsername");
		}

		if (System.getProperty("suitePassword") != null) {
			this.c_password = System.getProperty("suitePassword");
		}

		if (System.getProperty("suiteUdid") != null) {
			this.c_deviceid = System.getProperty("suiteUdid");
		}

		api = new LucyApiHelper(server, c_username);
		api.userLogin(this.c_username, this.c_password);
		api.getDevices();
		c_device = api.getDeviceByDeviceId(c_deviceid);

		launchApp();
//		 Get_Started_page page = new Get_Started_page();
//		 Device_page devicePage = page.goToSigninPage().loginApp(c_username, c_password, true);
	}

	@DataProvider(name = "Data")
	public Object[][] createData() {
		return new Object[][] { { "Event_01" },	{ "Event_02" },	{ "Event_03" }, { "Event_04" }, { "Event_05" },
								{ "Event_06" }, { "Event_07" }, { "Event_08" }, { "Event_09" }, { "Event_10" },
								{ "Event_11" },	{ "Event_12" }, { "Event_13" }, { "Event_14" }, { "Event_15" },
								{ "Event_16" }, { "Event_17" }, { "Event_18" }, { "Event_19" }, { "Event_20" },
								};
	}

	@Test(priority = 1, dataProvider = "Data")
	public void TC_Event(String tc_name) throws InterruptedException, IOException {
		this.tc_name = tc_name;
		Log.info(tc_name);
		Log.info(c_device.getName());
		eventid = "";
		deviceLog = "";

		TerminalHelper.clearLogCat(driverSetting.getDeviceUDID());
		PopupNotification popup = new PopupNotification();
		start_event = StringHelper.getCurrentDateTime();
		TerminalHelper.sendCommand(c_comport, "y", "");
		TerminalHelper.sendCommand(c_comport, "atecmd ver", "");
		TerminalHelper.sendCommand(c_comport, "atecmd calling", "OK");
		int count = 0;
		boolean hasEvent = false;
		while (count < 30) {
			deviceLog = TerminalHelper.getLogCat(driverSetting.getDeviceUDID(), "MqttPushService", c_device.getDevice_id());
			if (deviceLog.contains("Doorbell pressed detected")) {
				end_event = StringHelper.getCurrentDateTime();
				eventid = StringHelper.getStringByString(deviceLog, "ev_id\":\"", "\",\"", false);
				hasEvent = true;
				break;
			}
			TimeHelper.sleep(1000);
			count++;
		}

		Assert.assertTrue(hasEvent, "Error: there is no push notification event");
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) throws ParseException, InterruptedException, IOException {
		// DriverManager.captureScreen(tc_name);

		Log.info("----------Information----------");

		Log.info(tc_name + "_start_event: " + start_event);
		Log.info(tc_name + "_end_event: " + end_event);

		Log.info("----------End Information------");

		long event_duration = StringHelper.getDuration(start_event, end_event);
		Log.info(tc_name + "_duration_event: " + event_duration);

		String systemPath = StringHelper.getSystemPath();
		String fileName = (tc_name + "_" + start_event.replace(":", "-")).toLowerCase() + ".txt";
		String fullPath = systemPath + "/html/" + fileName;
		String fileUrl = StringHelper.getBuildPath() + fileName;
		System.out.println(fullPath);

		String errorCode = "";
		if (eventid.equals("")) {
			errorCode = "No push notification event";
		} else {
			int retry = 30;
			errorCode = "No snapshot. EventId=" + eventid;
			while (retry > 0) {
				api.getEventsByDeviceId(c_deviceid);
				Event o = api.getEventByEventId(eventid);
				if (o != null && !o.getSnapshot().equals("")) {
					errorCode = "Has snapshot. EventId=" + eventid;
					break;
				}
				TimeHelper.sleep(2 * 1000);
				retry--;
			}
		}

		TerminalHelper.exportLogCatLucy(driverSetting.getDeviceUDID(), fullPath);
		TerminalHelper.clearLogCat(driverSetting.getDeviceUDID());
		db.doInsertNotificationData(c_device, event_duration, start_event, end_event, c_platform, errorCode, fileUrl,
				c_username + "/" + c_password, "NOTIFICATION PERFORMANCE");
		TimeHelper.sleep(60 * 1000);
	}

	private void launchApp() {
		 driverSetting = (DriverSetting) context.getBean(c_platform);
//		 appium = new AppiumHelper(driverSetting.getRemoteURL(),driverSetting.getPlatformName());
//		 appium.startAppiumServer();
//		 DriverManager.createWebDriver(driverSetting);
	}
}
