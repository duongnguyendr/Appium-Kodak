package com.cinatic.object;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.log.Log;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class Terminal {
	private String response = "";
	private String teratermLog;
	private SerialPort serialPort;

	public Terminal(String portName) throws SerialPortException {
		clearTeratermLog();
		serialPort = new SerialPort(portName);
		serialPort.openPort();
		serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
		serialPort.addEventListener(new SerialPortEventListener() {
			public void serialEvent(SerialPortEvent serialPortEvent) {
				try {
					response = StringHelper
							.cleanInvalidCharacters(serialPort.readString(serialPortEvent.getEventValue()));
					teratermLog += response;
				} catch (SerialPortException e) {
					Log.error(e.getMessage());
				}
			}
		});
	}

	public Terminal(String portName, boolean printLog) throws SerialPortException {
		clearTeratermLog();
		serialPort = new SerialPort(portName);
		serialPort.openPort();
		serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
		serialPort.addEventListener(new SerialPortEventListener() {
			public void serialEvent(SerialPortEvent serialPortEvent) {
				try {
					response = StringHelper
							.cleanInvalidCharacters(serialPort.readString(serialPortEvent.getEventValue()));
					teratermLog += response;
					if (printLog) {
						if (!response.trim().equals(""))
							System.out.println("Camera_Log ==> " + response);
					}
				} catch (SerialPortException e) {
					Log.error(e.getMessage());
				}
			}
		});
	}

	public void sendCommand(String command) throws SerialPortException {
		Log.info(String.format(this.getClass() + ": Sending \'%s\' to \'%s\'",command,serialPort));
		serialPort.writeString("\r " + command + " \r");
		
	}

	public boolean sendCommand(String command, String expectedMessage, int timeout) throws SerialPortException {
		clearTeratermLog();
		serialPort.writeString("\r " + command + " \r");
		for (int i = 0; i < timeout; i++) {
			TimeHelper.sleep(1000);
			if (getTeratermLog().contains(expectedMessage)) {
				Log.info(String.format(this.getClass() + ": Sending \'%s\' to \'%s\' sucess", command, serialPort));
				return true;
			}
		}
		Log.info(String.format((this.getClass() + ": Sending \'%s\' to \'%s\' fail: response does not contain %s"),command,serialPort,expectedMessage));
		return false;
	}

	public void closePort() throws SerialPortException {
		serialPort.closePort();
	}

	public String getTeratermLog() {
		TimeHelper.sleep(10000);
		return teratermLog;
	}

	public void clearTeratermLog() {
		teratermLog = "";
	}
}
