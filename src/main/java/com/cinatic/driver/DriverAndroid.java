package com.cinatic.driver;

import java.io.File;
import java.net.MalformedURLException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.cinatic.constant.TestConstant;

import io.appium.java_client.android.AndroidDriver;

public class DriverAndroid extends DriverBase {

	public DriverAndroid(DriverSetting driverSetting) throws MalformedURLException {
		super(driverSetting);
		DesiredCapabilities cap = new DesiredCapabilities();
		File appdir = new File(TestConstant.appFolder);
		File app = new File(appdir, driverSetting.getApk());
		
		cap.setCapability("platformName", driverSetting.getPlatformName());
		cap.setCapability("deviceName", driverSetting.getDeviceName());
		cap.setCapability("deviceVersion", driverSetting.getDeviceVersion());
		cap.setCapability("app", (new File(appdir,"app-kodak-release.apk")).toString());
		cap.setCapability("noReset", true);
		cap.setCapability("newCommandTimeout", 300);
		cap.setCapability("udid", driverSetting.getDeviceUDID());
		cap.setCapability("appActivity", "com.your.activity");
		cap.setCapability("appPackage", "com.your.package");
		cap.setCapability("automationName", "uiautomator2");
		cap.setCapability("noSign", true);
		
		switch (driverSetting.getApk()) {
			case "LucyApp.apk":
				cap.setCapability("appPackage","com.cinatic.lucyconnect");
				cap.setCapability("appActivity", "com.cinatic.demo2.activities.splash.SplashActivity");
				break;
			case "Hubble.apk":
				cap.setCapability("appPackage","com.blinkhd");
				cap.setCapability("appActivity", "com.msc3.registration.LoginOrRegistrationActivity");
				break;
			case "PopupNotifier.apk":
				cap.setCapability("appPackage","com.nlucas.popupnotifications");
				cap.setCapability("appActivity", "com.productigeeky.ui.MonitoringListActivity");
				break;
			case "bt.apk":
				cap.setCapability("appPackage","com.bt.smarthome");
				cap.setCapability("appActivity", "com.msc3.registration.LoginOrRegistrationActivity");
				break;
			case "Life.apk":
				cap.setCapability("appPackage","com.tct.life");
				cap.setCapability("appActivity", "com.tct.life.account.SplashActivity");
				break;
			case "UnoApp.apk": // same with Orbit_Neo.apk 
			case "Orbit_Neo.apk":
				cap.setCapability("appPackage","com.blinkhd");
				cap.setCapability("appActivity", "com.blinkhd.StartActivity");
				break;
			case "KodakApp.apk":
				cap.setCapability("appPackage","com.perimetersafe.kodakmonitors");
				cap.setCapability("appActivity", "com.cinatic.demo2.activities.splash.SplashActivity");
				break;
			case "KodakSmartHome.apk":
				cap.setCapability("appPackage","com.perimetersafe.kodaksmarthome");
				cap.setCapability("appActivity", "com.cinatic.demo2.activities.splash.SplashActivity");
				break;
		}
		cap.setCapability("appPackage","com.perimetersafe.kodaksmarthome");
		cap.setCapability("appActivity", "com.cinatic.demo2.activities.splash.SplashActivity");
		TestConstant.appPackage = cap.getCapability("appPackage").toString();
//		this.setAppiumDriver(new AndroidDriver<WebElement>(new URL(driverSetting.getRemoteURL()), cap));
		this.setAppiumDriver(new AndroidDriver<WebElement>(cap));
	}

}