package android.kodak.object;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
/**
 * 
 * @author Duong.Nguyen
 * 
 * PageGmail open when user click on Gmail icon to send camera log, device log or share video
 *
 */
public class PageGmail extends PageBase{
	public Element getMailTo() {
		String mailTo = "//android.widget.MultiAutoCompleteTextView[@resource-id='com.google.android.gm:id/to']";
		return new Element(By.xpath(mailTo), "From").setDescription("Gmail: Mail to");
	}
	
	public Element getSendEmailButton() {
		String sendBtn = "//android.widget.TextView[@resource-id='com.google.android.gm:id/send']";
		return new Element(By.xpath(sendBtn)).setDescription("Send email button");
	}
	
	public Element getContentEmailView() {
		String content = "//android.widget.LinearLayout//android.view.View";
		return new Element(By.xpath(content)).setDescription("Gmail: Content email");
	}
	
	public Element getTitleEmail() {
		String subJ = "//android.widget.EditText[@resource-id='com.google.android.gm:id/subject']";
		return new Element(By.xpath(subJ)).setDescription("Gmail: title email");
	}
	
	
	
	public Element getAttachmentGrid() {
		String attactmentgrid = "//android.widget.FrameLayout[@resource-id='com.google.android.gm:id/attachment_tile_grid']";
		return new Element(By.xpath(attactmentgrid)).setDescription("Gmail: grid contains attachments");
	}
	
	public void inputMailTo(String email) {
		getMailTo().sendKeys(email);
	}
	/**
	 * author: duong.nguyen
	 * Send an email with customize reception and subject
	 * @param email
	 * @param subj
	 */
	public void sendEmail(String email, String subj) {
		TimeHelper.sleep(3000);
		DriverManager.getAppiumDriver().hideKeyboard();
		TimeHelper.sleep(3000);
		inputMailTo(email);
		getTitleEmail().sendKeys(subj);
		getSendEmailButton().click();
		TimeHelper.sleep(5000);
	}
	// Now, cannot get content of email, because its stay in android.webkit.webview class
	public String getContentEmail() {
		return getContentEmailView().getText();
	}

	public List<String> getAttachmentsFile(){
		List<String> attachments = new ArrayList<>();
		String attName = "//android.widget.TextView[@resource-id='com.google.android.gm:id/attachment_tile_title']";
		for(WebElement ele : getAttachmentGrid().findElements(By.xpath(attName))) {
			attachments.add(ele.getText());
		}
		return attachments;
	}
}
