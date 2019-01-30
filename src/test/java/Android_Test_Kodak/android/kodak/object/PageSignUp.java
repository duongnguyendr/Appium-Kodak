package Android_Test_Kodak.android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;
import com.cinatic.log.Log;

import io.qameta.allure.Step;

public class PageSignUp {
	/*** Message ***/
	public static final String msg_WRONG_EMAIL_FORMAT = "Invalid email format. Example: aaa@bbb.ccc";
	public static final String msg_PASSWD_STRENGTH = "Password must be between 8–30 characters, including at least one upper-, and lower-case letter, digits.";
	public static final String msg_SHORT_USERNAME = "Username must be 6 to 30 characters.";
	public static final String msg_PLZ_ACCEPT_TERM = "Please accept to our Terms of Service and Privacy Policy";
	public static final String msg_REGISTRATION_EMAIL_NOT_DELIVERED = "Registration failed. Email is not delivered. Check your email to ensure it is valid.";
	
	/***ELEMENT***/
	public Element getUsernameTextbox(){ 
		String txtUsername = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/username_register']";
		return new Element(By.xpath(txtUsername)).setDescription("Username textbox"); }
	
	private String lblUsernameConstraint = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_dialog_msg']";
	public Element getUsernameConstraintMessage() { return new Element(By.xpath(lblUsernameConstraint)).setDescription("Username constraint message"); }
	
	public Element getEmailTextbox() { 
		String txtEmail = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/email_register']";
		return new Element(By.xpath(txtEmail)).setDescription("Email textbox"); }
	
	public Element getConfirmEmailTextbox() { 
		String txtConfirmEmail = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/email_confirm_register']";
		return new Element(By.xpath(txtConfirmEmail)).setDescription("Confirm email textbox"); }
	
	public Element getEmailConstraintMessage() { 
		String lblEmail = lblUsernameConstraint;
		return new Element(By.xpath(lblEmail)).setDescription("Email constraint message"); }
	
	public Element getPasswordTextbox() { 
		String txtPassword = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/password_register']";
		return new Element(By.xpath(txtPassword)).setDescription("Password textbox"); }
	
	public Element getPasswordConstraintMessage() { 
		String lblPassword = lblUsernameConstraint;
		return new Element(By.xpath(lblPassword)).setDescription("Password constraint message"); }
	
	public Element getConfirmPasswordTextbox() { 
		String txtConfirmPassword = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/confirm_password_register']";
		return new Element(By.xpath(txtConfirmPassword)).setDescription("Confirm password textbox"); }
	
	public Element getAgreeCheckbox() { 
		String chkAgree = "//android.widget.CheckBox[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_register_agree']";
		return new Element(By.xpath(chkAgree)).setDescription("Agree term and condition checkbox"); }
	
	public Element getSignUpButton() { 
		String btnSignUp = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_register_create']";
		return new Element(By.xpath(btnSignUp)).setDescription("Sign up button"); }
		
	public Element getBackButton() { 
		String btnBack = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_back']";
		return new Element(By.xpath(btnBack)).setDescription("Back button"); }
	
	//MARKETING CONSENT
	public Element getYesButton() { 
		String btnYes = "//android.widget.Button[@text='YES']";
		return new Element(By.xpath(btnYes)).setDescription("Yes button"); }
	
	public Element getNoButton() { 
		String btnNo = "//android.widget.Button[@text='NO']";
		return new Element(By.xpath(btnNo)).setDescription("No button"); }
	
	// "Verify Your Account" page with 2 button
	public Element getVerifyEmailLaterBtn() {
		String xpathLocator = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_proceed']";
		return new Element(By.xpath(xpathLocator));
	}
	
	@Step("Fill up with username")
	public void fillData(String name, String email,String confirmEmail, String password, String confirmPasswd){
		Log.info(String.format("FILL DATA: %s \r\n %s %s %s %s", name, email, confirmEmail, password, confirmPasswd));
		getUsernameTextbox().sendKeys(name);
		getEmailTextbox().sendKeys(email);
		getConfirmEmailTextbox().sendKeys(email);
		getPasswordTextbox().sendKeys(password);
		getConfirmPasswordTextbox().sendKeys(password);		
	}

	@Step("Register account")
	public void registerAccount(String name, String email, String confirmEmail, String password, String confirmPasswd) {
		Log.info(String.format("REGISTER ACCOUNT: %s %s %s", name, email, password));
		fillData(name, email, confirmEmail, password, confirmPasswd);
		getAgreeCheckbox().setValue(true);
		getSignUpButton().click();
		if (getNoButton().getWebElement() != null)
			getNoButton().click();
	}
	
	@Step("Back to Get Started page")
	public PageGetStart backToGetStartedPage() {
		getBackButton().click();
		return new PageGetStart();
	}
}
