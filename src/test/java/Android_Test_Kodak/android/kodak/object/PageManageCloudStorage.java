package Android_Test_Kodak.android.kodak.object;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

public class PageManageCloudStorage extends PageBase{
	
	public Element getYourCurrentPlan() {
		String youPlan = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_sub_plan_name']";
		return new Element(By.xpath(youPlan)).setDescription("Current storage plan");
	}
	
	public List<WebElement> getListDevicesEle(){
		String device = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_chosen']";
		return DriverManager.getAppiumDriver().findElements(By.xpath(device));
	}
	
	public String getYourCurrentStoragePlan() {
		return getYourCurrentPlan().getText();
	}
	
	public List<String> getListDevicesName(){
		List<String> lstDevices = new ArrayList<>();
		for (WebElement ele : getListDevicesEle()) {
			lstDevices.add(ele.getText());
		}
		return lstDevices;
	}
}
