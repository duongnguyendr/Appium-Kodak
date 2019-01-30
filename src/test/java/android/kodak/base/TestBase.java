package android.kodak.base;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.cinatic.AppiumHelper;
import com.cinatic.FileHelper;
import com.cinatic.StringHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.driver.DriverSetting;
import com.cinatic.log.Log;
import java.lang.reflect.Method;

public class TestBase {
	public String c_platform, 
				  c_server,
				  c_username,
				  c_password,
				  c_deviceid,
				  c_devicessid,
				  c_comport,
				  c_wifiname,
				  c_wifipassword,
				  tc_name;
	public DriverSetting driverSetting;
	private static ApplicationContext context;
	public AppiumHelper appium;
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		// TerminalHelper.execCmd("taskkill /f /im adb.exe"); // temporary disable this line to prevent problem when
		// running test with device over wifi ADB 		
		Log.info(String.format("----------------------START: %s ----------------------", method.getName()));

		driverSetting = (DriverSetting) context.getBean(this.c_platform);
		appium = new AppiumHelper(driverSetting.getRemoteURL(), driverSetting.getPlatformName());
		appium.startAppiumServer();
		DriverManager.createWebDriver(driverSetting);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws InterruptedException {
		String methodName = result.getMethod().getMethodName();
		DriverManager.captureScreen(methodName);
		DriverManager.getAppiumDriver().quit();
		appium.stopAppiumServer();
		Log.info("END: ----------------------" + result.getMethod().getMethodName() + "| Result code : " + result.getStatus() + "----------------------");
	}

		
	@BeforeSuite
	public void loadBean () {
		context = new ClassPathXmlApplicationContext("Bean.xml");
		String systemPath = StringHelper.getSystemPath();
		try {
			FileHelper.clearFolder("test-output/html/");
			FileHelper.clearFolder(systemPath + "/html/");
			FileHelper.clearFolder(systemPath + "/allure-results/");	
		} catch (Exception e) {
			Log.info(String.format("Got exception while clean up previous report: %s",e.getMessage()));
		}	
	}
	
	@Parameters({ "platform", "server", "username", "password",
				  "deviceid", "devicessid", "comport", "wifiname",
				  "wifipassword"})
	@BeforeClass
	public void loadParam(String platform, String server, String username, String password,
						  String deviceid, String devicessid, String comport, String wifiname, 
						  String wifipassword) throws IOException {
		this.c_platform = platform;
		this.c_server = server;
		this.c_username = username;
		this.c_password = password;
		this.c_deviceid = deviceid;
		this.c_devicessid = devicessid;
		this.c_comport = comport;
		this.c_wifiname = wifiname;
		this.c_wifipassword = wifipassword;

		if (System.getProperty("suitePlatform") != null) {
			this.c_platform = System.getProperty("suitePlatform");
		}
	}
}
