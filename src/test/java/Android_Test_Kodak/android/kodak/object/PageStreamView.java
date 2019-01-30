package Android_Test_Kodak.android.kodak.object;

import org.openqa.selenium.By;
import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
import com.cinatic.log.Log;

public class PageStreamView extends PageBase{
	public static boolean flagAutoTutorialClosed = false;
	
	public PageStreamView() {
		closeAnyTutorial(flagAutoTutorialClosed);
		flagAutoTutorialClosed = true;
	}
	
	/***ELEMENT***/		
	//COMMON
	public Element getStreamLoadingImg(){
		String imgLoading = "//android.widget.ProgressBar[@resource-id='com.perimetersafe.kodaksmarthome:id/img_stream_loading']";
		return new Element(By.xpath(imgLoading), 3).setDescription("Stream loading image"); 
	}
	
	public Element getP2pStatusLabel(){ 
		String lblP2pStatus = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_p2p_status']";
		return new Element(By.xpath(lblP2pStatus), 3).setDescription("P2P status"); 
	}
	
	public Element getLiveStreamStatusLabel(){ 
		String lblLiveStreamStatus = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_live_stream_status']";
		return new Element(By.xpath(lblLiveStreamStatus), 3).setDescription("Live stream status"); 
	}
	
	public Element getBackButtonInStreamView(){
		String btnBack = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/imgMain']";
		return new Element(By.xpath(btnBack)).setDescription("Back button in Stream view"); 
	}
	
	public Element getCameraSettingBtn() {
		String xpartLocator = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/menu_setting']";
		return new Element(By.xpath(xpartLocator)).setDescription("Camera setting button in Stream view");
	}
	
	//MENU
	public Element getMenuImage() {
		String imgMenu = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_menu']";
		return new Element(By.xpath(imgMenu)).setDescription("Mini memu button in Stream view");
	};
	
	public Element getTalkBackDisableImg() {
		String imgTalkBackDisable = "//android.widget.ImageView[@content-desc='disable talkback']";
		return new Element(By.xpath(imgTalkBackDisable)).setDescription("Talkback disabled button"); 
	};
	
	public Element getTalkBackEnableImg() {
		String imgTalkBackEnable = "//android.widget.ImageView[@content-desc='enable talkback']";
		return new Element(By.xpath(imgTalkBackEnable)).setDescription("Talkback enabled button"); 
	};
	
	public Element getCaptureImage() {
		String imgCapture = "//android.widget.ImageView[@content-desc='capture image']";
		return new Element(By.xpath(imgCapture), 5).setDescription("Capture image button"); 
	};
	
	public Element getStartRecordVideoBtn() {
		String xpathLocator = "//android.widget.ImageView[@content-desc='start record video']";
		return new Element(By.xpath(xpathLocator), 5).setDescription("Start video record button"); 
	}
	
	public Element getStopRecordVideoImage() {
		String xpathLocator = "//android.widget.ImageView[@content-desc='stop record video']";
		return new Element(By.xpath(xpathLocator), 5).setDescription("Stop video record button"); 
	}
	
	public Element getMelodyImage() { 
		String xpathLocator = "//android.widget.ImageView[@content-desc='disable melody']";
		return new Element(By.xpath(xpathLocator), 5).setDescription("Melody menu button"); 
	}
	
	public Element getMuteSoundImage() { 
		String imgMuteSound = "//android.widget.ImageView[@content-desc='mute sound']";
		return new Element(By.xpath(imgMuteSound)).setDescription("Mute sound button"); 
	};
	
	public Element getUnMuteSoundImage() {
		String imgUnMuteSound = "//android.widget.ImageView[@content-desc='unmute sound']";
		return new Element(By.xpath(imgUnMuteSound)).setDescription("Unmute sound button"); 
	};
	
	//MELODY
	public Element getMelodyByName(int number) { 
		String lblMelody = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_melody' and @text='Melody %s']";
		return new Element(By.xpath(String.format(lblMelody, number))).setDescription(String.format("Melody number: %s", number)); 
	};
	
	public Element getStopMelody() {
		String lblStopMelody = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_melody' and @text='Stop Melody']";
		return new Element(By.xpath(lblStopMelody)).setDescription("Stop melody button");
	};
	
	//EVENT
	public Element getFirstMotionDetectionButton() { 
		String btnFirstMotionDetection = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_timeline_type' and @text='Motion Detected']/../..//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/ic_play']";
		return new Element(By.xpath(btnFirstMotionDetection)).setDescription("First MD clip in stream view event"); 
	};
	
	//VIDEO PLAY
	public Element getVideoPlayImage() {
		String imgVideoPlay = "//android.view.View[@resource-id='com.perimetersafe.kodaksmarthome:id/img_video_play']";
		return new Element(By.xpath(imgVideoPlay)).setDescription("MD clip play button"); 
	};
	
	public Element getVideoLoading() {
		String imgVideoLoading = "//android.widget.ProgressBar[@resource-id='com.perimetersafe.kodaksmarthome:id/img_loading']";
		return new Element(By.xpath(imgVideoLoading), 3).setDescription("MD clip loading image"); 
	};
	
	public Element getVideoPlaySeekbar() { 
		String seekbarVideoPlay = "//android.widget.SeekBar[@resource-id='com.perimetersafe.kodaksmarthome:id/seekbar_progress']";
		return new Element(By.xpath(seekbarVideoPlay)).setDescription("MD clip player seekbar"); 
	};
	
	public Element getCancelMelodyButton() {
		String cancelBtn = "//android.widget.Button[@resource-id='android:id/button1']";
		return new Element(By.xpath(cancelBtn)).setDescription("Melody menu cancel button");
	}
	
	public Element getEditMotionSettingButton() {
		String editBtn = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_edit_motion_setting']";
		return new Element(By.xpath(editBtn)).setDescription("Big red Edit motion button setting");
	}
	
	public Element getHumidityTextView() {
		String humidity = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_humidity']";
		return new Element(By.xpath(humidity)).setDescription("Stream view: Humidity value");
	}
	
	public Element getTemperatureTextView() {
		String tempareture = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_temperature']";
		return new Element(By.xpath(tempareture));
	}
	
	/***ACTION***/
	public String getStreamMode(int timeOut) {
		Log.info("Get stream mode");
		String mode = "";		
		while (timeOut >= 0) {
			try {
				mode = getP2pStatusLabel().getText();
			} catch (Exception e) {
				mode = "";
			}
			if (mode.contains("OK"))
				break;
			timeOut--;
			TimeHelper.sleep(1000);
		}
		return mode;
	}		
	
	public PageDashboard exitToDashboard() {
		Log.info("Exit page");
		exitPage();
		TimeHelper.sleep(5000);
		// handle new tip added in 1.0.4 (40) when back to dashboard
		PageDashboard pageDashboard = new PageDashboard();
		try {
			pageDashboard.getGrandAccessDashboardTipCloseBtn().click();
			Log.info("Additional tip about preview and Grant access feature has just been close");
		} catch (Exception e) {
			Log.info("Additional tip about preview and Grant access feature does not show");
		}		
		return pageDashboard;		
	}
	
	public void enableTalkBack() {
		Log.info("Enable talk back");
		getMenuImage().click();
		getTalkBackDisableImg().click();
		allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_MICROPHONE);
		TimeHelper.sleep(5000);
	}
	
	public void disableTalkBack() {
		Log.info("Disable talk back");
		try {
			getTalkBackEnableImg().click();
			TimeHelper.sleep(5000);
		} catch (Exception e) {
			
		}
	}
	
	public void muteSound() {
		Log.info("Mute sound");
		if (getCaptureImage().getWebElement() == null && getMelodyImage().getWebElement() == null)
			getMenuImage().click();

		if (getMuteSoundImage().getWebElement() != null) {
			getMuteSoundImage().click();
			TimeHelper.sleep(5000);
		}
	}
	
	public void unMuteSound() {
		Log.info("Unmute sound");
		if (getCaptureImage().getWebElement() == null && getMelodyImage().getWebElement() == null)
			getMenuImage().click();

		if (getUnMuteSoundImage().getWebElement() != null) {
			getUnMuteSoundImage().click();
			TimeHelper.sleep(5000);
		}
	}
	
	public void captureImage() {
		Log.info("Capture image");
		if (getCaptureImage().getWebElement() == null && getMelodyImage().getWebElement() == null) {
			getMenuImage().click();
		}

		getCaptureImage().click();
		TimeHelper.sleep(5000);		
	}
	
	public void startRecordVideo(int duration) {
		Log.info("Start record video in: " + duration);
		if (getCaptureImage().getWebElement() == null && getMelodyImage().getWebElement() == null)
			getMenuImage().click();

		if (getStartRecordVideoBtn().getWebElement() != null) {
			getStartRecordVideoBtn().click();
			TimeHelper.sleep(duration * 1000);
		}
	}
	
	public void stopRecordVideo() {
		Log.info("Stop record video");
		if (getCaptureImage().getWebElement() == null && getMelodyImage().getWebElement() == null)
			getMenuImage().click();

		if (getStopRecordVideoImage().getWebElement() != null) {
			getStopRecordVideoImage().click();
			TimeHelper.sleep(5000);
		}
	}
	
	public void swipeUp() {
		Log.info("Swipe up");
		if (getCaptureImage().getWebElement() == null && getMelodyImage().getWebElement() == null)
			getMenuImage().click();
		
		if(getMelodyImage().getWebElement() == null)
		{
			Element element1 = getCaptureImage();
			int tapX1 = element1.getLocation().x + element1.getSize().getWidth() / 2;
			int tapY1 = element1.getLocation().y + element1.getSize().getHeight() / 2;

			Element element2 = getStartRecordVideoBtn();
			int tapX2 = element2.getLocation().x + element2.getSize().getWidth() / 2;
			int tapY2 = element2.getLocation().y + element2.getSize().getHeight() / 2;

			DriverManager.getAppiumDriver().swipe(tapX1, tapY1, tapX2, tapY2, 2000);
		}
	}
	
	public void openMelody() {
		Log.info("Open melody");
		swipeUp();
		getMelodyImage().click();
		TimeHelper.sleep(1500);
	}
	
	public void playMelody(int number) {
		getMelodyByName(number).click();
		TimeHelper.sleep(5000);
	}
	
	public void playStopMelody() {
		Log.info("Play stop melody");
		getStopMelody().click();
		TimeHelper.sleep(5000);
	}
	
	public boolean verifyTalkBackEnable() {
		Log.info("Verify talk back enable");
		return getTalkBackEnableImg().getWebElement() != null;
	}
	
	public boolean verifyTalkBackDisable() {
		Log.info("Verify talk back disable");
		return getTalkBackDisableImg().getWebElement() != null;
	}
	
	public void clickCancelOnPlayMelodyPanel() {
		Log.info("Click cancel on play medody panel");
		getCancelMelodyButton().click();
	}
	
	public void clickEditSoundMotionSetting() {
		Log.info("Click edit sound motion setting");
		getEditMotionSettingButton().click();
	}
	
	public boolean verifyStreamViewPage() {
		Log.info("Verify stream view page");
		return getMenuImage().getWebElement() != null;
	}
	
	public String getHumidityInApp() {
		Log.info("Get humidity in app");
		return getHumidityTextView().getText();
	}
	
	public boolean verifyEmptyEventInStreamPage() {
		return getEditMotionSettingButton().getWebElement() != null;
	}
	
	public boolean verifyTemperatureUnitInStreamPage(String unit) {
		return getTemperatureTextView().getText().toUpperCase().endsWith(unit.toUpperCase());
	}
}