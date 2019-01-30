package com.cinatic;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialHelper {
	public static String response = "";
	private static SerialPort serialPort;
	private static String portName = "";
	
	public static String sendCommand(String portName, String command, String expectedMessage, int timeout) {
		response = "";
		if (serialPort == null)
			serialPort = new SerialPort(portName);
		try {
			openPort();
			serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			serialPort.writeString(command + " \r");			
			serialPort.addEventListener(new SerialPortEventListener() {
				public void serialEvent(SerialPortEvent serialPortEvent) {
					try {			
						String temp = cleanInvalidCharacters(serialPort.readString(serialPortEvent.getEventValue()));
						System.out.println(temp);
						response += temp;						
					} catch (SerialPortException e) {
						System.out.println(e.getMessage());
					}					
				}
			});
		} catch (SerialPortException e) {
			System.out.println(e.getMessage());
		}

		for (int i = 0; i < timeout; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {				
				System.out.println(e.getMessage());				
			}
			if (response.contains(expectedMessage)) {
				closePort();
				break;
			}
		}			
		return response;
	}	
	
	private static void openPort() {
		try {
			serialPort.openPort();
		} catch (SerialPortException e) {

		}
	}

	private static void closePort() {
		if (serialPort.isOpened()) {
			try {
				serialPort.closePort();
			} catch (SerialPortException e) {

			}
		}
	}
	
	private static String cleanInvalidCharacters(String in) {
		StringBuilder out = new StringBuilder();
		char current;
		if (in == null || ("".equals(in))) {
			return "";
		}
		for (int i = 0; i < in.length(); i++) {
			current = in.charAt(i);
			if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF)) || ((current >= 0xE000) && (current <= 0xFFFD)) || ((current >= 0x10000) && (current <= 0x10FFFF))) {
				out.append(current);
			}

		}
		return out.toString().replaceAll("\\s", " ");
	}
	
	public static String setupCamera(String udid, String comPort, String routerName, String routerPass, String api_token) throws InterruptedException{
		String cameraModel = udid.substring(2, 6);
		
		if(cameraModel.equals("0066")){
			SerialHelper.sendCommand(comPort, "cd /mnt/cache/autotest/", "", 30);
			SerialHelper.sendCommand(comPort, "./install.sh", "", 30);			
			SerialHelper.sendCommand(comPort, String.format("lua autotest_setup.lua '%s' '%s' '%s'", routerName, routerPass, api_token), "", 30);
		}
		else
		{
			SerialHelper.sendCommand(comPort, "root\n", "", 30);
			SerialHelper.sendCommand(comPort, String.format("lua /usr/local/bin/autotest/autotest_setup.lua '%s' '%s' '%s'", routerName, routerPass, api_token), "", 30);
		}

		String result = "";
		int count = 0;
		while (!result.contains(";PASS;") && !result.contains(";FAIL;") && count < 120) {
			result = SerialHelper.sendCommand(comPort, "cat /mnt/cache/autotest/setup_result", "", 30);
			if (result.contains(";PASS;")) {
				return "passed";				
			} else if (result.contains(";FAIL;")) {
				return "failed";				
			}
			count++;
			Thread.sleep(5 * 1000);
		}
		return "failed";
	}
}
