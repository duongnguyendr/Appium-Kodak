package com.cinatic.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.ios.IOSDriver;

public class DriverIOS extends DriverBase {

	public DriverIOS(DriverSetting driverSetting) throws MalformedURLException {
		super(driverSetting);
		
		if (driverSetting.getApk().equals("UnoApp.apk")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("automationName", "XCUITest");
			cap.setCapability("platformName", driverSetting.getPlatformName());
			cap.setCapability("deviceName", driverSetting.getDeviceName());
			cap.setCapability("platformVersion", driverSetting.getDeviceVersion());
			cap.setCapability("bundleId", "com.binatonetelecom.hubble");
			cap.setCapability("noReset", true);
			cap.setCapability("newCommandTimeout", 300);
			cap.setCapability("showIOSLog", true);
			cap.setCapability("clearSystemFiles", true);
			cap.setCapability("usePrebuiltWDA", true);
			cap.setCapability("udid", driverSetting.getDeviceUDID());

			this.setAppiumDriver(new IOSDriver<WebElement>(new URL(driverSetting.getRemoteURL()), cap));
		}
		else if(driverSetting.getApk().equals("bt.apk")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("automationName", "XCUITest");
			cap.setCapability("platformName", driverSetting.getPlatformName());
			cap.setCapability("deviceName", driverSetting.getDeviceName());
			cap.setCapability("platformVersion", driverSetting.getDeviceVersion());
			cap.setCapability("bundleId", "com.hubble.btappphase1");
			cap.setCapability("noReset", true);
			cap.setCapability("newCommandTimeout", 300);
			cap.setCapability("showIOSLog", true);
			cap.setCapability("clearSystemFiles", true);
			cap.setCapability("usePrebuiltWDA", true);
			cap.setCapability("udid", driverSetting.getDeviceUDID());

			this.setAppiumDriver(new IOSDriver<WebElement>(new URL(driverSetting.getRemoteURL()), cap));
		}
		else if(driverSetting.getApk().equals("Hubble.apk")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("automationName", "XCUITest");
			cap.setCapability("platformName", driverSetting.getPlatformName());
			cap.setCapability("deviceName", driverSetting.getDeviceName());
			cap.setCapability("platformVersion", driverSetting.getDeviceVersion());
			cap.setCapability("bundleId", "com.binatonetelecom.hubble");
			cap.setCapability("noReset", true);
			cap.setCapability("newCommandTimeout", 300);
			cap.setCapability("showIOSLog", true);
			cap.setCapability("clearSystemFiles", true);
			cap.setCapability("usePrebuiltWDA", true);
			cap.setCapability("udid", driverSetting.getDeviceUDID());

			this.setAppiumDriver(new IOSDriver<WebElement>(new URL(driverSetting.getRemoteURL()), cap));
		}
		else if(driverSetting.getApk().equals("HubbleHome.apk")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("automationName", "XCUITest");
			cap.setCapability("platformName", driverSetting.getPlatformName());
			cap.setCapability("deviceName", driverSetting.getDeviceName());
			cap.setCapability("platformVersion", driverSetting.getDeviceVersion());
			cap.setCapability("bundleId", "com.hubbleconnected.camera");
			cap.setCapability("noReset", true);
//			cap.setCapability("simpleIsVisibleCheck", true);
//			cap.setCapability("preventWDAAttachments", true);
			cap.setCapability("newCommandTimeout", 300);
			cap.setCapability("showIOSLog", true);
			cap.setCapability("clearSystemFiles", true);
			cap.setCapability("usePrebuiltWDA", true);
			cap.setCapability("udid", driverSetting.getDeviceUDID());
			cap.setCapability("xcodeOrgId", "xcodeOrgId");
			cap.setCapability("xcodeSigningId", "iOS");

			this.setAppiumDriver(new IOSDriver<WebElement>(new URL(driverSetting.getRemoteURL()), cap));
		}
	}
}