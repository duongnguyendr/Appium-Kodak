package Android_Test_Kodak.android.kodak.testcases.bugs;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.TerminalHelper;
import com.cinatic.driver.DriverManager;

import Android_Test_Kodak.android.kodak.base.TestBase;
import Android_Test_Kodak.android.kodak.object.PageDashboard;
import Android_Test_Kodak.android.kodak.object.PageGetStart;
import Android_Test_Kodak.android.kodak.object.PageStreamView;

public class Bug_5190_ClickCameraToViewStream extends TestBase {
	private PageStreamView streamViewPage;
	private PageGetStart getStartPage;
	private PageDashboard pageDashboard;

	@Test(priority = 1, description = "TC001: Press here for live view button show in the first time")
	public void TC001_VerifyShowLiveStreamButtonInTheFirstTime() throws IOException, InterruptedException {
		String clearAdbCmd = "adb shell pm clear com.perimetersafe.kodaksmarthome";
		String outPut = TerminalHelper.exeBashCommand(clearAdbCmd);
		Assert.assertTrue(outPut.contains("Success"));
		DriverManager.createWebDriver(driverSetting);
		getStartPage = new PageGetStart();
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		Assert.assertTrue(pageDashboard.verifyPressForLiveViewButtonDisplay(true),
				"Live View Button should display at the first time.");
	}

	@Test(priority = 2, description = "TC002: Click on Press here for live view button")
	public void TC002_VerifyClickOnShowLiveStreamButton() {
		getStartPage = new PageGetStart();
		streamViewPage = new PageStreamView();
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		Assert.assertTrue(pageDashboard.verifyPressForLiveViewButtonDisplay(true),
				"Live View Button should display at the first time.");
		pageDashboard.clickLiveViewButton();
		Assert.assertTrue(streamViewPage.getMenuImage().getWebElement().isDisplayed(), "Live stream page should display");
	}

	@Test(priority = 3, description = "TC003: Press here for live view button should not show because user already pressed it before.")
	public void TC003_VerifyShowLiveStreamButtonNotShow() {
		getStartPage = new PageGetStart();
		streamViewPage = new PageStreamView();
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		Assert.assertTrue(pageDashboard.verifyPressForLiveViewButtonDisplay(false),
				"Press here for live view button should not show because user already pressed it before.");
	}
}
