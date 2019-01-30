package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.LucyApiHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.base.TestBase;
import android.kodak.object.PageBase;
import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import jssc.SerialPortException;

public class TC_17_OTASetup extends TestBase{
	Device device;
	LucyApiHelper api;
	PageDashboard pageDashboard;
	PageCameraSetting settingPage;
	Terminal terminal;
	String deviceId1;
	String devicessid1;
	
	@BeforeMethod
	@Parameters({"comport1", "deviceid1", "devicessid1"})
	public void Precondition(String comport1, String deviceid1, String devicessid1) throws SerialPortException{
		this.deviceId1 = deviceid1;
		this.devicessid1 = devicessid1;

		terminal = new Terminal(comport1, true);
		api = new LucyApiHelper(c_server, c_username);
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(deviceid1);
	}
	
	@Test(priority = 40, description = "FW OTA manually by clicking via camera setting/detail/force upgrade")
	public void verifyOTAManualForceUpgrade() throws SerialPortException {
		// Downgrade camera firmware version
		terminal.sendCommand("sdcard bu_upgrade", "---------- Success ---------", 10);
		
		PageGetStart page = new PageGetStart();
		pageDashboard = page.goToSigninPage().loginApp(c_username, c_password);		
		settingPage = new PageCameraSetting();
		
		//Wait for device online
		pageDashboard.waitForDeviceOnline(device.getName(), 30);
		
		// Go to setting and update firmware		
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		Assert.assertTrue(settingPage.verifyNewFirmwareAlreadyToUpdate(), "New firmware version should already to update.");
		
		// Upgrade new firmware and verify
		boolean rs = settingPage.manualUpdateFirmware();
		Assert.assertTrue(rs, "Firmware setup");
	}
	
	@Test(priority = 41, description = "FW OTA during App setup")
	public void verifyOTADuringSetup() throws SerialPortException {
		// Downgrade camera firmware version
		terminal.sendCommand("sdcard bu_upgrade", "---------- Success ---------", 10);
		
		PageGetStart page = new PageGetStart();
		pageDashboard = page.checkAndSignin(c_username, c_password);
		
		pageDashboard.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_MEDIA);		
		pageDashboard.getAddDeviceBigBtn().click();
		
		try {
			pageDashboard.getLocationPermissionProcessBtn().click();
			pageDashboard.getAndroidPermissionAllowBtn().click();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// Pair camera with app
		pageDashboard.getHomeSeriesImage().click();
		pageDashboard.proceedAnyway5GWifiIfAsk();
		pageDashboard.getDeviceModelLabel("670").click();
		terminal.sendCommand("pair", "start_pairing_mode", 10);
		pageDashboard.getContinueButton().click();
		pageDashboard.getContinueButton().click();
		
		// Click on device name
		if (pageDashboard.getSSIDLabel(devicessid1).getWebElement() != null) {
			pageDashboard.getSSIDLabel(devicessid1).click();
		} else {
			pageDashboard.getSSIDRefreshImage().click();
			pageDashboard.getSSIDLabel(devicessid1).click();
		}
		
		// Config wifi for camera
		pageDashboard.configWifiForCamera(c_wifiname, "WPA2", c_wifipassword);
		
		// Verify new firmware available
		boolean rs = pageDashboard.verifyNewFirmwareAvailable();
		Assert.assertTrue(rs, "New firmware should available.");
		
		if(pageDashboard.getMobileDataContinueButton().getWebElement()!=null) {
			pageDashboard.getMobileDataContinueButton().click();
		}
		
		pageDashboard.getCustomButton().click();
		pageDashboard.inputCameraName(device.getName());
		pageDashboard.getContinueButton().click();
		pageDashboard.getDoneButton().click();
		
		// Verify camera in dashboard after settup
		Assert.assertTrue(pageDashboard.getListCameraNameInDashBoard().contains(device.getName()),
				String.format("Camera: %s should display in dashboard.", device.getName()));
		//Wait for device online
		pageDashboard.waitForDeviceOnline(device.getName(), 30);
		
		// Open setting page and verify latest firmware already.
		settingPage = pageDashboard.openDeviceSetting(device.getName());
		rs = settingPage.verifyLatestFirmwareVersion();
		Assert.assertTrue(rs, "New firmware already to update.");
	}
	
	@AfterMethod
	public void cleanUp() throws SerialPortException {
		terminal.closePort();
	}
}
