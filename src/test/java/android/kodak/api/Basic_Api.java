package android.kodak.api;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.LucyApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.TimeHelper;
import com.cinatic.constant.TestConstant;
import com.cinatic.driver.WebDriverHelper;
import com.jayway.restassured.response.Response;

public class Basic_Api {

	LucyApiHelper helper;
	String username = StringHelper.randomNumber("qatestrandom", 10).toLowerCase();
	String email = username + TestConstant.emailHost;
	String password = "Aaaa1111";
	String newpassword = "Aaaa2222";

	@DataProvider(name = "registerInvalidData")
	public Object[][] createRegisterInvalidData() {
		return new Object[][] {
				{ "qatest", "qatestmailinator.com", "Aaaa1111", "Aaaa1111", 400, "Email address is invalid" },
				{ "qatest", "qatest@@mailinator.com", "Aaaa1111", "Aaaa1111", 400, "Email address is invalid" },
				{ "qatest", "qatest@mailinator.com", "Aaaa 1111", "Aaaa 1111", 400,
						"Your password must be 8 to 30 characters long and must have at least one Capital letter, one small letter and one number. Space is not allowed" },
				{ "qatest", "qatest@mailinator.com", "aaaa1111", "aaaa1111", 400,
						"Your password must be 8 to 30 characters long and must have at least one Capital letter, one small letter and one number. Space is not allowed" },
				{ "qatest", "qatest@mailinator.com", "Aaaa111", "Aaaa111", 400,
						"Your password must be 8 to 30 characters long and must have at least one Capital letter, one small letter and one number. Space is not allowed" },
				{ "qatest", "qatest@mailinator.com", "Aaaabbbb", "Aaaabbbb", 400,
						"Your password must be 8 to 30 characters long and must have at least one Capital letter, one small letter and one number. Space is not allowed" },
				{ "qatest", "qatest@mailinator.com", "Aaaa1111Aaaa1111Aaaa1111Aaaa111",
						"Aaaa1111Aaaa1111Aaaa1111Aaaa111", 400,
						"Your password must be 8 to 30 characters long and must have at least one Capital letter, one small letter and one number. Space is not allowed" },
				{ "qatest", "qatest@mailinator.com", "Aaaa1111", "Aaaabbbb", 400, "Password does not match" },
				{ "qatest!$", "qatest@mailinator.com", "Aaaa1111", "Aaaa1111", 400,
						"Your username must be 6 to 30 characters long. Only digit, alphabet and these special characters -.@_ are supported" },
				{ "qates", "qatest@mailinator.com", "Aaaa1111", "Aaaa1111", 400,
						"Your username must be 6 to 30 characters long. Only digit, alphabet and these special characters -.@_ are supported" },
				{ "qatestqatestqatestqatestqatestt", "qatest@mailinator.com", "Aaaa1111", "Aaaa1111", 400,
						"Your username must be 6 to 30 characters long. Only digit, alphabet and these special characters -.@_ are supported" },
				{ "qaapitest", StringHelper.randomString("qatestapi", 5) + "@mailinator.com", "Aaaa1111", "Aaaa1111",
						400, "Username exists" },
				{ StringHelper.randomString("qatestapi", 5), "qatestapi@mailinator.com", "Aaaa1111", "Aaaa1111", 400,
						"Email address exists" }, };
	}

	@Parameters({ "server" })
	@BeforeSuite
	public void init(String server) {		
//			helper = new LucyApiHelper(TestConstant.getKodakUri(server));
			helper = new LucyApiHelper(server);
	}

	@Test(dataProvider = "registerInvalidData")
	public void CreateNewUserWithInvalidData(String username, String email, String password, String confirmPassword,
			int code, String message) {
		Response response = helper.registerUserAccount(email, username, password, confirmPassword);
		int status = response.path("status");
		String msg = response.path("msg");

		Assert.assertEquals(status, code, "Error: status is " + status);
		Assert.assertEquals(msg, message, "Error: msg is " + msg);
	}

	@Test()
	public void CreateNewUser() {
		Response response = helper.registerUserAccount(email, username, password, password);

		int status = response.path("status");
		String msg = response.path("msg");

		Assert.assertEquals(status, 200, "Error: status is " + status);
		Assert.assertEquals(msg, "Account registration is successful. Please activate your account within 30 days",
				"Error: msg is " + msg);
	}

	@Test(dependsOnMethods = "CreateNewUser")
	public void UserLoginWithInactiveEmail() {
		Response response = helper.userLogin(username, password);

		int status = response.path("status");
		String msg = response.path("msg");
		String user_name = response.path("data.user_name");

		Assert.assertEquals(status, 200, "Error: status is " + status);
		Assert.assertEquals(msg,
				"Your email address is not verified yet. Account may expire after 30 days. Refer to your email to activate the account",
				"Error: msg is " + msg);
		Assert.assertEquals(user_name, username, "Error: user_name is " + user_name);
	}

	@Test(dependsOnMethods = "UserLoginWithInactiveEmail")
	public void ActiveEmail() throws IOException, InterruptedException {
		WebDriverHelper helper = new WebDriverHelper();
		helper.driver.navigate().to("https://www.mailinator.com");
		helper.findElement(By.xpath("//input[@id='inboxfield']")).sendKeys(username);
		helper.findElement(By.xpath("//button[@class='btn btn-dark']")).click();
		helper.findElement(By.xpath("//div[contains(text(),'Account Confirmation')]")).click();
		TimeHelper.sleep(10 * 1000);
		
		helper.findElement(By.xpath("//iframe[@id='msg_body']"));
		helper.driver.switchTo().frame("msg_body");
		helper.findElement(By.xpath("//a[contains(@href,'verify') and text()='here']")).click();
		String currentTab = helper.driver.getWindowHandle();
		for (String tab : helper.driver.getWindowHandles()) {
			if (!tab.equals(currentTab)) {
				helper.driver.switchTo().window(tab);
			}
		}
		String message = helper.findElement(By.xpath("//div[@class='alert alert-success ng-binding']")).getText()
				.trim();
		helper.driver.close();
		helper.driver.switchTo().window(currentTab);
		helper.driver.close();
		TerminalHelper.execCmd("taskkill /f /im chromedriver.exe");
		Assert.assertEquals(message, "Congratulation! Your account has been verified", "Error: message is " + message);
	}

	@Test(dependsOnMethods = "ActiveEmail")
	public void UserLoginAfterActiveEmail() {
		Response response = helper.userLogin(username, password);

		int status = response.path("status");
		String msg = response.path("msg");
		String user_name = response.path("data.user_name");

		Assert.assertEquals(status, 200, "Error: status is " + status);
		Assert.assertEquals(msg, "Success", "Error: msg is " + msg);
		Assert.assertEquals(user_name, username, "Error: user_name is " + user_name);
	}

	@Test(dependsOnMethods = "UserLoginAfterActiveEmail")
	public void ChangePassword() {
		Response response = helper.changePassword(password, newpassword, newpassword);

		int status = response.path("status");
		String msg = response.path("msg");

		Assert.assertEquals(status, 200, "Error: status is " + status);
		Assert.assertEquals(msg, "Password is changed successfully", "Error: msg is " + msg);
	}

	@Test(dependsOnMethods = "ChangePassword")
	public void UserLoginWithNewPassword() {
		Response response = helper.userLogin(username, newpassword);

		int status = response.path("status");
		String msg = response.path("msg");
		String user_name = response.path("data.user_name");

		Assert.assertEquals(status, 200, "Error: status is " + status);
		Assert.assertEquals(msg, "Success", "Error: msg is " + msg);
		Assert.assertEquals(user_name, username, "Error: user_name is " + user_name);
	}

	@Test(dependsOnMethods = "UserLoginWithNewPassword")
	public void UserRecoverPassword() throws IOException, InterruptedException {
		Response response = helper.recoverPassword(username + TestConstant.emailHost);

		int status = response.path("status");
		String msg = response.path("msg");

		Assert.assertEquals(status, 200, "Error: status is " + status);
		Assert.assertEquals(msg,
				"You will receive password reset instruction in the email, if the user exists in the system",
				"Error: msg is " + msg);

		WebDriverHelper helper = new WebDriverHelper();
		helper.driver.navigate().to("https://www.mailinator.com");
		helper.findElement(By.xpath("//input[@id='inboxfield']")).sendKeys(username);
		helper.findElement(By.xpath("//button[@class='btn btn-dark']")).click();
		helper.findElement(By.xpath("//div[contains(text(),'Password recovery')]")).click();
		TimeHelper.sleep(10 * 1000);
		
		helper.findElement(By.xpath("//iframe[@id='msg_body']"));
		helper.driver.switchTo().frame("msg_body");
		helper.findElement(By.xpath("//a[contains(@href,'reset_password') and text()='recover it here']")).click();
		String currentTab = helper.driver.getWindowHandle();
		for (String tab : helper.driver.getWindowHandles()) {
			if (!tab.equals(currentTab)) {
				helper.driver.switchTo().window(tab);
			}
		}
		helper.findElement(By.xpath("//input[@ng-model='password']")).sendKeys(password);
		helper.findElement(By.xpath("//input[@ng-model='confirmPassword']")).sendKeys(password);
		helper.findElement(By.xpath("//input[@value='Update']")).click();
		String message = helper.findElement(By.xpath("//div[@class='alert alert-success ng-binding']")).getText()
				.trim();

		helper.driver.close();
		helper.driver.switchTo().window(currentTab);
		helper.driver.close();
		TerminalHelper.execCmd("taskkill /f /im chromedriver.exe");
		Assert.assertEquals(message, "Password is reset successfully", "Error: message is " + message);
	}
}
