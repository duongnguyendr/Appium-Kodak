package android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PopupNotification {
	public Element getCloseButton() {
		String btnClose = "//android.widget.Button[@resource-id='com.nlucas.popupnotifications:id/popup_button_close']";
		return new Element(By.xpath(btnClose), 60).setDescription("Close button");
	}

	public Element getViewButton() {
		String btnView = "//android.widget.Button[@resource-id='com.nlucas.popupnotifications:id/popup_button_view']";
		return new Element(By.xpath(btnView), 3).setDescription("View Button");
	}

	public Element getPopupMessageLabel() {
		String lblPopupMessage = "//android.widget.TextView[@resource-id='com.nlucas.popupnotifications:id/popup_text']";
		return new Element(By.xpath(lblPopupMessage)).setDescription("Popup message");
	}

	public Element getPopUpHeaderLabel() {
		String lblPopupHeader = "//android.widget.TextView[@resource-id='com.nlucas.popupnotifications:id/popup_header_withpicture_title']";
		return new Element(By.xpath(lblPopupHeader)).setDescription("Header of popup");
	}
}
