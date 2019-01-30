package com.cinatic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.cinatic.constant.TestConstant;
import com.cinatic.log.Log;
import com.cinatic.log.TeraLog;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class TerminalHelper {
	public static String response = "";
	private static SerialPort serialPort;
	private static String portName = "";

	public static String sendCommand(String portName, String command, String expectedMessage, int timeout) {
		response = "";
		if (serialPort == null)
			serialPort = new SerialPort(portName);
		try {
			openPort();
			serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			serialPort.writeString("\r " + command + " \r");
			serialPort.addEventListener(new SerialPortEventListener() {
				public void serialEvent(SerialPortEvent serialPortEvent) {
					try {
						response += StringHelper
								.cleanInvalidCharacters(serialPort.readString(serialPortEvent.getEventValue()));
					} catch (SerialPortException e) {
						Log.error(e.getMessage());
					}
				}
			});
		} catch (SerialPortException ex) {
			Log.error(ex.getMessage());
		}

		for (int i = 0; i < timeout; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Log.error(e.getMessage());
			}
			if (response.contains(expectedMessage)) {
				closePort();
				break;
			}
		}
		Log.info(TerminalHelper.response);
		return TerminalHelper.response;
	}

	public static String sendCommand(String portName, String command, String expectedMessage) {
		return sendCommand(portName, command, expectedMessage, TestConstant.shortTime);
	}

	public static String sendCommand(String command, String expectedMessage) {
		response = "";
		if (portName.equals("")) {
			String[] ports = SerialPortList.getPortNames();
			if (ports.length > 0) {
				for (String port : ports) {
					portName = port;
					Log.info(portName);
					break;
				}
			}
		}
		return sendCommand(portName, command, expectedMessage);
	}

	public static String sendCommand(String command) {
		response = "";
		if (portName.equals("")) {
			String[] ports = SerialPortList.getPortNames();
			if (ports.length > 0) {
				for (String port : ports) {
					portName = port;
					Log.info(portName);
					break;
				}
			}
		}
		return sendCommand(portName, command, "");
	}

	public static void openPort() {
		try {
			serialPort.openPort();
		} catch (SerialPortException e) {

		}
	}

	public static void closePort() {
		if (serialPort.isOpened()) {
			try {
				serialPort.closePort();
			} catch (SerialPortException e) {

			}
		}
	}

	public static String execShell(String cmd) throws IOException, InterruptedException {
		Log.info(cmd);
		Process p = Runtime.getRuntime().exec(new String[] { "sh", "-c", cmd });
		InputStream is = p.getErrorStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String output = "";
		String line;
		while ((line = reader.readLine()) != null) {
			Log.info(line);
			output += line;
		}

		InputStream iss = p.getInputStream();
		BufferedReader readerr = new BufferedReader(new InputStreamReader(iss));
		while ((line = readerr.readLine()) != null) {
			Log.info(line);
			output += line;
		}
		p.waitFor();
		p.destroy();
		Log.info(output);
		return output;
	}

	public static String execCmd(String cmd) throws IOException, InterruptedException {
		Log.info(cmd);
		Process p = Runtime.getRuntime().exec("cmd /c " + cmd);
		InputStream is = p.getErrorStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String output = "";
		String line;
		while ((line = reader.readLine()) != null) {
			Log.info(line);
			output += line;
		}

		InputStream iss = p.getInputStream();
		BufferedReader readerr = new BufferedReader(new InputStreamReader(iss));
		while ((line = readerr.readLine()) != null) {
			Log.info(line);
			output += line;
		}
		p.waitFor();
		p.destroy();
		Log.info(output);
		return output;
	}

	public static String execCmd(String cmd, String sig) throws IOException, InterruptedException {
		Log.info(cmd);
		Process p = Runtime.getRuntime().exec("cmd /c " + cmd);
		p.waitFor();
		InputStream is = p.getErrorStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String output = "";
		String line;
		while ((line = reader.readLine()) != null) {
			output += line + sig;
		}

		is = p.getInputStream();
		reader = new BufferedReader(new InputStreamReader(is));
		while ((line = reader.readLine()) != null) {
			output += line + sig;
		}

		p.destroy();
		Log.info(output);
		return output;
	}

	public static String execBash(String cmd) throws IOException, InterruptedException {
		Log.info(cmd);
		Process p = Runtime.getRuntime().exec("/bin/bash -c " + cmd);
		p.waitFor();
		InputStream is = p.getErrorStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String output = "";
		String line;
		while ((line = reader.readLine()) != null) {
			output += line;
		}

		is = p.getInputStream();
		reader = new BufferedReader(new InputStreamReader(is));
		while ((line = reader.readLine()) != null) {
			output += line;
		}

		p.destroy();
		Log.info(output);
		return output;
	}
	
	public static String exeBashCommand(String cmd) throws IOException, InterruptedException{
		Log.info(cmd);
		Process p = Runtime.getRuntime().exec(cmd);
		p.waitFor();
		InputStream is = p.getErrorStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String output = "";
		String line;
		while ((line = reader.readLine()) != null) {
			output += line;
		}

		is = p.getInputStream();
		reader = new BufferedReader(new InputStreamReader(is));
		while ((line = reader.readLine()) != null) {
			output += line;
		}
		p.destroy();
		Log.info(output);
		return output;
	}

	public static String tlsCmd(String psk, String ipcam, String command) throws IOException, InterruptedException {
		String cmd = "C:/Automation/resources/mbedtls-development/programs/ssl/ssl_client2.exe psk=" + psk
				+ " server_addr=" + ipcam + " data_sent=\"" + command + "\"";
		Log.info(cmd);
		String output = execCmd(cmd);
		Pattern p = Pattern.compile("<p>.*<\\\\p>");
		java.util.regex.Matcher m = p.matcher(output);
		String message = "";
		if (m.find()) {
			message = m.group();
		}

		if (command.contains("command=move_") || command.contains("command=melody"))
			Thread.sleep(5000);
		return message;
	}

	public static String tlsCmd(String psk, String identity, String ipcam, String command)
			throws IOException, InterruptedException {
		String cmd = "C:/Automation/resources/mbedtls-development/programs/ssl/ssl_client2.exe psk_identity=" + identity
				+ " psk=" + psk + " server_addr=" + ipcam + " data_sent=\"" + command + "\"";
		Log.info(cmd);
		String output = execCmd(cmd);
		Pattern p = Pattern.compile("<p>.*<\\\\p>");
		java.util.regex.Matcher m = p.matcher(output);
		String message = "";
		if (m.find()) {
			message = m.group();
		}

		if (command.contains("command=move_") || command.contains("command=melody"))
			Thread.sleep(5000);
		return message;
	}

	public static String ffmpegCmd(String fileName, String rtmpUrl) throws IOException, InterruptedException {
		String path = "test-output";
		if (System.getProperty("suiteOutput") != null) {
			path = System.getProperty("suiteOutput");
		}
		String fullPath = "\"" + path + "\\html\\" + fileName + ".flv" + "\"";
		Log.info(fullPath);
		TerminalHelper.execCmd("del /f " + fullPath);
		String message = TerminalHelper.execCmd(String.format(
				"C:/Automation/resources/ffmpeg.exe -t 10 -y -i %s -vcodec copy -acodec copy -f flv " + fullPath,
				rtmpUrl));
		return message;
	}

	public static String ffmpegCmd1(String fileName, String rtmpUrl) throws IOException, InterruptedException {
		String path = "test-output";
		if (System.getProperty("suiteOutput") != null) {
			path = System.getProperty("suiteOutput");
		}
		String fullPath = "\"" + path + "\\html\\" + fileName + ".flv" + "\"";
		Log.info(fullPath);
		TerminalHelper.execCmd("del /f " + fullPath);
		String message = TerminalHelper.execCmd(String.format(
				"C:/Automation/resources/ffmpeg1.exe -t 10 -y -i %s -vcodec copy -acodec copy -f flv " + fullPath,
				rtmpUrl));
		return message;
	}

	public static String p2pCmd(String username, String password, String udid, int millisecond)
			throws IOException, InterruptedException {
		String path = "test-output";
		if (System.getProperty("suiteOutput") != null) {
			path = System.getProperty("suiteOutput");
		}

		String fullPath = path + "\\html\\test.flv";
		Log.info(fullPath);
		TerminalHelper.execCmd("del /f " + fullPath);
		String message = TerminalHelper.execCmd(String.format(
				"C:/Automation/resources/p2p_test/p2p_viewer_pc.exe \"api.hubble.in\" \"%s\" \"%s\" \"%s\" \"%s\" \"%s\" \"%s\"",
				username, password, 1, udid, millisecond, fullPath));
		return message;
	}

	public static String getLogCat(String deviceId, String grep) throws IOException, InterruptedException {
		ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(1);
		Runnable workerResource = new Runnable() {
			public void run() {
				try {
					Thread.sleep(10 * 1000);
					execCmd("taskkill /f /im grep.exe");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		scheduleThreadPool.schedule(workerResource, 0, TimeUnit.SECONDS);
		return TerminalHelper.execCmd(String.format(
				"adb -s %s logcat -v time -d | grep \"%s\"", deviceId, grep), "\n");
	}

	public static String getLogCat(String deviceId, String grep1, String grep2)
			throws IOException, InterruptedException {
		ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(1);
		Runnable workerResource = new Runnable() {
			public void run() {
				try {
					Thread.sleep(10 * 1000);
					execCmd("taskkill /f /im grep.exe");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		scheduleThreadPool.schedule(workerResource, 0, TimeUnit.SECONDS);
		return TerminalHelper.execCmd(String.format("adb -s %s logcat -v time -d | " + TestConstant.resourcePath
				+ "\\grep.exe \"%s\" | " + TestConstant.resourcePath + "\\grep.exe \"%s\"", deviceId, grep1, grep2),
				"\n");
	}

	public static String getLogCat(String deviceId) throws IOException, InterruptedException {
		ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(1);
		Runnable workerResource = new Runnable() {
			public void run() {
				try {
					Thread.sleep(10 * 1000);
					execCmd("taskkill /f /im grep.exe");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		scheduleThreadPool.schedule(workerResource, 0, TimeUnit.SECONDS);
		String adbCommands = "adb -s %s logcat -v time -d *:s Lucy:v mbp:v System.err:v AndroidRuntime:v ActivityManager:s FFMpegPlayer-JNI:v FFMediaPlayer-native:v FFMpeg:v ffmpeg_onLoad:v stun_onLoad:v chk_pkg:v FFMovieView:v libffmpeg:v DEBUG:v libc:s dalvikvm:s art:s FFMpegMediaPlayer-java:v FFMpegPacketQueue:v FFDecoder:v FFAudioDecoder:v FFVideoDecoder:v FFMpegVideoRecorder:v FFMpegAudioRecorder:v Retrofit:v Volley:v OkHttp:v AutoConfigureCamerasNew:v rmc-native:v librmc-client:v stun-client-jni:v stun-client-java:v librmc:v RmcChannel-java:v P2pClient:v CrashAnrDetector:v P2pManager:v P2pService:v PushActivity:v FFPlayer-JNI:v FFPlayer-java:v FFPlayer-native:v P2PAVPlayer:v ConnectToNetworkTask:v AppApplication:v DevicesFragment:v DevicesPresenter:v DeviceInnerFragment:v P2pAlarmReceiver:v MainActivity:v SelectWifiDialogFragment:v EventBus:v JWebClient:v CommandSession:v SessionManager:v LocalDiscoveryTask:v CommandCommunicator:v LocalDevice:v SignUpPhoneNumberFragment:v NetworkUtils:v AES:s AEE_AED:v tlspsk.c:v MqttService:v MqttPushService:v PushSession:v SecureDevice:v LocalDevice:v TalkbackSession:v PCMPlayer:v PCMRecorder:v VerificationCodeActivity:v chk_pkg:v DeviceSettingFragment:v DeviceSettingPresenter:v VideoDecoder:v ImageConverter-java:v ImageConverter-JNI:v AudioPanelFragment:v HttpRequestManagerImpl:v SplashActivity:v JPushService:v JPushBroadcastReceiver:v LucyJPushReceiver:v PushReceiver:v JIGUANG-JCore:s GcmIntentService:v M800SDK:v InitM800SDKTask:v SMACK:v a:v d:v e:v f:v g:v i:v k:v l:v MSME:v AppM800Service:v StreamManager:v VideoPlayFragment:v FA:s FA-SVC:s DeviceActivatedPresenter:v LoginActivity:v MainPresenter:v EventFragment:v DeviceInnerEventFragment:v NetworkStateChangeReceiver:v RegisterActivity:v RegisterPresenter:v SetupCameraTask:v Setup:v AnalyticsController:v FirebaseAnalyticsController:v PushNotifHandler:v VideoBrowserFragment:v VideoPlaybackFragment:v VideoBrowserUtils:v KeyStoreUtils:v MqttJobService:v";
		return TerminalHelper.execCmd(String.format(adbCommands, deviceId), "\n");
	}

	public static String getDeviceConsole(String grep1, String grep2) throws IOException, InterruptedException {
		ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(1);
		Runnable workerResource = new Runnable() {
			public void run() {
				try {
					Thread.sleep(10 * 1000);
					execShell("pkill grep");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		scheduleThreadPool.schedule(workerResource, 0, TimeUnit.SECONDS);
		return execShell(String.format(
				"grep -a '%s' /Users/auto/Repo/Hubble/testlog.txt | grep -a '%s' /Users/auto/Repo/Hubble/testlog.txt",
				grep1, grep2));
	}

	public static void exportDeviceConsole(String fullPath) throws IOException, InterruptedException {
		execShell(String.format("grep -a 'Hubble' /Users/auto/Repo/Hubble/testlog.txt >'%s'", fullPath));
	}

	public static void exportLogCat(String deviceId, String fullPath) throws IOException, InterruptedException {
		execCmd(String.format("adb -s %s logcat -v time -d >\"%s\"", deviceId, fullPath), "\n");
	}

	public static void exportLogCatLucy(String deviceId, String fullPath) throws IOException, InterruptedException {
		String adbCommands = "*:s Lucy:v mbp:v System.err:v AndroidRuntime:v ActivityManager:s FFMpegPlayer-JNI:v FFMediaPlayer-native:v FFMpeg:v ffmpeg_onLoad:v stun_onLoad:v chk_pkg:v FFMovieView:v libffmpeg:v DEBUG:v libc:s dalvikvm:s art:s FFMpegMediaPlayer-java:v FFMpegPacketQueue:v FFDecoder:v FFAudioDecoder:v FFVideoDecoder:v FFMpegVideoRecorder:v FFMpegAudioRecorder:v Retrofit:v Volley:v OkHttp:v AutoConfigureCamerasNew:v rmc-native:v librmc-client:v stun-client-jni:v stun-client-java:v librmc:v RmcChannel-java:v P2pClient:v CrashAnrDetector:v P2pManager:v P2pService:v PushActivity:v FFPlayer-JNI:v FFPlayer-java:v FFPlayer-native:v P2PAVPlayer:v ConnectToNetworkTask:v AppApplication:v DevicesFragment:v DevicesPresenter:v DeviceInnerFragment:v P2pAlarmReceiver:v MainActivity:v SelectWifiDialogFragment:v EventBus:v JWebClient:v CommandSession:v SessionManager:v LocalDiscoveryTask:v CommandCommunicator:v LocalDevice:v SignUpPhoneNumberFragment:v NetworkUtils:v AES:s AEE_AED:v tlspsk.c:v MqttService:v MqttPushService:v PushSession:v SecureDevice:v LocalDevice:v TalkbackSession:v PCMPlayer:v PCMRecorder:v VerificationCodeActivity:v chk_pkg:v DeviceSettingFragment:v DeviceSettingPresenter:v VideoDecoder:v ImageConverter-java:v ImageConverter-JNI:v AudioPanelFragment:v HttpRequestManagerImpl:v SplashActivity:v JPushService:v JPushBroadcastReceiver:v LucyJPushReceiver:v PushReceiver:v JIGUANG-JCore:s GcmIntentService:v M800SDK:v InitM800SDKTask:v SMACK:v a:v d:v e:v f:v g:v i:v k:v l:v MSME:v AppM800Service:v StreamManager:v VideoPlayFragment:v FA:s FA-SVC:s DeviceActivatedPresenter:v LoginActivity:v MainPresenter:v EventFragment:v DeviceInnerEventFragment:v NetworkStateChangeReceiver:v RegisterActivity:v RegisterPresenter:v SetupCameraTask:v Setup:v AnalyticsController:v FirebaseAnalyticsController:v PushNotifHandler:v VideoBrowserFragment:v VideoPlaybackFragment:v VideoBrowserUtils:v KeyStoreUtils:v MqttJobService:v";
		execBash(String.format("adb -s %s logcat -v time -d %s >\"%s\"", deviceId, adbCommands, fullPath));
	}
	
	public static void clearLogCat(String deviceId) throws IOException, InterruptedException {
		execBash(String.format("adb -s %s logcat -c", deviceId));
	}

	public static String getTime(String type, String message) {
		Pattern p = Pattern.compile("\\d+-\\d+ \\d+:\\d+:\\d+.\\d+.*" + type + "-PUSH");
		java.util.regex.Matcher m = p.matcher(message);
		String strLastLine = "";
		while (m.find()) {
			strLastLine = m.group();
		}
		Log.info(strLastLine);
		p = Pattern.compile("\\d+-\\d+ \\d+:\\d+:\\d+.\\d+");
		m = p.matcher(strLastLine);
		String strTime = "";
		while (m.find()) {
			strTime = m.group();
		}
		return strTime;
	}

	public static String getTime_REFRESH_EVENTS_BROADCAST(String message) {
		Pattern p = Pattern
				.compile("\\d+-\\d+ \\d+:\\d+:\\d+.\\d+.*GcmIntentService.*: Send broadcast REFRESH_EVENTS_BROADCAST");
		java.util.regex.Matcher m = p.matcher(message);
		String strLastLine = "";
		while (m.find()) {
			strLastLine = m.group();
		}
		Log.info(strLastLine);
		p = Pattern.compile("\\d+-\\d+ \\d+:\\d+:\\d+.\\d+");
		m = p.matcher(strLastLine);
		String strTime = "";
		while (m.find()) {
			strTime = m.group();
		}
		return strTime;
	}

	public static TeraLog convertToTeraLog(String message) {
		Pattern p = Pattern.compile("\\d+-\\d+ \\d+:\\d+:\\d+.*");
		java.util.regex.Matcher m = p.matcher(message);
		String strLastLine = "";
		while (m.find()) {
			strLastLine = m.group();
		}
		Log.info(strLastLine);
		p = Pattern.compile("\\d+-\\d+ \\d+:\\d+:\\d+");
		m = p.matcher(strLastLine);
		String strTime = "";
		while (m.find()) {
			strTime = m.group();
		}

		String content = strLastLine.substring(strLastLine.lastIndexOf(strTime));
		TeraLog o = new TeraLog(strTime.trim(), content.replace(strTime, "").trim());
		return o;
	}

	public static void killProcess(String processName) {
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec(String.format("cmd /c taskkill /f /im %s", processName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
