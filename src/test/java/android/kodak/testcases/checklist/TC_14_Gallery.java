package android.kodak.testcases.checklist;

import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.KodakApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.object.Device;

import android.kodak.base.TestBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGallery;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageGmail;
import android.kodak.object.PageStreamView;
import android.kodak.object.PageTimeline;
import net.restmail.KodakRestMailHelper;

public class TC_14_Gallery extends TestBase {
	private PageGetStart getStartPage;
	private PageDashboard pageDashboard;
	private PageGallery galleryPage;
	private PageTimeline timelinePage;
	
	@Test(priority = 1, description = "Download motion recording clip to gallery")
	public void verifyDownloadMotionClipToGallery() {
		getStartPage = new PageGetStart();
		galleryPage = new PageGallery();
		timelinePage = new PageTimeline();
		
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		pageDashboard.navigateToGalleryPage();
		// Delete all existed video in gallery page
		if (!galleryPage.verifyEmptyMotionVideo()) {
			galleryPage.deleteAllMotionVideo();
			Assert.assertTrue(galleryPage.verifyEmptyMotionVideo(), "All video should removed.");
		}
		// Navigate to timeline page and download motion video
		pageDashboard.navigateToTimelinePage();
		boolean rs = timelinePage.donwloadMotionVideo(); 
		if (!rs) {
			Assert.fail("There are no motion video in timeline page");
		}
		pageDashboard.navigateToGalleryPage();
		Assert.assertFalse(galleryPage.verifyEmptyMotionVideo(), "Gallery page should contains motion video.");
	}
	
	@Test(priority = 2, description = "Gallery tab view and function: Select, delete, share")
	public void verifyFunctionInGalleryTab() {
//		String email = "qatest1234@mailinator.com";
		String user = "qatest1234";
		String email = user + "@restmail.net";
		String subj = StringHelper.randomString("Share gallery from: ", 10);
		PageGmail gmailPage = new PageGmail();
		getStartPage = new PageGetStart();
		galleryPage = new PageGallery();
		timelinePage = new PageTimeline();
		
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		pageDashboard.navigateToGalleryPage();
		// If not video in gallery, navigate to time line tab and download motion clip
		if (galleryPage.verifyEmptyMotionVideo()) {
			pageDashboard.navigateToTimelinePage();
			boolean rs = timelinePage.donwloadMotionVideo();
			if(!rs) {
				Assert.fail("There are no motion video in timeline page");
			}else {
				pageDashboard.navigateToGalleryPage();
			}
		}
		// verify select motion clip  
		int numberVideo = galleryPage.selectMotionClip();
		Assert.assertTrue(galleryPage.verifyNumberVideoSeleted(numberVideo), String.format("Number video selected expected: %s", numberVideo));
		// verify share function
		galleryPage.shareMotionVideo();
		gmailPage.sendEmail(email, subj);
		
		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(user);
		restMailHelper.deleteAllRestMail();
		Assert.assertTrue(restMailHelper.getEmailSubject().contains(subj), "Subject feedback email should contains string: " + subj);
		// verify delete all video
		galleryPage.clickCancelSelected();
		galleryPage.deleteAllMotionVideo();
		Assert.assertTrue(galleryPage.verifyEmptyMotionVideo(), "All video should removed.");
	}
	
	@Test(priority = 1, description = "Share motion video & recorded video from: Stream view, Video Gallery, Events tab")
	public void verifyShareFunction() {
		String user = "qatest1234";
		String email = user + "@restmail.net";
		String subj = StringHelper.randomString("Share gallery from: ", 10);
		PageGmail gmailPage = new PageGmail();
		getStartPage = new PageGetStart();
		galleryPage = new PageGallery();
		timelinePage = new PageTimeline();
		
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		pageDashboard.navigateToGalleryPage();
		// If not video in gallery, navigate to time line tab and download motion clip
		if (galleryPage.verifyEmptyMotionVideo()) {
			pageDashboard.navigateToTimelinePage();
			boolean rs = timelinePage.donwloadMotionVideo();
			if(!rs) {
				Assert.fail("There are no motion video in timeline page");
			}else {
				pageDashboard.navigateToGalleryPage();
			}
		}
		// Verify share motion clip in gallery tab
		galleryPage.selectMotionClip();
		galleryPage.shareMotionVideo();
		// get attachment file in email before share
		List<String> attachments = gmailPage.getAttachmentsFile();
		// Send email
		gmailPage.sendEmail(email, subj);

//		RestMailHelper restMailHelper = new RestMailHelper(user);
		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(user);
		restMailHelper.deleteAllRestMail();
		// Get attachment file in received
		List<String> attachments1 = restMailHelper.getEmailAttachments();
		Collections.sort(attachments);
		Collections.sort(attachments1);
		Assert.assertEquals(attachments, attachments1, String.format("Expected: %s, actual: %s", attachments, attachments1));
		
		// Verify share motion clip in event tab
		galleryPage.clickCancelSelected();
		pageDashboard.navigateToTimelinePage();
		timelinePage.shareMotionVideo();
		// get attachment file in email before share
		attachments = gmailPage.getAttachmentsFile();
		// Send email
		gmailPage.sendEmail(email, subj);
		
		restMailHelper.getRestMailDriver().downloadEmail();
		// Get attachment file in received
		attachments1 = restMailHelper.getEmailAttachments();
		restMailHelper.deleteAllRestMail();
		Collections.sort(attachments);
		Collections.sort(attachments1);
		Assert.assertEquals(attachments, attachments1, String.format("Expected: %s, actual: %s", attachments, attachments1));
		
		pageDashboard.navigateToDashBoardPage();
		KodakApiHelper api;
		api = new KodakApiHelper(c_server, c_username);
		api.userLogin(c_username, c_password);
		api.getDevices();
		Device device = api.getDeviceByDeviceId(c_deviceid);
		PageStreamView streamViewPage = pageDashboard.selectDeviceToView(device.getName());
		// Verify share  motion in stream page
		Assert.assertFalse(streamViewPage.verifyEmptyEventInStreamPage(), "There are no event in stream view page.");
		timelinePage.shareMotionVideo();
		// get attachment file in email before share
		attachments = gmailPage.getAttachmentsFile();
		// Send email
		gmailPage.sendEmail(email, subj);
		
		restMailHelper.getRestMailDriver().downloadEmail();
		// Get attachment file in received
		attachments1 = restMailHelper.getEmailAttachments();
		restMailHelper.deleteAllRestMail();
		Collections.sort(attachments);
		Collections.sort(attachments1);
		Assert.assertEquals(attachments, attachments1, String.format("Expected: %s, actual: %s", attachments, attachments1));
	}
}
