package Android_Test_Kodak.android.kodak.object;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.element.Element;

public class PageGrantAccess extends PageBase{
	
	public Element getAddUserButton() {
		String btn = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/menu_add_share_user']";
		return new Element(By.xpath(btn)).setDescription("Add user button");
	}
	
	public Element getEmailGrantAccess(String email) {
		String emailTextView = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_email' and @text='%s']";
		return new Element(By.xpath(String.format(emailTextView, email))).setDescription(String.format("Access granted email: %s", email));
	}
	
	public Element getEmailTextBox() {
		String emailTb = "//android.widget.EditText[@resource-id='com.perimetersafe.kodaksmarthome:id/edittext_email']";
		return new Element(By.xpath(emailTb)).setDescription("Add email to grant access textbox");
	}
	
	public Element getDeviceToShareByName(String deviceName) {
		String deviceCb = String.format(
				"//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_device_chosen' and @text='%s']/../android.widget.CheckBox",
				deviceName);
		return new Element(By.xpath(deviceCb)).setDescription(String.format("Camera to share: %s", deviceName));
	}
	
	public Element getGrantAccessSwitch() {
		String grantAcc = "//android.widget.Switch[@resource-id='com.perimetersafe.kodaksmarthome:id/sw_allow_access_rights']";
		return new Element(By.xpath(grantAcc)).setDescription("Grant acess switch");
	}
	
	public Element getSaveButton() {
		String saveBtn = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_add_share_user']";
		return new Element(By.xpath(saveBtn)).setDescription("Grant access save button");
	}
	
	public List<WebElement> getListUserShared(){
		String lstUserPanel = "//android.support.v7.widget.RecyclerView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_share_user']";
		Element userPanel = new Element(By.xpath(lstUserPanel));
		String lstUser = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_forward']";
		return userPanel.findElements(By.xpath(lstUser));	
	}
	
	public Element getRemoveUserIcon() {
		String removeIcon = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/remove_share_user_menu_item']";
		return new Element(By.xpath(removeIcon)).setDescription("remove shared user button");
	}
	
	public Element getConfirmRemoveButton() {
		String removeBtn = "//android.widget.Button[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_remove']";
		return new Element(By.xpath(removeBtn)).setDescription("Confirm button");
	}
	
	public void clickAddUserButton() {
		getAddUserButton().click();
	}
	
	public void grantAccessToUser(String email, String deviceName, boolean allowAccessRight) {
		clickAddUserButton();
		getEmailTextBox().sendKeys(email);
		getDeviceToShareByName(deviceName).click();
		if(allowAccessRight) {
			getGrantAccessSwitch().click();
		}
		if(getSaveButton().isEnabled()) {
			getSaveButton().click();
		}
	}
	
	public boolean verifyEmailWasGrantAccess(String email) {
		return getEmailGrantAccess(email).getWebElement() != null;
	}
	
	public void removeAllSharedUser() {
		for (WebElement ele : getListUserShared()) {
			ele.click();
			getRemoveUserIcon().click();
			getConfirmRemoveButton().click();
		}
	}
}
