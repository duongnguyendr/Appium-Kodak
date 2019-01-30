package android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageHomeSetting extends PageBase{

	public Element getDebugInfoSw() {
		String swDebugInfo = String.format("//android.widget.Switch[@resource-id='com.perimetersafe.kodaksmarthome:id/sw_debug_info']");
		return new Element(By.xpath(swDebugInfo)).setDescription("Show debug info switch");
	}
	
	public Element getCelsiusUnit() {
		String celsiusUnit = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_temperature_c']";
		return new Element(By.xpath(celsiusUnit));
	}
	
	public Element getFahrenheitUnit() {
		String fahrenheitUnit = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/textview_temperature_f']";
		return new Element(By.xpath(fahrenheitUnit));
	}
	
	public Element getPreviewSwitch() {
		String previewSw = "//android.widget.Switch[@resource-id='com.perimetersafe.kodaksmarthome:id/use_hw_decode_sw']";
		return new Element(By.xpath(previewSw));
	}
		
	public void enableShowDebugInfo() {
		// check if the switch exist
		if (getDebugInfoSw().getWebElement() !=null) {
			if (getDebugInfoSw().getWebElement().getAttribute("checked").equals("false")) {
				getDebugInfoSw().click();
			}
		} else {
			this.exitPage();
			PageDashboard pageDashboard = new PageDashboard().handlePermissionsAndHintsWhenPageOpen(); 
			PageHomeMenu pageHomeMenu =  pageDashboard.gotoHomeMenuPage();
			pageHomeMenu.enableDebug();
			pageHomeMenu.gotoHomeSetingPage();
			getDebugInfoSw().click();
		}
	}
	
	public void clickCelsiusUnit() {
		getCelsiusUnit().click();
	}
	
	public void clickFahrenheitUnit() {
		getFahrenheitUnit().click();
	}
	
	public void enablePreviewMode(boolean value) {
		if(getPreviewSwitch().getAttribute("checked").equals("false") && value == true) {
			getPreviewSwitch().setValue(value);
			clickConfirmButton();
		}else {
			getPreviewSwitch().setValue(value);
		}
	}
	
	public boolean verifyPreviewMode(String value) {
		return getPreviewSwitch().getText().toUpperCase().equals(value.toUpperCase());
	}
}
