package android.kodak.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.cinatic.LucyApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.TimeHelper;
import com.cinatic.constant.TestConstant;
import com.cinatic.driver.WebDriverHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;
import com.jayway.restassured.RestAssured;

import android.kodak.base.TestBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageLogin;
import android.kodak.object.PageSignUp;
import android.kodak.object.PageStreamView;
import jssc.SerialPortException;

public class TM_000_new_user_a_z_flow extends TestBase {
	private String username;
	private String password;
	private String email;
	private Terminal terminal;
	private Device device;
	PageSignUp page;
	PageLogin loginPage;
	PageDashboard devicePage;
	PageStreamView viewDevicePage;
	String camLog;
	
	@BeforeTest
	public void Precondition() {		
		this.username = StringHelper.randomString("qatest", 10);
		this.password = "Aaaa1111";
		this.email = this.username + "@mailinator.com";
		
//		this.username = "thach.nguyen"; //delete this
//		this.password = "Cinatic123"; // delete this		
		
		RestAssured.baseURI = TestConstant.internalBaseURI;
		
		// for setup new cam test
		try {
			terminal = new Terminal(c_comport);
		} catch (SerialPortException e) {
			e.printStackTrace();
			return;
		}
	}
	
	@Test(priority = 1, description="TC000: New user A-Z flow")
	public void TC_000_New_User_AZ_Flow() throws InterruptedException, IOException {		
		// *** DO NOT change the chronicle order of the following sub-tests, doing that will brake the test  		
		// *** Sign up ***
		 testSignUpNewAccount();		
		
		// *** login to app after sign up a new account***
		loginWithSignedUpAccount(); 
		
		// setup new camera
		testSetupNewCam();
		
		// *** open stream view ***
		openStreamView();
		
		// *** talk back ***				
		testTalkback();
		
		// *** mute sound test ***
		testSound();
		
		// *** capture image ***
		testImageCapture();	
		
		// *** record video ***
		testVideoRecording();
		
		// *** play melody ***
		testMelodyPlayback();		
	}	
	
// ******************************************** sub-tests ********************************************************** //
	public void testSignUpNewAccount() {			
		page = new PageGetStart().goToSignUpPage();
		page.registerAccount(username, email, email, password, password);
		TimeHelper.sleep(10 * 1000);
		
		WebDriverHelper helper = new WebDriverHelper();
		helper.driver.navigate().to("https://www.mailinator.com");
		helper.findElement(By.xpath("//input[@id='inboxfield']")).sendKeys(username);
		helper.findElement(By.xpath("//button[@class='btn btn-dark']")).click();
		helper.findElement(By.xpath("//td[contains(text(),'Account Confirmation')]")).click();
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
		try {
			TerminalHelper.execCmd("taskkill /f /im chromedriver.exe");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(message, "Congratulation! Your account has been verified", "Error: message is " + message);
	}
	
	public void loginWithSignedUpAccount() {		
		// ** use this with SignUpTest
		loginPage = page.backToGetStartedPage().goToSigninPage();
		devicePage = loginPage.loginApp(username, password);
	}
	
	public void testSetupNewCam( ) {
		devicePage.getAddDeviceBigBtn().click();
		devicePage.getBabySeriesImage().click();
		devicePage.getDeviceModelLabel("520").click();
		try {
			terminal.sendCommand("pair", "start_pairing_mode", 10);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
		devicePage.getContinueButton().click();
		devicePage.getContinueButton().click();
		if (devicePage.getSSIDLabel(c_devicessid).getWebElement() != null) {
			devicePage.getSSIDLabel(c_devicessid).click();
		} else {
			devicePage.getSSIDRefreshImage().click();
			devicePage.getSSIDLabel(c_devicessid).click();
		}
		devicePage.getTextWifiPasswordTextbox().sendKeys(c_wifipassword);		
		devicePage.getContinueButton().click();
		if(devicePage.getMobileDataContinueButton().getWebElement()!=null) {
			devicePage.getMobileDataContinueButton().click();
		}
		devicePage.getCustomButton().click();
		devicePage.getContinueButton().click();
		devicePage.getDoneButton().click();
	}
	
	public void openStreamView() {
		LucyApiHelper api;
		api = new LucyApiHelper(c_server, c_username);
		api.userLogin(username, password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		viewDevicePage = devicePage.selectDeviceToView(device.getName());
		viewDevicePage.getStreamMode(20);
	}
	
	public void testTalkback() {
		terminal.clearTeratermLog();
		viewDevicePage.enableTalkBack();		
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("P2P Talkback state 1"), true);		
		
		terminal.clearTeratermLog();
		viewDevicePage.disableTalkBack();
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("P2P Talkback state 0"), true);
	}
	
	public void testSound() {
		viewDevicePage.unMuteSound();
		viewDevicePage.muteSound();
		viewDevicePage.unMuteSound();
	}
	
	public void testVideoRecording() {
		viewDevicePage.startRecordVideo(30);
		viewDevicePage.stopRecordVideo();		
		
		// *** skip, got problem while open gallery tab ***
//		 viewDevicePage.BackToDevicePage().openVideoGalleryTab();		
//		String videoGalleryTime = devicePage.getFirstVieoGalleryInfo();		
//		long duration = 0;
//		try {
//			duration = StringHelper.getDuration("MMM dd yyyy hh:mm:ss a", currentTime, videoGalleryTime);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		Assert.assertEquals(duration > 0, true);		
	}
	
	public void testMelodyPlayback() {
		viewDevicePage.openMelody();
		terminal.clearTeratermLog();
		viewDevicePage.playMelody(1);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("melody1: 0"), true);
		
		terminal.clearTeratermLog();
		viewDevicePage.playMelody(2);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("melody2: 0"), true);
		
		terminal.clearTeratermLog();
		viewDevicePage.playMelody(3);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("melody3: 0"), true);
		
		terminal.clearTeratermLog();
		viewDevicePage.playMelody(4);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("melody4: 0"), true);
		
		terminal.clearTeratermLog();
		viewDevicePage.playMelody(5);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("melody5: 0"), true);
		
		terminal.clearTeratermLog();
		viewDevicePage.playStopMelody();
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("melodystop: 0"), true);
	}
	
	public void testImageCapture() {
		viewDevicePage.captureImage();
	}
}
