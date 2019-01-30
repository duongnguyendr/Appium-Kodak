	package com.cinatic.driver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.Response;
import org.springframework.util.StopWatch;

import com.cinatic.log.Log;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class DriverBase implements Driver {	
	private AppiumDriver<WebElement> appiumDriver;
	private DriverSetting driverSetting;

	public AppiumDriver<WebElement> getAppiumDriver() {
		return appiumDriver;
	}

	public void setAppiumDriver(AppiumDriver<WebElement> appiumDriver) {
		this.appiumDriver = appiumDriver;
	}

	public DriverSetting getDriverSetting() {
		return driverSetting;
	}

	public void setDriverSetting(DriverSetting driverSetting) {
		this.driverSetting = driverSetting;
	}

	public DriverBase(DriverSetting driverSetting) {
		setDriverSetting(driverSetting);
	}

	public void get(String url) {		
		appiumDriver.get(url);
	}

	public String getCurrentUrl() {		
		logStart("get current url");
		return appiumDriver.getCurrentUrl();
	}

	public String getTitle() {
		return appiumDriver.getTitle();
	}

	public List<WebElement> findElements(By by) {
//		logStart(String.format("find elements: %s", by));
		List<WebElement> elements = appiumDriver.findElements(by);
		return elements;
	}

	public WebElement findElement(By by) {
//		logStart(String.format("find element: %s", by));
		WebElement element = appiumDriver.findElement(by);
		return element;
	}

	public WebElement findElement(By by, int timeout) {
//		logStart(String.format("find element: %s", by));
		WebElement element = null;		
		StopWatch tw = new StopWatch();
		if (timeout > 0) {
			try {
				tw.start();
				element = appiumDriver.findElement(by);
			} catch (Exception ex) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tw.stop();
				timeout = (int) (timeout - tw.getTotalTimeSeconds());
				element = findElement(by, timeout);
			}
		}				
		return element;
	}

	public String getPageSource() {
		logStart("get page source");
		return appiumDriver.getPageSource();
	}

	public void close() {
		logStart("close driver");
		appiumDriver.close();
	}

	public void quit() {
		logStart("quit driver");
		appiumDriver.quit();
	}

	public Set<String> getWindowHandles() {
		logStart("get window handles");
		return appiumDriver.getWindowHandles();
	}

	public String getWindowHandle() {
		logStart("get window handle");
		return appiumDriver.getWindowHandle();
	}	

	public TargetLocator switchTo() {
		// TODO Auto-generated method stub
		return null;
	}

	public Navigation navigate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Options manage() {
		// TODO Auto-generated method stub
		return null;
	}

	public WebDriver context(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> getContextHandles() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public void performMultiTouchAction(MultiTouchAction arg0) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	public TouchAction performTouchAction(TouchAction arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void hideKeyboard() {
		try {
			logStart("hide keyboard");
			appiumDriver.hideKeyboard();
		} catch (Exception ex) {

		}
	}

	public void tap(int x, int y) {
		logStart(String.format("tap: %s %s", x, y));
		new TouchAction<>(appiumDriver).tap(PointOption.point(x, y)).perform();
	}

	public BufferedImage captureScreenshot() {
		logStart("capture screenshot");
		File screen = ((TakesScreenshot) DriverManager.getAppiumDriver().getAppiumDriver()).getScreenshotAs(OutputType.FILE);
		try {
			return ImageIO.read(screen);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BufferedImage captureScreenshot(int x, int y, int w, int h) {
		logStart(String.format("capture screenshot: %s %s %s %s", x, y, w, h));
		File screen = ((TakesScreenshot) DriverManager.getAppiumDriver().getAppiumDriver()).getScreenshotAs(OutputType.FILE);

		BufferedImage img = null;
		try {
			img = ImageIO.read(screen);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedImage dest = img.getSubimage(x, y, w, h);
		return dest;
	}

	@Override
	public ScreenOrientation getOrientation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rotate(ScreenOrientation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WebElement findElementByAccessibilityId(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByAccessibilityId(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location location() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocation(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("rawtypes")
	public void swipe(int tapX1, int tapY1, int tapX2, int tapY2, int i) {
		// TODO Auto-generated method stub
		logStart(String.format("swipe: %s %s %s %s %s", tapX1, tapY1, tapX2, tapY2, i));
		new TouchAction(appiumDriver).press(PointOption.point(tapX1, tapY1))
		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(i)))
		.moveTo(PointOption.point(tapX2, tapY2)).release().perform();
	}	

	@Override
	public byte[] pullFile(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] pullFolder(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeApp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void installApp(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAppInstalled(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void launchApp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetApp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAppStrings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAppStrings(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByClassName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByCssSelector(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementById(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByLinkText(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByPartialLinkText(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByTagName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByXPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByClassName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByCssSelector(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsById(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByLinkText(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByPartialLinkText(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByTagName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByXPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeviceTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getAppStringMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getAppStringMap(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getAppStringMap(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void pressKeyCode(int keyCode) {
		try {
			logStart(String.format("press key code: %s", keyCode));
			((AndroidDriver<WebElement>) appiumDriver).pressKeyCode(keyCode);
		} catch (Exception ex) {

		}
	}

	@Override
	public WebElement scrollTo(String text) {
		return ((AndroidDriver<WebElement>)appiumDriver).findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+text+"\").instance(0))");
	}

	@Override
	public Response execute(String driverCommand, Map<String, ?> parameters) {		
		return null;
	}
	
	private void logStart(String msg) {
		Log.debug(String.format("- [{%s}]", msg));
	}

//	private void logEnd(String msg) {		
//		Log.debug(String.format("+ [{%s}]", msg));
//	}

	@Override
	public void rotate(DeviceRotation rotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DeviceRotation rotation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response execute(String driverCommand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElement(String by, String using) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElements(String by, String using) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement scrollToExact(String text) {
		// TODO Auto-generated method stub
		return null;
	}
}