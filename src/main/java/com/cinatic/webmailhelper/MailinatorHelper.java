package com.cinatic.webmailhelper;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cinatic.TimeHelper;
import com.cinatic.driver.WebDriverHelper;
import com.cinatic.log.Log;

public class MailinatorHelper {

	// Compatible with Mailinator Web UI v3 only
	// Need to update all xpathLocator if UI change.

	private static final String URL = "https://www.mailinator.com";
	private static final String msg_VERIFY_SUCCESS = "Congratulation! Your account has been verified";
	private static final String msg_CHANGE_EMAIL_CONFIRM_OLD = "You agreed to change your old email to %s. Check your new email inbox to activate the final change.";
	private static final String msg_CHANGE_EMAIL_CONFIRM_NEW = "Email is changed successfully";
	private static final String subj_ACCOUNT_CONFIRMATION = "Account confirmation";
	private static final String subj_EMAIL_CHANGE = "Email change confirmation needed";
	private static final String SUBJ_EMAIL_GRANT_ACCESS = "been invited to view KODAK live video stream";
	private static final String MSG_SHARE_DEVICE_SUCCESS = "%s device is shared successfully";
	private WebDriverHelper webHelper;
	private String username;

	public MailinatorHelper(String email) {
		webHelper = new WebDriverHelper();
		webHelper.driver.manage().deleteAllCookies();
		webHelper.driver.navigate().to(URL);
		this.username = email;
		getInboxField().sendKeys(username);
		getGoToInboxBtn().click();
	}
	
	public MailinatorHelper(String browser, String email) {
		webHelper = new WebDriverHelper(browser);
		webHelper.driver.manage().deleteAllCookies();
		webHelper.driver.manage().window().maximize();
		webHelper.driver.navigate().to(URL);
		this.username = email;
		getInboxField().sendKeys(username);
		getGoToInboxBtn().click();
	}

	public WebElement getInboxField() {
		String xpathLocator = "//input[@id='inboxfield']";
		return webHelper.findElement(By.xpath(xpathLocator));
	}

	public WebElement getGoToInboxBtn() {
		String xpathLocator = "//button[@class='btn btn-dark']";
		return webHelper.findElement(By.xpath(xpathLocator));
	}

	public WebElement getAccountConfirmEmail() {
		String xpathLocator = String.format("//td[contains(text(),'%s')]", subj_ACCOUNT_CONFIRMATION);
		return webHelper.findElement(By.xpath(xpathLocator));
	}

	public WebElement getMessageBodyFrame() {
		String xpathLocator = "//iframe[@id='msg_body']";
		return webHelper.findElement(By.xpath(xpathLocator));
	}

	public WebElement getHereVerifyHyperlink() {
		String xpathLocator = "//a[contains(@href,'verify') and text()='here']";
		return webHelper.findElement(By.xpath(xpathLocator));
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

	public WebElement getChangeEmailConfirmationMail() {
		String xpathLocator = String.format("//td[contains(text(),'%s')]", subj_EMAIL_CHANGE);
		return webHelper.findElement(By.xpath(xpathLocator));
	}

	public WebElement getEmptyInboxMessage() {
		String xpathLocator = "//div[@id='publicm8rguy']";
		return webHelper.findElement(By.xpath(xpathLocator));
	}

	public WebElement getGrantAccessEmail() {
		String locator = "//td[contains(text(),'%s')]";
		return webHelper.findElement(By.xpath(String.format(locator, SUBJ_EMAIL_GRANT_ACCESS)));
	}

	public WebElement getAcceptGrantAccessInvitationLink() {
		String locatorLink = "//a[contains(text(), 'kodaksmarthome.com/web/#/user/login')]";
		return webHelper.findElement(By.xpath(locatorLink));
	}
	
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
	
	public WebElement getFeedBackEmail(String title) {
		String feedbackEmail = "//td[contains(text(), '%s')]";
		return webHelper.findWebElement((By.xpath(String.format(feedbackEmail, title))), 300);
	}
	
	public WebElement getFromEmail() {
		String fromEmail = "//div[@class='x_title']//small";
		return webHelper.findElement(By.xpath(fromEmail));
	}
	
	public WebElement getContentEmailEle() {
		String content = "//body/div[@dir='auto']";
		return webHelper.findElement(By.xpath(content));
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

	public String confirmSignupEmail() {
		// open 'Account confirm' mail
		getAccountConfirmEmail().click();
		TimeHelper.sleep(3000);
		webHelper.driver.switchTo().frame("msg_body");
		TimeHelper.sleep(3000);
		getHereVerifyHyperlink().click();
		TimeHelper.sleep(3000);
		// Switch to other tab
		String currentTab = webHelper.driver.getWindowHandle();
		for (String tab : webHelper.driver.getWindowHandles()) {
			if (!tab.equals(currentTab)) {
				webHelper.driver.switchTo().window(tab);
			}
		}

		// sometime the link does not load, need to check if it's load
		// successfully or not
		// reload page if not.

		// get message
		String message = getVerificationMessage().getText().trim();

		// Verify message
		Assert.assertEquals(message, msg_VERIFY_SUCCESS, "Error: message is " + message);

		webHelper.closeBrowser();
		return message;
	}

	public void confirmGrantAccesEmail(String userName, String password, String deviceName) {
		refreshIfInboxEmpty();
		getGrantAccessEmail().click();
		webHelper.driver.switchTo().frame("msg_body");
		TimeHelper.sleep(2000);
		getAcceptGrantAccessInvitationLink().click();
		// Switch to other tab
		TimeHelper.sleep(2000);
		String currentTab = webHelper.driver.getWindowHandle();
		for (String tab : webHelper.driver.getWindowHandles()) {
			if (!tab.equals(currentTab)) {
				webHelper.driver.switchTo().window(tab);
			}
		}
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

	public String confirmChangeEmailOld(String newEmailUsername) {
		if (!newEmailUsername.contains("@mailinator.com")) {
			newEmailUsername += "@mailinator.com";
		}
		refreshIfInboxEmpty();
		// Open 'change email' mail
		getChangeEmailConfirmationMail().click();
		 TimeHelper.sleep(3000);
		webHelper.driver.switchTo().frame("msg_body");
		getHereVerifyHyperlink().click();
		TimeHelper.sleep(3000);
		// Switch to other tab
		String currentTab = webHelper.driver.getWindowHandle();
		for (String tab : webHelper.driver.getWindowHandles()) {
			if (!tab.equals(currentTab)) {
				webHelper.driver.switchTo().window(tab);
			}
		}

		// sometime the link does not load, need to check if it's load
		// successfully or not
		// reload page if not.
		// get message
		String message = getVerificationMessage().getText().trim();

		// Verify message
		String msgVerifyChangeEmailOld = String.format(msg_CHANGE_EMAIL_CONFIRM_OLD, newEmailUsername);
		Assert.assertEquals(message, msgVerifyChangeEmailOld, "Error: message is " + message);
		webHelper.closeBrowser();
		return message;
	}

	public String confirmChangeEmailNew() {
		refreshIfInboxEmpty();
		// Open 'change email' mail
		getChangeEmailConfirmationMail().click();
		TimeHelper.sleep(3000);
		webHelper.driver.switchTo().frame("msg_body");
		getHereVerifyHyperlink().click();
		TimeHelper.sleep(3000);
		// Switch to other tab
		String currentTab = webHelper.driver.getWindowHandle();
		for (String tab : webHelper.driver.getWindowHandles()) {
			if (!tab.equals(currentTab)) {
				webHelper.driver.switchTo().window(tab);
			}
		}

		// sometime the link does not load, need to check if it's load
		// successfully or not
		// reload page if not.
		// get message
		String message = getVerificationMessage().getText().trim();

		// Verify message
		Assert.assertEquals(message, msg_CHANGE_EMAIL_CONFIRM_NEW, "Error: message is " + message);
		webHelper.closeBrowser();
		return message;
	}

	public void quit() {
		try {
			webHelper.closeBrowser();
			Log.info("Quit the browser");
		} catch (Exception e) {
			// nothing to handle
		}

	}

	public void refreshIfInboxEmpty() {
		if (getEmptyInboxMessage().isDisplayed()) {
			clearCookieAndRefress();
		}
	}

	public void clearCookieAndRefress() {
		webHelper.driver.manage().deleteAllCookies();
		TimeHelper.sleep(2 * 1000);
		webHelper.driver.navigate().refresh();
	}
	
	public boolean verifyFeedBackEmail(String subJ) {
		boolean rs = getFeedBackEmail(subJ).isDisplayed();
		webHelper.closeBrowser();
		return rs; 
	}
	
	public String getContentEmailFeedback(String subj) {
		getFeedBackEmail(subj).click();
		TimeHelper.sleep(3000);
		webHelper.driver.switchTo().frame("msg_body");
		String rs = getContentEmailEle().getText();
		webHelper.closeBrowser();
		return rs;
	}
}
