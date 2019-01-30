package Android_Test_Kodak.android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.LucyApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import Android_Test_Kodak.android.kodak.base.TestBase;
import Android_Test_Kodak.android.kodak.object.PageDashboard;
import Android_Test_Kodak.android.kodak.object.PageGetStart;
import Android_Test_Kodak.android.kodak.object.PageStreamView;
import jssc.SerialPortException;

public class TC_18_VerifyHumidity extends TestBase{
	private Device device;
	private Terminal terminal;
	
	@BeforeMethod
	public void preCondition() throws SerialPortException {
		LucyApiHelper api;
		api = new LucyApiHelper(c_server, c_username);
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		terminal = new Terminal(c_comport);
	}
	
	@Test(priority = 1, description = "Verify humidity same on App and server")
	public void verifyHumiditySameOnAppAndServer() throws SerialPortException {
		PageGetStart pageGetStart = new PageGetStart();
		PageDashboard pageDashboard = pageGetStart.checkAndSignin(c_username, c_password);
		PageStreamView viewDevicePage = pageDashboard.selectDeviceToView(device.getName());
		viewDevicePage.getStreamMode(60);
		// Get humidity in server
		terminal.clearTeratermLog();
		terminal.sendCommand("atecmd get_humi");
		String camLog = terminal.getTeratermLog();
		String partern = "Humidity = %s percent";
		// Get humidity in app
		String humidity = StringHelper.getNumberInString(viewDevicePage.getHumidityInApp()).get(0);
		Assert.assertTrue(camLog.contains(String.format(partern, humidity)),
				String.format("Humidity should sync between app and server.App: %s, Server: %s.",
						humidity, StringHelper.getStringByString(camLog, "Humidity", "percent", false)));
	}

	@AfterMethod
	public void tearDown() throws SerialPortException {
		terminal.closePort();
	}
}
