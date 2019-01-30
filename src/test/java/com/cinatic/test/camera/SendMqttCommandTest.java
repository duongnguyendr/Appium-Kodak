package com.cinatic.test.camera;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.CommandHelper;
import com.cinatic.FileHelper;
import com.cinatic.LucyApiHelper;
import com.cinatic.MQTTHelper;
import com.cinatic.StringHelper;
import com.cinatic.constant.TestConstant;
import com.cinatic.log.Log;
import com.cinatic.object.Camera;
import com.cinatic.object.MqttObject;;

public class SendMqttCommandTest {
	
	private static final String SVR_MQTT_KODAK_HK = "ssl://mqtt-t01-r1.perimetersafe.com:8894";
	private static final String SVR_MQTT_KODAK_US = "ssl://mqtt-t01-r2.perimetersafe.com:8894";
	private static final String SVR_MQTT_KODAK_EU = "ssl://mqtt-t01-r3.perimetersafe.com:8894";
	private static final String SVR_MQTT_KODAK_ST = "ssl://mqtt-t01-st.perimetersafe.com:8894";
	
//	private String region="ST";
//	private String username="thachst";
//	private String password="123123Aa";
//	private File commandFile = new File("C520_commands.txt");
	
	private int loopCount = 1;
	private String reportFolder = StringHelper.getSystemPath() + File.separatorChar + "mqtt_command_test_result";
	private String txtMqttCommandSendResult = reportFolder + File.separatorChar + "Mqtt_command_result.txt";
	private String htmlMqttCommandSendReportTemplate = "mqtt_command_result_template.html";
	
	@BeforeClass
	public void cleanReportFolder() {
		try {
			File f = new File(reportFolder);
			if (!f.exists()) {
				f.mkdir();
			}
			else {
				FileHelper.clearFolder(reportFolder);
			}
		} catch (Exception e) {
		}
	}
	
	@Parameters({"username","password","region","commandFile"})
	@Test
	public void sendCommandViaMQTT(String username, String password, String region, String commandFile) throws Exception {
		
		// choose api server and mqtt broker
		String broker="";
		

		//login and get MQTT topic first
		LucyApiHelper lucyApiHelper = new LucyApiHelper(region,username);
		lucyApiHelper.userLogin(username, password);
		
		//
		String apiSvr = lucyApiHelper.getApiUrlByServerName(region, username);
		switch (apiSvr) {
		case TestConstant.kodak_hk_URI:
			broker = SVR_MQTT_KODAK_HK;
			break;
		case TestConstant.kodak_us_URI:
			broker = SVR_MQTT_KODAK_US;
			break;
		case TestConstant.kodak_eu_URI:
			broker = SVR_MQTT_KODAK_EU;
			break;
		case TestConstant.STAGING_GLOBAL_SERVER:
			broker = SVR_MQTT_KODAK_ST;
			break;
		default:
			break;
		}
		System.out.println(broker);
		
		// connect to api server and get MQTT channel
		CommandHelper command = new CommandHelper(apiSvr);
		command.authenticationToken(username, password);
		command.AppRegister();
		command.getDevices();
		MqttObject mqttObject = command.getMqttChanel();
		Camera cam = command.getLstCamera().get(0);
		String mqttTopic = cam.getMqtt_topic();
		MQTTHelper mqttHelper = new MQTTHelper(broker, mqttObject.getAccessKey(), mqttObject.getSecretKey(), mqttObject.getSubscribeTopic(),
				mqttObject.getClientId(), mqttTopic);

		Log.info("======Begin execute command======");
		int currentRun = 0;
		
		// open file which contains list of commands.
		FileReader fileReader = new FileReader(commandFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		String udid = cam.getRegistration_id();

		for (int i = 0; i < loopCount; i++) {
			currentRun++;
			int totalpassed = 0;
			int totalfailed = 0;
			String strLine ="";
			while (strLine != null) {
				strLine = bufferedReader.readLine();
				if (strLine != null) {
					Log.info(String.format("\n!!! Commands send & expect: %s",strLine));
					String strCommand = strLine.split(",")[0].trim();
					String strExpected = "";
					strExpected = strLine.split(",")[1].trim();
					String starttime = StringHelper.getCurrentDateTime();
					mqttHelper.runClient(strCommand);
					String mqtt_message = mqttHelper.output;
					String endtime = StringHelper.getCurrentDateTime();
					if (mqtt_message.toString().contains(strExpected)) {
						appendResult(strCommand, mqtt_message, "passed", starttime,
								StringHelper.getDuration(starttime, endtime), strExpected, mqtt_message);
						totalpassed++;
						Log.info(String.format("%s [%s]", strLine, udid));
						Log.info(String.format("%s [%s]", mqtt_message, udid));
					} else {
						appendResult(strCommand, mqtt_message, "failed", starttime,
								StringHelper.getDuration(starttime, endtime), strExpected, mqtt_message);
						totalfailed++;
						Log.info(String.format("%s [%s]", strLine, udid));
						Log.info(String.format("%s [%s]", mqtt_message, udid));
					}

				}
			}
		}
		bufferedReader.close();
		exportResult(commandFile);
		Log.info("======End execute command======");
	}
	
	private void appendResult(String action, String actual_result, String passed_failed, String starttime,
			long duration, String expected, String reason) {
		String temp = String.format("%s###%s###%s###%s###%s###%s###%s\n", action, actual_result, passed_failed,
				starttime, duration, expected, reason);
		Log.info(temp);
		try {
			FileHelper.writeFile(txtMqttCommandSendResult, temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exportResult(String fileName) {
		try {
			String content_results = FileHelper.readFile(txtMqttCommandSendResult);
			String content_template = FileHelper.readFile(htmlMqttCommandSendReportTemplate);
			String[] array_results = content_results.split("\n");

			String html = "";
			for (int i = 0; i < array_results.length; i++) {
				String result = array_results[i];
				if (!result.trim().equals("")) {
					int stt = i + 1;
					String tcommand = StringHelper.stringToHTMLString(result.split("###")[0]);
					String tsummary = StringHelper.stringToHTMLString(result.split("###")[1]);
					String tresult = StringHelper.stringToHTMLString(result.split("###")[2]);
					String tStartTime = StringHelper.stringToHTMLString(result.split("###")[3]);
					String tDuration = StringHelper.stringToHTMLString(result.split("###")[4]);
					String texpected = StringHelper.stringToHTMLString(result.split("###")[5]);
					String tclipboard = "<a href='#' onclick='setClipboard(this)'>Copy response to clipboard</a>";
					String tactual_result = "";
					try {
						tactual_result = StringHelper.stringToHTMLString(result.split("###")[6]);
					} catch (Exception ex) {

					}

					html += "<tr>\n";
					html += "<td>";
					html += stt;
					html += "</td>\n";

					html += "<td>";
					html += tcommand;
					html += "</td>\n";

					html += "<td>";
					html += tresult;
					html += "</td>\n";

					html += "<td>";
					html += tStartTime;
					html += "</td>\n";

					html += "<td>";
					html += tDuration;
					html += "</td>\n";

					html += "<td>";
					html += texpected;
					html += "</td>\n";

					html += "<td>";
					html += tsummary;
					html += "</td>\n";

					html += "<td>";
					html += tclipboard;
					html += "</td>\n";

					html += "<td>";
					html += tactual_result;
					html += "</td>\n";
					html += "</tr>\n";
				}
			}

			int index = content_template.indexOf("<!--Details-->");
			content_template = content_template.substring(0, index) + html
					+ content_template.substring(index, content_template.length());

			String url = reportFolder + File.separatorChar + "mqtt_command_report" + ".html";
			FileHelper.writeFile(url, content_template);
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
	}
}
