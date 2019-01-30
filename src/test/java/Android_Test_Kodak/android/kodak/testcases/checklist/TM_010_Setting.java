package Android_Test_Kodak.android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.LucyApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.constant.TestConstant;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import Android_Test_Kodak.android.kodak.base.TestBase;
import Android_Test_Kodak.android.kodak.object.PageCameraSetting;
import Android_Test_Kodak.android.kodak.object.PageDashboard;
import Android_Test_Kodak.android.kodak.object.PageGetStart;
import Android_Test_Kodak.android.kodak.object.PageGmail;
import Android_Test_Kodak.android.kodak.object.PageStreamView;
import Android_Test_Kodak.android.kodak.object.PageTimeline;
import jssc.SerialPortException;
import net.restmail.KodakRestMailHelper;


public class TM_010_Setting extends TestBase {
	private String username;
	private String password;
	private Device device;
	LucyApiHelper api;
	PageDashboard pageDashboard;
	PageCameraSetting settingPage;
	Terminal terminal;

	@BeforeMethod
	public void Precondition() {
		this.username = c_username;
		this.password = c_password;

		api = new LucyApiHelper(c_server, c_username);
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}

	@Test(priority = 10, description = "TC001: Verify that user can change Ceiling Mount setting successfully")
	public void TC001_CeilingMount() {
		PageGetStart page = new PageGetStart();
		pageDashboard = page.checkAndSignin(username, password);
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		
		settingPage.enableCeilingMount();
		TimeHelper.sleep(3 *1000);
		api.userLogin(c_username, c_password);
		String body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		String value = StringHelper.getStringByString(body, "flipup=", "&", false);
		Assert.assertEquals(value, "1", "Error: actual result is " + value);

		settingPage.disableCeilingMount();
		api.userLogin(c_username, c_password);
		body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		value = StringHelper.getStringByString(body, "flipup=", "&", false);
		Assert.assertEquals(value, "0", "Error: actual result is " + value);
	}
	
	@Test(priority = 11, description = "TC002: Verify that user can change Motion Detection setting successfully")
	public void TC002_MotionDetection() {
		pageDashboard = new PageDashboard();
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		
		settingPage.enableMotionDetection();
		settingPage.closeCouldUpgradeTipIfAsked();
		TimeHelper.sleep(3 *1000);
		api.userLogin(c_username, c_password);
		String body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		String value = StringHelper.getStringByString(body, "md=", "&", false);
		Assert.assertEquals(value, "1:1:3:0", "Error: actual result is " + value);

		settingPage.changeMotionSensitivityLevel(TestConstant.motionLevel.Low);
		api.userLogin(c_username, c_password);
		body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		value = StringHelper.getStringByString(body, "md=", "&", false);
		Assert.assertEquals(value, "1:1:1:0", "Error: actual result is " + value);
		
		settingPage.changeMotionSensitivityLevel(TestConstant.motionLevel.High);
		api.userLogin(c_username, c_password);
		body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		value = StringHelper.getStringByString(body, "md=", "&", false);
		Assert.assertEquals(value, "1:1:5:0", "Error: actual result is " + value);
		
		settingPage.changeMotionSensitivityLevel(TestConstant.motionLevel.Medium);
		api.userLogin(c_username, c_password);
		body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		value = StringHelper.getStringByString(body, "md=", "&", false);
		Assert.assertEquals(value, "1:1:3:0", "Error: actual result is " + value);
		
		settingPage.disableMotionDetection();
		api.userLogin(c_username, c_password);
		body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		value = StringHelper.getStringByString(body, "md=", "&", false);
		Assert.assertEquals(value, "0:0:3:0", "Error: actual result is " + value);
		settingPage.enableMotionDetection();
	}
	
	@Test(priority = 12, description = "TC003: Verify that user can change Sound Detection setting successfully")
	public void TC003_SoundDetection() {
		pageDashboard = new PageDashboard();
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		
		settingPage.enableSoundDetection();
		TimeHelper.sleep(3 *1000);
		api.userLogin(c_username, c_password);
		String body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		String value = StringHelper.getStringByString(body, "sd=", "&", false);
//		Assert.assertEquals(value, "1:2:1:0", "Error: actual result is " + value);

		settingPage.changeSoundSensitivityLevel(TestConstant.soundLevel.Low);
		api.userLogin(c_username, c_password);
		body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		value = StringHelper.getStringByString(body, "sd=", "&", false);
		Assert.assertEquals(value, "1:2:1:0", "Error: actual result is " + value);
		
		settingPage.changeSoundSensitivityLevel(TestConstant.soundLevel.High);
		api.userLogin(c_username, c_password);
		body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		value = StringHelper.getStringByString(body, "sd=", "&", false);
		Assert.assertEquals(value, "1:2:5:0", "Error: actual result is " + value);
		
		settingPage.changeSoundSensitivityLevel(TestConstant.soundLevel.Medium);
		api.userLogin(c_username, c_password);
		body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		value = StringHelper.getStringByString(body, "sd=", "&", false);
		Assert.assertEquals(value, "1:2:3:0", "Error: actual result is " + value);
		
		settingPage.disableSoundDetection();
		api.userLogin(c_username, c_password);
		body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		value = StringHelper.getStringByString(body, "sd=", "&", false);
		Assert.assertEquals(value, "0:2:3:0", "Error: actual result is " + value);
	}
	
	@Test(priority = 13, description = "TC004: Verify that user can change Temperature setting successfully")
	public void TC004_Temperature() {
		pageDashboard = new PageDashboard();
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		
		settingPage.enableTemperature();
		TimeHelper.sleep(3 *1000);
		api.userLogin(c_username, c_password);
		String body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		String value = StringHelper.getStringByString(body, "td=", "&", false);
		Assert.assertEquals(value, "1:3:1529:0", "Error: actual result is " + value);

		settingPage.disableTemperature();
		api.userLogin(c_username, c_password);
		body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		value = StringHelper.getStringByString(body, "td=", "&", false);
		Assert.assertEquals(value, "0:3:1529:0", "Error: actual result is " + value);
	}
	
	@Test(priority = 14, description = "TC005: Verify that user can change Night Vision setting successfully")
	public void TC005_NightVision() {
		pageDashboard = new PageDashboard();
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		
		settingPage.setNightVisionOn();
		TimeHelper.sleep(3 *1000);
		api.userLogin(c_username, c_password);
		String body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		String value = StringHelper.getStringByString(body, "ir=", "&", false);
		Assert.assertEquals(value, "1", "Error: actual result is " + value);

		settingPage.setNightVisionOff();
		api.userLogin(c_username, c_password);
		body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		value = StringHelper.getStringByString(body, "ir=", "&", false);
		Assert.assertEquals(value, "2", "Error: actual result is " + value);
		
		settingPage.setNightVisionAuto();
		api.userLogin(c_username, c_password);
		body = api.getCamInfo(device.getDevice_id()).path("data.device_response.body");
		value = StringHelper.getStringByString(body, "ir=", "&", false);
		Assert.assertEquals(value, "0", "Error: actual result is " + value);
	}
	
	@Test(priority = 1, description = "Light Source Frequency auto set during setup")
	public void verifyLightSourceFrequencyAutoSetup() {
		PageGetStart page = new PageGetStart();
		pageDashboard = page.checkAndSignin(username, password);		
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		float timeZone = settingPage.getTimeZoneSystem();
		String lightSource = settingPage.getLightSourceFrequencyValue();
		Assert.assertTrue(settingPage.verifyLightSourceFrequencyByTimeZone(timeZone, lightSource),
				String.format("Light source frequency display wrong. Time zone is: %s, expected %s, actual: %s", 
						timeZone, (timeZone > -4 && timeZone < 9 ? "50Hz" : "60Hz") , lightSource));
	}
	
	@Test(priority = 2, description = "Light Source Frequency manual set via camera setting page")
	public void verifyUserCanChangeLightSourceFrequency() {
		float timeZone = 10000;
		String currentFrequency;
		String newFrequency;
		settingPage = new PageCameraSetting();
		try {
			PageGetStart page = new PageGetStart();
			pageDashboard = page.checkAndSignin(username, password);			
			settingPage = pageDashboard.openDeviceSetting(device.getName());
			timeZone = settingPage.getTimeZoneSystem();
			currentFrequency = settingPage.getLightSourceFrequencyValue();
			settingPage.changeLightSourceFrequency();
			newFrequency = settingPage.getLightSourceFrequencyValue();
			Assert.assertNotEquals(currentFrequency, newFrequency, 
					String.format("Frequency should change. Before: %s. After change frequency: %s",
							currentFrequency, newFrequency));
		} finally {
			String freq = (timeZone > -4 && timeZone < 9) ? "50Hz" : "60Hz";
			if(!settingPage.getLightSourceFrequencyValue().equals(freq)) {
				settingPage.changeLightSourceFrequency();
			}
		}
	}
	
	@Test(priority = 3, description = "Zoning setting and apply to camera: add, edit, remove, update")
	public void verifyZoningSetting() {
		String zone1 = "Zone 1";
		String zone2 = "Zone 2";
		PageGetStart page = new PageGetStart();
		pageDashboard = page.checkAndSignin(username, password);
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		settingPage.enableMotionDetection();
		settingPage.clickOnZoneDetectionMotion();
		settingPage.removeAllZone();
		Assert.assertTrue(settingPage.verifyRemoveAllZoneSuccessful(), "All zone should remove successful.");
		settingPage.addZone(zone1);
		Assert.assertTrue(settingPage.verifyZoneByName(zone1), String.format("Zone %s should create successful.", zone1));
		settingPage.editZone(zone1);
		settingPage.addZone(zone2);
		Assert.assertTrue(settingPage.verifyZoneByName(zone1, zone2), String.format("Zone %s, %s should create successful.", zone1, zone2));
		settingPage.clickUpdateZoneToCamera();
		settingPage.clickOnZoneDetectionMotion();
		settingPage.clickRefreshZoneButton();
		Assert.assertTrue(settingPage.verifyZoneByName(zone1, zone2), "Update zone to camera fail.");
	}
	
	@Test(priority = 4, description = "Send App Log")
	public void verifySendAppLog() {
//		String email = "qatest123@mailinator.com";
		String user = "qatest123";
		String email = user + "@restmail.net";
		String subJ = StringHelper.randomString("Feedback from user", 10);
		PageGetStart page = new PageGetStart();
		pageDashboard = page.checkAndSignin(username, password);
		PageGmail gmailPage = new PageGmail();
		
//		pageDashboard = new PageDashboard();
		settingPage = new PageCameraSetting();
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		settingPage.sendCameraLog();
		gmailPage.sendEmail(email, subJ);
		
		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(user);
		restMailHelper.deleteAllRestMail();
		Assert.assertTrue(restMailHelper.getEmailSubject().contains(subJ), "Subject feedback email should contains string: " + subJ);
	}
	
	@Test(priority = 30, description = "Delete all events")
	public void verifyDeleteAllEvents() {
		PageGetStart page = new PageGetStart();
		pageDashboard = page.checkAndSignin(username, password);
		
		
		settingPage = new PageCameraSetting();
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		//Delete all events
		settingPage.deleteAllEvents();
		// Verify empty event in page stream view
		settingPage.exitPage();
		PageStreamView streamViewPage = pageDashboard.selectDeviceToView(device.getName());
		Assert.assertTrue(streamViewPage.verifyEmptyEvent(), "Empty event image should display.");
		// Verify empty event in time line page
		streamViewPage.exitPage();
		PageTimeline timeLinePage = pageDashboard.navigateToTimelinePage();
		Assert.assertTrue(timeLinePage.verifyEmptyEvent(), "Empty event image should display.");
	}
	
	@Test(priority = 100, description = "Verify user can remove camera")
	public void verifyUserCanRemoveCamera() throws SerialPortException{
		terminal = new Terminal(c_comport, true);
		PageGetStart page = new PageGetStart();
		pageDashboard = page.checkAndSignin(username, password);		
		settingPage = new PageCameraSetting();
		// Go to setting and remove camera
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		terminal.clearTeratermLog();
		settingPage.removeCamera();
		// Get camera log and verify reset factory command
		String camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("reset_factory"), "Reset factory command should executed.");
		// Verify camera deleted on dashboard 
		if(!pageDashboard.verifyEmptyDevice()) {
			Assert.assertFalse(pageDashboard.verifyDeviceExisted(device.getName()), "Camera should remove from device.");
		}
	}
	
	@Test(priority = 31, description = "Edit Sound/Motion Detection Settings and back from stream view. (camera has no event)")
	public void verifyEditSoundMotionDetectionSetting() {
		PageGetStart page = new PageGetStart();
		pageDashboard = page.checkAndSignin(username, password);
		// Delete all events
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		settingPage.deleteAllEvents();
		settingPage.exitPage();
		// Go to stream view page and Edit 
		PageStreamView streamViewPage = pageDashboard.selectDeviceToView(device.getName());
		streamViewPage.clickEditSoundMotionSetting();
		Assert.assertTrue(settingPage.verifyDeviceSettingPage(), "App should navigate to Device Setting page.");
		// Click back button and and verify back to Stream View page
		streamViewPage.exitPage();
		Assert.assertTrue(streamViewPage.verifyStreamViewPage(), "App should back to stream view page.");
	}
}
