package Android_Test_Kodak.android.kodak.testcases.bugs;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.webmailhelper.MailinatorHelper;

import Android_Test_Kodak.android.kodak.base.TestBase;
import Android_Test_Kodak.android.kodak.object.PageDashboard;
import Android_Test_Kodak.android.kodak.object.PageGetStart;
import Android_Test_Kodak.android.kodak.object.PageHomeMenu;
import Android_Test_Kodak.android.kodak.object.PageProfile;

public class Bug_5262_UpdateEmailProfile extends TestBase {
	PageDashboard pageDashboard;
	PageProfile profilePage;
	@Test(priority = 1, description = "TC001: App should update profile after change new email successfully")
	public void TC001_UpdateEmailProfile() {
		String newEmail = "";
		
		PageGetStart page = new PageGetStart();
		profilePage = new PageProfile();
		
		pageDashboard = page.checkAndSignin(c_username, c_password);
		PageHomeMenu homeMenupage = pageDashboard.gotoHomeMenuPage();
		homeMenupage.gotoHomeProfilePage();
		
		String currentEmail = profilePage.getCurrentEmailProfile();
		String prefixMail = StringHelper.randomString(currentEmail.substring(0,currentEmail.indexOf('@')), 3).toLowerCase();
		newEmail = prefixMail + "@mailinator.com";
		
		profilePage.updateEmailProfile(newEmail);
		Assert.assertTrue(profilePage.verifyChangeEmailConfimationPanel(), "Change email confirmation panel should display.");
		profilePage.exitPage();
		
		MailinatorHelper mailinatorHelper = new MailinatorHelper(currentEmail);
		mailinatorHelper.confirmChangeEmailOld(newEmail);
		mailinatorHelper = new MailinatorHelper(newEmail);
		mailinatorHelper.confirmChangeEmailNew();

		homeMenupage = pageDashboard.gotoHomeMenuPage();
		homeMenupage.gotoHomeProfilePage();
		Assert.assertTrue(
				profilePage.verifyEmailProfileUpdated(newEmail),
				String.format("Email profile should update, Expected: %s. Actual: %s", newEmail, currentEmail));
	}
}
