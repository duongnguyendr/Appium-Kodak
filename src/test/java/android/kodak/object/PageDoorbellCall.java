package android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageDoorbellCall {
	
	/***ELEMENT***/	
	
	/*Accept call button*/
	public Element getAcceptButton(){ 
		String btnAccept = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_accept']";
		return new Element(By.xpath(btnAccept), 15).setDescription("Doorbell: Accept call button"); 
	}
	
	/*Decline call button*/
	public Element getDeclineButton(){ 
		String btnDecline = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/btn_decline']";
		return new Element(By.xpath(btnDecline), 15).setDescription("Doorbell: Decline call button"); 
	}
	
}
