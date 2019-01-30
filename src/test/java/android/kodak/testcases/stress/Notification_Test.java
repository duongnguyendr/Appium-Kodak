package android.kodak.testcases.stress;

import java.io.IOException;
import java.text.ParseException;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.AppiumHelper;
import com.cinatic.DatabaseHelper;
import com.cinatic.FileHelper;
import com.cinatic.LucyApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.TimeHelper;
import com.cinatic.constant.TestConstant;
import com.cinatic.driver.DriverManager;
import com.cinatic.driver.DriverSetting;
import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.jayway.restassured.response.Response;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PopupNotification;

public class Notification_Test {
	private static ApplicationContext context;
	private AppiumHelper appium;

	private String c_platform, c_username, c_password, c_deviceid, c_device_token, tc_name;
	private String start_event, end_event, eventid;
	private Device c_device;

	private DriverSetting driverSetting;

	DatabaseHelper db = new DatabaseHelper();
	Random ran = new Random();
	LucyApiHelper api;

	@Parameters({ "platform", "server", "username", "password", "deviceid", "device_token" })
	@BeforeSuite
	public void berforeSuite(String platform, String server, String username, String password, String deviceid,
			String device_token) throws IOException, InterruptedException {
		context = new ClassPathXmlApplicationContext("Bean.xml");
		String systemPath = StringHelper.getSystemPath();
		FileHelper.clearFolder(systemPath + "/html/");
		FileHelper.clearFolder(systemPath + "/allure-results/");

		this.c_platform = platform;
		this.c_username = username;
		this.c_password = password;
		this.c_deviceid = deviceid;
		this.c_device_token = device_token;

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

		api = new LucyApiHelper(TestConstant.internalBaseURI);
		api.userLogin("minhth", "Aaaa1111");
		api.getDevices();
		c_device = api.getDeviceByDeviceId(c_deviceid);

		launchApp();
		PageGetStart page = new PageGetStart();
		PageDashboard devicePage = page.checkAndSignin(c_username, c_password);
	}

	@DataProvider(name = "Data")
	public Object[][] createData() {
		return new Object[][] { { "Event_01" }, { "Event_02" }, { "Event_03" }, { "Event_04" }, { "Event_05" },
				{ "Event_06" }, { "Event_07" }, { "Event_08" }, { "Event_09" }, { "Event_10" }, { "Event_11" },
				{ "Event_12" }, { "Event_13" }, { "Event_14" }, { "Event_15" }, { "Event_16" }, { "Event_17" },
				{ "Event_18" }, { "Event_19" }, { "Event_20" }, };
	}

	@Test(priority = 1, dataProvider = "Data")
	public void TC_Event(String tc_name) throws InterruptedException {
		this.tc_name = tc_name;
		Log.info(tc_name);
		Log.info(c_device.getName());
		eventid = "";

		PopupNotification popup = new PopupNotification();
		start_event = StringHelper.getCurrentDateTime();
		Response response = api.addNewEvent(c_deviceid, c_device_token, 1);
		int statusCode = response.path("status");
		Assert.assertEquals(statusCode, 200, "Error: status code is: " + statusCode);
		String success_action_redirect = response.path("data.storage_data.success_action_redirect");
		eventid = StringHelper.getStringByString(success_action_redirect, "event_id=", "&", false);
		popup.getCloseButton().click();
		end_event = StringHelper.getCurrentDateTime();
		TimeHelper.sleep((ran.nextInt(120) + 60) * 1000);
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) throws ParseException, InterruptedException, IOException {
		DriverManager.captureScreen(tc_name);

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
		String deviceLog = "";

		deviceLog = TerminalHelper.getLogCat(driverSetting.getDeviceUDID(), "LucyJPushReceiver",
				c_device.getDevice_id());
		Pattern p = Pattern.compile("\"event_id\":\"" + eventid + "\"");
		java.util.regex.Matcher m = p.matcher(deviceLog);
		String strLastLine = "";
		while (m.find()) {
			if (!errorCode.contains(m.group() + ","))
				errorCode += m.group() + ",";
		}
		if (errorCode.length() > 0)
			errorCode = errorCode.substring(0, errorCode.length() - 1);

		TerminalHelper.exportLogCatLucy(driverSetting.getDeviceUDID(), fullPath);
		TerminalHelper.clearLogCat(driverSetting.getDeviceUDID());

		db.doInsertNotificationData(c_device, event_duration, start_event, end_event, c_platform, errorCode, fileUrl,
				c_username + "/" + c_password, "NOTIFICATION PERFORMANCE");
	}

	private void launchApp() {
		driverSetting = (DriverSetting) context.getBean(c_platform);
		appium = new AppiumHelper(driverSetting.getRemoteURL(), driverSetting.getPlatformName());
		appium.startAppiumServer();
		DriverManager.createWebDriver(driverSetting);
	}
}
