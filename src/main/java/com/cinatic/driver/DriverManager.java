package com.cinatic.driver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import com.cinatic.StringHelper;

public class DriverManager {

	private static Map<Long, Driver> hashDriver = new ConcurrentHashMap<Long, Driver>();

	public static synchronized void storeWebDriver(Driver driver) {
		hashDriver.put(Thread.currentThread().getId(), driver);
	}

	public static synchronized Driver getAppiumDriver() {
		return hashDriver.get(Thread.currentThread().getId());
	}

	public static synchronized void removeWebDriver() {
		hashDriver.remove(Thread.currentThread().getId());
	}

	public static synchronized void createWebDriver(DriverSetting driverSetting) {
		Driver driver = null;
		if (driverSetting.getPlatformName().toLowerCase().equals("android")) {
			try {
				driver = new DriverAndroid(driverSetting);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (driverSetting.getPlatformName().toLowerCase().equals("ios")) {
			try {
				driver = new DriverIOS(driverSetting);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		if (driver != null)
			storeWebDriver(driver);
	}

	public static void captureScreen(String filename) throws InterruptedException {
		try {
			String systemPath = StringHelper.getSystemPath();			
			String fullPath = systemPath + "/html/" + filename + ".png";
			System.out.println(fullPath);

			new File(fullPath).mkdirs();
			ImageIO.write(getAppiumDriver().captureScreenshot(), "png", new File(fullPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
