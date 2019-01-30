package com.cinatic.test.camera;

import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.object.Terminal;

import jssc.SerialPortException;

public class HardResetPairingStressTest {

	String comPort = "/dev/ttyUSB0";
	Terminal terminal;
	String unlockShell = "shell 0 LAnXh7fr7yB3JJEtKqkFBxN3jrEPS4sN";
	
	// commands and expected response
	String[] cmdPairFirst = {"pair","start_pairing_mode"};
	String[] cmdPairAfter = {"pair","Currently in AP mode"};
	String[] cmdReset = {"reset","SOC_EVT_SCAN_DONE"};
	String[] cmdReboot = {"reboot","INFO <network_mgr.c:157> Waiting for DNS..."};
	String[] cmdUnlockShell = {unlockShell,"<system_cmd.c:2480> OK"};
	
	int cmdTimeout = 10;	
	long resetTime; 		
	long then;	
	
	@BeforeClass
	public void openCOM() throws SerialPortException{
		boolean printComLog = false;
		terminal = new Terminal(comPort,printComLog);
	}
	
	@DataProvider(name = "Data")
	public Object[][] createData() {
		return new Object[][] { { "Test_01" }, { "Test_02" }, { "Test_03" }, { "Test_04" }, { "Test_05" },
								{ "Test_06" }, { "Test_07" }, { "Test_08" }, { "Test_09" }, { "Test_10" },
								{ "Test_11" }, { "Test_12" }, { "Test_13" }, { "Test_14" }, { "Test_15" },
								{ "Test_16" }, { "Test_17" }, { "Test_18" }, { "Test_19" }, { "Test_20" },
								};
	}	
	@Test(enabled = true, description = "Hard reset stress test", dataProvider = "Data")
	public void hardReset(String data) throws SerialPortException, InterruptedException{
		System.out.println(String.format("\n---------------- %s ------------------",data));
		terminal.clearTeratermLog();
		
		// just send to be sure shell is unlocked
		sendCmd(cmdUnlockShell);
		
		// reset factory the camera
		long now = sendCmd(cmdReset).getTime();
		resetTime = now;
		
		// Wait 90s for reset to complete then unlock shell		
		long wait = 90;
		System.out.println(String.format(" !!! %s - Wait %s second then send \"pair\" command", (new Date()).toString(),wait));
		Thread.sleep(wait * 1000);
		sendCmd(cmdUnlockShell);
			
		// pair camera
		this.then = sendCmd(cmdPairFirst).getTime();
			
		// Reboot camera
		System.out.println(String.format(" !!! %s - Wait 3 second then reboot the camera", (new Date()).toString()));
		Thread.sleep(3000);
		sendCmd(cmdReboot);
	}
	
	@AfterClass
	public void closeCom() throws SerialPortException{
		terminal.closePort();
	}
	
	@AfterMethod
	public void showSumary() {
		Date now = new Date(this.resetTime);
		Date then = new Date(this.then);
		System.out.println(String.format(" !!! Started reset at: %s", now.toString()));
		System.out.println(String.format(" !!!          Pair at: %s", then.toString()));
		System.out.println(String.format(" !!!     Testing time: %d seconds", (this.then-this.resetTime)/1000));

	}
	
	public Date sendCmd(String[] command) {
		Date now = new Date();
		String log = String.format(" !!! %s - Sending command \"%s\" \n",now.toString() ,command[0] );
		try {
			terminal.sendCommand(command[0],command[1],10);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(String.format(" ### Failed to send command \"%s\"\n",command[0]));
			System.out.print(log);
			System.out.println(e.getMessage());
			return new Date(0);
		}
		
		System.out.print(log);
		return now;
	}
}
