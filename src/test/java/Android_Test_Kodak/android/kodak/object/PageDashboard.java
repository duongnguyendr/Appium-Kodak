package Android_Test_Kodak.android.kodak.object;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.TerminalHelper;
import com.cinatic.TimeHelper;
import com.cinatic.element.Element;
import com.cinatic.log.Log;

public class PageDashboard extends PageBase {
	public static boolean flagAutoTutorialClosed = false;
	public static boolean flagCloudUpgradeSuggestionClosed = false;
	/***ELEMENT***/	
	//MENU
	public Element getMenuButton(){
		String btnMenu = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/imgMain']";
		return new Element(By.xpath(btnMenu), 15).setDescription("Menu button on dashboard"); 
	}

	public Element getSignOutMenuItem(){ 
		String mniSignOut = "//android.widget.TextView[@text='Sign Out' and @resource-id='com.perimetersafe.kodaksmarthome:id/account_setting_title']";
		return new Element(By.xpath(mniSignOut)).setDescription("Sign out button in home menu"); 
	}
	
	// Dashboard additional tip about grant access and preview
	public Element getGrandAccessDashboardTipCloseBtn() {
		String xpathLocator = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_close_dashboard_additional_tips']";
		return new Element(By.xpath(xpathLocator),ELEMENT_TIMEOUT_SHORT).setDescription("Grant access tip close button");
	}
	
	public Element getGrantAccessFeatureBtn() {
		String xpathLocator = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/button_grant_access_setting']";
		return new Element(By.xpath(xpathLocator)).setDescription("Grant access setting in home menu");
	}
	
	public Element getDashboardSettingBtn() {
		String xpathLocator = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/button_preview_setting']";
		return new Element(By.xpath(xpathLocator)).setDescription("Dashboard setting button in home menu");		
	}
	
	// Primary camera click for live view hint
	public Element getClickForLiveViewButton() {
		String xpathLocator = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_press_for_live_view']";
		return new Element(By.xpath(xpathLocator)).setDescription("Click here for live view hint");
	}
	
	// account name text view
	public Element getAccountTextViewInMenu() {
		String xpathLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/tv_account_name']";
		return new Element(By.xpath(xpathLocator)).setDescription("Account name in home menu");
	}
	
	// Dashboard tab
	public Element getCameraByName(String name){
		String layoutDevice = "//android.widget.TextView[contains(@resource-id,'_name') and @text='%s']/../..";
		return new Element(By.xpath(String.format(layoutDevice, name))).setDescription(String.format("Camera %s",name)); 
	}
	
	public Element getCameraNameLabel(String name){
		String lblDeviceName = "//android.widget.TextView[contains(@resource-id,'_name') and @text='%s']";
		return new Element(By.xpath(String.format(lblDeviceName, name))).setDescription(String.format("Camera label %s",name));
	}
	
	public Element getCameraStatusLabel(String name){
		String lblDeviceStatus = "//android.widget.TextView[@text='%s']/../following-sibling::android.widget.TextView[contains(@resource-id, 'status') and not(contains(@resource-id, 'p2p'))]";
		return new Element(By.xpath(String.format(lblDeviceStatus, name)), 3).setDescription(String.format("Camera %s status", name)); 
	}
	
	public Element getCameraSettingButton(String name){ 
		String btnDeviceSetting = "//android.widget.TextView[contains(@resource-id,'_name') and @text='%s']/../../..//android.widget.ImageView[contains(@resource-id,'_setting')]";
		return new Element(By.xpath(String.format(btnDeviceSetting, name))).setDescription("Camera setting button"); 
	}
	
	public Element getAddDeviceBigBtn(){
		String xpathLocator = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/ic_add_1']";
		return new Element(By.xpath(String.format(xpathLocator))).setDescription("Add camera big button"); 
	}
	
	public Element getAddDeviceMenuBtn() {
		String xpathLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/menu_add_device']";
		return new Element(By.xpath(xpathLocator)).setDescription("Add camera small button");
	}
		
	public static Element getDashboardLabel() {
		String xpathLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_title_main']";
		return new Element(By.xpath(xpathLocator)).setDescription("Dashboard label");
	}
	//SETUP
	public Element getBabySeriesImage(){ 
		String imgBabySeries = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_baby_series']";
		return new Element(By.xpath(String.format(imgBabySeries))).setDescription("Baby series image"); 
	};
	
	public Element getHomeSeriesImage(){
		String imgFSeries = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_frontier_series']";
		return new Element(By.xpath(String.format(imgFSeries))).setDescription("Home series image");
	};
	
	public PageDashboard handlePermissionsAndHintsWhenPageOpen() {
		
		// Handle media permission request at first time app launch
		allowAndroidPermissionIfAsked(ANDROID_PERMISSION_MEDIA);
		
		// Handle Cloud upgrade ("Want to see more") hint at first launch
		closeCloudUpgradeSuggestion();
		
		// Check and close the tutorial if appears, memorize it
		closeAnyTutorial(flagAutoTutorialClosed);
		flagAutoTutorialClosed = true;
		return this;
	}
	
	public Element getDeviceModelLabel(String name){
		String lblDeviceModel = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_device_model' and contains(@text,'%s')]";
		return new Element(By.xpath(String.format(lblDeviceModel, name))).setDescription("Camera model" +name+ " label");
	};
	
	public Element getModel685Label() {
		String imgF685="//android.widget.TextView['@text=KODAK F685']";
		return new Element(By.xpath(String.format(imgF685))).setDescription("F685 image");
	};
	
	public Element getContinueButton(){
		String btnContinue = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_continue']";
		return new Element(By.xpath(String.format(btnContinue))).setDescription("Continue button"); 
	};
	
	public Element getSSIDLabel(String name) {
		String lblSSID = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_access_point_item_name' and contains(@text,'%s')]";
		return new Element(By.xpath(String.format(lblSSID, name)), name).setDescription("Camera SSID for pairing"); 
	};
	
	public Element getSSIDRefreshImage(){ 
		String imgSSIDRefresh = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/imageview_select_wifi_refresh']";
		return new Element(By.xpath(String.format(imgSSIDRefresh))).setDescription("Pairing: Refresh camera SSID list");
	};
	
	public Element getWifiSettingButton() { 
		String btnWifiSetting = "//android.widget.Button[@text='WI-FI SETTINGS']";
		return new Element(By.xpath(String.format(btnWifiSetting))).setDescription("Pairing: wifi setting button"); 
	};
	
	public Element getWifiNameLabel(String name){ 
		String lblWiFiName = "//android.widget.TextView[@resource-id='android:id/title' and @text='%s']";
		return new Element(By.xpath(String.format(lblWiFiName, name))).setDescription("Pairing: Wifi name label"); 
	}; 
	
	public Element getWifiPasswordTextbox() { 
		String txtWifiPassword = "//android.widget.EditText[@resource-id='com.android.settings:id/password']";
		return new Element(By.xpath(String.format(txtWifiPassword))).setDescription("Pairing: wifi password"); 
	};
	
	public Element getWifiConnectButton() { 
		String btnWifiConnect = "//android.widget.Button[@resource-id='android:id/button1' and @text='CONNECT']";
		return new Element(By.xpath(String.format(btnWifiConnect))).setDescription("Pairing: Wifi connect button"); 
	};
	
	public Element getWifiConnectedLabel(String name){ 
		String lblWifiConnected = "//android.widget.TextView[@resource-id='android:id/title' and @text='%s']/..//android.widget.TextView[@resource-id='android:id/summary' and @text='Connected']";
		return new Element(By.xpath(String.format(lblWifiConnected, name))).setDescription("Wifi connect label"); 
	};
		
	public Element getCurrentWifiLabel() {
		String lblCurrentWifi = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_current_wifi']";
		return new Element(By.xpath(String.format(lblCurrentWifi))).setDescription("Current wifi name"); 
	};
	
	public Element getTextWifiPasswordTextbox() {
		String txtTextWifiPassword = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/text_wifi_password']";
		return new Element(By.xpath(String.format(txtTextWifiPassword))).setDescription("Pairing: wifi password input"); 
	};
	
	public Element getSelectAnotherWifiLabel() {
		String lblSelectAnotherWifi = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/tvSelectAnotherWifi']";
		return new Element(By.xpath(String.format(lblSelectAnotherWifi))).setDescription("Select Another Wi-Fi button"); 
	};
	
	public Element getMainProgressbar() {
		String progressbarMain = "//android.widget.ProgressBar[@resource-id='com.perimetersafe.kodaksmarthome:id/progressbar_main']";
		return new Element(By.xpath(String.format(progressbarMain))).setDescription("Pairing progress bar"); 
	};
	
	public Element getCustomButton() {
		String btnCustom = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_custom']";
		return new Element(By.xpath(String.format(btnCustom)), 400).setDescription("Paring: set custom name button"); 
	};
	
	public Element getDoneButton() { 
		String btnDone = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_continue' and @text='Done']";
		return new Element(By.xpath(String.format(btnDone))).setDescription("Paring: Done buton"); 
	};
	
	public Element getMobileDataContinueButton() {
		String btnMobileDataContinue = "//android.widget.Button[@resource-id='android:id/button1' and @text='CONTINUE']";
		return new Element(By.xpath(String.format(btnMobileDataContinue))).setDescription("Confirm using mobile data button"); 
	};

	public Element getWifi5gProcessAnywayButton() {
		String btnWifi5gProcessAnyway = "//android.widget.Button[@resource-id='android:id/button2']";
		return new Element(By.xpath(String.format(btnWifi5gProcessAnyway))).setDescription("Process using 5Ghz Wifi button");
	};

	public Element getHomeIcon() {
		String xpathLocator = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/account_setting_home']";
		return new Element(By.xpath(xpathLocator)).setDescription("Home button in home menu");
	}
	//TAB: 3 tab, dashboard, gallerya and Timeline
		// Already define in PageBase
	
	//IN VIDEO GALLERY TAB
	public Element getFirstVideoGalleryTime() { 
		String timeFirstVideoGallery = "//android.widget.RelativeLayout[@resource-id='com.perimetersafe.kodaksmarthome:id/rootView']//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/timeTextView']";
		return new Element(By.xpath(String.format(timeFirstVideoGallery))).setDescription("Recorded time of first item in Gallery"); 
	};
	
	public Element getAccountNotActivateYetIcon() {
		String inActiveIcon = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/imgNotify']";
		return new Element(By.xpath(inActiveIcon)).setDescription("Your account not yet active icon");
	}
	
	public Element getEmptyDeviceImage() {
		String emptyImg = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_empty_device']";
		return new Element(By.xpath(emptyImg)).setDescription("Image appear when no camera is paired");
	}
	
	public List<WebElement> getListCameraInDashBoard(){
		String lstCam = "//android.view.ViewGroup[@resource-id='com.perimetersafe.kodaksmarthome:id/swiperefreshlayout_device']";
		return new Element(By.xpath(lstCam)).
				setDescription("List of cameras in dashboard").
				findElements(By.xpath("//android.widget.TextView[contains(@resource-id,'name')]"));
	}
	
	public Element getPassWordWifiTextBox() {
		String pwdTb = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/edittext_diaglog_access_point_key']";
		return new Element(By.xpath(pwdTb)).setDescription("Pairing: Another wifi: Wifi passwd input");
	}
	
	public Element getConnectButton() {
		String connectBtn = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_diaglog_access_point_connect']";
		return new Element(By.xpath(connectBtn)).setDescription("Pairing: Another wifi: Wifi passwd input: Connect button");
	}
	
	public Element getSSIDNameTextBox() {
		String ssidName = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/edtWifiName']";
		return new Element(By.xpath(ssidName)).setDescription("Pairing: Another wifi: Other: Input wifi SSID name");
	}
	
	public Element getSecurityWifiSpinner() {
		String spinner = "//android.widget.Spinner[@resource-id='com.perimetersafe.kodaksmarthome:id/spnWifiSecurityType']";
		return new Element(By.xpath(spinner)).setDescription("Wifi security dropdown list");
	}
	
	public Element getSecurityTypeByName(String securityType) {
		String secure = "//android.widget.CheckedTextView[@text='%s']";
		return new Element(By.xpath(String.format(secure, securityType))).setDescription("Wifi Security type");
	}
	
	public Element getSSIDPasswordTextBox() {
		String pwd = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/edtWifiPassword']";
		return new Element(By.xpath(pwd)).setDescription("Pairing: Another wifi: Other: Input Wifi passwd");
	}
	
	public Element getDialogTitleUpdateFirmware() {
		String dialog = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_dialog_title']";
		return new Element(By.xpath(dialog)).setDescription("Firmware upgrade confirm dialog");
	}
	
	public Element getDeviceNameTextView() {
		String deviceName = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/text_device_name']";
		return new Element(By.xpath(deviceName)).setDescription("Device name text view");
	}
	
	public Element getProceedUpgradeButton() {
		String processBtn = "//android.widget.Button[@text='Proceed']";
		return new Element(By.xpath(processBtn), 400).setDescription("Proceed button");
	}
	
	/***ACTION***/
	public void signOut() {
		getMenuButton().click();
		TimeHelper.sleep(3000);
		getSignOutMenuItem().click();
	}	
	
	public String waitForDeviceOnline(String name, int timeOut) {
		String status = "";
		while (timeOut >= 0) {
			try {
				status = getCameraStatusLabel(name).getText();
			} catch (Exception e) {
				status = "";
			}
			if (status.equals("Online"))
				break;
			swipeBottomToTop();
			timeOut--;
			TimeHelper.sleep(1000);
		}
		return status;
	}
	
	public PageStreamView selectDeviceToView(String name) {
		getCameraByName(name).click();
		return new PageStreamView();
	}
	
	public PageCameraSetting openDeviceSetting(String name) {
		getCameraSettingButton(name).click();
		return new PageCameraSetting();
	}
	
	public void setUpDevice(String wifiSSID, String wifiPassword, String comPort, String model, String deviceSSID) {
		getAddDeviceBigBtn().click();
		getBabySeriesImage().click();
		getDeviceModelLabel(model).click();
		TerminalHelper.sendCommand(comPort, "pair", "Network is not ready");
		getContinueButton().click();
		getContinueButton().click();
		if (getSSIDLabel(deviceSSID).getWebElement() != null) {
			getSSIDLabel(deviceSSID).click();
		} else {
			getSSIDRefreshImage().click();
			getSSIDLabel(deviceSSID).click();
		}

		getTextWifiPasswordTextbox().sendKeys(wifiPassword);
		getContinueButton().click();
		getCustomButton().click();
		getContinueButton().click();
		getDoneButton().click();
	}
	
	public void openVideoGalleryTab() {
		getGalleryIcon().click();
	}
	
	public String getFirstVieoGalleryInfo() {
		return getFirstVideoGalleryTime().getText();
	}
	
	public void gotoAddNewDevice() {
		getAddDeviceBigBtn().click();
		allowAndroidPermissionIfAsked(ANDROID_PERMISSION_LOCATION);
	}
	public PageHomeMenu gotoHomeMenuPage() {
		getMenuButton().click();
		return new PageHomeMenu();
	}
	
	/**
	 * Verify "Press here for live view" button display
	 * @param isDisplay: verify display or not
	 * @return
	 */
	public boolean verifyPressForLiveViewButtonDisplay(boolean isDisplay) {
		boolean rs = false;
		if(isDisplay) {
			rs = getClickForLiveViewButton().getWebElement().isDisplayed();
		}else {
			rs = getClickForLiveViewButton().getWebElement() == null;
		}
		return rs;
	}
	
	public void clickLiveViewButton() {
		getClickForLiveViewButton().click();
		if (getAndroidPermissionAllowBtn().getWebElement() != null) {
			getAndroidPermissionAllowBtn().click();
		}
	}
	
	public boolean verifyInActiveIconDisplay() {
		Log.info("Verify inactve user icon '!' display on top left dashboard page");
		return getAccountNotActivateYetIcon().isDisplayed();
	}
	
	public boolean verifyEmptyDevice() {
		return getEmptyDeviceImage().getWebElement() != null;
	}
	
	public boolean verifyDeviceExisted(String cameraName) {
		for(WebElement ele : getListCameraInDashBoard()) {
			if(ele.getText().equals(cameraName))
				return true;
		}
		return false;
	}
	
	public List<String> getListCameraNameInDashBoard(){
		List<String> lstCam = new ArrayList<>();
		for(WebElement ele : getListCameraInDashBoard()) {
			lstCam.add(ele.getText());
		}
		return lstCam;
	}
	/**
	 * Duong Nguyen
	 * Ignore wifi setting when device connect to Wifi 5G by proceed anyway
	 */
	public void proceedAnyway5GWifiIfAsk() {
		if(getWifi5gProcessAnywayButton().getWebElement() != null) {
			getWifi5gProcessAnywayButton().click();
		}
	}
	/**
	 * Duong Nguyen
	 * Config wifi for camera while setup
	 * 
	 * Steps:
	 * If current wifi connect as expected, input password and continue
	 * Else click Select Another Wifi
	 * 		If list wifi display contains expected wifi --> select wifi name --> input password and Connect
	 * 		Else scroll to the end, select Other --> input ssid, security type, password and continue
	 * @param wifiName
	 * @param security
	 * @param wifiPwd
	 */
	public void configWifiForCamera(String wifiName, String security, String wifiPwd) {
		if(!getCurrentWifiLabel().getText().equals(wifiName)) {
			getSelectAnotherWifiLabel().click();
			if(getSSIDLabel(wifiName).getWebElement() != null) {
				getSSIDLabel(wifiName).click();
				getPassWordWifiTextBox().sendKeys(wifiPwd);
				getConnectButton().click();
			}else {
				getSSIDLabel("Other").click();
				configNetWorkWithNewWifi(wifiName, security, wifiPwd);
				getContinueButton().click();
			}
		}else{
			getTextWifiPasswordTextbox().sendKeys(wifiPwd);
			getContinueButton().click();
		}
		
	}
	/**
	 * Config new wifi for camera
	 * @param wifiName
	 * @param security
	 * @param wifiPwd
	 */
	private void configNetWorkWithNewWifi(String wifiName, String security, String wifiPwd) {
		getSSIDNameTextBox().sendKeys(wifiName);
		getSecurityWifiSpinner().click();
		getSecurityTypeByName(security).click();
		getSSIDPasswordTextBox().sendKeys(wifiPwd);
	}
	
	public void closeCloudUpgradeSuggestion() {
		if (flagCloudUpgradeSuggestionClosed) return;
		try {
			getCloudUpgradeTipCloseBtn().click();
		} catch (Exception e) {
		}
		flagCloudUpgradeSuggestionClosed = true;
	}
	
	public static boolean isDisplayed() {
		if (getDashboardLabel().getWebElement() != null) return true;
		return false;
	}
	
	/**
	 * Duong Nguyen
	 * 
	 * Verify new firmware available to upgrade
	 * @return
	 */
	public boolean verifyNewFirmwareAvailable() {
		boolean rs = false;
		// Verify dialog confirm upgrade firmware version
		if(getProceedUpgradeButton().getWebElement() != null) {
			rs = getDialogTitleUpdateFirmware().getText().equals("New Firmware Detected");
		}
		// Click proceed to upgrade firmware
		getProceedUpgradeButton().click();
		return rs;
	}
	
	public void inputCameraName(String name) {
		getDeviceNameTextView().sendKeys(name);
	}
}