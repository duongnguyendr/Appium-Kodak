package Android_Test_Kodak.android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageProfile extends PageBase {
	private static final String MARKETING_CONSENT_ON = "ON";
	private static final String MARKETING_CONSENT_OFF = "OFF";
	private String currentEmail;

	public String getCurrentEmail() {
		return currentEmail;
	}

	public void setCurrentEmail(String currentEmail) {
		this.currentEmail = currentEmail;
	}

	public Element getEmailTextView() {
//		String tvEmail = "//android.widget.TextView[@text='%s']";
		String tvEmail = "//android.widget.RelativeLayout[@index='1']/android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/account_setting_title']";
		return new Element(By.xpath(tvEmail)).setDescription("Email field");
	}

	public Element getChangeEmailPanel() {
		String pnChangeEmail = "//android.widget.LinearLayout[@resource-id='android:id/parentPanel']";
		return new Element(By.xpath(pnChangeEmail)).setDescription("Change email profile popup");
	}

	public Element getEmailTextEdit() {
		String textEditEmail = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/text_new_email']";
		return new Element(By.xpath(textEditEmail)).setDescription("Email textbox");
	}

	public Element getChangeButton() {
		String btnChange = "//android.widget.Button[@text='CHANGE']";
		return new Element(By.xpath(btnChange)).setDescription("Change button");
	}

	public Element getChangeEmailConfirmationPanel() {
		String confirmationPanel = "//android.widget.RelativeLayout[@resource-id='com.perimetersafe.kodaksmarthome:id/layout_dialog_body']";
		return new Element(By.xpath(confirmationPanel)).setDescription("Confirm change email popup");
	}

	public Element getOpenEmailButton() {
		String openBtn = "//android.widget.Button[@text='Open Email']";
		return new Element(By.xpath(openBtn)).setDescription("Open email button");
	}

	public Element getCancelOpenEmailButton() {
		String cancelBtn = "//android.widget.Button[@text='Cancel']";
		return new Element(By.xpath(cancelBtn)).setDescription("Cancel open email");
	}

	public Element getChangePasswordButton() {
		String pwdBtn = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/account_setting_title' and @text='Change Password']";
		return new Element(By.xpath(pwdBtn)).setDescription("Change password button");
	}

	public Element getChangePasswordPanel() {
		String pwdPanel = "//android.widget.TextView[@resource-id='android:id/alertTitle' and @text='Change Password']";
		return new Element(By.xpath(pwdPanel)).setDescription("Change password profile popup");
	}

	public Element getOldPasswordTextBox() {
		String oldPwdTextBox = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/edittext_old_password']";
		return new Element(By.xpath(oldPwdTextBox)).setDescription("Old password textbox");
	}

	public Element getNewPasswordTextBox() {
		String newPwdTextBox = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/edittext_new_password']";
		return new Element(By.xpath(newPwdTextBox)).setDescription("New password textbox");
	}

	public Element getConfirmPasswordTextBox() {
		String confirmPwd = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/edittext_confirm_password']";
		return new Element(By.xpath(confirmPwd)).setDescription("Confirm passaword textbox");
	}

	public Element getConfirmChangePwdButton() {
		String confirmBtn = "//android.widget.Button[@resource-id='android:id/button1']";
		return new Element(By.xpath(confirmBtn)).setDescription("Change password button");
	}

	public Element getCancelChangePwdButton() {
		String cancelBtn = "//android.widget.Button[@resource-id='android:id/button2']";
		return new Element(By.xpath(cancelBtn)).setDescription("Cancel change password button");
	}

	public Element getMarketingConsentSwitch() {
		String marketingSwitch = "//android.widget.Switch[@resource-id='com.perimetersafe.kodaksmarthome:id/account_setting_switch']";
		return new Element(By.xpath(marketingSwitch)).setDescription("Marketing consent switch");
	}

	public boolean verifyChangeEmailPanelDisplay() {
		return getChangeEmailPanel().isDisplayed();
	}

	public void updateEmailProfile(String newEmail) {
		getEmailTextView().click();
		if (verifyChangeEmailPanelDisplay()) {
			getEmailTextEdit().sendKeys(newEmail);
			getChangeButton().click();
		}
	}

	public boolean verifyChangeEmailConfimationPanel() {
		boolean rs = false;
		if (getChangeEmailConfirmationPanel().isDisplayed()) {
			getCancelOpenEmailButton().click();
			rs = true;
		}
		return rs;
	}

	public boolean verifyEmailProfileUpdated(String email) {
		return getEmailTextView().getText().equals(email);
	}

	public String getCurrentEmailProfile() {
		String rs = "";
		if (getEmailTextView().getWebElement() != null) {
			rs = getEmailTextView().getText();
		}
		return rs;
	}

	public void clickChangePassword() {
		getChangePasswordButton().click();
	}

	/**
	 * Change password account in profile page
	 * 
	 * @param oldPwd: current password
	 * @param newPwd: new password
	 */
	public void changePasswordAccount(String oldPwd, String newPwd) {
		getOldPasswordTextBox().sendKeys(oldPwd);
		getNewPasswordTextBox().sendKeys(newPwd);
		getConfirmPasswordTextBox().sendKeys(newPwd);
		getConfirmChangePwdButton().click();
	}

	public String getMarketingConsentStatus() {
		String rs = "";
		if (getMarketingConsentSwitch() != null) {
			rs = getMarketingConsentSwitch().getText();
		}
		return rs;
	}
	
	public boolean verifyMarketingConsentOn() {
		return getMarketingConsentStatus().equalsIgnoreCase(MARKETING_CONSENT_ON);
	}
	
	public boolean verifyMarketingConsentOFF() {
		return getMarketingConsentStatus().equalsIgnoreCase(MARKETING_CONSENT_OFF);
	}
	
	public void enableMarketingConsent() {
		if (verifyMarketingConsentOFF()) {
			getMarketingConsentSwitch().click();
		}
	}
	
	public void disableMarketingConsent() {
		if (verifyMarketingConsentOn()) {
			getMarketingConsentSwitch().click();
		}
	}
}
