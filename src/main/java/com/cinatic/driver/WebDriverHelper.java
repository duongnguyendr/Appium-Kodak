package com.cinatic.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.util.StopWatch;

import com.cinatic.TimeHelper;
import com.cinatic.constant.TestConstant;

public class WebDriverHelper {
	public WebDriver driver;
	public String browser = "";
	
	public WebDriverHelper()
	{
		if(System.getProperty("os.name").equals("Linux"))
			System.setProperty("webdriver.chrome.driver", TestConstant.resourcePath + "/chromedriver-linux/chromedriver");
		else if(System.getProperty("os.name").equals("Windows"))
				System.setProperty("webdriver.chrome.driver", TestConstant.resourcePath + "\\chromedriver-windows\\chromedriver");
		else
			System.setProperty("webdriver.chrome.driver", String.format("/Users/%s/resources/chromedriver", TestConstant.systemUser));
		driver = new ChromeDriver();
	}
	
	public WebDriverHelper(String browser){
		this.browser = browser;
		switch (browser) {
		case "chrome":
			if(System.getProperty("os.name").equals("Linux"))
				System.setProperty("webdriver.chrome.driver", TestConstant.resourcePath + "/chromedriver-linux/chromedriver");
			else if(System.getProperty("os.name").equals("Windows"))
					System.setProperty("webdriver.chrome.driver", TestConstant.resourcePath + "\\chromedriver-windows\\chromedriver");
			else
				System.setProperty("webdriver.chrome.driver", String.format("/Users/%s/resources/chromedriver", TestConstant.systemUser));
			driver = new ChromeDriver();		
			break;

		case "firefox":
			System.setProperty("webdriver.gecko.driver", TestConstant.resourcePath + "/firefoxdriver-linux/geckodriver");
			driver = new FirefoxDriver();
			break;
		}
		
	}
	
	public WebElement findWebElement(By by, int timeout){
		WebElement element = null;		
		StopWatch tw = new StopWatch();
		if (timeout > 0) {
			try {
				tw.start();
				element = driver.findElement(by);				
			} catch (Exception ex) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tw.stop();
				timeout = (int) (timeout - tw.getTotalTimeSeconds());
				element = findWebElement(by, timeout);
			}
		}		
		return element;
	}
	
	public WebElement findElement(By by){
		return findWebElement(by, 30);
	}
	
	public void clearCookieAndRefress() {
		driver.manage().deleteAllCookies();
		TimeHelper.sleep(2 * 1000);
		driver.navigate().refresh();
	}
	
	public void closeBrowser(){
		driver.close();		
		if(!browser.equals("firefox")) {
			driver.quit();
		}
	}
	
	public void openBrowserAndNavigatetoUrl(String url) {
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
}