package Android_Test_Kodak.android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

/**
 * @author Thach Nguyen
 * Intro page has 2 buttons Sign In and Sign up
 * with demo and some commercial 
 */
public class PageGetStart extends PageBase{
	/***ELEMENT***/
	public Element getSignUpBtn(){
		String xpathLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_introduction_register']";
		return new Element(By.xpath(xpathLocator), 5).setDescription("Get Start: Sign up button"); 
	}
	
	
	public Element getSigninBtn(){ 
		String xpathLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_introduction_login']";
		return new Element(By.xpath(xpathLocator), 5).setDescription("Get Start: Signin button"); 
	}
	
	/***ACTION***/
	public PageDashboard checkAndSignin(String username, String passwd) {
		PageDashboard pageDashboard = new PageDashboard();
		PageLogin pageLogin = new PageLogin();
		if (PageDashboard.isDisplayed()) {
			pageDashboard.handlePermissionsAndHintsWhenPageOpen();
			/*
			*  if Dashboard is showing, means that app already login,
			* check if it's the same user, if not, log out and login to desired account
			*/
			// Open menu and check get current username and check
			pageDashboard.getMenuButton().click();
			// current username is match, return
			if (pageDashboard.getAccountTextViewInMenu().getText().equals(username)) {
				pageDashboard.getHomeIcon().click();
				return pageDashboard;
			}
			// not match, goto PageLogin.
			pageDashboard.getSignOutMenuItem().click();
		} else if (this.isDisplayed()) {
			// if PageGetStart is displayed, click Signin button to go to PageLogin
			getSigninBtn().click();
		} 	/*
			* if already at login page then proceed to login
			* nothing else to do, just list here to know that
			* the case is covered
			*/ 
		
		// login with provide username & passwd
		pageLogin.loginApp(username, passwd);
		return pageDashboard;
	}
	
	public PageLogin goToSigninPage() {
		PageDashboard pageDashboard = new PageDashboard();
		PageLogin pageLogin = new PageLogin();
		if (getSigninBtn().getWebElement() != null) {
			getSigninBtn().click();			
		} else if ( pageDashboard.getMenuButton().getWebElement() != null) {
			pageDashboard.signOut();
			pageLogin.getLoginBackButton().click();
			getSigninBtn().click();
		} else if (pageLogin.getLoginBackButton().getWebElement() == null) {
			getSigninBtn().click();
		}
		return pageLogin;
	}
	
	public PageSignUp goToSignUpPage() {
		if (getSignUpBtn().getWebElement() != null) {
			getSignUpBtn().click();			
		} else if (new PageDashboard().getMenuButton().getWebElement() != null) {
			new PageDashboard().signOut();
			new PageLogin().getLoginBackButton().click();
			getSignUpBtn().click();
		} else if (new PageLogin().getLoginBackButton().getWebElement() == null) {
			getSignUpBtn().click();
		}
		return new PageSignUp();
	}
		
	public boolean isDisplayed() {
		if (getSigninBtn().getWebElement() != null) return true;
		return false;
	}
}
