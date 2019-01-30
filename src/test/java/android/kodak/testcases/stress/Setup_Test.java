package android.kodak.testcases.stress;

import java.io.IOException;
import java.text.ParseException;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
import com.cinatic.object.Terminal;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import jssc.SerialPortException;

public class Setup_Test {
	private static ApplicationContext context;
	private AppiumHelper appium;

	private String c_platform, c_server, c_username, c_password, c_deviceid, c_devicessid, c_comport,
			c_wifipassword, tc_name;
	private String start_setup, end_setup;
	private Device c_device;

	private DriverSetting driverSetting;

	//MySqlDbHelper db = new MySqlDbHelper();
	DatabaseHelper db = new DatabaseHelper();
	Random ran = new Random();
	LucyApiHelper api;
	PageDashboard devicePage;
	Terminal terminal;

	@Parameters({ "platform", "server", "username", "password", "deviceid", "devicessid", "comport", "wifiname",
			"wifipassword" })
	@BeforeSuite
	public void berforeSuite(String platform, String server, String username, String password, String deviceid,
			String devicessid, String comport, String wifiname, String wifipassword)
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
		this.c_devicessid = devicessid;
		this.c_comport = comport;
		this.c_wifipassword = wifipassword;

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

		if (this.c_server.equals("production")) {
			api = new LucyApiHelper(c_server, c_username);
			api.userLogin(c_username, c_password);
		} else if (this.c_server.equals("staging")) {
			api = new LucyApiHelper(TestConstant.internalStagingURI);
			api.userLogin(c_username, c_password);
		}

//		launchApp();
//		Get_Started_page page = new Get_Started_page();
//		devicePage = page.goToSigninPage().loginApp(c_username, c_password);
//		closeApp();
	}

	@DataProvider(name = "Data")
	public Object[][] createData() {
		return new Object[][] { { "Setup_001" }, { "Setup_02" }, { "Setup_03" }, { "Setup_04" }, { "Setup_05" },
				{ "Setup_06" }, { "Setup_07" }, { "Setup_08" }, { "Setup_09" }, { "Setup_10" }, { "Setup_11" },
				{ "Setup_12" }, { "Setup_13" }, { "Setup_14" }, { "Setup_15" }, { "Setup_16" }, { "Setup_17" },
				{ "Setup_18" }, { "Setup_19" }, { "Setup_20" }, };
	}
	@Test(priority = 1, dataProvider = "Data")
	public void TC_Setup(String tc_name) throws SerialPortException, InterruptedException {
		this.tc_name = tc_name;
		terminal = new Terminal(c_comport);
		Log.info(tc_name);

		launchApp();
		start_setup = StringHelper.getCurrentDateTime();
		
		// in case app already login and ask for Media access permission
		try { 
			devicePage.getAndroidPermissionAllowBtn().click();
		} catch (Exception e) {
		// If not then it's not login yet, login to app
			PageGetStart page = new PageGetStart();
			devicePage = page.goToSigninPage().loginApp(c_username, c_password);
			try {
			// try to allow media permission request if there's any
				devicePage.getAndroidPermissionAllowBtn().click();
			} catch (Exception e2) {
			}
		}
		
		devicePage.getAddDeviceBigBtn().click();
		
		devicePage.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_LOCATION);
		
		devicePage.getBabySeriesImage().click();
		devicePage.getDeviceModelLabel("520").click();
		terminal.sendCommand("pair", "start_pairing_mode", 10);
		devicePage.getContinueButton().click();
		devicePage.getContinueButton().click();
		if (devicePage.getSSIDLabel(c_devicessid).getWebElement() != null) {
			devicePage.getSSIDLabel(c_devicessid).click();
		} else {
			devicePage.getSSIDRefreshImage().click();
			devicePage.getSSIDLabel(c_devicessid).click();
		}
		devicePage.getTextWifiPasswordTextbox().sendKeys(c_wifipassword);		
		devicePage.getContinueButton().click();
		if(devicePage.getMobileDataContinueButton().getWebElement()!=null) {
			devicePage.getMobileDataContinueButton().click();
		}
		devicePage.getCustomButton().click();
		devicePage.getContinueButton().click();
		devicePage.getDoneButton().click();
		
		
		end_setup = StringHelper.getCurrentDateTime();
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) throws SerialPortException, ParseException, InterruptedException, IOException {
		DriverManager.captureScreen(tc_name);

		Log.info("----------Information----------");

		Log.info(tc_name + "_start_setup: " + start_setup);
		Log.info(tc_name + "_end_setup: " + end_setup);

		Log.info("----------End Information------");

		long setup_duration = StringHelper.getDuration(start_setup, end_setup);
		Log.info(tc_name + "_duration_setup: " + setup_duration);

		if (result.isSuccess()) {
			api.userLogin(c_username, c_password);
			api.getDevices();
			c_device = api.getDeviceByDeviceId(c_deviceid);
		} else {
			c_device = new Device();
			c_device.setDevice_id(c_deviceid);
			c_device.setName("");
			c_device.setFirmware_version("");
		}
		String systemPath = StringHelper.getSystemPath();
		String fileName = (tc_name + "_" + start_setup.replace(":", "-")).toLowerCase() + ".txt";
		String fullPath = systemPath + "/html/" + fileName;
		String fileUrl = StringHelper.getBuildPath() + fileName;
		System.out.println(fullPath);

		String errorCode = "";
		String deviceLog = "";

		TerminalHelper.exportLogCatLucy(driverSetting.getDeviceUDID(), fullPath);
		TerminalHelper.clearLogCat(driverSetting.getDeviceUDID());

		int code = db.doInsertSetupData(c_device, setup_duration, start_setup, end_setup, c_platform, errorCode, fileUrl,
				c_username + "/" + c_password, "SETUP PERFORMANCE");
		closeApp();
		terminal.closePort();
		
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
