package Android_Test_Kodak.android.kodak.testcases.checklist;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import Android_Test_Kodak.android.kodak.base.TestBase;
import Android_Test_Kodak.android.kodak.object.PageDashboard;
import Android_Test_Kodak.android.kodak.object.PageDevicesOrder;
import Android_Test_Kodak.android.kodak.object.PageGetStart;
import Android_Test_Kodak.android.kodak.object.PageHomeMenu;
import Android_Test_Kodak.android.kodak.object.PageLogin;

public class TC_13_HomeMenuDashboard extends TestBase {
	PageGetStart getStartPage;
	PageLogin loginPage;
	PageDashboard pageDashboard;
	PageHomeMenu homeMenuPage;
	
	@Test(priority = 1, description = "Preview in mobile network")
	public void verifyPreviewInMobileNetwork() {
		PageGetStart page = new PageGetStart();
		pageDashboard = page.checkAndSignin(c_username, c_password);
		homeMenuPage = new PageHomeMenu();
		// Enable preview mode
		pageDashboard.gotoHomeMenuPage();
		homeMenuPage.clickOnHomeDashboardTextView();
		homeMenuPage.enablePreviewMode(true);
		// Go to dashboard and go back again to verify
		homeMenuPage.exitPage();
		pageDashboard.gotoHomeMenuPage();
		homeMenuPage.clickOnHomeDashboardTextView();
		Assert.assertTrue(homeMenuPage.verifyPreviewEnable(true), "Preview mode should enable.");
		homeMenuPage.enablePreviewMode(false);
		Assert.assertTrue(homeMenuPage.verifyPreviewEnable(false), "Preview mode should disable.");
	}
	
	@Test(priority = 35, description = "Verify devices order")
	public void verifyDevicesOrder() {
		PageGetStart page = new PageGetStart();
		PageDevicesOrder deviceOrderPage = new PageDevicesOrder();
		homeMenuPage = new PageHomeMenu();
//		PageLogin loginPage = page.goToSigninPage();
//		pageDashboard = loginPage.loginApp(c_username, c_password);
//		dashBoardPage = new PageDashboard();
		pageDashboard = page.checkAndSignin(c_username, c_password);
		List<String> lstCamInDashBoard = pageDashboard.getListCameraNameInDashBoard();
		Assert.assertTrue(lstCamInDashBoard.size() > 1, "Not enought camera for this test case. Expected > 1, actual: " + lstCamInDashBoard.size());
		// Go to Device order page
		pageDashboard.gotoHomeMenuPage();
		homeMenuPage.clickOnHomeDashboardTextView();
		homeMenuPage.gotoDevicesOrderpage();
		
		List<String> lstDeviceBefore = deviceOrderPage.getListDeviceName();
		Assert.assertEquals(lstCamInDashBoard, lstDeviceBefore, "The order of device not match.");
		//Change devices order
		deviceOrderPage.changeDevicesOrder();
		List<String> lstDeviceAfter = deviceOrderPage.getListDeviceName();
		Assert.assertNotEquals(lstDeviceBefore, lstDeviceAfter, "Devices order should change.");
		// Back to dash board page and verify
		deviceOrderPage.exitPage();
		lstCamInDashBoard = pageDashboard.getListCameraNameInDashBoard();
		Assert.assertEquals(lstCamInDashBoard, lstDeviceAfter, "The order of device in dashboard should match with order in device order page.");
		// Logout and login again to verify
		loginPage = page.goToSigninPage();
		loginPage.loginApp(c_username, c_password);
		lstCamInDashBoard = pageDashboard.getListCameraNameInDashBoard();
		Assert.assertEquals(lstCamInDashBoard, lstDeviceAfter, "The order of device in dashboard should match with order in device order page.");
	}
}
