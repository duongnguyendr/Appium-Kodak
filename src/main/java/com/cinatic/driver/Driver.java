package com.cinatic.driver;

import java.awt.image.BufferedImage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;

public interface Driver extends MobileDriver<WebElement> {

	AppiumDriver<?> getAppiumDriver();

	DriverSetting getDriverSetting();

	WebElement findElement(By by, int timeout);

	void tap(int x, int y);

	WebElement scrollTo(String text);

	BufferedImage captureScreenshot();

	BufferedImage captureScreenshot(int x, int y, int w, int h);

	void pressKeyCode(int keyCode);	

	WebElement scrollToExact(String text);

	String getAppStrings();

	String getAppStrings(String text);

	void swipe(int tapX1, int tapY1, int tapX2, int tapY2, int i);
}
