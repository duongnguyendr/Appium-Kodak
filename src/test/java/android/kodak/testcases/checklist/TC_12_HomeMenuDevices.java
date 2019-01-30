package android.kodak.testcases.checklist;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.KodakApiHelper;
import com.cinatic.LucyApiHelper;
import com.cinatic.object.Device;

import android.kodak.base.TestBase;
import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageGrantAccess;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageManageCloudStorage;
import android.kodak.object.PageNotifications;
import jssc.SerialPortException;
import net.restmail.KodakRestMailHelper;

public class TC_12_HomeMenuDevices extends TestBase{
	private PageGetStart getStartPage;
	private PageDashboard pageDashboard;
	private PageHomeMenu homeMenuPage;
	private PageGrantAccess grantAccessPage;
	private Device device;
	private PageCameraSetting settingPage;
	private PageNotifications notifyPage;
	
	@BeforeMethod
	public void Precondition() throws SerialPortException {

		LucyApiHelper api;
		api = new LucyApiHelper(c_server, c_username);
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 1, description = "Grant Access and Share devices with access right OFF")
	public void grantAccessWithAccessRightOff() {
//		String username = "qatest135";
//		String emailGranted = "qatest135@mailinator.com";
		String username = "qatest1235";
		String emailGranted = "qatest1235@restmail.net";
		String password = "Cinatic123";
		getStartPage = new PageGetStart();
		grantAccessPage = new PageGrantAccess();
		
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		Assert.assertEquals(pageDashboard.getAddDeviceBigBtn().isEnabled(), true);
		
		homeMenuPage = pageDashboard.gotoHomeMenuPage();
		homeMenuPage.gotoGrantAccessPage();
		try {
			// Remove grant access to email if existed
			if(grantAccessPage.verifyEmailWasGrantAccess(emailGranted)) {
				grantAccessPage.removeAllSharedUser();
			}
			// grant access to user
			grantAccessPage.grantAccessToUser(emailGranted, device.getName(), false);
			Assert.assertTrue(grantAccessPage.verifyEmailWasGrantAccess(emailGranted), "Email: " + emailGranted + " should appear in list user shared.");
			pageDashboard.exitPage();
			KodakRestMailHelper restMailHelper = new KodakRestMailHelper(username);
			restMailHelper.getRestMailDriver().downloadEmail();
			restMailHelper.deleteAllRestMail();
			restMailHelper.confirmGrantAccesEmail(username, password, device.getName());
			
			getStartPage.checkAndSignin(username, password);
			settingPage = pageDashboard.openDeviceSetting(device.getName());

			// verify shared user and setting
			Assert.assertTrue(settingPage.verifyDeviceSharedBy(c_username), "Owner device should display with: " + c_username);
			Assert.assertFalse(settingPage.verifySettingTitleDisplay(), "SETTINGS title in camera setting page should not display.");
		} finally {
			// login to main account and remove grant access user
			getStartPage.exitPage();
			pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
			homeMenuPage = pageDashboard.gotoHomeMenuPage();
			homeMenuPage.gotoGrantAccessPage();
			if(grantAccessPage.verifyEmailWasGrantAccess(emailGranted)) {
				grantAccessPage.removeAllSharedUser();
			}
		}
	}
	
	@Test(priority = 2, description = "Grant Access and Share devices with access right ON")
	public void grantAccessWithAccessRightOn() {
//		String username = "qatest135";
//		String emailGranted = "qatest135@mailinator.com";
		String username = "qatest1235";
		String emailGranted = "qatest1235@restmail.net";
		String password = "Cinatic123";
		getStartPage = new PageGetStart();
		grantAccessPage = new PageGrantAccess();
		
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		Assert.assertEquals(pageDashboard.getAddDeviceBigBtn().isEnabled(), true);
		
		homeMenuPage = pageDashboard.gotoHomeMenuPage();
		homeMenuPage.gotoGrantAccessPage();
			// Remove grant access to email if existed
		try {
			if(grantAccessPage.verifyEmailWasGrantAccess(emailGranted)) {
				grantAccessPage.removeAllSharedUser();
			}
			//grant access to user
			grantAccessPage.grantAccessToUser(emailGranted, device.getName(), true);
			Assert.assertTrue(grantAccessPage.verifyEmailWasGrantAccess(emailGranted), "Email: " + emailGranted + " should appear in list user shared.");
			pageDashboard.exitPage();
			KodakRestMailHelper restMailHelper = new KodakRestMailHelper(username);
			restMailHelper.deleteAllRestMail();
			restMailHelper.confirmGrantAccesEmail(username, password, device.getName());

			getStartPage.checkAndSignin(username, password);
			settingPage = pageDashboard.openDeviceSetting(device.getName());

			// verify shared user and setting
			Assert.assertTrue(settingPage.verifyDeviceSharedBy(c_username), "Owner device should display with: " + c_username);
			Assert.assertTrue(settingPage.verifySettingTitleDisplay(), "SETTINGS title in camera setting page should display.");
		}finally {
			// login to main account and remove grant access user
			getStartPage.exitPage();
			pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
			homeMenuPage = pageDashboard.gotoHomeMenuPage();
			homeMenuPage.gotoGrantAccessPage();
			if(grantAccessPage.verifyEmailWasGrantAccess(emailGranted)) {
				grantAccessPage.removeAllSharedUser();
			}
		}
	}
	
	@Test(priority = 3, description = "Do not disturb")
	public void verifyDoNotDisturb() {
		getStartPage = new PageGetStart();
		grantAccessPage = new PageGrantAccess();
		notifyPage = new PageNotifications();
		
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		homeMenuPage = pageDashboard.gotoHomeMenuPage();
		// Enable do not disturb
		homeMenuPage.gotoNotificationPage();
		notifyPage.enableDoNotDisturb(true);
		// Go to dashboard and go back to notification page verify
		notifyPage.exitPage();
		pageDashboard.gotoHomeMenuPage();
		homeMenuPage.gotoNotificationPage();
		Assert.assertTrue(notifyPage.verifyDoNotDisturbEnable(true), "Do not disturb should enable.");
		// Disable do not disturb
		notifyPage.enableDoNotDisturb(false);
		Assert.assertTrue(notifyPage.verifyDoNotDisturbEnable(false), "Do not disturb should disable.");
	}
	
	@Test(priority = 1, description = "Verify free cloud storage plan")
	public void verifyFreeCloudStorage() {
		getStartPage = new PageGetStart();
		
		KodakApiHelper api;
		api = new KodakApiHelper(c_server, c_username);
		api.userLogin(c_username, c_password);
		api.getDevices();
		Device device = api.getDeviceByDeviceId(c_deviceid);
		// Get current storage plan in setting page
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		PageCameraSetting camSetting = pageDashboard.openDeviceSetting(device.getName());
		String settingStorage = camSetting.getCurrentStoragePlan();
		
		camSetting.exitPage();
		homeMenuPage = pageDashboard.gotoHomeMenuPage();
		// Get Storage plan in Manage Storage Plan page
		PageManageCloudStorage pageManageCloud = homeMenuPage.gotoDevices_ManageCloudStoragePage();
		String storagePlan = pageManageCloud.getYourCurrentStoragePlan();
		Assert.assertEquals(storagePlan, settingStorage, String.format("Current storage plan is: %s, but found: %s.", settingStorage, storagePlan));
		// Verify device in current storage plan
		List<String> devices = pageManageCloud.getListDevicesName();
		Assert.assertTrue(devices.contains(device.getName()), "your device should stay in list device of current storage plan.");
		
	}
	
	@Test(priority = 1, description = "Verify setting notification for devices")
	public void verifyNotificationUnderDevice() {
		getStartPage = new PageGetStart();
		notifyPage = new PageNotifications();
		
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		// Verify camera existed.
		Assert.assertTrue(pageDashboard.getListCameraNameInDashBoard().size() > 0, "There are no camera in this account.");
		homeMenuPage = pageDashboard.gotoHomeMenuPage();
		homeMenuPage.gotoNotificationPage();
		
		// Disable notification
		notifyPage.enableAllDeviceNotification(false);
		notifyPage.exitPage();
		
		// Go notification page and verify
		pageDashboard.gotoHomeMenuPage().gotoNotificationPage();
		Assert.assertTrue(notifyPage.verifyAllDeviceNotificationEnable(false), "Notification should disable.");
		
		// Enable notification
		notifyPage.enableAllDeviceNotification(true);
		notifyPage.exitPage();
		
		// Logout - login again and verify
		getStartPage.goToSigninPage().loginApp(c_username, c_password);
		pageDashboard.gotoHomeMenuPage().gotoNotificationPage();
		Assert.assertTrue(notifyPage.verifyAllDeviceNotificationEnable(true), "Notification should enable.");
	}
}