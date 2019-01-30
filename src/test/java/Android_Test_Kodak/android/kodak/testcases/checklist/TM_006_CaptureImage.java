package Android_Test_Kodak.android.kodak.testcases.checklist;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.LucyApiHelper;
import com.cinatic.object.Device;

import Android_Test_Kodak.android.kodak.base.TestBase;
import Android_Test_Kodak.android.kodak.object.PageDashboard;
import Android_Test_Kodak.android.kodak.object.PageGetStart;
import Android_Test_Kodak.android.kodak.object.PageStreamView;
import jssc.SerialPortException;

public class TM_006_CaptureImage extends TestBase {
	private String username;
	private String password;
	private Device device;	

	@BeforeMethod
	public void Precondition() throws SerialPortException {
		this.username = c_username;
		this.password = c_password;

		LucyApiHelper api;
		api = new LucyApiHelper(c_server, c_username);
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}

	@Test(priority = 1, description = "TC001: Verify that user can capture image successfully")
	public void TC001_CaptureImage() {
		PageGetStart page = new PageGetStart();
		PageDashboard devicePage = page.checkAndSignin(username, password);		
		PageStreamView viewDevicePage = devicePage.selectDeviceToView(device.getName());
		viewDevicePage.getStreamMode(60);
				
		viewDevicePage.captureImage();		
	}
}
