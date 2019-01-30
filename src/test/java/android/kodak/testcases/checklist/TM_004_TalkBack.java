package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.LucyApiHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.base.TestBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageStreamView;
import jssc.SerialPortException;

public class TM_004_TalkBack extends TestBase {
	private String username;
	private String password;
	private Device device;
	private Terminal terminal;

	@BeforeMethod
	public void Precondition() throws SerialPortException {
		this.username = c_username;
		this.password = c_password;

		LucyApiHelper api;
		api = new LucyApiHelper(c_server, c_username);
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		terminal = new Terminal(c_comport);
	}

	@Test(priority = 1, description = "TC001: Verify that user can talkback successfully")
	public void TC001_TalkBack() {
		TimeHelper.sleep(10000);
		PageGetStart pageGetStart = new PageGetStart();
		PageDashboard devicePage = pageGetStart.checkAndSignin(username, password);
		
		
		PageStreamView viewDevicePage = devicePage.selectDeviceToView(device.getName());
		viewDevicePage.getStreamMode(60);
		
		terminal.clearTeratermLog();
		viewDevicePage.enableTalkBack();		
		String camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("P2P Talkback state 1"), true);		
		
		terminal.clearTeratermLog();
		viewDevicePage.disableTalkBack();
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("P2P Talkback state 0"), true);
	}
	
	@AfterClass
	public void cleanup() throws SerialPortException{
		terminal.closePort();
	}
}
