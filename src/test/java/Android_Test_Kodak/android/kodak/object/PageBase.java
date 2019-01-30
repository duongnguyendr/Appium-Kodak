package Android_Test_Kodak.android.kodak.object;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
import com.cinatic.log.Log;

public class PageBase {
//	All permission request dialog has the same id
//  I just create these dummy variables so it would be easier to understand the test case
	public static final String ANDROID_PERMISSION_MEDIA = "media";
	public static final String ANDROID_PERMISSION_LOCATION = "location";
	public static final String ANDROID_PERMISSION_MICROPHONE = "mic";
	private static boolean flagPermissionMediaGranted = false;
	private static boolean flagPermissionLocationGranted = false;
	private static boolean flagPermissionMicrophoneGranted = false;
	public static int ELEMENT_TIMEOUT_SHORT = 5;
	
	// Android permission request dialog
	public Element getLocationPermissionProcessBtn() {
		String btnLocationPermissionProcess= String.format("//android.widget.Button[@resource-id='android:id/button1']");
		return new Element(By.xpath(btnLocationPermissionProcess),ELEMENT_TIMEOUT_SHORT).setDescription("Location permission Allow button");
	};
	public Element getAndroidPermissionAllowBtn() {
		String xpathLocator = String.format("//android.widget.Button[@resource-id='com.android.packageinstaller:id/permission_allow_button']");
		return new Element(By.xpath(xpathLocator),ELEMENT_TIMEOUT_SHORT).setDescription("Android permission Allow button");
	};
	
	public Element getBackButton() {
		String btmBack = String.format("//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/imgMain']");
		return new Element(By.xpath(btmBack)).setDescription("Back button");
	}
	/**
	 * using for confirm popup: (Ok, Yes, Confirm...) button
	 * @return
	 */
	public Element getConfirmButton() {
		String confirmBtn = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_confirm']";
		return new Element(By.xpath(confirmBtn)).setDescription("Confirm button");
	}
	/**
	 * Using for confirm popup: (No, Cancel...) button
	 * @return
	 */
	public Element getCancelButton() {
		String cancelBtn = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_cancel']";
		return new Element(By.xpath(cancelBtn)).setDescription("Cancel buton");
	}
	
	public Element getGmailApp() {
		String gmailIcon = "//android.widget.TextView[@text='Gmail']/..//android.widget.ImageView[@resource-id='android:id/icon']";
		return new Element(By.xpath(gmailIcon), 120).setDescription("Share via Gmail button");
	}
	
	public void allowAndroidPermissionIfAsked(String dummyPermission) {
		
		switch (dummyPermission) {
		case ANDROID_PERMISSION_LOCATION:
			if (flagPermissionLocationGranted) {
				Log.info("Location permission is already granted");
				break;
			}
			if (getLocationPermissionProcessBtn().getWebElement() != null) {
				getLocationPermissionProcessBtn().click();
				getAndroidPermissionAllowBtn().click();
				flagPermissionLocationGranted = true;
			}
			break;
		case ANDROID_PERMISSION_MEDIA:
			if (flagPermissionMediaGranted) {
				Log.info("Media access permission is already granted");
				break;
			}
			if (getAndroidPermissionAllowBtn().getWebElement() != null) {
				getAndroidPermissionAllowBtn().click();
				flagPermissionMediaGranted = true;
			}
			break;
		case ANDROID_PERMISSION_MICROPHONE:
			if (flagPermissionMicrophoneGranted) {
				Log.info("Record Audio permission is already granted");
				break;
			}
			if (getAndroidPermissionAllowBtn().getWebElement() != null) {
				getAndroidPermissionAllowBtn().click();
				flagPermissionMicrophoneGranted = true;
			}
			break;
		default:
			break;
		}	
	}	

	// Tutorial - appears when hit (?) button
	public Element getTutorialNextDoneBtn() {
		String btnHintNextDone = String.format("//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_action_bottom']");
		return new Element(By.xpath(btnHintNextDone),3).setDescription("Tutorial Next/Done button");
	};
	public void closeAnyTutorial(boolean flag) {
		if (flag) return;		
		// click Next/done 3 time to close the tutorial
		for (int i = 0; i < 3; i++) {
			if (getTutorialNextDoneBtn().getWebElement() != null) {
				getTutorialNextDoneBtn().click();
			} 
		}
	}

	// Dashboard upgrade cloud tip
	// This only appears in PageDashboard and PageCameraSetting when turn on motion detection
	public Element getCloudUpgradeTipCloseBtn () {
		//this button is NAF
		String xpathLocator = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_close']";
		return new Element(By.xpath(xpathLocator),10).setDescription("Close \"Want to see more\" hint button");
	}
	
	public Element getTimelineIcon() {
		String timelineIcon = "//android.widget.HorizontalScrollView/android.widget.LinearLayout/android.support.v7.app.ActionBar.Tab[3]/android.widget.ImageView";
		return new Element(By.xpath(timelineIcon)).setDescription("Timeline tab button");
	}
	
	public Element getGalleryIcon() {
		String galleryIcon = "//android.widget.HorizontalScrollView/android.widget.LinearLayout/android.support.v7.app.ActionBar.Tab[2]/android.widget.ImageView";
		return new Element(By.xpath(galleryIcon)).setDescription("Gallery tab button");
	}
	
	public Element getDashBoardIcon() {
		String dashboardIcon = "//android.widget.HorizontalScrollView/android.widget.LinearLayout/android.support.v7.app.ActionBar.Tab[1]/android.widget.ImageView";
		return new Element(By.xpath(dashboardIcon)).setDescription("Dashboard tab button");
	}
	// Empty event image exited in page stream view and Timeline page
	public Element getEmptyEventTextView() {
		String emptyEvent = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/tv_empty_event']";
		return new Element(By.xpath(emptyEvent)).setDescription("Empty event image exited in page stream view and Timeline page");
	}
	
	/*
	 * Close "Want to see more" top
	 */
	public void closeCouldUpgradeTipIfAsked() {
		try {
			getCloudUpgradeTipCloseBtn().click();
			Log.info("Clould uprade hint has just been closed");
		} catch (Exception e) {
			Log.info("Cloud upgrade hint does not show up");
		}
	}	
	// There are many page using this method, so move on PageBase
	public void exitPage() {
		getBackButton().click();
	}
	
	/**
	 * Swipe from top to bottom
	 */
	public void swipeTopToBottom(){
		Dimension dim = DriverManager.getAppiumDriver().getAppiumDriver().manage().window().getSize();
		int height = dim.getHeight();
		int width = dim.getWidth();
		int x = width/2;
		int top_y = (int)(height*0.80);
		int bottom_y = (int)(height*0.20);
		DriverManager.getAppiumDriver().swipe(x, top_y, x, bottom_y, 2000);
	}
	
	public void swipeBottomToTop(){
		Dimension dim = DriverManager.getAppiumDriver().getAppiumDriver().manage().window().getSize();
		int height = dim.getHeight();
		int width = dim.getWidth();
		int x = width/2;
		int top_y = (int)(height*0.20);
		int bottom_y = (int)(height*0.80);
		DriverManager.getAppiumDriver().swipe(x, top_y, x, bottom_y, 2000);
	}

//	public boolean waitForDisplayElement(Element ele) {
//		boolean isElementPresent = false;
//		try{
//			WebDriverWait wait = new WebDriverWait(DriverManager.getAppiumDriver(), 10);
//			wait.until(ExpectedConditions.visibilityOf(ele));
//			isElementPresent = ele.isDisplayed();
//			return isElementPresent;	
//		}catch(Exception e){
//			isElementPresent = false;
//			System.out.println(e.getMessage());
//			return isElementPresent;
//		}
//	}
	
	/**
	 * using for confirm popup: (Ok, Yes, Confirm...) button
	 * @return
	 */
	public void clickConfirmButton() {
		getConfirmButton().click();
	}
	
	/**
	 * Using for confirm popup: (No, Cancel...) button
	 * @return
	 */
	public void clickCancelButton() {
		getCancelButton().click();
	}
	
	public PageTimeline navigateToTimelinePage() {
		getTimelineIcon().click();
		return new PageTimeline();
	}
	
	public PageDashboard navigateToDashBoardPage() {
		getDashBoardIcon().click();
		return new PageDashboard();
	}
	
	public void navigateToGalleryPage() {
		getGalleryIcon().click();
	}
	// There are many page using this method, so move on PageBase
	public boolean verifyEmptyEvent() {
		return getEmptyEventTextView().getWebElement() != null;
	}
}
