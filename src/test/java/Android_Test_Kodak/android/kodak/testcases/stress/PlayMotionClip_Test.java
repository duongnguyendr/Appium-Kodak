package Android_Test_Kodak.android.kodak.testcases.stress;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
import com.cinatic.driver.DriverManager;
import com.cinatic.driver.DriverSetting;
import com.cinatic.log.Log;
import com.cinatic.object.Device;

import Android_Test_Kodak.android.kodak.object.PageDashboard;
import Android_Test_Kodak.android.kodak.object.PageGetStart;
import Android_Test_Kodak.android.kodak.object.PageStreamView;
import io.appium.java_client.android.AndroidKeyCode;

public class PlayMotionClip_Test {
	private static ApplicationContext context;
	private AppiumHelper appium;

	private String c_platform, c_server, c_username, c_password, c_deviceid, tc_name;
	private String start_status, end_status, device_status, start_mode, end_mode, device_mode;
	private String start_video, end_video;
	private Device c_device;

	private DriverSetting driverSetting;
	private boolean hasStream;
	private String playSuccess;

	DatabaseHelper db = new DatabaseHelper();
	PageDashboard devicePage = new PageDashboard();

	@Parameters({ "platform", "server", "username", "password", "deviceid" })
	@BeforeSuite
	public void berforeSuite(String platform, String server, String username, String password, String deviceid)
			throws IOException, InterruptedException {
		context = new ClassPathXmlApplicationContext("Bean.xml");
		String systemPath = StringHelper.getSystemPath();
		FileHelper.clearFolder(systemPath + "/html/");
		FileHelper.clearFolder(systemPath + "/allure-results/");

		this.c_server = server;
		this.c_platform = platform;
		this.c_username = username;
		this.c_password = password;
		this.c_deviceid = deviceid;

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

		LucyApiHelper api;
//		api = new LucyApiHelper(TestConstant.getKodakUri(this.c_server));
		api = new LucyApiHelper(c_server, c_username);
		api.userLogin(c_username, c_password);
		api.getDevices();
		c_device = api.getDeviceByDeviceId(c_deviceid);

		launchApp();

		PageGetStart page = new PageGetStart();
		PageDashboard devicePage = page.checkAndSignin(c_username, c_password);

		closeApp();

	}

	@DataProvider(name = "Data")
	public Object[][] createData() {
		return new Object[][] { { "Streaming_01" }
//		, { "Streaming_02" }, { "Streaming_03" }, { "Streaming_04" },
//				{ "Streaming_05" }, { "Streaming_06" }, { "Streaming_07" }, { "Streaming_08" }, { "Streaming_09" },
//				{ "Streaming_10" }, { "Streaming_11" }, { "Streaming_12" }, { "Streaming_13" }, { "Streaming_14" },
//				{ "Streaming_15" }, { "Streaming_16" }, { "Streaming_17" }, { "Streaming_18" }, { "Streaming_19" },
//				{ "Streaming_20" }, 
		};
	}

	@BeforeMethod
	public void beforeMethod() throws InterruptedException {
		launchApp();
	}

	@Test(priority = 1, dataProvider = "Data")
	public void TC_View_Stream(String tc_name) throws InterruptedException {
		this.tc_name = tc_name;
		Log.info(tc_name);
		Log.info(c_device.getName());

		device_mode = "Drop internet";
		hasStream = false;
		playSuccess = "Play video clip failed";

		start_status = StringHelper.getCurrentDateTime();
		device_status = devicePage.handlePermissionsAndHintsWhenPageOpen().waitForDeviceOnline(c_device.getName(), 30);
		end_status = StringHelper.getCurrentDateTime();

		if (device_status.equals("Online")) {
			device_mode = "";
			PageStreamView viewDevicePage = devicePage.selectDeviceToView(c_device.getName());
			hasStream = viewDevicePage.getStreamLoadingImg().waitForDisappear(90);
			device_mode = viewDevicePage.getStreamMode(60);
			
			viewDevicePage.getFirstMotionDetectionButton().click();
			start_video = StringHelper.getCurrentDateTime();
			viewDevicePage.getVideoLoading().waitForDisappear(60);
			end_video = StringHelper.getCurrentDateTime();
			if (viewDevicePage.getVideoPlaySeekbar().getWebElement() != null)
				playSuccess = "Play video clip passed";

			DriverManager.getAppiumDriver().pressKeyCode(AndroidKeyCode.BACK);
			start_mode = StringHelper.getCurrentDateTime();
			hasStream = viewDevicePage.getStreamLoadingImg().waitForDisappear(90);
			device_mode = viewDevicePage.getStreamMode(60);
			end_mode = StringHelper.getCurrentDateTime();
			
			Assert.assertEquals(device_mode.contains("OK"), true, "Error: actual mode is " + device_mode);
			Assert.assertTrue(hasStream, "Error: Load streaming is " + hasStream);
		} else {
			device_mode = "";
			Assert.assertTrue(false, "Error: Camera is not online");
		}
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) throws ParseException, InterruptedException, IOException {
		DriverManager.captureScreen(tc_name);

		Log.info("----------Information----------");

		Log.info(tc_name + "_device_mode: " + device_mode);
		Log.info(tc_name + "_start_status: " + start_status);
		Log.info(tc_name + "_end_status: " + end_status);
		Log.info(tc_name + "_start_device: " + start_mode);
		Log.info(tc_name + "_end_device: " + end_mode);
		Log.info(tc_name + "_start_video: " + start_video);
		Log.info(tc_name + "_end_video: " + end_video);

		Log.info("----------End Information------");

		long status_duration = StringHelper.getDuration(start_status, end_status);
		Log.info(tc_name + "_duration_status: " + status_duration);

		long streaming_duration = StringHelper.getDuration(start_mode, end_mode);
		Log.info(tc_name + "_duration_cam: " + StringHelper.getDuration(start_mode, end_mode));

		String systemPath = StringHelper.getSystemPath();
		String fileName = (tc_name + "_" + start_status.replace(":", "-")).toLowerCase() + ".txt";
		String fullPath = systemPath + "/html/" + fileName;
		String fileUrl = StringHelper.getBuildPath() + fileName;
		System.out.println(fullPath);

		String errorCode = "";
		String deviceLog = "";

		// deviceLog = TerminalHelper.getLogCat(driverSetting.getDeviceUDID(),
		// "errorCode", c_device.getDevice_id());
		// Pattern p = Pattern.compile("errorCode = -?\\d+");
		// java.util.regex.Matcher m = p.matcher(deviceLog);
		// String strLastLine = "";
		// while (m.find()) {
		// if (!errorCode.contains(m.group() + ","))
		// errorCode += m.group() + ",";
		// }
		// if (errorCode.length() > 0)
		// errorCode = errorCode.substring(0, errorCode.length() - 1);
		//
		TerminalHelper.exportLogCatLucy(driverSetting.getDeviceUDID(), fullPath);
		TerminalHelper.clearLogCat(driverSetting.getDeviceUDID());

		if (!device_status.equals("Online")) {
			errorCode = device_status + " " + errorCode;
		} else {
			if (device_mode.equals("")) {
				errorCode = "Tap failed";
			} else if (hasStream == false) {
				errorCode = "Stream failed";
			} else {
				errorCode = device_mode + " - " + playSuccess;
			}
		}

		db.doInsertVideoData(c_device, device_mode, streaming_duration, start_video, end_video, status_duration,
				start_status, end_status, c_platform, errorCode, fileUrl, c_username + "/" + c_password,
				"PLAY MOTION CLIP PERFORMANCE");

		device_mode = "";
		start_status = "";
		end_status = "";
		start_mode = "";
		end_mode = "";
		start_video = "";
		end_video = "";
		closeApp();

		TimeHelper.sleep(60 * 1000);
	}

	private void launchApp() {
		driverSetting = (DriverSetting) context.getBean(c_platform);
		appium = new AppiumHelper(driverSetting.getRemoteURL(), driverSetting.getPlatformName());
		appium.startAppiumServer();
		DriverManager.createWebDriver(driverSetting);
	}

	private void closeApp() {
		DriverManager.getAppiumDriver().quit();
		appium.stopAppiumServer();
	}
}
