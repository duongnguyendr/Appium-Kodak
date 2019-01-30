package Android_Test_Kodak.android.kodak.object;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.element.Element;

public class PageGallery extends PageBase{
	
	public Element getSelectTextView() {
		String selectEle = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_action_select']";
		return new Element(By.xpath(selectEle)).setDescription("Gallery: Select mode button");
	}
	
	public Element getSelectAllTextView() {
		String allEle = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_action_select_all']";
		return new Element(By.xpath(allEle)).setDescription("Gallery: Select all button");
	}
	
	public Element getDeselectAllTextView() {
		String allEle = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_action_deselect_all']";
		return new Element(By.xpath(allEle)).setDescription("Gallery: Deselect all buton");
	}
	
	public Element getCancelTextView() {
		String cancelEle = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_action_cancel']";
		return new Element(By.xpath(cancelEle)).setDescription("Gallery: Cancel selection button");
	}
	
	public Element getDeleteImage() {
		String deleteImg = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_gallery_delete']";
		return new Element(By.xpath(deleteImg)).setDescription("Gallery: Delete button");
	}
	
	public Element getShareImage() {
		String shareImg = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/img_gallery_share']";
		return new Element(By.xpath(shareImg)).setDescription("Gallery: Share button");
	}
	
	public Element getListVideoItemsPanel() {
		String lstVideo = "//android.support.v7.widget.RecyclerView[@resource-id='com.perimetersafe.kodaksmarthome:id/list_video_items']";
		return new Element(By.xpath(lstVideo)).setDescription("Gallery: Videos section");
	}
	
	public Element getNumberVideoSeletedTextView() {
		String videoSeleted = "//android.widget.TextView[@resource-id='com.perimetersafe.kodaksmarthome:id/text_selected_count']";
		return new Element(By.xpath(videoSeleted)).setDescription("Gallery: Number of selected Videos");
	}
	
	public List<WebElement> getListNumberVideoSeleted() {
		String videoSeleted = "//android.widget.ImageView[@resource-id='com.perimetersafe.kodaksmarthome:id/imgSelected']";
		List<WebElement> lstEle = getListVideoItemsPanel().findElements(By.xpath(videoSeleted));
		return lstEle;
	}
	
	public List<WebElement> getListExistedVideo(){
		String exitedVideo = "//android.widget.RelativeLayout[@resource-id='com.perimetersafe.kodaksmarthome:id/rootView']";
		return getListVideoItemsPanel().findElements(By.xpath(exitedVideo));
	}
	
	public Element getEmptyVideoIcon() {
		String emptyVideoIcon = "//android.widget.RelativeLayout[@resource-id='com.perimetersafe.kodaksmarthome:id/layout_empty_gallery']";
		return new Element(By.xpath(emptyVideoIcon)).setDescription("Gallery: Icon show when ther's video");
	}
	
	public Element getDeleteButton() {
		String deleteBtn = "//android.widget.Button[@resource-id='android:id/button1']";
		return new Element(By.xpath(deleteBtn)).setDescription("Gallery: Delete button");
	}
	
	public boolean verifyListMotionVideo() {
		return getListVideoItemsPanel().getWebElement() != null;
	}
	
	public boolean verifyNumberVideoSeleted(int expected) {
		 return getListNumberVideoSeleted().size() == expected && getNumberVideoSeletedTextView().getText().contains(expected+"");
	}
	
	public boolean verifyEmptyMotionVideo() {
		return getEmptyVideoIcon().getWebElement() != null;
	}
	
	public void deleteAllMotionVideo() {
		getSelectTextView().click();
		getSelectAllTextView().click();
		getDeleteImage().click();
		getDeleteButton().click();
		getCancelTextView().click();
	}
	
	public void shareMotionVideo() {
//		getSelectTextView().click();
//		selectMotionClip();
		getShareImage().click();
		getGmailApp().click();
	}
	
	public int selectMotionClip() {
		getSelectTextView().click();
		int numVideo = getListExistedVideo().size();
		if (numVideo > 0) {
			for(WebElement ele : getListExistedVideo()) {
				ele.click();
			}
		}
		return numVideo;
	}
	
	public void clickCancelSelected() {
		getCancelTextView().click();
	}
}
