package Android_Test_Kodak.android.kodak.object;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.cinatic.TimeHelper;
import com.cinatic.constant.TestConstant;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

import io.qameta.allure.Step;

public class PageCameraSetting extends PageBase {
	private static final String _50HZ = "50Hz";
	private static final String _60HZ = "60Hz";

	/*** ELEMENT ***/
	// DETAILS

	public Element getCameraNameValue() {
		String xpathLocator = "//android.widget.TextView[@text='Camera Name']";
		return new Element(By.xpath(xpathLocator)).setDescription("Camera name");
	}

	public Element getModelNameValue() {
		String xpathLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_setting_model']";
		return new Element(By.xpath(xpathLocator)).setDescription("Camera model");
	}

	public Element getMacAddressValue() {
		String xpathLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_setting_mac_address']";
		return new Element(By.xpath(xpathLocator)).setDescription("MAC address");
	}

	public Element getFirmwareVersion() {
		String xpathLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_firmware_label']";
		return new Element(By.xpath(xpathLocator)).setDescription("Camera FW ver");
	}

	public Element PUVersion() {
		String xpathLocator = "//android.widget.TextView[@resource-id='PU Version']";
		return new Element(By.xpath(xpathLocator)).setDescription("PU fw ver");
	}

	public Element getCurrentPlanValue() {
		String xpathLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_setting_current_plan']";
		return new Element(By.xpath(xpathLocator)).setDescription("Curent plan value");
	}

	public Element getTimeZoneValue() {
		String xpathLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_setting_timezone']";
		return new Element(By.xpath(xpathLocator)).setDescription("Time zone value");
	}

	public Element getWifiSignalStrengthValue() {
		String xpathLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_setting_wifi_network_strength']";
		return new Element(By.xpath(xpathLocator)).setDescription("Wifi strengh value");
	}

	public Element getBatteryLevelValue() {
		String xpathLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_setting_battery_level']";
		return new Element(By.xpath(xpathLocator)).setDescription("Camera battery value");
	}

	public Element getSendDeviceLog() {
		String scrollToString = "Send Camera Log";
		String xpathLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_send_device_log']";
		return new Element(By.xpath(xpathLocator),scrollToString).setDescription("Send camera log button");
	}

	// CHANGE CAMERA NAME
	
	public Element getRenameTextbox() {
		String txtRename = "//android.widget.EditText[@index='0']";
		return new Element(By.xpath(txtRename)).setDescription("Rename camera textbox");
	}

	public Element getDoneButton() {
		String btnDone = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_confirm']";
		return new Element(By.xpath(btnDone)).setDescription("Cam rename done button");
	}

	public Element Cancel_button() {
		String btnCancel = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_cancel']";
		return new Element(By.xpath(btnCancel)).setDescription("Cam rename cancel button");
	}

	// SETTINGS
	public Element getNightVisionAutoBtn() {
		String lblNightVersionAuto = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_setting_night_vision_auto']";
		return new Element(By.xpath(lblNightVersionAuto), "Night Vision").setDescription("Auto night vision button");
	}

	public Element getNightVisionOnBtn() {
		String lblNightVersionOn = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_setting_night_vision_on']";
		return new Element(By.xpath(lblNightVersionOn), "Night Vision").setDescription("Night vision button on");
	}

	public Element getNightVisionOffBtn() {
		String lblNightVersionOff = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_setting_night_vision_off']";
		return new Element(By.xpath(lblNightVersionOff), "Night Vision").setDescription("Night vision button off");
	}

	public Element getCeilingMountSwitch() {
		String swCeilingMount = "//android.widget.Switch[@resource-id='com.perimetersafe.kodaksmarthome:id/sw_ceiling_mount']";
		return new Element(By.xpath(swCeilingMount), "Ceiling Mount").setDescription("Ceiling mount sw");
	}

	public Element getMotionDetectionSwitch() {
		String swMotionDetection = "//android.widget.Switch[@resource-id='com.perimetersafe.kodaksmarthome:id/swSetMotionDetection']";
		return new Element(By.xpath(swMotionDetection), "Motion Detection").setDescription("MD switch");
	}

	public Element getSoundDetectionSwitch() {
		String swSoundDetection = "//android.widget.Switch[@resource-id='com.perimetersafe.kodaksmarthome:id/swSoundDetection']";
		return new Element(By.xpath(swSoundDetection), "Sound Detection").setDescription("Sound detection switch");
	}

	public Element getTemperatureSwitch() {
		String swTemperature = "//android.widget.Switch[@resource-id='com.perimetersafe.kodaksmarthome:id/swTemperatureEnable']";
		return new Element(By.xpath(swTemperature), "Temperature").setDescription("Temp detection switch");
	}

	public Element getVideoResolutionLabel() {
		String lblVideoResolution = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/tvTitle']";
		return new Element(By.xpath(lblVideoResolution), "Video Resolution").setDescription("Video resolution label");
	}

	public Element getMotionSensitiveSeekbar() {
		String seekbarMotion = "//android.widget.SeekBar[@resource-id='com.perimetersafe.kodaksmarthome:id/seekbar_motion_sensitive']";
		return new Element(By.xpath(seekbarMotion), "Motion Sensitivity").setDescription("Motion seitivity setting");
	};

	public Element getSoundSensitiveSeekbar() {
		String seekbarSound = "//android.widget.SeekBar[@resource-id='com.perimetersafe.kodaksmarthome:id/seekbar_sound_sensitive']";
		return new Element(By.xpath(seekbarSound), "Sound Sensitivity").setDescription("Sound sensitive setting");
	};

	public Element getLightSourceFrequencyTextBox() {
		String lightSourceTb = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/tvDetail' and contains(@text, 'Hz')]";
		return new Element(By.xpath(lightSourceTb), "Light Source Frequency",  10).setDescription("Light source frequency setting value");
	}

	public Element getFrequencySelected() {
		String xpath = "//android.widget.RadioButton[@checked='true']/../android.widget.TextView";
		return new Element(By.xpath(xpath)).setDescription("Selected light source frequency");
	}

	public Element getFrequencyNotSelected() {
		String xpath = "//android.widget.RadioButton[@checked='false']/../android.widget.TextView";
		return new Element(By.xpath(xpath)).setDescription("Not selected light source frequency");
	}

	public Element getFrequencyPanel() {
		String panel = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/alertTitle' and @text='Light Source Frequency']";
		return new Element(By.xpath(panel)).setDescription("Light source frequency setting panel");
	}

	public Element getZoneDetectionMotion() {
		String zoneDetection = "//android.widget.RelativeLayout[@resource-id='com.perimetersafe.kodaksmarthome:id/zone_detection']";
		return new Element(By.xpath(zoneDetection), "Define Zone to Detect Motion");
	}

	public Element getAddNewZoneButton() {
		String newBtn = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/add_zone']";
		return new Element(By.xpath(newBtn)).setDescription("Add new zone button");
	}

	public Element getUpdateZoneToCameraButton() {
		String updateBtn = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_update_zone']";
		return new Element(By.xpath(updateBtn)).setDescription("Update zone to camera button");
	}

	public Element getEditZoneIcon(String zoneName) {
		String editIcon = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/zone_name' and @text = '%s']/../android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/edit_button']";
		return new Element(By.xpath(String.format(editIcon, zoneName))).setDescription("Edit zone button");
	}

	public Element getZoneByName(String zoneName) {
		String zoneTextView = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/zone_name' and @text = '%s']";
		return new Element(By.xpath(String.format(zoneTextView, zoneName))).setDescription("Zone name");
	}
	
	public Element getZoneDefault() {
		String defaultZone = "//android.widget.FrameLayout[@resource-id='com.perimetersafe.kodaksmarthome:id/zone_img_container']/android.view.View";
		return new Element(By.xpath(defaultZone)).setDescription("Defaut zone");
	}

	public Element getZoneDefaultEditView() {
		String editView = "//android.view.View[@resource-id='com.perimetersafe.kodaksmarthome:id/editable_image']";
		return new Element(By.xpath(editView)).setDescription("Edit default zone");
	}
	
	public Element getZoneListPanel() {
		String lstZoneEle = "//android.support.v7.widget.RecyclerView[@resource-id='com.perimetersafe.kodaksmarthome:id/zone_list']";
		return new Element(By.xpath(lstZoneEle)).setDescription("Zone list panel");
	}
	
	public Element getZoneContainer() {
		String zoneContainer = "//android.widget.FrameLayout[@resource-id='com.perimetersafe.kodaksmarthome:id/zone_img_container']";
		return new Element(By.xpath(zoneContainer)).setDescription("Zone container");
	}
	
	public Element getRefreshZoneButton() {
		String zoneButton = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/menu_refresh']";
		return new Element(By.xpath(zoneButton)).setDescription("Refresh zone button");
	}

	/*** ACTION ***/
	public Element getSharedByTextView() {
		String sharedText = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_device_owner']";
		return new Element(By.xpath(sharedText),"Shared By").setDescription("Camera owner info text");
	}
	
	public Element getCameraSettingTitle() {
		String settingTitle = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/setting_title']";
		return new Element(By.xpath(settingTitle),"Motion Detection").setDescription("SETTING section of camera setting");
	}
	
	public Element getConfirmYesBtn() {
		String okBtn = "//android.widget.Button[@resource-id='android:id/button1']";
		return new Element(By.xpath(okBtn)).setDescription("Yes button");
	}
	
	public Element getDeleteAllEventsTextView() {
		String scrollToText = "Delete All Events";
		String eventEle = "//android.widget.LinearLayout[@resource-id='com.perimetersafe.kodaksmarthome:id/layout_delete_all_events_top']";
		return new Element(By.xpath(eventEle),scrollToText).setDescription("Delete all event button");
	}
	
	public Element getDeleteCamIcon() {
		String removeIcon = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/remove_device_setting_menu_item']";
		return new Element(By.xpath(removeIcon)).setDescription("Delete camera button");
	}
	
	public Element getDeviceSettingPageTitle() {
		String titleEle = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_title_main']";
		return new Element(By.xpath(titleEle)).setDescription("Device setting title");
	}
	
	public Element getHighTemperatureThreshold() {
		String scrollToText = "High Temperature";
		String hightTem = "//android.widget.LinearLayout[@resource-id='com.perimetersafe.kodaksmarthome:id/seekbar_high_temperature']//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/number_picker_value']";
		return new Element(By.xpath(hightTem), scrollToText).setDescription("High Temperature Threshold");
	}
	
	public Element getLowTemperatureThreshold() {
		String scrollToText = "Low Temperature";
		String lowTem = "//android.widget.LinearLayout[@resource-id='com.perimetersafe.kodaksmarthome:id/seekbar_low_temperature']//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/number_picker_value']";
		return new Element(By.xpath(lowTem), scrollToText).setDescription("Low Temperature Threshold");
	}
	public Element getConfirmFirmwareUpgrade() {
		String confirmBtn = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_confirm']";
		return new Element(By.xpath(confirmBtn), 400).setDescription("Confirm upgrade firmware successful.");
	}
	
	public Element getFirmwareUpdateStatus() {
		String msg = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_dialog_msg']";
		return new Element(By.xpath(msg)).setDescription("Firmware update message status");
	}
	
	/***ACTION***/
	@Step("Change Camera name")
	public void changeCameraName(String name) {
		getCameraNameValue().click();
		getRenameTextbox().sendKeys(name);
		getDoneButton().click();
	}

	@Step("Enable Ceiling Mount")
	public void enableCeilingMount() {
		getCeilingMountSwitch().setValue(true);
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Disable Ceiling Mount")
	public void disableCeilingMount() {
		getCeilingMountSwitch().setValue(false);
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Enable Motion Detection")
	public void enableMotionDetection() {
		getMotionDetectionSwitch().setValue(true);
		closeCouldUpgradeTipIfAsked();
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Disable Motion Detection")
	public void disableMotionDetection() {
		getMotionDetectionSwitch().setValue(false);
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Enable Sound Detection")
	public void enableSoundDetection() {
		getSoundDetectionSwitch().setValue(true);
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Disable Sound Detection")
	public void disableSoundDetection() {
		getSoundDetectionSwitch().setValue(false);
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Enable Temperature")
	public void enableTemperature() {
		getTemperatureSwitch().setValue(true);
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Disable Temperature")
	public void disableTemperature() {
		getTemperatureSwitch().setValue(false);
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Set Night Vision Auto")
	public void setNightVisionAuto() {
		getNightVisionAutoBtn().click();
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Set Night Vision On")
	public void setNightVisionOn() {
		getNightVisionOnBtn().click();
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Set Night Vision Off")
	public void setNightVisionOff() {
		getNightVisionOffBtn().click();
		TimeHelper.sleep(3 * 1000);
	}

	public void changeMotionSensitivityLevel(TestConstant.motionLevel level) {
		Element element = getMotionSensitiveSeekbar();
		int tapX = 0;
		int tapY = element.getLocation().y + element.getSize().getHeight() / 2;
		if (level == TestConstant.motionLevel.Low)
			tapX = element.getLocation().x + 5;
		else if (level == TestConstant.motionLevel.Medium)
			tapX = element.getLocation().x + element.getSize().getWidth() / 2;
		else if (level == TestConstant.motionLevel.High)
			tapX = element.getLocation().x + element.getSize().getWidth() - 5;
		DriverManager.getAppiumDriver().tap(tapX, tapY);
		TimeHelper.sleep(3 * 1000);
	}

	public void changeSoundSensitivityLevel(TestConstant.soundLevel level) {
		Element element = getSoundSensitiveSeekbar();
		int tapX = 0;
		int tapY = element.getLocation().y + element.getSize().getHeight() / 2;
		if (level == TestConstant.soundLevel.Low)
			tapX = element.getLocation().x + 5;
		else if (level == TestConstant.soundLevel.Medium)
			tapX = element.getLocation().x + element.getSize().getWidth() / 2;
		else if (level == TestConstant.soundLevel.High)
			tapX = element.getLocation().x + element.getSize().getWidth() - 5;
		DriverManager.getAppiumDriver().tap(tapX, tapY);
		TimeHelper.sleep(3 * 1000);
	}

	public float getTimeZoneSystem() {
		return Float.parseFloat(getTimeZoneValue().getText());
	}

	/**
	 * Rule: iOffset > -4 && iOffset < 9 --> 50Hz, else --> 60Hz
	 * 
	 * @param timeZone
	 * @param hz
	 * @return
	 */
	public boolean verifyLightSourceFrequencyByTimeZone(float timeZone, String hz) {
		if (timeZone > -4 && timeZone < 9) {
			return _50HZ.equals(hz);
		} else {
			return _60HZ.equals(hz);
		}
	}

	/**
	 * Try to get Light source frequency, if element not existed, swipe to
	 * bottom until it display.
	 * 
	 * @return
	 */
	public String getLightSourceFrequencyValue() {
		if (getLightSourceFrequencyTextBox().getWebElement() != null) {
			return getLightSourceFrequencyTextBox().getText();
		}
		return "";
	}

	public void changeLightSourceFrequency() {
		getLightSourceFrequencyTextBox().click();
		if (getFrequencyPanel().getWebElement() != null) {
			getFrequencyNotSelected().click();
		}
	}

	public boolean verifyFrequencySelected(String hz) {
		if (getFrequencySelected().getWebElement() != null) {
			return getFrequencySelected().getText().equals(hz);
		}
		return false;
	}

	public void clickOnZoneDetectionMotion() {
		getZoneDetectionMotion().click();
	}

	public void clickAddNewZone() {
		getAddNewZoneButton().click();
	}

	public void addZone(String zoneName) {
		clickAddNewZone();
		getEditZoneIcon(zoneName).click();
	}

	public void editZone(String zoneName) {
		Dimension d = getZoneDefault().getSize();
		getEditZoneIcon(zoneName).click();
		dragAndDropZone();
		getEditZoneIcon(zoneName).click();
	}

	/**
	 * Hard code, because cannot get editable zone
	 */
	public void dragAndDropZone() {
		DriverManager.getAppiumDriver().swipe(100, 558, 600, 558, 2000);
	}

	public void clickRefreshZoneButton() {
		getRefreshZoneButton().click();
	}
	
	public void removeAllZone() {
		String lstDeleteIconpath = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/delete_button']";
		List<WebElement> lstDeleteIcon = getZoneListPanel().findElements(By.xpath(lstDeleteIconpath));
		if (lstDeleteIcon.size() > 0) {
			for (WebElement ele : lstDeleteIcon) {
				// ele variable not use because after delete a zone, dom was refresh, but ele not update.
				getZoneListPanel().findElement(By.xpath(lstDeleteIconpath)).click();
				clickConfirmButton();
			}
		}
	}
	
	public boolean verifyZoneByName(String... zoneName) {
		String zoneInView = "//android.view.View";
		List<WebElement> listZoneInView = getZoneContainer().findElements(By.xpath(zoneInView));
		if(zoneName.length != listZoneInView.size()) {
			return false;
		}
		for(String zone : zoneName) {
			if(getZoneByName(zone).getWebElement() == null) {
				return false;
			}
		}
		return true;
	}
	
	public boolean verifyRemoveAllZoneSuccessful() {
		return getZoneListPanel().getWebElement() == null;
	}
	
	public void clickUpdateZoneToCamera() {
		getUpdateZoneToCameraButton().click();
	}
	public boolean verifyDeviceSharedBy(String owner) {
		return getSharedByTextView().getText().trim().equals(owner);
	}
	
	public boolean verifySettingTitleDisplay() {
		return getCameraSettingTitle().getWebElement() != null;
	}
	
	public void sendCameraLog() {
		getSendDeviceLog().click();
		getConfirmYesBtn().click();
		getGmailApp().click();
	}
	
	public void deleteAllEvents() {
		getDeleteAllEventsTextView().click();
		// Confirm Delete events or not
		getConfirmYesBtn().click();
		// Confirm Events were deleted
		getConfirmYesBtn().click();
	}
	
	public void removeCamera() {
		getDeleteCamIcon().click();
		getConfirmYesBtn().click();
	}
	
	public boolean verifyDeviceSettingPage() {
		return getDeviceSettingPageTitle().getWebElement() != null;
	}
	
	public String getCurrentStoragePlan() {
		return getCurrentPlanValue().getText();
	}
	
	public boolean verifyTemperatureUnitInDeviceSetting(String unit) {
		return getHighTemperatureThreshold().getText().toUpperCase().endsWith(unit.toUpperCase()) &&
				getLowTemperatureThreshold().getText().toUpperCase().endsWith(unit.toUpperCase());
	}
	
	public boolean verifyNewFirmwareAlreadyToUpdate() {
		return getFirmwareVersion().getText().contains("New firmware version");
	}
	
	public boolean verifyLatestFirmwareVersion() {
		return getFirmwareVersion().getText().equals("Firmware Version");
	}
	
	/**
	 * Duong Nguyen
	 * Waiting for message Firmware upgrade is successful display.
	 * @return
	 */
	public boolean verifyFirmwareUpgradeSuccessful() {
		boolean rs = false;
		if(getConfirmFirmwareUpgrade().getWebElement() != null && getFirmwareUpdateStatus().getText().equals("Firmware upgrade is successful.")) {
			clickConfirmButton();
			rs = verifyLatestFirmwareVersion();
		}
		return rs;
	}
	
	/**
	 * Manual update firmware by clicking via camera setting/detail/force upgrade
	 * @return
	 */
	public boolean manualUpdateFirmware() {
		// Click on update new firmware
		getFirmwareVersion().click();
		// Click confirm button
		clickConfirmButton();
		// Verify firmware update successful
		return verifyFirmwareUpgradeSuccessful();
	}
}