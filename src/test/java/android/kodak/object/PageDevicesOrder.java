package android.kodak.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

public class PageDevicesOrder extends PageBase{
	
	public Element getEditButton() {
		String editBtn = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_action_edit']";
		return new Element(By.xpath(editBtn)).setDescription("Edit order button");
	}
	
	public Element getDoneButton() {
		String doneBtn = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_action_done']";
		return new Element(By.xpath(doneBtn)).setDescription("Edit order done button");
	}
	
	public Element getPrimaryDevice() {
		String primary = "//android.widget.TextView[@text='Devices']/../../preceding-sibling::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_chosen']";
		return new Element(By.xpath(primary)).setDescription("Device order: Primary camera");
	}
	
	// List off devices in secondary section ("Devices" section of non primary devices)
	public List<WebElement> getListSecondaryDevices(){
		String secondary = "//android.widget.TextView[@text='Devices']/../../following-sibling::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_chosen']";
		return DriverManager.getAppiumDriver().findElements(By.xpath(secondary));
	}

	// Parent view which contains both Primary and Devices section
	public Element getDeviceOrderListEle() {
		String deviceOder = "//android.support.v7.widget.RecyclerView[@resource-id='com.perimetersafe.kodaksmarthome:id/device_order_list']";
		return new Element(By.xpath(deviceOder)).setDescription("Device Order: Whole Primary & Devices section");
	}
	
	// Get a list off all Elements which shows as camera entry in this page
	public List<WebElement> getListDevicesEle(){
		String deviceName = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_chosen']";
		return DriverManager.getAppiumDriver().findElements(By.xpath(deviceName));
	}
	/**
	 * Duong Nguyen
	 * Get Name of device in device order page
	 * @return list name of device
	 */
	public List<String> getListDeviceName(){
		List<String> lstDevices = new ArrayList<>();
		for(WebElement ele : getListDevicesEle()) {
			lstDevices.add(ele.getText());
		}
		return lstDevices;
	}
	
	public void clickEditButton() {
		getEditButton().click();
	}
	
	public void clickDoneButton() {
		getDoneButton().click();
	}
	/**
	 * Duong Nguyen
	 * Change primary device with random secondary device
	 */
	public void changeDevicesOrder() {
		clickEditButton();
		// Get primary device location
		int tapX1 = getPrimaryDevice().getLocation().x + getPrimaryDevice().getSize().getWidth() / 2;
		int tapY1 = getPrimaryDevice().getLocation().y + getPrimaryDevice().getSize().getHeight() / 2;
		// Get random device to change with primary device
		int rnd = new Random().nextInt(getListSecondaryDevices().size());
		// Get secondary device location
		int tapX2 = getListSecondaryDevices().get(rnd).getLocation().x + getListSecondaryDevices().get(rnd).getSize().getWidth() / 2;
		int tapY2 = getListSecondaryDevices().get(rnd).getLocation().y + getListSecondaryDevices().get(rnd).getSize().getHeight();
		// Swipe from primary device to secondary device
		DriverManager.getAppiumDriver().swipe(tapX1, tapY1, tapX2, tapY2, 2000);
		clickDoneButton();
	}
	
}
