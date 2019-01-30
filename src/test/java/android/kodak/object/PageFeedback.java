package android.kodak.object;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageFeedback extends PageBase{
	private String radioButtonSeletedPath = "//android.widget.RadioButton[@resource-id='com.perimetersafe.kodaksmarthome:id/radioItem' and @checked='true']/../android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/tvItemTitle']";
	
	public Element getListWhatIssuePanel() {
		String lstIssue = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_issue']";
		return new Element(By.xpath(lstIssue), "What is the issue?").setDescription("What is the issue?");
	}
	
	public Element getSetupOption() {
		String setupEle = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_issue']//android.widget.TextView[@text='Setup']";
		return new Element(By.xpath(setupEle), "Setup").setDescription("What's the issue: Setup radio button");
	}
	
	public Element getStreamingOption() {
		String streamingEle = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_issue']//android.widget.TextView[@text='Streaming']";
		return new Element(By.xpath(streamingEle), "Streaming").setDescription("What's the issue: Streaming radio button");
	}
	
	public Element getOfflineOption() {
		String offEle = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_issue']//android.widget.TextView[@text='Offline']";
		return new Element(By.xpath(offEle), "Offline").setDescription("What's the issue: Offline radio button");
	}
	
	public Element getMotionDetectionOption() {
		String motionEle = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_issue']//android.widget.TextView[@text='Motion Detection']";
		return new Element(By.xpath(motionEle), "Motion Detection").setDescription("What's the issue: Motion detection radio button");
	}
	
	public Element getWhatAppRelatedOption() {
		String appRelatedEle = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_issue']//android.widget.TextView[@text='App related']";
		return new Element(By.xpath(appRelatedEle), "App related").setDescription("What's the issue: App relate radio button");
	}
	
	public Element getOtherOption() {
		String othersEle = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_issue']//android.widget.TextView[@text='Others/Feedback']	";
		return new Element(By.xpath(othersEle), "Others/Feedback").setDescription("What's the issue: Other radio button");
	}
	
	public Element getListWhenHappenPanel() {
		String lstWhen = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_when']";
		return new Element(By.xpath(lstWhen), "Not Applicable").setDescription("When happened? section");
	}
	
	public Element getLastFiveMinOption() {
		String fiveMinEle = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_when']//android.widget.TextView[@text='Last 5 min']";
		return new Element(By.xpath(fiveMinEle), "Last 5 min").setDescription("When happened: 5 min");
	}
	
	public Element getFiveToThirtyOption() {
		String ele = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_when']//android.widget.TextView[@text='5 to 30 min ago']";
		return new Element(By.xpath(ele), "5 to 30 min ago").setDescription("When happened: 5-30 min");
	}
	
	public Element getThirtyToHoursOption() {
		String ele = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_when']//android.widget.TextView[@text='30 to 60 min ago']";
		return new Element(By.xpath(ele), "30 to 60 min ago").setDescription("When happened: 30-60 min");
	}
	
	public Element getOneDayOption() {
		String oneDayEle = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_when']//android.widget.TextView[@text='1 day ago']";
		return new Element(By.xpath(oneDayEle), "1 day ago").setDescription("When happened: 1 day");
	}
	
	public Element getWhenNotApplicableOption() {
		String scrollToTxt = "Not Applicable";
		String applicableEle = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_when']//android.widget.TextView[@text='Not Applicable']";
		return new Element(By.xpath(applicableEle), scrollToTxt).setDescription("When: " + scrollToTxt);
	}
	
	public Element getListWhichDevicePanel() {
		String scrollToString="Which Device?";
		String lstWhich = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_which']";
		return new Element(By.xpath(lstWhich), scrollToString).setDescription(scrollToString + " section");
	}
	
	public Element getWhichAppRelatedOption() {
		String scrollToString="App related";
		String relatedEle = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_which']//android.widget.TextView[@text='App related']";
		return new Element(By.xpath(relatedEle),scrollToString ).setDescription("Which dev: " + scrollToString);
	}
	
	public Element getWhichNotApplicableOption() {
		String scrollToString="Not Applicable";
		String applicapbleEle = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_which']//android.widget.TextView[@text='Not Applicable']";
		return new Element(By.xpath(applicapbleEle), scrollToString).setDescription("Which dev: " + scrollToString);
	}
	
	public Element getDeviceOptionByName(String name) {
		String deviceName = "//android.widget.ListView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_which']//android.widget.TextView[@text='%s']";
		return new Element(By.xpath(String.format(deviceName, name)), name).setDescription("Which dev: Select devices: " + name);
	}
	
	public Element getCameraWithIssue() {
		String ele = "//android.widget.TextView[@text='Select Camera with issue']";
		return new Element(By.xpath(ele), "Short Description").setDescription("Which dev: Select cam with issue button");
	}
	
	public Element getDeviceChosenByName(String name) {
		String ele = "//android.widget.TextView[@text='%s']/../android.widget.RadioButton[@resource-id='com.perimetersafe.kodaksmarthome:id/checkbox_device_chosen']";
		return new Element(By.xpath(String.format(ele, name))).setDescription("Select cam " + name + " from the list");
	}
	
	public Element getDescriptionTextEdit() {
		String descEle = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/edittext_description']";
		return new Element(By.xpath(descEle)).setDescription("Short Description section");
	}
	
	public Element getSendEmailIcon() {
		String sendIcon = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/done_event_menu_item']";
		return new Element(By.xpath(sendIcon)).setDescription("Done/Send report button");
	}
	/**
	 * switch all option in What is the issues list and verify user can switch all option
	 * @return: list option user cannot selected
	 */
	public List<String> switchAllWhatIssueOption() {
		List<String> lstOpt = new ArrayList<String>();
		getSetupOption().setValue(true);
		if(getSetupOption().getAttribute("checked") == "false")
			lstOpt.add(getSetupOption().getText());
		getStreamingOption().setValue(true);
		if(getStreamingOption().getAttribute("checked") == "false")
			lstOpt.add(getStreamingOption().getText());
		getOfflineOption().setValue(true);
		if(getOfflineOption().getAttribute("checked") == "false")
			lstOpt.add(getOfflineOption().getText());
		getMotionDetectionOption().setValue(true);
		if(getMotionDetectionOption().getAttribute("checked") == "false")
			lstOpt.add(getMotionDetectionOption().getText());
		getWhatAppRelatedOption().setValue(true);
		if(getWhatAppRelatedOption().getAttribute("checked") == "false")
			lstOpt.add(getWhatAppRelatedOption().getText());
		getOtherOption().setValue(true);
		if(getOtherOption().getAttribute("checked") == "false")
			lstOpt.add(getOtherOption().getText());
	
		return lstOpt;
	}
	
	/**
	 * switch all option in When happened list and verify user can switch all option
	 * @return: list option user cannot selected
	 */
	public List<String> switchAllWhenIssueHappened() {
		List<String> lstOpt = new ArrayList<String>();
		getLastFiveMinOption().setValue(true);
		if(getLastFiveMinOption().getAttribute("checked") == "false")
			lstOpt.add(getLastFiveMinOption().getText());
		getFiveToThirtyOption().setValue(true);
		if(getFiveToThirtyOption().getAttribute("checked") == "false")
			lstOpt.add(getFiveToThirtyOption().getText());
		getThirtyToHoursOption().setValue(true);
		if(getThirtyToHoursOption().getAttribute("checked") == "false")
			lstOpt.add(getThirtyToHoursOption().getText());
		getOneDayOption().setValue(true);
		if(getOneDayOption().getAttribute("checked") == "false")
			lstOpt.add(getOneDayOption().getText());
		getWhenNotApplicableOption().setValue(true);
		if(getWhenNotApplicableOption().getAttribute("checked") == "false")
			lstOpt.add(getWhenNotApplicableOption().getText());
		return lstOpt;
	}
	
	public void selectDeviceByName(String deviceName) {
		if(getCameraWithIssue().getWebElement() != null) {
			getCameraWithIssue().click();
		}else {
			getDeviceOptionByName(deviceName).setValue(true);
		}
		getDeviceChosenByName(deviceName).setValue(true);
	}
	
	public void inputShortDescription(String value) {
		getDescriptionTextEdit().sendKeys(value);
	}
	
	public void clickSendReport() {
		getSendEmailIcon().click();
		getGmailApp().click();
	}
	/**
	 * Get device selected
	 * @return
	 */
	public String getWhichDeviceSelected() {
		return getListWhichDevicePanel().findElement(By.xpath(radioButtonSeletedPath)).getText();
	}
	/**
	 * Get When happened selected
	 * @return
	 */
	public String getWhenHappendSelected() {
		return getListWhenHappenPanel().findElement(By.xpath(radioButtonSeletedPath)).getText();
	}
	/**
	 * get What issue selected
	 * @return
	 */
	public String getWhatIssueSeleted() {
		return getListWhatIssuePanel().findElement(By.xpath(radioButtonSeletedPath)).getText();
	}
	/**
	 * Collect What issue, when happened, which device selected and description
	 * @return
	 */
	public List<String> collectReportData() {
		List<String> data = new ArrayList<>();
		data.add(getDescriptionTextEdit().getText());
		data.add(getWhichDeviceSelected());
		data.add(getWhenHappendSelected());
		data.add(getWhatIssueSeleted());
		
		return data;
	}
}
