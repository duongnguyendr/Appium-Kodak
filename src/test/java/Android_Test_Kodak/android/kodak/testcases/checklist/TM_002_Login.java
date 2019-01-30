package Android_Test_Kodak.android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.driver.DriverManager;

import Android_Test_Kodak.android.kodak.base.TestBase;
import Android_Test_Kodak.android.kodak.object.PageDashboard;
import Android_Test_Kodak.android.kodak.object.PageGetStart;
import Android_Test_Kodak.android.kodak.object.PageLogin;
import Android_Test_Kodak.android.kodak.object.PageSignUp;

public class TM_002_Login extends TestBase {
	private PageGetStart getStartPage;
	private PageDashboard dashBoardPage;
	private PageLogin loginPage;
	private PageSignUp signUpPage;
	
	@Test(priority = 2, description = "TC002: Verify that user can login with remember me successfully")
	public void loginWithRememberMe() {
		getStartPage = new PageGetStart();
		dashBoardPage = getStartPage.checkAndSignin(c_username, c_password);		
		Assert.assertEquals(dashBoardPage.getAddDeviceBigBtn().isEnabled(), true);
		
		DriverManager.getAppiumDriver().quit();	
		DriverManager.createWebDriver(driverSetting);
		Assert.assertEquals(dashBoardPage.getAddDeviceBigBtn().isEnabled(), true);
	}
	
	@Test(priority = 3, description = "TC003: Verify that user cannot login with invalid data")
	public void loginWithInvalidData() {
		getStartPage = new PageGetStart();
		loginPage = new PageLogin();
		dashBoardPage = getStartPage.goToSigninPage().loginApp(c_username, c_password + "1");
		String message = loginPage.getLoginMsg().getText();

		Assert.assertEquals(message.matches(
				PageLogin.msg_WRONG_PASSWORD),
				true, "Error: actual result is " + message);
	}
	
	@Test(priority = 4, description = "TC004: Log in with not active user")
	public void loginWithNotActiveUser() {
		String username = StringHelper.randomString("qatest", 10);
		String email = username + "@mailinator.com";
		
		signUpPage = new PageGetStart().goToSignUpPage();
		signUpPage.registerAccount(username, email, email, c_password, c_password);
		signUpPage.getVerifyEmailLaterBtn().click();
		
		getStartPage = new PageGetStart();
		dashBoardPage = getStartPage.checkAndSignin(username, c_password);
		Assert.assertTrue(
				dashBoardPage.verifyInActiveIconDisplay(),
				"Inactve user icon '!' should display on top left dashboard page");
	}
	
	@Test(priority = 5, description = "TC005: Log in with google user not exist in system")
	public void loginWithGoogleAccountNotExisted() {
		String gmailAccount = "autoqatest01@gmail.com";
		String gmailPassword = "Cinatic123";
		String username = StringHelper.randomString("qatest", 10);
		String email = username + "@mailinator.com";
		
		getStartPage = new PageGetStart();
		loginPage = getStartPage.goToSigninPage();
		loginPage.loginWithGoogleAccount(gmailAccount, gmailPassword);
		String message = loginPage.getLoginMessage();
		if(PageLogin.MSG_USER_NOT_EXISTED.equals(message)) {
			loginPage.clickConfirmButton();
		}else {
			Assert.fail("Error: Actual result is: " + message);
		}
		signUpPage = new PageSignUp();
		signUpPage.registerAccount(username, email, email, c_password, c_password);
		signUpPage.getVerifyEmailLaterBtn().click();
		dashBoardPage = new PageDashboard();
		Assert.assertTrue(dashBoardPage.getAddDeviceBigBtn().isEnabled(), "App should navigate to dashboard page.");
	}
	
	@Test(priority = 6, description = "TC006: Log in with google user existed in system")
	public void loginWithGoogleAccountExisted() {
		String gmailAccount = "qa0855@gmail.com";
		String gmailPassword = "Cinatic123";
		getStartPage = new PageGetStart();
		loginPage = getStartPage.goToSigninPage();
		loginPage.loginWithGoogleAccount(gmailAccount, gmailPassword);
		dashBoardPage = new PageDashboard();
		Assert.assertTrue(dashBoardPage.getAddDeviceBigBtn().isEnabled(), "App should navigate to dashboard page.");
	}
	
	@Test(priority = 7, description = "TC007: Log in with Facebook user not exist in system")
	public void loginWithFacebookAccountNotExisted() {
		String facebookAccount = "autoqatest999@gmail.com";
		String facebookPassword = "Cinatic123";
		String username = StringHelper.randomString("qatest", 10);
		String email = username + "@mailinator.com";
		
		getStartPage = new PageGetStart();
		loginPage = getStartPage.goToSigninPage();
		loginPage.loginWithFacebookAccount(facebookAccount, facebookPassword);
		String message = loginPage.getLoginMessage();
		if(PageLogin.MSG_USER_NOT_EXISTED.equals(message)) {
			loginPage.clickConfirmButton();
		}else {
			Assert.fail("Error: Actual result is: " + message);
		}
		signUpPage = new PageSignUp();
		signUpPage.registerAccount(username, email, email, c_password, c_password);
		signUpPage.getVerifyEmailLaterBtn().click();
		dashBoardPage = new PageDashboard();
		Assert.assertTrue(dashBoardPage.getAddDeviceBigBtn().isEnabled(), "App should navigate to dashboard page.");
	}
	
	@Test(priority = 8, description = "TC008: Log in with facebook user existed in system")
	public void loginWithFaceBookAccountExisted() {
		String facebookAccount = "qa4290@gmail.com";
		String facebookPassword = "123456aA";
		getStartPage = new PageGetStart();
		loginPage = getStartPage.goToSigninPage();
		loginPage.loginWithFacebookAccount(facebookAccount, facebookPassword);
		dashBoardPage = new PageDashboard();
		Assert.assertTrue(dashBoardPage.getAddDeviceBigBtn().isEnabled(), "App should navigate to dashboard page.");
	}
	
	@Test(priority = 9, description = "TC009: Login/Logout with existed user")
	public void loginLogOutWithExistedUser() {
		getStartPage = new PageGetStart();
		dashBoardPage = new PageDashboard();
		loginPage = getStartPage.goToSigninPage();
		loginPage.loginApp(c_username, c_password);
		Assert.assertTrue(dashBoardPage.getAddDeviceBigBtn().isEnabled(), "App should navigate to dashboard page.");
		getStartPage.goToSigninPage();
		Assert.assertTrue(PageLogin.getSignInButton().isDisplayed(), "App should navigate to signin page.");
	}
}
