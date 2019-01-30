package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.object.Terminal;

import android.kodak.base.TestBase;
import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import jssc.SerialPortException;

public class TM_003_Setup extends TestBase {
	private Terminal terminal;

	@Test(priority = 0, description = "TC001: Verify that user can setup camera successfully")
	public void setupFirstCamWithCurrentWifi() throws SerialPortException {
		terminal = new Terminal(c_comport);
		PageGetStart page = new PageGetStart();
		PageDashboard devicePage = page.checkAndSignin(c_username, c_password);
				
		devicePage.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_MEDIA);		
		devicePage.getAddDeviceBigBtn().click();
		
		devicePage.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_LOCATION);
					
		devicePage.getBabySeriesImage().click();
		
		devicePage.getDeviceModelLabel("520").click();
		terminal.sendCommand("pair", "start_pairing_mode", 10);
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
	

	@Test(priority = 0, description = "TC001: Verify that user can setup camera with WEP successfully", dataProvider="setupCameraProvider")
	public void setupCameraWithSpecificWifi(String wifiname, String wifiPwd, String securityType, String devicessid1, String comport1) throws SerialPortException {
	
		terminal = new Terminal(comport1);
		terminal.sendCommand("shell 0 LAnXh7fr7yB3JJEtKqkFBxN3jrEPS4sN");
		PageGetStart page = new PageGetStart();
		PageDashboard devicePage = page.checkAndSignin(c_username, c_password);
		
		devicePage.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_MEDIA);		
		devicePage.getAddDeviceBigBtn().click();
		
		try {
			devicePage.getLocationPermissionProcessBtn().click();
			devicePage.getAndroidPermissionAllowBtn().click();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		devicePage.getHomeSeriesImage().click();
		devicePage.proceedAnyway5GWifiIfAsk();
		devicePage.getDeviceModelLabel("670").click();
		terminal.sendCommand("pair", "start_pairing_mode", 10);
		devicePage.getContinueButton().click();
		devicePage.getContinueButton().click();
		// Click on device name
		if (devicePage.getSSIDLabel(devicessid1).getWebElement() != null) {
			devicePage.getSSIDLabel(devicessid1).click();
		} else {
			devicePage.getSSIDRefreshImage().click();
			devicePage.getSSIDLabel(devicessid1).click();
		}
		// Config wifi for camera
		devicePage.configWifiForCamera(wifiname, securityType, wifiPwd);

		if(devicePage.getMobileDataContinueButton().getWebElement()!=null) {
			devicePage.getMobileDataContinueButton().click();
		}
		devicePage.getCustomButton().click();
		devicePage.getContinueButton().click();
		devicePage.getDoneButton().click();
		// Verify camera in dashboard after settup
		Assert.assertTrue(devicePage.getListCameraNameInDashBoard().contains(devicessid1),
				String.format("Camera: %s should display in dashboard.", devicessid1));
	}
	/**
	 * Data provider for test case setup camera with specific wifi
	 * 
	 * @param context: get test param from xml config file
	 * @return
	 */
	@DataProvider(name="setupCameraProvider")
	public Object[][] wifiSettup(ITestContext context){
		String wepWifiName = context.getCurrentXmlTest().getParameter("wepwifiname");
		String wepWifiPwd = context.getCurrentXmlTest().getParameter("wepwifipassword");
		String comport1 = context.getCurrentXmlTest().getParameter("comport1");
		String deviceSSid1 = context.getCurrentXmlTest().getParameter("devicessid1");
		String wifiname = "`~!@#$%^&*()_-+={}[]|\\:;\"' ?<>,.";
		String wifiPwd = "13579135";
	    return new Object[][] {
	    	{wepWifiName, wepWifiPwd, "WEP", deviceSSid1, comport1},
	    	{wifiname, wifiPwd, "WPA2", deviceSSid1, comport1}
	    };
	}
	
	// pair the second camera in the test case above to the main wifi 
	@Parameters({ "devicessid1", "comport1"})
	@Test (priority=1, description="Re-setup the same camera to another SSID")
	public void reSetupToAnotherWifi(String devicessid1, String comport1) throws SerialPortException {
		terminal = new Terminal(comport1);
		PageGetStart page = new PageGetStart();
		PageDashboard devicePage = page.checkAndSignin(c_username, c_password);
				
		devicePage.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_MEDIA);		
		devicePage.getAddDeviceBigBtn().click();
		
		devicePage.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_LOCATION);
					
		devicePage.getHomeSeriesImage().click();
		
		devicePage.getDeviceModelLabel("680").click();
		terminal.sendCommand("pair", "start_pairing_mode", 10);
		devicePage.getContinueButton().click();
		devicePage.getContinueButton().click();
		if (devicePage.getSSIDLabel(devicessid1).getWebElement() != null) {
			devicePage.getSSIDLabel(devicessid1).click();
		} else {
			devicePage.getSSIDRefreshImage().click();
			devicePage.getSSIDLabel(devicessid1).click();
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
	
	@AfterMethod
	public void cleanup() throws SerialPortException{
		terminal.closePort();
	}
}
