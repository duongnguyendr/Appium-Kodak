package android.kodak.testcases.checklist;

import org.testng.annotations.Test;

import com.cinatic.log.Log;
import com.cinatic.object.Terminal;

import android.kodak.base.TestBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageHomeSetting;
import jssc.SerialPortException;

public class TC_0_Enable_debug extends TestBase{

	@Test (priority = -10)
	public void enableShowDebugInfo() throws SerialPortException {
		PageGetStart pageGetStart = new PageGetStart();
		PageDashboard pageDashboard = pageGetStart.checkAndSignin(c_username, c_password);
		 
		PageHomeMenu pageHomeMenu = pageDashboard.gotoHomeMenuPage();
		pageHomeMenu.enableDebug();
		PageHomeSetting pageHomeSetting = pageHomeMenu.gotoHomeSetingPage();
		pageHomeSetting.enableShowDebugInfo();		
		pageHomeSetting.exitPage();
		
		// unlock camera shell
		try {
			Log.info("Unlock camera shell");
			Terminal t = new Terminal(c_comport);
			t.sendCommand("shell 0 LAnXh7fr7yB3JJEtKqkFBxN3jrEPS4sN");
			t.closePort();
		} catch (SerialPortException e) {
			Log.fatal(e.getMessage());
			throw e;
		}
	}

}
