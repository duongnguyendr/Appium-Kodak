package Android_Test_Kodak.android.kodak.testcases.checklist;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.constant.TestConstant;
import com.cinatic.webmailhelper.MailinatorHelper;
import com.jayway.restassured.RestAssured;

import Android_Test_Kodak.android.kodak.base.TestBase;
import Android_Test_Kodak.android.kodak.object.PageDashboard;
import Android_Test_Kodak.android.kodak.object.PageGetStart;
import Android_Test_Kodak.android.kodak.object.PageSignUp;
import net.restmail.KodakRestMailHelper;

public class TM_001_SignUp extends TestBase {
	private String username;
	private String password;
	private String email;
	private MailinatorHelper mailinatorHelper;

	@BeforeClass
	public void Precondition() {
		this.username = StringHelper.randomNumber("qatest", 10);
		this.password = "Aaaa1111";
		this.email = this.username + "@restmail.net";
		
		RestAssured.baseURI = TestConstant.internalBaseURI;
	}
	
	@Test(priority = 1, description="TC001: Verify that user can signup new account with valid data")
	public void TC_001_SignUpWithValidData() {
		PageSignUp pageSignUp = new PageGetStart().goToSignUpPage();
		pageSignUp.registerAccount(username, email, email, password, password);
		TimeHelper.sleep(5 * 1000);

		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(username);
		restMailHelper.deleteAllRestMail();
		restMailHelper.confirmSignupEmail();
		
//		mailinatorHelper = new MailinatorHelper("firefox", username);
//		mailinatorHelper.confirmSignupEmail();
		
		pageSignUp.getVerifyEmailLaterBtn().click();
		
		// make sure that app login to new account automatically navigate to Dashboard page, able to see add button 
		PageDashboard pageDashboard = new PageDashboard();
		pageDashboard.handlePermissionsAndHintsWhenPageOpen().getAddDeviceBigBtn().getWebElement();
	}
	
	@Test(priority = 2, description = "TC002: Verify that user cannot signup new account with invalid username")
	public void TC_002_SignUpWithInvalidUsername() throws InterruptedException, IOException {
		PageSignUp page = new PageGetStart().goToSignUpPage();
		page.registerAccount("qa", email, email, password, password);

		String message = page.getUsernameConstraintMessage().getText();
		Assert.assertEquals(message, PageSignUp.msg_SHORT_USERNAME,
				"Error: actual result is " + message);
	}
	
	@Test(priority = 3, description = "TC003: Verify that user cannot signup new account with invalid email")
	public void TC_003_SignUpWithInvalidEmail() throws InterruptedException, IOException {
		PageSignUp page = new PageGetStart().goToSignUpPage();
		page.registerAccount(username, "qa", "qa", password, password);

		String message = page.getEmailConstraintMessage().getText();
		Assert.assertEquals(message, PageSignUp.msg_WRONG_EMAIL_FORMAT,
				"Error: actual result is " + message);
	}
	
	@Test(priority = 4, description = "TC004: Verify that user cannot signup new account with invalid password")
	public void TC_004_SignUpWithInvalidPassword() throws InterruptedException, IOException {
		PageSignUp page = new PageGetStart().goToSignUpPage();
		page.registerAccount(username,email, email, "qa","qa");

		String message = page.getPasswordConstraintMessage().getText();
		Assert.assertEquals(message, PageSignUp.msg_PASSWD_STRENGTH,
				"Error: actual result is " + message);
	}
	
	@AfterMethod
	public void cleanup() {
		try {
			mailinatorHelper.quit();
		} catch (Exception e) {
			// nothing to handle
		}
	}
}
