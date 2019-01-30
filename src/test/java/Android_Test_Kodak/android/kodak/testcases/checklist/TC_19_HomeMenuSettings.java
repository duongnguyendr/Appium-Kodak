package Android_Test_Kodak.android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.KodakApiHelper;
import com.cinatic.object.Device;

import Android_Test_Kodak.android.kodak.base.TestBase;
import Android_Test_Kodak.android.kodak.object.PageCameraSetting;
import Android_Test_Kodak.android.kodak.object.PageDashboard;
import Android_Test_Kodak.android.kodak.object.PageGetStart;
import Android_Test_Kodak.android.kodak.object.PageHomeSetting;
import Android_Test_Kodak.android.kodak.object.PageStreamView;

public class TC_19_HomeMenuSettings extends TestBase {
	KodakApiHelper api;
	Device device;
	PageDashboard pageDashboard;
	PageCameraSetting settingPage;
	PageHomeSetting homeSettingPage;
	PageGetStart getStartPage;
	
	@BeforeMethod
	public void Precondition() {
		api = new KodakApiHelper(c_server, c_username);
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 1, description = "Verify temperature (C and F) will show according phone setting")
	public void verifyTemperatureSetup() {
		getStartPage = new PageGetStart();
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		homeSettingPage = pageDashboard.gotoHomeMenuPage().gotoHomeSetingPage();
		// Set Celsius temperature unit
		homeSettingPage.clickCelsiusUnit();
		homeSettingPage.exitPage();
		
		// Go to camera setting page and verify Celsius temperature unit
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		settingPage.enableTemperature();
		// verify temperature unit
		boolean rs = settingPage.verifyTemperatureUnitInDeviceSetting("℃");
		Assert.assertTrue(rs, "Temperature unit should display with Celsius in camera setting.");
		settingPage.exitPage();
		
		// Go to stream view page and verify Celsius temperature unit
		PageStreamView pageStreamView = pageDashboard.selectDeviceToView(device.getName());
		rs = pageStreamView.verifyTemperatureUnitInStreamPage("℃");
		Assert.assertTrue(rs, "Temperature unit should display with Celsius in stream view.");
		pageStreamView.exitPage();
		
		pageDashboard.gotoHomeMenuPage().gotoHomeSetingPage();
		// Set Fahrenheit temperature unit
		homeSettingPage.clickFahrenheitUnit();
		homeSettingPage.exitPage();
		
		// Go to camera setting page and verify Fahrenheit temperature unit
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		rs = settingPage.verifyTemperatureUnitInDeviceSetting("℉");
		Assert.assertTrue(rs, "Temperature unit should display with Fahrenheit in camera setting.");
		settingPage.exitPage();
		
		// Go to stream view page and verify Fahrenheit temperature unit
		pageDashboard.selectDeviceToView(device.getName());
		rs = pageStreamView.verifyTemperatureUnitInStreamPage("℉");
		Assert.assertTrue(rs, "Temperature unit should display with Fahrenheit in strean view.");
		pageStreamView.exitPage();
	}
	
	@Test(priority = 1, description = "Preview mode in dashboard")
	public void verifyPreviewModeInDashBoard() {
		getStartPage = new PageGetStart();
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		homeSettingPage = pageDashboard.gotoHomeMenuPage().gotoHomeSetingPage();
		// Enable preview mode
		homeSettingPage.enablePreviewMode(true);
		homeSettingPage.exitPage();
		// Go to home menu settings and verify
		pageDashboard.gotoHomeMenuPage().gotoHomeSetingPage();
		boolean rs = homeSettingPage.verifyPreviewMode("ON");
		Assert.assertTrue(rs, "Preview mode should enable.");
		// Disable preview mode
		homeSettingPage.enablePreviewMode(false);
		homeSettingPage.exitPage();
		// Go to home menu settings and verify
		pageDashboard.gotoHomeMenuPage().gotoHomeSetingPage();
		rs = homeSettingPage.verifyPreviewMode("OFF");
		Assert.assertTrue(rs, "Preview mode should disable.");
	}

}
