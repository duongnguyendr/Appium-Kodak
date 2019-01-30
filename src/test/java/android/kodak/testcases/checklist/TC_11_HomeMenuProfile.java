package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.LucyApiHelper;

import android.kodak.base.TestBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageProfile;

public class TC_11_HomeMenuProfile extends TestBase{
	private PageGetStart getStartPage;
	private PageDashboard pageDashboard;
	private PageProfile profilePage;
	PageHomeMenu homeMenupage;
	LucyApiHelper api;
	
	@Test (priority = 1, description = "User can change password of account")
	public void verifyUserCanChangePasswordAccount() {
		String newPassword = "Cinatic123";
		getStartPage = new PageGetStart();
		profilePage = new PageProfile();
		try {
			pageDashboard = getStartPage.checkAndSignin(c_username, c_password);	
			Assert.assertEquals(pageDashboard.getAddDeviceBigBtn().isEnabled(), true);
			
			homeMenupage = pageDashboard.gotoHomeMenuPage();
			homeMenupage.gotoHomeProfilePage();
			profilePage.clickChangePassword();
			profilePage.changePasswordAccount(c_password, newPassword);
			profilePage.exitPage();
			pageDashboard = getStartPage.goToSigninPage().loginApp(c_username, newPassword);
			Assert.assertEquals(pageDashboard.getAddDeviceBigBtn().isEnabled(), true);
			getStartPage.goToSigninPage();
		} finally {
			api = new LucyApiHelper(c_server, c_username);
			api.userLogin(c_username, newPassword);
			api.changePassword(newPassword, c_password, c_password);
		}
	}
	
	@Test(priority = 2, description = "Turn of Marketing Consent in Profile page")
	public void verifyUserCanTurnOnMarketingConsent() {
		getStartPage = new PageGetStart();
		profilePage = new PageProfile();
		try {
			pageDashboard = getStartPage.checkAndSignin(c_username, c_password);	
			Assert.assertEquals(pageDashboard.getAddDeviceBigBtn().isEnabled(), true);
			
			homeMenupage = pageDashboard.gotoHomeMenuPage();
			homeMenupage.gotoHomeProfilePage();
			profilePage.enableMarketingConsent();
			profilePage.exitPage();
			getStartPage.goToSigninPage().loginApp(c_username, c_password);
			homeMenupage = pageDashboard.gotoHomeMenuPage();
			homeMenupage.gotoHomeProfilePage();
			Assert.assertTrue(profilePage.verifyMarketingConsentOn(), "Marketing Consent should be turn on");
		}finally {
			profilePage.disableMarketingConsent();
		}
		
		
	}
}
