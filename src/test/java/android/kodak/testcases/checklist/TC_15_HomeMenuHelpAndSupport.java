package android.kodak.testcases.checklist;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.KodakApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.log.Log;
import com.cinatic.object.Device;

import android.kodak.base.TestBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageFeedback;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageGmail;
import android.kodak.object.PageHomeMenu;
import net.restmail.KodakRestMailHelper;

public class TC_15_HomeMenuHelpAndSupport extends TestBase{
	private PageGetStart getStartPage;
	private PageDashboard pageDashboard;
	private PageHomeMenu homeMenuPage;
	private PageFeedback feedbackPage;
	private PageGmail gmailPage;
	private Device device;
	
	@BeforeMethod
	public void Precondition() {
		KodakApiHelper api;
		api = new KodakApiHelper(c_server, c_username);
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 1, description = "Send device log (fw log & app log)")
	public void verifySendDeviceLog() {
//		String email = "qatest8888@mailinator.com";
		String user = "qatest8888";
		String email = user + "@restmail.net";
		String subJ = StringHelper.randomNumber("Feedback from: ", 10);
		String description = StringHelper.randomNumber("Descriptions ", 10);
		
		getStartPage = new PageGetStart();
		homeMenuPage = new PageHomeMenu();
		feedbackPage = new PageFeedback();
		gmailPage = new PageGmail();
		
		pageDashboard = getStartPage.checkAndSignin(c_username, c_password);
		
		pageDashboard.gotoHomeMenuPage();
		homeMenuPage.gotoFeedbackPage();
		// switch all option in What is the issue
		List<String> rs = feedbackPage.switchAllWhatIssueOption();
		if(rs.size() > 0) {
			Assert.fail("Cannot select options: " + rs.toString());
		}
		// switch all option in When happened
		rs = feedbackPage.switchAllWhenIssueHappened();
		if(rs.size() > 0) {
			Assert.fail("Cannot select options: " + rs.toString());
		}
		// Select device
		feedbackPage.selectDeviceByName(device.getName());
		feedbackPage.exitPage();
		// Input description
		feedbackPage.inputShortDescription(description);
		// Collect data to compare with email received
		rs = feedbackPage.collectReportData();
		rs.add(c_username);
		rs.add(device.getName());
		// Send email report
		feedbackPage.clickSendReport();
		gmailPage.sendEmail(email, subJ);
		// Open email and verify
		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(user);
		restMailHelper.deleteAllRestMail();
		String contentEmail = restMailHelper.getRestMailDriver().getTextEmail();
		Log.info("Content email: " + contentEmail);
		for(String data : rs) {
			if(!contentEmail.contains(data)) {
				Assert.fail(String.format("Option: %s when send log not contain in email.", data));
			}
		}
	}
}
