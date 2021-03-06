package com.cinatic;

import static com.jayway.restassured.RestAssured.given;

import com.cinatic.constant.TestConstant;
import com.cinatic.object.Device;
import com.cinatic.object.Event;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class KodakApiHelper {

	private static final String CONTENT_TYPE_JSON = "application/json";
	private static final String CONTENT_TYPE_TEXT = "text/html;charset=ISO-8859-1";
	public static final String API_SVR_NAME_PRODUCTION = "production";
	public static final String API_SVR_NAME_STAGING = "staging";
	public static final String API_SVR_HK = "https://api-tn001-reg01.perimetersafe.com";
	public static final String API_SVR_US = "https://api-tn001-reg02.perimetersafe.com";
	public static final String API_SVR_EU = "https://api-tn001-reg03.perimetersafe.com";
	private String c_accessToken;
	private Response c_response;
	private RequestSpecification spec;
	private int tandc_id;
	
	/**
	 * 
	 * @param server
	 */
	public KodakApiHelper(String server) {
		spec = new RequestSpecBuilder().setBaseUri(getApiUrlByServerName(server)).build();
		spec.relaxedHTTPSValidation();
		getTandC();
	}
	
	public KodakApiHelper(String server, String username) {
		spec = new RequestSpecBuilder().setBaseUri(getApiUrlByServerName(server, username)).build();
		spec.relaxedHTTPSValidation();
		getTandC();
	}
	
	/**
	 * Get api url base on server name
	 * @param server
	 * @return
	 */
	public String getApiUrlByServerName(String server) {
		Response response;
		String url= "";
		switch (server) {
		// if input server is one of these specific server, just return.
			case API_SVR_HK:
				return API_SVR_HK;
			case API_SVR_US:
				return API_SVR_US;
			case API_SVR_EU:
				return API_SVR_EU;
		// if input server is general (staging or production), find the specific server for each case then return.
			case API_SVR_NAME_PRODUCTION:
				url = TestConstant.PRODUCTION_GLOBAL_SERVER;
				break;
			case API_SVR_NAME_STAGING:
			default:
				url = TestConstant.STAGING_GLOBAL_SERVER;
				break;
		}
		//find the specific server for each case then return.
		response = given().auth().preemptive().basic(TestConstant.KODAK_USERNAME_API, TestConstant.KODAK_PASSWORD_API)
				.when().get(url).then().log().all().extract().response();
		return "https://" + response.path("data.master.api");
	}
	
	/**
	 * Get api url base on server and username
	 * @param server
	 * @param username
	 * @return
	 */
	public String getApiUrlByServerName(String server, String username) {
		Response response;
		String url = "";
		switch (server) {
		// if input server is one of these specific server, just return.
			case API_SVR_HK:
				return API_SVR_HK;
			case API_SVR_US:
				return API_SVR_US;
			case API_SVR_EU:
				return API_SVR_EU;
		// if input server is general (staging or production), find the specific server for each case then return.
			case API_SVR_NAME_PRODUCTION:
				url = TestConstant.PRODUCTION_GLOBAL_SERVER;
				break;
			case API_SVR_NAME_STAGING:
			default:
				url = TestConstant.STAGING_GLOBAL_SERVER;
				break;
		}
		//find the specific server for each case then return.
		response = given().auth().preemptive().basic(TestConstant.KODAK_USERNAME_API, TestConstant.KODAK_PASSWORD_API)
				.when().get(url + "?username=" + username).then().log().all().extract().response();
		return "https://" + response.path("data.master.api");
	}
	
	// Mailinator
	public void activeAccount() {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_TEXT).log().all().when()
				.get("https://www.mailinator.com/").then().log().all().extract().response();

		response = given().spec(spec).contentType(CONTENT_TYPE_TEXT).log().all().when()
				.get("https://www.mailinator.com/v2/inbox.jsp?zone=public&query=qatestrandom9650680478").then().log()
				.all().extract().response();
	}

	// Utils
	public Response getTandC() {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all().when().get("/v1/utils/t_and_c").then()
				.log().all().extract().response();
		given().spec(spec);
		c_response = response;
		tandc_id = response.path("data.tandc_id");
		return response;
	}

	// Users
	public Response registerUserAccount(String email, String username, String password, String confirmPassword) {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all()
				.body("{\"secure_code\": \"\", \"user_name\":\"" + username + "\", \"email\":\"" + email
						+ "\", \"password\":\"" + password + "\", \"confirm_password\":\"" + confirmPassword
						+ "\", \"tandc_id\": " + tandc_id + " }")
				.when().post("/v1/users/account").then().log().all().extract().response();
		return response;
	}

	public Response verifyUserAccountRegister(String verificationToken) {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all().when()
				.get("/v1/users/account/verify?token=" + verificationToken).then().log().all().extract().response();
		return response;
	}

	public Response userLogin(String username, String password) {
		
		
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all()
				.body("{\"os\":\"3\",\"model\":\"unknown\",\"oauth_type\":0,\"is_refresh\":true,\"login\":\"" + username
						+ "\",\"password\":\"" + password + "\"}")
				.when().post("/v1/users/authenticate").then().log().all().extract().response();

		c_accessToken = response.path("data.access_token");
		return response;
	}

	public Response changePassword(String oldPassword, String newPassword, String confirmPassword) {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all()
				.body("{\"current_password\":\"" + oldPassword + "\", \"password\":\"" + newPassword
						+ "\", \"confirm_password\":\"" + confirmPassword + "\"}")
				.when().put("/v1/users/account/change_password?access_token=" + c_accessToken).then().log().all()
				.extract().response();
		return response;
	}

	public Response resetPassword(String resetPassToken, String newPassword) {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all()
				.body("{\"password\":\"" + newPassword + "\", \"confirm_password\":\"" + newPassword + "\"}").when()
				.put("/v1/users/account/reset_password?token=" + resetPassToken).then().log().all().extract()
				.response();
		return response;
	}

	public Response recoverPassword(String email) {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all().body("{\"email\":\"" + email + "\"}")
				.when().post("/v1/users/account/recover_password?access_token=" + c_accessToken).then().log().all()
				.extract().response();
		return response;
	}

	public Response getUserLogInAccessToken(String refreshToken) {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all().when()
				.get("/v1/users/authenticate?refresh_token=" + refreshToken).then().log().all().extract().response();
		return response;
	}

	public Response getUserLogInLogs() {
		return given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all().when()
				.get("/v1/users/access_log?access_token=" + c_accessToken).then().log().all().extract().response();
	}

	public Response getCurrentLoggedInUser() {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all().when()
				.get("/v1/users/account?access_token=" + c_accessToken).then().log().all().extract().response();
		return response;
	}

	// Audit
	public Response subscribe(String planId) {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all().body("\"plan_id\":\"" + planId + "\"")
				.when().post("/v1/audit/fake_subscribe?access_token=" + c_accessToken).then().log().all().extract()
				.response();
		return response;
	}
	
	public Response getCamInfo(String deviceId) {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all()
				.body("{\"access_token\":\"" + c_accessToken + "\",\"is_ssl\":true,\"command\":\"get_caminfo\"}").when()
				.post("/v1/audit/" + deviceId + "/send_cmd").then().log().all().extract().response();
		return response;
	}

	// Devices
	public Response getDevices() {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all().when()
				.get("/v1/users/devices?access_token=" + c_accessToken).then().log().all().extract().response();
		c_response = response;
		return response;
	}

	public Response getShareDevices() {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all().when()
				.get("/v1/users/share_devices?access_token=" + c_accessToken).then().log().all().extract().response();
		c_response = response;
		return response;
	}

	public Device getDeviceByDeviceId(String deviceId) {
		String name = c_response.path("data.find{ it.device_id == '" + deviceId + "' }.name").toString();
		String is_online = c_response.path("data.find{ it.device_id == '" + deviceId + "' }.is_online").toString();
		String mqtt_topic = c_response.path("data.find{ it.device_id == '" + deviceId + "' }.mqtt_topic").toString();
		String local_ip = c_response.path("data.find{ it.device_id == '" + deviceId + "' }.local_ip").toString();
		String firmware_version = c_response.path("data.find{ it.device_id == '" + deviceId + "' }.firmware.version")
				.toString();

		Device o = new Device();
		o.setName(name);
		o.setDevice_id(deviceId);
		o.setIs_online(is_online);
		o.setMqtt_topic(mqtt_topic);
		o.setLocal_ip(local_ip);
		o.setFirmware_version(firmware_version);

		return o;
	}

	public Response registerDevice(String deviceId, String version, String timeZone, String localIp, String routerSsid,
			String routerName) {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all()
				.body("{\"name\":\"camtest\", \"device_id\":\"" + deviceId + "\"version\":\"" + version
						+ "\", \"time_zone\":\"" + timeZone + "\", \"local_ip\":\"" + localIp + "\", \"router_ssid\":\""
						+ routerSsid + "\", \"router_name\":\"" + routerName + "\"}")
				.when().post("/v1/devices/" + deviceId + "/register?access_token=" + c_accessToken).then().log().all()
				.extract().response();
		return response;
	}

	public Response addSubscriptionForDevice(String deviceId) {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all()
				.body("{\"device_ids\":\"" + deviceId + "\"}").when()
				.post("/v1/users/sub_plan/update_devices?access_token=" + c_accessToken).then().log().all().extract()
				.response();
		return response;
	}

	// Events
	public Response addNewEvent(String deviceId, String device_token, int event_type) {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all()
				.body("{\"device_id\":\"" + deviceId + "\",\"event_type\":" + event_type + ",\"device_token\":\""
						+ device_token + "\"}")
				.when().post("/v1/devices/events?access_token=" + c_accessToken).then().log().all().extract()
				.response();
		return response;
	}

	public Response getEventsByDeviceId(String deviceId) {
		Response response = given().spec(spec).contentType(CONTENT_TYPE_JSON).log().all().when()
				.get("/v1/devices/events?access_token=" + c_accessToken + "&device_ids=" + deviceId).then().log().all()
				.extract().response();
		c_response = response;
		return response;
	}

	public Event getEventByEventId(String eventId) {
		try {
			String event_id = c_response.path("data.events.find{ it.id == '" + eventId + "' }.id") != null
					? c_response.path("data.events.find{ it.id == '" + eventId + "' }.id").toString()
					: "";
			String device_id = c_response.path("data.events.find{ it.id == '" + eventId + "' }.device_id") != null
					? c_response.path("data.events.find{ it.id == '" + eventId + "' }.device_id").toString()
					: "";
			String event_type = c_response.path("data.events.find{ it.id == '" + eventId + "' }.event_type") != null
					? c_response.path("data.events.find{ it.id == '" + eventId + "' }.event_type").toString()
					: "";
			String created_date = c_response.path("data.events.find{ it.id == '" + eventId + "' }.created_date") != null
					? c_response.path("data.events.find{ it.id == '" + eventId + "' }.created_date").toString()
					: "";
			String snapshot = c_response.path("data.events.find{ it.id == '" + eventId + "' }.snapshot") != null
					? c_response.path("data.events.find{ it.id == '" + eventId + "' }.snapshot").toString()
					: "";
			String file = c_response.path("data.events.find{ it.id == '" + eventId + "' }.data.file") != null
					? c_response.path("data.events.find{ it.id == '" + eventId + "' }.data.file").toString()
					: "";
			String file_type = c_response.path("data.events.find{ it.id == '" + eventId + "' }.data.file_type") != null
					? c_response.path("data.events.find{ it.id == '" + eventId + "' }.data.file_type").toString()
					: "";
			String file_created_date = c_response
					.path("data.events.find{ it.id == '" + eventId + "' }.data.created_date") != null
							? c_response.path("data.events.find{ it.id == '" + eventId + "' }.data.created_date")
									.toString()
							: "";
			String file_size = c_response.path("data.events.find{ it.id == '" + eventId + "' }.data.file_size") != null
					? c_response.path("data.events.find{ it.id == '" + eventId + "' }.data.file_size").toString()
					: "";

			Event o = new Event();
			o.setEvent_id(event_id);
			o.setDevice_id(device_id);
			o.setEvent_type(event_type);
			o.setCreated_date(created_date);
			o.setSnapshot(snapshot);
			o.setFile(file);
			o.setFile_type(file_type);
			o.setFile_created_date(file_created_date);
			o.setFile_size(file_size);

			return o;
		} catch (Exception e) {
			return null;
		}
	}
	
}
