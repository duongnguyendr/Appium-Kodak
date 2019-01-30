package android.kodak.object;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
import com.cinatic.log.Log;

public class PageTimeline extends PageBase {
	public static final String CLOUD_STORAGE = "cloud";
	
	public Element getBackToTopElement() {
		String ele = "//android.widget.TextView[@text='Back To Top']";
		return new Element(By.xpath(ele), 5).setDescription("Back to Top Icon");
	}
	
	public Element getPlayClipIcon() {
		String playIcon = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/imageview_event_item_play']";
		return new Element(By.xpath(playIcon), 5).setDescription("Play Video Icon");
	}
	
	public Element getDownloadMotionIcon() {
		String downloadIcon = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_download']";
		return new Element(By.xpath(downloadIcon)).setDescription("Download Motion Icon");
	}
	
	public Element getFilterButton() {
		String filterBtn = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/filter_event_menu_item']";
		return new Element(By.xpath(filterBtn)).setDescription("Filter Button");
	}
	
	public Element getListDevicePanel() {
		String devicePanel = "//android.support.v7.widget.RecyclerView[@resource-id='com.perimetersafe.kodaksmarthome:id/recyclerview_event_setting_devices']";
		return new Element(By.xpath(devicePanel)).setDescription("List device panel in filter");
	}
	
	public Element getCheckBoxDeviceByName(String deviceName) {
		String cbEle = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_chosen' and @text='%s']/../android.widget.CheckBox";
		return new Element(By.xpath(String.format(cbEle, deviceName))).setDescription("Device checkbox to filter");
	}
	
	public Element getClearButton() {
		String clearBtn = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_title_clear']";
		return new Element(By.xpath(clearBtn)).setDescription("Clear button");
	}
	
	public Element getDoneButton() {
		String doneBtn = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/done_event_setting_menu_item']";
		return new Element(By.xpath(doneBtn)).setDescription("Done button");
	}
	
	public Element getListEventsPanel() {
		String eventPanelEle = "//android.support.v7.widget.RecyclerView[@resource-id='com.perimetersafe.kodaksmarthome:id/recyclerview_home_event']";
		return new Element(By.xpath(eventPanelEle)).setDescription("List events panel in timeline page");
	}
	
	public List<WebElement> getListEvents(){
		String eventEle = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_event_item_name']";
		return getListEventsPanel().findElements(By.xpath(eventEle));
	}
	
	public Element getShareIcon() {
		String shareIcon = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_event_share_video']";
		return new Element(By.xpath(shareIcon)).setDescription("Share icon");
	}
	
	public Element getShareEventPanel() {
		String sharePanel = "//android.support.v7.widget.LinearLayoutCompat[@resource-id='com.perimetersafe.kodaksmarthome:id/parentPanel']";
		return new Element(By.xpath(sharePanel)).setDescription("Share event panel");
	}
	
	public Element getDownloadClipButton() {
		String clipBtn = "//android.widget.Button[@resource-id='android:id/button2']";
		return new Element(By.xpath(clipBtn)).setDescription("Download clip button in confirm popup");
	}
	
	public Element getDownloadSnapButton() {
		String snapBtn = "//android.widget.Button[@resource-id='android:id/button1']";
		return new Element(By.xpath(snapBtn)).setDescription("Download snap button in confirm popup");
	}
	
	public Element getPlayVideoErrorMessageEle() {
		String errMsg = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_load_fail']";
		return new Element(By.xpath(errMsg)).setDescription("Play video error message");
	}
	
	public Element getPlayBackIcon() {
		String playBack = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_refresh']";
		return new Element(By.xpath(playBack)).setDescription("Play back icon");
	}
	
	public Element getPlayVideoIconStorageInCloud() {
		String playBtn = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_event_share_video']/../../..//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/imageview_event_item_play']";
		return new Element(By.xpath(playBtn), 5).setDescription("Play button");
	}
	
	public Element getPlayVideoIconStorageInSDCard() {
		String playBtn = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_event_video_location'][not(../android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_event_share_video'])]/../../..//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/imageview_event_item_play']";
		return new Element(By.xpath(playBtn), 5).setDescription("Play button");
	}
	
	/**
	 * Duong Nguyen
	 * Swipe to end of timeline page and verify video storage in cloud exist
	 * @return
	 */
	public boolean getVideoStorageInCloud() {
		boolean rs = false;
		if(verifyEmptyEvent()) {
			return false;
		}
		while(getPlayVideoIconStorageInCloud().getWebElement() == null && getBackToTopElement().getWebElement() == null) {
			swipeTopToBottom();
		}
		if(getPlayVideoIconStorageInCloud().getWebElement() != null) {
			rs = true;
		}
		return rs;
	}

	/**
	 * Duong Nguyen
	 * Swipe to end of timeline page and verify video storage in sd-card exist
	 * @return
	 */
	public boolean getVideoStorageInSDCard() {
		boolean rs = false;
		if(verifyEmptyEvent()) {
			return false;
		}
		while(getPlayVideoIconStorageInSDCard().getWebElement() == null && getBackToTopElement().getWebElement() == null) {
			swipeTopToBottom();
		}
		if(getPlayVideoIconStorageInSDCard().getWebElement() != null) {
			rs = true;
		}
		return rs;
	}
	
	/**
	 * Duong Nguyen
	 * Playback video and return number of failure
	 * 
	 * @param storagePlace: places storage video
	 * @param playTimes: number times playback video
	 * 
	 * @return number playback failure
	 */
	public int verifyPlayBackVideoFunction(String storagePlace, int playTimes) {
		int countFail = 0;
		if(storagePlace.equalsIgnoreCase(CLOUD_STORAGE)) {
			getPlayVideoIconStorageInCloud().click();
		}else {
			getPlayVideoIconStorageInSDCard().click();
		}
		for(int i = 0; i < playTimes; i++) {
			getPlayBackIcon().click();
			if(!(getPlayBackIcon().getWebElement() != null && getPlayVideoErrorMessageEle().getWebElement() == null)) {
				countFail ++;
			}
		}
		return countFail;
	}
	
	public boolean clickOnPlayMotionVideo() {
		boolean rs = false;
		if(verifyEmptyEvent()) {
			return false;
		}
		while(getPlayClipIcon().getWebElement() == null && getBackToTopElement().getWebElement() == null) {
			swipeTopToBottom();
		}
		if(getPlayClipIcon().getWebElement() != null) {
			getPlayClipIcon().click();
			rs = true;
		}
		return rs;
	}
	
	public boolean donwloadMotionVideo() {
		boolean rs = false;
		if (clickOnPlayMotionVideo()) {
			getDownloadMotionIcon().click();
			TimeHelper.sleep(5000);
			DriverManager.getAppiumDriver().getAppiumDriver().navigate().back();
			rs = true;
		}
		return rs;
	}
	
	public void clickFilterButton() {
		getFilterButton().click();
	}
	
	public List<String> getListDevices() {
		String deviceNamePath = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_chosen']";
		List<String> lstDevices = new ArrayList<>();
		for(WebElement ele : getListDevicePanel().findElements(By.xpath(deviceNamePath))) {
			lstDevices.add(ele.getText());
		}
		return lstDevices;
	}
	
	public void selectDeviceFilterByName(String deviceName) {
		getCheckBoxDeviceByName(deviceName).setValue(true);
	}
	
	public void clickClearFilter() {
		getClearButton().click();
	}
	
	public void clickDoneFilter() {
		getDoneButton().click();
	}
	
	public boolean verifyDeviceSeleted(String deviceName) {
		return getCheckBoxDeviceByName(deviceName).getAttribute("checked").equals("true");
	}
	/**
	 * Swipe in timeline and verify events by camera name
	 * @param deviceName
	 * @return
	 */
	public boolean verifyDetectionByDeviceName(String deviceName) {
		List<WebElement> eles = new ArrayList<WebElement>();
		while(getBackToTopElement().getWebElement() == null) {
			eles = getListEvents();
			for (WebElement ele : eles) {
				if(!ele.getText().contains(deviceName)) {
					Log.info(String.format("Event: %s not in device: %s.", ele.getText(), deviceName));
					return false;
				}
			}
			swipeTopToBottom();
		}
		return true;
	}
	/**
	 * share motion video in time line, stream view page
	 */
	public void shareMotionVideo() {
		int count = 0;
		// Swipe until share icon display (maximum 5 times)
		while(count < 5) {
			try {
				getShareIcon().click();
				count = 10;
			} catch (Exception e) {
				swipeTopToBottom();
				count++;
			}
		}
		// If pop up confirm download clip or snap display, select clip
		if(getShareEventPanel().getWebElement() != null) {
			getDownloadClipButton().click();
		}
		getGmailApp().click();
	}
}
