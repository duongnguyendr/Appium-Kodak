package android.kodak.testcases.checklist;

import java.text.ParseException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.LucyApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.object.Device;

import android.kodak.base.TestBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageStreamView;
import jssc.SerialPortException;

public class TM_007_RecordVideo extends TestBase {
	private String username;
	private String password;
	private Device device;	
	private String timeBeforeRecordVideo;

	@BeforeMethod
	public void Precondition() throws SerialPortException {
		this.username = c_username;
		this.password = c_password;

		LucyApiHelper api;
		if (c_server.equals("production")) {
			api = new LucyApiHelper(c_server, c_username);
			api.userLogin(c_username, c_password);
			api.getDevices();
			device = api.getDeviceByDeviceId(c_deviceid);
		}
	}

	@Test(priority = 1, description = "TC001: Verify that user can record video successfully")
	public void TC001_RecordVideo() throws ParseException {
		PageGetStart page = new PageGetStart();
		PageDashboard devicePage = page.checkAndSignin(username, password);
		PageStreamView viewDevicePage = devicePage.selectDeviceToView(device.getName());
		viewDevicePage.getStreamMode(60);				
		timeBeforeRecordVideo = StringHelper.getCurrentDateTime("MMM dd yyyy hh:mm:ss a");
		viewDevicePage.startRecordVideo(30);
		viewDevicePage.stopRecordVideo();		
		viewDevicePage.exitToDashboard().openVideoGalleryTab();		
		String videoGalleryTime = devicePage.getFirstVieoGalleryInfo();		
		long duration = StringHelper.getDuration("MMM dd yyyy hh:mm:ss a", timeBeforeRecordVideo, videoGalleryTime);
		Assert.assertEquals(duration > 0, true);
	}
}
