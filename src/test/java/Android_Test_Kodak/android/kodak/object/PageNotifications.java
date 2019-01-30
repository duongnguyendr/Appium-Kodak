package Android_Test_Kodak.android.kodak.object;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

public class PageNotifications extends PageBase {
	public Element getDoNotDisturbSwitch() {
		String doNotDisturbSw = "//android.widget.Switch[@resource-id='com.perimetersafe.kodaksmarthome:id/switch_do_not_disturb']";
		return new Element(By.xpath(doNotDisturbSw)).setDescription("DND switch");
	}
	
	public Element getNotificationContainer() {
		String container = "//android.widget.RelativeLayout[@resource-id='com.perimetersafe.kodaksmarthome:id/layout_device_notification_container']";
		return new Element(By.xpath(container)).setDescription("Notification setting page: Notification container");
	}
	
	public List<WebElement> getListDeviceSwitch(){
		String deviceSw = "//android.widget.Switch[@resource-id='com.perimetersafe.kodaksmarthome:id/switch_device_chosen']";
		return DriverManager.getAppiumDriver().findElements(By.xpath(deviceSw));
	}
	
	public void enableDoNotDisturb(boolean value) {
		getDoNotDisturbSwitch().setValue(value);
	}
	
	public boolean verifyDoNotDisturbEnable(boolean value) {
		boolean rs = false;
		if(value) {
			rs = getDoNotDisturbSwitch().getAttribute("checked").equals(value + "") && getNotificationContainer().getWebElement() == null;
		}else {
			rs = getDoNotDisturbSwitch().getAttribute("checked").equals(value + "") && getNotificationContainer().getWebElement() != null;
		}
		return rs;
	}
	/**
	 * Duong Nguyen
	 * Enable/disable all device notifications
	 * @param value
	 */
	public void enableAllDeviceNotification(boolean value) {
		for(WebElement ele : getListDeviceSwitch()) {
			if (Boolean.parseBoolean(ele.getAttribute("checked")) != value) {
				ele.click();
			}
		}
	}
	/**
	 * Duong nguyen
	 * Verify all device notification enable/disable
	 * @param value
	 * @return
	 */
	public boolean verifyAllDeviceNotificationEnable(boolean value) {
		boolean rs = true;
		for(WebElement ele : getListDeviceSwitch()) {
			if (Boolean.parseBoolean(ele.getAttribute("checked")) != value) {
				rs = false;
			}
		}
		return rs;
	}
	
}
