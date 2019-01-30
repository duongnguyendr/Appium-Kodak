package Android_Test_Kodak.android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.LucyApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.object.Device;

import Android_Test_Kodak.android.kodak.base.TestBase;
import Android_Test_Kodak.android.kodak.object.PageCameraSetting;
import Android_Test_Kodak.android.kodak.object.PageDashboard;
import Android_Test_Kodak.android.kodak.object.PageGetStart;

public class TM_009_Detail extends TestBase {
	private String username;
	private String password;
	private String cameraname;
	private Device device;
	LucyApiHelper api;
	PageDashboard pageDashboard;

	@BeforeMethod
	public void Precondition() {
		this.cameraname = StringHelper.randomNumber("cam", 5);
		this.username = c_username;
		this.password = c_password;

		if (c_server.equals("production")) {
			api = new LucyApiHelper(c_server, c_username);
			api.userLogin(c_username, c_password);
			api.getDevices();
			device = api.getDeviceByDeviceId(c_deviceid);
		}
	}

	@Test(priority = 1, description = "TC001: Verify UI")
	public void TC001_UI() {
		PageGetStart page = new PageGetStart();
		pageDashboard = page.checkAndSignin(username, password);
		PageCameraSetting settingPage = pageDashboard.openDeviceSetting(device.getName());
		
		Assert.assertEquals(settingPage.getCameraNameValue().isDisplayed(), true);
		Assert.assertEquals(settingPage.getModelNameValue().isDisplayed(), true);
		Assert.assertEquals(settingPage.getMacAddressValue().isDisplayed(), true);
		Assert.assertEquals(settingPage.getFirmwareVersion().isDisplayed(), true);
		Assert.assertEquals(settingPage.getCurrentPlanValue().isDisplayed(), true);
		Assert.assertEquals(settingPage.getTimeZoneValue().isDisplayed(), true);
		Assert.assertEquals(settingPage.getWifiSignalStrengthValue().isDisplayed(), true);
		Assert.assertEquals(settingPage.getBatteryLevelValue().isDisplayed(), true);
		Assert.assertEquals(settingPage.getSendDeviceLog().isDisplayed(), true);
	}
	
	@Test(priority = 1, description = "TC002: Verify that user can change camera name successfully")
	public void TC002_ChangeCameraName() {
		PageGetStart page = new PageGetStart();
		pageDashboard = page.checkAndSignin(username, password);		
		PageCameraSetting settingPage = pageDashboard.openDeviceSetting(device.getName());
		settingPage.changeCameraName(cameraname);
		
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		Assert.assertEquals(device.getName(), cameraname);		
	}
	
}
