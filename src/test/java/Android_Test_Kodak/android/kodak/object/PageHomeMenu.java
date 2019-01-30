package Android_Test_Kodak.android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageHomeMenu {
	public Element getAppVerTextview() {
		String tvAppVer = String.format("//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_app_version']");
		return new Element(By.xpath(String.format(tvAppVer))).setDescription("App Version text view");
	};
	
	public Element getSettingTextview() {
		String tvSetting = String.format("//android.widget.TextView[@text='Settings']");
		return new Element(By.xpath(tvSetting)).setDescription("Settings text view");
	}	
	
	public Element getHomeButton() {
		String btnHome = String.format("//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/account_setting_home']");
		return new Element(By.xpath(btnHome)).setDescription("Home button");
	}
	
	public Element getProfileTextView() {
		String tvProfile = "//android.widget.TextView[@text='Profile']";
		return new Element(By.xpath(tvProfile)).setDescription("Profile text view");
	}
	
	public Element getDevicesTextView() {
		String tvDevices = "//android.widget.TextView[@text='Devices']";
		return new Element(By.xpath(tvDevices)).setDescription("Devices text view");
	}
	
	public Element getGrantAccestTextView() {
		String grantAcc = "//android.widget.TextView[@text='Grant Access']";
		return new Element(By.xpath(grantAcc)).setDescription("Grant Access text view");
	}
	
	public Element getDashBoardTextView() {
		String dashboard = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/account_setting_title' and @text='Dashboard']";
		return new Element(By.xpath(dashboard)).setDescription("Dashboard text view");
	}
	
	public Element getNotificationTextView() {
		String notifyTextView = "//android.widget.TextView[@text='Notification']";
		return new Element(By.xpath(notifyTextView)).setDescription("Notification text view");
	}
	
	public Element getHelpAndSupportTextView() {
		String helpTextView = "//android.widget.TextView[@text='Help & Support']";
		return new Element(By.xpath(helpTextView)).setDescription("Help & Support text view");
	}
	
	public Element getReportIssueFeedBackTextView() {
		String feedbackTv = "//android.widget.TextView[@text='Report Issue/Feedback']";
		return new Element(By.xpath(feedbackTv)).setDescription("Report Issue/Feedback text view");
	}
	
	public Element getPreviewModeSwitch() {
		String previewSw = "//android.widget.Switch[@resource-id='com.perimetersafe.kodaksmarthome:id/account_setting_switch']";
		return new Element(By.xpath(previewSw)).setDescription("Preview Mode switch");
	}
	
	public Element getDevicesOrderTextView() {
		String deviceOrder = "//android.widget.TextView[@text='Device Order']";
		return new Element(By.xpath(deviceOrder)).setDescription("Device order text view");
	}

	public Element getManageCloudStorageTextView() {
		String manageStorage = "//android.widget.TextView[@text='Manage Cloud Storage']";
		return new Element(By.xpath(manageStorage)).setDescription("Manage Cloud Storage text view");
	}
	
	public Element getCloudStoragePlanTextView() {
		String storagePlan = "//android.widget.TextView[@text='Cloud Storage Plan']";
		return new Element(By.xpath(storagePlan)).setDescription("Cloud Storage Plan text view");
	}
	
	public void enableDebug() {
		for (int i=0; i<10; i++) {
			getAppVerTextview().click();
		}		
	}	
	
	public PageHomeSetting gotoHomeSetingPage() {
		getSettingTextview().click();
		return new PageHomeSetting();
	}
	
	public PageDashboard exitPage() {
		getHomeButton().click();
		return new PageDashboard();
	}
	
	public void gotoHomeProfilePage() {
		getProfileTextView().click();
	}
	
	public void gotoGrantAccessPage() {
		getDevicesTextView().click();
		getGrantAccestTextView().click();
	}
	
	public void clickOnHomeDashboardTextView() {
		getDashBoardTextView().click();
	}
	
	public void enablePreviewMode(boolean value) {
		getPreviewModeSwitch().setValue(value);
	}
	
	public boolean verifyPreviewEnable(boolean value) {
		return getPreviewModeSwitch().getAttribute("checked").equals(value + "");
	}
	
	public void gotoNotificationPage() {
		getDevicesTextView().click();
		getNotificationTextView().click();
	}
	
	public void gotoFeedbackPage() {
		getHelpAndSupportTextView().click();
		getReportIssueFeedBackTextView().click();
	}
	
	public void gotoDevicesOrderpage() {
		getDevicesOrderTextView().click();
	}
	
	public PageManageCloudStorage gotoDevices_ManageCloudStoragePage() {
		getDevicesTextView().click();
		getManageCloudStorageTextView().click();
		return new PageManageCloudStorage();
	}
	
	public void gotoCloudStoragePlanPage() {
		getDevicesTextView().click();
		getCloudStoragePlanTextView();
	}
}
