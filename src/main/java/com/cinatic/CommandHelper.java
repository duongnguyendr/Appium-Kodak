package com.cinatic;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import com.cinatic.object.Camera;
import com.cinatic.object.CodeMessage;
import com.cinatic.object.MqttObject;
import com.cinatic.StringHelper;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class CommandHelper {
	private String url;
	private String udid;
	private String api_token;
	private HttpHelper http;
	private ArrayList<Camera> lstCamera;
	private String jsonFormat = "";
	private MqttObject mqttChanel;
	private String access_token;


	public CommandHelper(String url) {
		this.url = url;
		http = new HttpHelper();
		lstCamera = new ArrayList<Camera>();
		try {
			this.jsonFormat = FileHelper.readFile("json.tp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CodeMessage authenticationToken(String userName, String password) {
//		String[] output = http.httpPostRequest(url + "/v1/users/authenticate", "{\"login\":\"" + userName + "\",\"password\":\"" + password + "\"}");
//		String response = output[1];
//		try {
//			JsonObject object = Json.parse(response).asObject();
//			setApi_token(object.get("data").asObject().get("authentication_token").asString());
//			return new CodeMessage(1, "Login successfully", response);
//		} catch (Exception e) {
//			return new CodeMessage(0, "Login failed", response);
//		}
		String[] output = http.httpPostRequest(url + "/v1/users/authenticate",
				"{\"os\":\"3\",\"model\":\"unknown\",\"oauth_type\":0,\"is_refresh\":true,\"login\":\"" + userName
						+ "\",\"password\":\"" + password + "\"}");
		String response = output[1];
		try {
			JsonObject object = Json.parse(response).asObject();
			setAccess_token(object.get("data").asObject().get("access_token").asString());
			return new CodeMessage(1, "Login successfully", "summary", response);
		} catch (Exception e) {
			return new CodeMessage(0, "Login failed", "summary", response);
		}
	}

	public CodeMessage getOwner() {
		String response = http.httpGetRequest(url + "/v1/devices/own.json?api_key=" + getApi_token());
		try {
			JsonObject object = Json.parse(response).asObject();
			JsonArray items = object.get("data").asArray();
			for (JsonValue item : items) {
				String name = item.asObject().get("name").asString();
				String host_ssid = "";
				try {
					host_ssid = item.asObject().get("host_ssid").asString();
				} catch (Exception ex) {
				}
				String local_ip = item.asObject().get("device_location").asObject().get("local_ip").asString();
				String registration_id = "";
				try {
					registration_id = item.asObject().get("registration_id").asString();
				} catch (Exception e) {
				}

				String plan_id = "";

				try {
					plan_id = item.asObject().get("plan_id").asString();

				} catch (Exception e) {

				}

				String firmware_version = item.asObject().get("firmware_version").asString();
				boolean status = item.asObject().get("is_available").asBoolean();

				Camera o = new Camera();
				o.setName(name);
				o.setRegistration_id(registration_id);
				o.setPlan_id(plan_id);
				o.setHost_ssid(host_ssid);
				o.setLocal_ip(local_ip);
				o.setFirmware_version(firmware_version);
				if (status)
					o.setStatus("Online");
				else
					o.setStatus("Offline");
				lstCamera.add(o);
			}
			return new CodeMessage(1, "Get camera list successfully", response);
		} catch (Exception e) {
			return new CodeMessage(0, "Get camera list failed", response);
		}
	}

	public Camera getCamera() {
		String response = http.httpGetRequest(url + "/v1/devices/own.json?api_key=" + getApi_token());
		try {
			JsonObject object = Json.parse(response).asObject();
			JsonArray items = object.get("data").asArray();
			for (JsonValue item : items) {
				String name = item.asObject().get("name").asString();
				String host_ssid = "";
				try {
					host_ssid = item.asObject().get("host_ssid").asString();
				} catch (Exception ex) {
				}
				String local_ip = "";
				try {
					local_ip = item.asObject().get("device_location").asObject().get("local_ip").asString();
				} catch (Exception ex) {

				}
				String registration_id = "";
				try {
					registration_id = item.asObject().get("registration_id").asString();
				} catch (Exception e) {
				}

				String device_status = "";
				try {
					device_status = item.asObject().get("device_status").asObject().get("device").asObject().get("status").asString();
				} catch (Exception Ex) {
				}

				String plan_id = "";
				try {
					plan_id = item.asObject().get("plan_id").asString();
				} catch (Exception e) {

				}

				String firmware_version = item.asObject().get("firmware_version").asString();
				boolean status = item.asObject().get("is_available").asBoolean();

				if (registration_id.equalsIgnoreCase(udid)) {
					Camera o = new Camera();
					o.setName(name);
					o.setRegistration_id(registration_id);
					o.setPlan_id(plan_id);
					o.setHost_ssid(host_ssid);
					o.setLocal_ip(local_ip);
					o.setFirmware_version(firmware_version);
					o.setDeviceStatus(device_status);
					if (status)
						o.setStatus("Online");
					else
						o.setStatus("Offline");
					return o;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}	
	
	public CodeMessage send_command(String command, String expected) {
		String starttime = StringHelper.getCurrentDateTime();
		String[] output = http.httpPostRequest(url + "/v1/devices/" + getUdid() + "/send_command.json?api_key=" + getApi_token(), "{\"action\":\"command\",\"command\":\"action=command&command=" + command + "\"}");
		String endtime = StringHelper.getCurrentDateTime();
		long duration = 0;
		try {
			duration = StringHelper.getDuration(starttime, endtime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String response = output[1];
		try {
			JsonObject object = Json.parse(response).asObject();
			int status = object.get("status").asInt();
			String message = object.get("message").asString();
			String body = object.get("data").asObject().get("device_response").asObject().get("body").asString();
			int device_responde_code = object.get("data").asObject().get("device_response").asObject().get("device_response_code").asInt();

			if (status != 200)
				return new CodeMessage(0, String.format("Send command '%s' failed", command), response, duration, starttime);
			if (!message.equals("Success!"))
				return new CodeMessage(0, String.format("Send command '%s' failed", command), response, duration, starttime);
			if (!body.contains(expected))
				return new CodeMessage(0, String.format("Send command '%s' failed", command), response, duration, starttime);

			return new CodeMessage(1, String.format("Send command '%s' successfully", command), response, duration, starttime);
		} catch (Exception e) {
			return new CodeMessage(0, String.format("Send command '%s' failed", command), response, duration, starttime);
		}
	}

	public CodeMessage publish_command(String command, String expected) {
		if (!command.contains("&"))
			command = formatMQTT(command);
		else
			command = formatMQTT(command, jsonFormat);
		String[] output = http.httpPostRequest(url + "/v1/devices/" + getUdid() + "/publish_command.json?api_key=" + getApi_token(), command);
		String header = output[0];
		String response = output[1];
		try {
			String jobid = Json.parse(response).asObject().get("id").asString();
			int retry = Integer.parseInt(getStringByString(header, "Retry-After : [", "]"));
			int status_code = 0, DeviceResponseStatus = 0, DeviceResponseRetryAfter = 0;
			int count = 0;

			while (status_code != 200 && count < 10) {
				response = http.httpGetRequest(url + "/v1/jobs/" + jobid + "?api_key=" + getApi_token());
				JsonObject object = Json.parse(response).asObject();
				status_code = object.get("status").asInt();

				try {
					DeviceResponseStatus = object.get("data").asObject().get("DeviceResponseStatus").asInt();
				} catch (Exception e) {
					DeviceResponseStatus = 0;
				}
				if (DeviceResponseStatus == 712) {
					try {
						DeviceResponseRetryAfter = object.get("data").asObject().get("output").asObject().get("DeviceResponseRetryAfter").asInt();
						ThreadSleep(DeviceResponseRetryAfter);
					} catch (Exception e) {

					}
				} else if (DeviceResponseStatus == 713) {

				}

				if (status_code == 200) {
					String body = object.get("data").asObject().get("output").asObject().get("DeviceResponseMessage").asString();

					if (!body.contains(expected))
						return new CodeMessage(0, String.format("Send command '%s' failed", command), response);
					return new CodeMessage(1, String.format("Send command '%s' successfully", command), response);
				}
				if (status_code == 202) {
					ThreadSleep(retry);
				}
				count++;
			}
			return new CodeMessage(0, String.format("Send command '%s' failed", command), response);
		} catch (Exception e) {
			return new CodeMessage(0, String.format("Send command '%s' failed", command), response);
		}
	}

	private String formatMQTT(String command) {
		return String.format("{ \"command\": \"%s\" }", command.toUpperCase());
	}

	private String formatMQTT(String command, String jsonFormat) {
		String[] arrJson = jsonFormat.split("\n");

		String[] arrInput = command.split("&");
		String strCommand = arrInput[0].toUpperCase();
		String strAttributes = "";

		for (int j = 1; j < arrInput.length; j++) {
			String strInput = arrInput[j];
			strInput = String.format("%s:%s", strInput.split("=")[0], strInput.split("=")[1]);
			for (int i = 0; i < arrJson.length; i++) {
				if (arrJson[i].contains(strInput.split(":")[0])) {
					arrJson[i] = arrJson[i].replace("string", strInput.split(":")[1]);
				}
			}
		}

		for (int i = 0; i < arrJson.length; i++) {
			if (!arrJson[i].contains("value") && arrJson[i].contains("string")) {
				arrJson[i] = "";
			}
		}
		for (int i = 0; i < arrJson.length; i++) {
			strAttributes += arrJson[i];
		}
		return String.format("{ \"command\": \"%s\",%s}", strCommand, strAttributes.replace("\"value\": \"string\"", "\"value\": \"\""));
	}

	private String getStringByString(String input, String start, String end) {
		int indexStart = input.indexOf(start) + start.length();
		int indexEnd = input.indexOf(end, indexStart);
		return input.substring(indexStart, indexEnd);
	}

	public CodeMessage AppRegister() {
		String app_code = StringHelper.randomString("", 15);
		String[] output = http.httpPostRequest(url + "/v1/users/apps?access_token=" + getAccess_token(),
				"{\"secure_code\":\"\",\"name\":\"Java App\",\"app_code\":\"" + app_code + "\",\"version\":\"1.0(55)\""
						+ "}}");
		String response = output[1];
		try {
			JsonObject object = Json.parse(response).asObject();
			String subscribe_topic = object.get("data").asObject().get("mqtt").asObject().get("subscribe_topic")
					.asString();
			String client_id = object.get("data").asObject().get("mqtt").asObject().get("client_id").asString();
			String access_key = object.get("data").asObject().get("mqtt").asObject().get("access_key").asString();
			String secret_key = object.get("data").asObject().get("mqtt").asObject().get("secret_key").asString();

			MqttObject mqttObject = new MqttObject(subscribe_topic, client_id, access_key, secret_key);
			setMqttChanel(mqttObject);

			return new CodeMessage(1, "App register successfully", "summary", response);
		} catch (Exception e) {
			return new CodeMessage(0, "App register failed", "summary", response);
		}
	}
	
	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public ArrayList<Camera> getLstCamera() {
		return lstCamera;
	}

	public void setLstCamera(ArrayList<Camera> lstCamera) {
		this.lstCamera = lstCamera;
	}

	private void ThreadSleep(int milisecond) {
		try {
			Thread.sleep(milisecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getApi_token() {
		return api_token;
	}

	public void setApi_token(String api_token) {
		this.api_token = api_token;
	}
	
	public void setMqttChanel(MqttObject mqttChanel) {
		this.mqttChanel = mqttChanel;
	}
	public String getAccess_token() {
		return access_token;
	}
	public MqttObject getMqttChanel() {
		return mqttChanel;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public CodeMessage getDevices() {
		String response = http.httpGetRequest(url + "/v1/users/devices?access_token=" + getAccess_token());
		try {
			lstCamera.clear();
			JsonObject object = Json.parse(response).asObject();
			JsonArray items = object.get("data").asArray();
			for (JsonValue item : items) {
				String name = item.asObject().get("name").asString();
				String host_ssid = "";
				try {
					host_ssid = item.asObject().get("router_ssid").asString();
				} catch (Exception ex) {
				}
				String local_ip = "";
				try {
					local_ip = item.asObject().asObject().get("local_ip").asString();
				} catch (Exception e) {
				}
				String registration_id = "";
				try {
					registration_id = item.asObject().get("device_id").asString();
				} catch (Exception e) {
				}
				String firmware_version = item.asObject().get("firmware").asObject().get("version").asString();
				boolean status = item.asObject().get("is_online").asBoolean();
				String mqtt_topic = "";
				try {
					mqtt_topic = item.asObject().get("mqtt_topic").asString();
				} catch (Exception e) {
				}

				Camera o = new Camera();
				o.setName(name);
				o.setRegistration_id(registration_id);
				o.setHost_ssid(host_ssid);
				o.setLocal_ip(local_ip);
				o.setFirmware_version(firmware_version);
				o.setMqtt_topic(mqtt_topic);
				if (status)
					o.setStatus("Online");
				else
					o.setStatus("Offline");				
				lstCamera.add(o);
			}
			return new CodeMessage(1, "Get camera list successfully", "summary", response);
		} catch (Exception e) {
			return new CodeMessage(0, "Get camera list failed", "summary", response);
		}
	}
	
}
