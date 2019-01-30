package com.cinatic.webmailhelper;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cinatic.StringHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.TimeHelper;
import com.cinatic.driver.WebDriverHelper;
import com.cinatic.log.Log;

public class RestMailHelper {
	private String result = "";
	private String email;
	private JSONArray jSonArray;
	private WebDriverHelper webHelper;
	
	private static final String msg_VERIFY_SUCCESS = "Congratulation! Your account has been verified";
	private static final String MSG_SHARE_DEVICE_SUCCESS = "%s device is shared successfully";
	
	public WebElement getUsernameTextbox() {
		String locatorUser = "//input[@id='login']";
		return webHelper.findElement(By.xpath(locatorUser));
	}
	
	public WebElement getPasswordTextbox() {
		String locatorPwd = "//input[@id='password']";
		return webHelper.findElement(By.xpath(locatorPwd));
	}
	
	public WebElement getSignInButton() {
		String locatorSignIn = "//input[@type='submit' and @value='Sign In']";
		return webHelper.findElement(By.xpath(locatorSignIn));
	}
	
	
	public RestMailHelper(String email) {
		this.email = email;
		getEmail();
	}
	/**
	 * Try to get email from rest mail, if fail, sleep 20s and try again.
	 */
	private void getEmail() {
		String cmd = "curl https://restmail.net/mail/" + email;
		String rs;
		int count = 0;
		while(count < 180) {
			try {
				rs = TerminalHelper.exeBashCommand(cmd);
				// get result between [ and }]
				result = "[" + StringHelper.getStringByString(rs, "[", "}]", false) + "}]";
				count = 200;
			} catch (Exception e) {
				TimeHelper.sleep(20000);
				count += 20;
			}
		}
	}
	/**
	 * Delete all rest email
	 */
	public void deleteAllRestMail() {
		try {
			String cmd = "curl -X DELETE https://restmail.net/mail/" + email;
			String rs = TerminalHelper.exeBashCommand(cmd);
		} catch (Exception e) {
			Log.warn("Cannot delete rest mail");
		}
	}
	/**
	 * get Subject of email
	 * @return
	 */
	public String getSubjectEmail() {
		try {
			jSonArray = new JSONArray(result);
			if(jSonArray.length() > 0) {
				JSONObject obj = jSonArray.getJSONObject(0);
				return obj.getString("subject");
			}	
		} catch (Exception e) {
			return "";
		}
		return "";
	}
	/**
	 * Just get content of text email, if email contains link, return empty because there is no attribute 'text'
	 * @return
	 */
	public String getContentEmail() {
		try {
			jSonArray = new JSONArray(result);
			if(jSonArray.length() > 0) {
				JSONObject obj = jSonArray.getJSONObject(0);
				return obj.getString("text");
			}	
		} catch (Exception e) {
			return "";
		}
		return "";
	}
	
	/**
	 * Get confirm sign up account link
	 * @return
	 */
	public String getHyperLinkVerifyAccountSignUp() {
		try {
			String htmlBody = "";
			jSonArray = new JSONArray(result);
			if(jSonArray.length() > 0) {
				JSONObject obj = jSonArray.getJSONObject(0);
				htmlBody = obj.getString("html");
			}
			if(!htmlBody.isEmpty()) {
				return StringHelper.getStringByString(htmlBody, "To confirm your account registration, click <a href=\"", "\">here", false);
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}
	
	/**
	 * Get grant access hyper link
	 * @return
	 */
	public String getHyperLinkGrantAccess() {
		try {
			String loginUrl = "https://app-hk.kodaksmarthome.com/web/#/user/login/";
			String htmlBody = "";
			jSonArray = new JSONArray(result);
			if(jSonArray.length() > 0) {
				JSONObject obj = jSonArray.getJSONObject(0);
				htmlBody = obj.getString("html");
			}
			if(!htmlBody.isEmpty()) {
				String idToken = StringHelper.getStringByString(htmlBody, String.format("<a href=\"%s", loginUrl), "\">https", false);
				return loginUrl + idToken;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "";
	}

	public WebElement getVerificationMessage() {
		String xpathLocator = "//div[@class='alert alert-success ng-binding']";
		WebElement verificationMessage = null;
		try {
			verificationMessage = webHelper.findElement(By.xpath(xpathLocator));
		} catch (Exception e) {
			clearCookieAndRefress();
			verificationMessage = webHelper.findElement(By.xpath(xpathLocator));
		}
		return verificationMessage;
	}
	
	public void clearCookieAndRefress() {
		webHelper.driver.manage().deleteAllCookies();
		TimeHelper.sleep(2 * 1000);
		webHelper.driver.navigate().refresh();
	}
	
	public void openBrowserAndNavigatetoUrl(String url) {
		webHelper = new WebDriverHelper();
		webHelper.driver.manage().window().maximize();
		webHelper.driver.get(url);
		webHelper.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	public String confirmSignupEmail(String url) {
		// open 'Account confirm' mail
		openBrowserAndNavigatetoUrl(url);
		// get message
		String message = getVerificationMessage().getText().trim();

		// Verify message
		Assert.assertEquals(message, msg_VERIFY_SUCCESS, "Error: message is " + message);

		webHelper.closeBrowser();
		return message;
	}
	
	public void confirmGrantAccesEmail(String userName, String password, String deviceName) {
		try {
			verifyConfirmGrantAccessSuccess(userName, password, deviceName);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		webHelper.closeBrowser();
	}
	
	public void verifyConfirmGrantAccessSuccess(String username, String password, String deviceName) throws AWTException {
		WebDriverWait wait = new WebDriverWait(webHelper.driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='login']"))).click();
		try {
			webHelper.findWebElement(By.xpath("//a[@id='headerLogo']"), 5);
		} catch (Exception e) {
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_F5);
			r.keyRelease(KeyEvent.VK_F5);
		}
		getUsernameTextbox().sendKeys(username);
		getPasswordTextbox().sendKeys(password);
		getSignInButton().click();
		String msg = getGrantAccessSuccessMessage();
		Assert.assertEquals(msg, String.format(MSG_SHARE_DEVICE_SUCCESS, deviceName), "Error: message is " + msg);
	}
	
	public String getGrantAccessSuccessMessage() {
		String locatorMsg = "//div[@class='alert alert-success']/span[@class='ng-binding ng-scope']";
		WebElement verificationMessage;
		try {
			verificationMessage = webHelper.findElement(By.xpath(locatorMsg));
		} catch (Exception e) {
			clearCookieAndRefress();
			verificationMessage = webHelper.findElement(By.xpath(locatorMsg));
		}
		return verificationMessage.getText().trim();
	}
	
	public List<String> getAttachmentFileName(String subj) {
		List<String> lstAttachment = new ArrayList<>();
		try {
			jSonArray = new JSONArray(result);
			if(jSonArray.length() > 0) {
				for(int i = 0; i < jSonArray.length(); i ++) {
					if(jSonArray.getJSONObject(i).getString("subject").equals(subj)) {
						JSONArray objs = jSonArray.getJSONObject(i).getJSONArray("attachments");
						for(int j = 0; j < objs.length(); j++) {
							lstAttachment.add(objs.getJSONObject(j).getString("fileName"));
						}
					}
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return lstAttachment;
		}
		return lstAttachment;
	}
}
