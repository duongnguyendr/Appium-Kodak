package Android_Test_Kodak.android.kodak.testcases.checklist;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import Android_Test_Kodak.android.kodak.base.TestBase;
import Android_Test_Kodak.android.kodak.object.PageDashboard;
import Android_Test_Kodak.android.kodak.object.PageGetStart;
import Android_Test_Kodak.android.kodak.object.PageTimeline;

public class TC_16_Timeline extends TestBase{

	@Test(priority = 1, description = "In Timeline tab, filter events by own devices and shared devices")
	public void verifyFilterEventsByDevice() {
		PageGetStart getStartPage = new PageGetStart();
		PageDashboard pageDashboard = new PageDashboard();
		PageTimeline timelinePage = new PageTimeline();
		int count = 0;
		
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		pageDashboard.navigateToTimelinePage();
		timelinePage.clickFilterButton();
		List<String> listDevice = timelinePage.getListDevices();
		// Verify clear filter function
		timelinePage.clickClearFilter();
		timelinePage.clickDoneFilter();
		timelinePage.clickFilterButton();
		for(String deviceName : listDevice) {
			Assert.assertFalse(timelinePage.verifyDeviceSeleted(deviceName), "Device name should not selected.");
		}
		timelinePage.clickDoneFilter();
		// Verify select device filter
		for(String deviceName : listDevice) {
			timelinePage.clickFilterButton();
			timelinePage.clickClearFilter();
			// Select device filter and verify filter selected
			timelinePage.selectDeviceFilterByName(deviceName);
			Assert.assertTrue(timelinePage.verifyDeviceSeleted(deviceName), "Device name should seleted.");
			timelinePage.clickDoneFilter();
			// Verify events in time line
			if(timelinePage.verifyEmptyEvent()) {
				count++;
			}else {
				Assert.assertTrue(timelinePage.verifyDetectionByDeviceName(deviceName), String.format("Filter by device: %s wrong.", deviceName));
			}
		}
		if(count == listDevice.size()) {
			Assert.fail("There are no event detect from device");	
		}
	}
	
	@Test(priority = 1, description = "CLOUD storage: MVR clip playback on apps")
	public void verifyCloudStorageClipPlayBack() {
		PageGetStart getStartPage = new PageGetStart();
		PageDashboard pageDashboard = new PageDashboard();
		PageTimeline timelinePage = new PageTimeline();
		
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		pageDashboard.navigateToTimelinePage();
		// Verify motion clip storage in cloud exist
		boolean rs = timelinePage.getVideoStorageInCloud();
		Assert.assertTrue(rs, "There are no motion video storage in cloud in timeline page.");
		// Playback motion clip and verify
		int playTimes = 3;
		int playFail = timelinePage.verifyPlayBackVideoFunction("cloud", playTimes);
		if (playFail != 0) {
			Assert.fail(String.format("Playback video in %s times, but fail %s times.", playTimes, playFail));
		}
	}
	
	@Test(priority = 1, description = "SD Card storage: MVR clip playback on apps")
	public void verifySDCardStorageClipPlayBack() {
		PageGetStart getStartPage = new PageGetStart();
		PageDashboard pageDashboard = new PageDashboard();
		PageTimeline timelinePage = new PageTimeline();
		
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		pageDashboard.navigateToTimelinePage();
		// Verify motion clip storage in cloud exist
		boolean rs = timelinePage.getVideoStorageInSDCard();
		Assert.assertTrue(rs, "There are no motion video storage in sd-card in timeline page.");
		// Playback motion clip and verify
		int playTimes = 3;
		int playFail = timelinePage.verifyPlayBackVideoFunction("sd-card", playTimes);
		if (playFail != 0) {
			Assert.fail(String.format("Playback video in %s times, but fail %s times.", playTimes, playFail));
		}
	}
}
