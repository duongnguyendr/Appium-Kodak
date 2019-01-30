package android.kodak.testcases.checklist;

import java.text.ParseException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.LucyApiHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.base.TestBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageStreamView;
import jssc.SerialPortException;

public class TM_008_PlayMelody extends TestBase {
	private String username;
	private String password;
	private Device device;	
	private Terminal terminal;
//	private PageGetStart getStartPage;
//	private PageLogin loginPage;
//	private PageDashboard dashboarPage;
//	private PageStreamView streamViewPage; 

	@BeforeMethod
	public void Precondition() throws SerialPortException {
		this.username = c_username;
		this.password = c_password;

		LucyApiHelper api;
		api = new LucyApiHelper(c_server, c_username);
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		terminal = new Terminal(c_comport);
	}

	@Test(priority = 1, description = "TC001: Verify that user can play melody successfully")
	public void TC001_playMelody() throws ParseException {
		PageGetStart page = new PageGetStart();
		PageDashboard devicePage = page.checkAndSignin(username, password);
		PageStreamView viewDevicePage = devicePage.selectDeviceToView(device.getName());
		viewDevicePage.getStreamMode(60);
		viewDevicePage.openMelody();
		terminal.clearTeratermLog();
		viewDevicePage.playMelody(1);
		String camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("/melody/melody1.wav"), true);
		
		terminal.clearTeratermLog();
		viewDevicePage.playMelody(2);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("/melody/melody2.wav"), true);
		
		terminal.clearTeratermLog();
		viewDevicePage.playMelody(3);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("/melody/melody3.wav"), true);
		
		terminal.clearTeratermLog();
		viewDevicePage.playMelody(4);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("/melody/melody4.wav"), true);
		
		terminal.clearTeratermLog();
		viewDevicePage.playMelody(5);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("/melody/melody5.wav"), true);
		
		terminal.clearTeratermLog();
		viewDevicePage.playStopMelody();
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("req=melodystop"), true);		
	}
	
	/* Implement later */
	
//	@Test(priority = 2, description = "TC002: Play melody while talkback is on")
//	public void verifyPlayMelodyWhileTalkBackOn() throws InterruptedException, IOException {
//		String imgName = StringHelper.randomNumber("screenshot", 5);
//		String path = "/test-output/surefire-reports/html/";
//		getStartPage = new PageGetStart();
//		loginPage = getStartPage.goToSigninPage();
//		dashboarPage = loginPage.loginApp(c_username, c_password);
//		streamViewPage = dashboarPage.selectDeviceToView(device.getName());
//		
//		terminal.clearTeratermLog();
//		streamViewPage.enableTalkBack();
//		String camLog = terminal.getTeratermLog();
//		Assert.assertTrue(streamViewPage.verifyTalkBackEnable() && camLog.contains("P2P Talkback state 1"), "Talkback should enable.");
//	
//		streamViewPage.openMelody();
//		DriverManager.captureScreen(imgName);
//		String sysPath = System.getProperty("user.dir");
//		String pth = sysPath + path + imgName;
//		System.out.println("File name: " + pth);
//		File imgFile = new File(path + imgName);
//		BufferedImage buffImg = ImageIO.read(imgFile);
//		String result = ImageHelper.getTextFromImage(buffImg, false, false);
//		System.out.println("#################################: " + result);
//		
//	// In-progress
//	}
//	
//	@Test(priority = 3, description = "TC003: Enable talkback while melody is ON")
//	public void verifyEnableTalkbackWhilePlayMelody() {
//		getStartPage = new PageGetStart();
//		loginPage = getStartPage.goToSigninPage();
//		dashboarPage = loginPage.loginApp(c_username, c_password);
//		streamViewPage = dashboarPage.selectDeviceToView(device.getName());
//		if (!streamViewPage.verifyTalkBackDisable()) {
//			streamViewPage.disableTalkBack();
//		}
//		
//		streamViewPage.openMelody();
//		terminal.clearTeratermLog();
//		streamViewPage.playMelody(1);
//		String camLog = terminal.getTeratermLog();
//		Assert.assertEquals(camLog.contains("/melody/melody1.wav"), true);
//		streamViewPage.clickCancelOnPlayMelodyPanel();
//		
//		terminal.clearTeratermLog();
//		streamViewPage.enableTalkBack();
//		camLog = terminal.getTeratermLog();
//		Assert.assertTrue(streamViewPage.verifyTalkBackEnable() && camLog.contains("P2P Talkback state 1"), "Talkback should enable.");
//		Assert.assertEquals(camLog.contains("req=melodystop"), true);
//	}
	
	@AfterMethod
	public void Cleanup() throws SerialPortException{
		terminal.closePort();
	}
}
