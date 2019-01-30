package com.cinatic.constant;

import java.io.File;

import com.cinatic.StringHelper;

public class TestConstant {
	public static final int longTime = 30;
	public static final int mediumTime = 15;
	public static final int shortTime = 5;

	public static enum directioName {
		Left, Right, Up, Down
	}

	public static enum motionLevel {
		Low, Medium, High
	}

	public static enum soundLevel {
		Low, Medium, High
	}

	public static String logtime = StringHelper.getCurrentDateTime("MM-dd HH:mm:ss.SSS");
	public static String filecreatetime = StringHelper.getCurrentDateTime("dd_MM_YY_HHmmss");

	public static String formatDate = "hh:mm a, dd MMM";
	public static String durationformat = "MM-dd HH:mm:ss.SSS";
	public static String systemUser = System.getProperty("user.name");

	public static String emailHost = "@mailinator.com";
	public static String baseURI = "https://api.hubble.in";
	public static String devURI = "https://dev-h2o.hubble.in";
	public static String btURI = "https://bt-r1-cs.hubble.in";
	public static String mqttURI = "https://dev-api.hubble.in";
	public static String stagingURI = "https://staging-h2o.hubble.in";

	public static String internalBaseURI = "https://api-lucy-reg01.lucyconnect.com";
	public static String internalBaseUsURI = "https://api-lucy-reg02.lucyconnect.com";
	public static String internalStagingURI = "https://api-lucy-st.lucyconnect.com";
	public static String resourcePath = System.getenv("CINATIC_AUTOMATION_RES");
	public static String appFolder = resourcePath + File.separator + "app";

	public static final String kodak_hk_URI = "https://api-t01-r1.perimetersafe.com";
	public static final String kodak_us_URI = "https://api-t01-r2.perimetersafe.com";
	public static final String kodak_eu_URI = "https://api-t01-r3.perimetersafe.com";
	
	public static final String KODAK_USERNAME_API = "f46a862657578608343b9fd386307995c56191301bbcf5c627716f2998e3fdd6";
	public static final String KODAK_PASSWORD_API = "4f020e8b0e4edc9e76397fb8f34ff5071d4e2a3f929219158d942b114da7aa08";
	public static final String PRODUCTION_GLOBAL_SERVER = "https://gl-tn001-reg01.perimetersafe.com/v1/services";
	public static final String STAGING_GLOBAL_SERVER = "https://gl-tn001-st01.perimetersafe.com/v1/services";

	public static String username = "qa0855test";
	public static String email = "qa0855test@gmail.com";
	public static String emailPassword = "Aaaa1111";
	public static String password = "Aaaa1111";
	public static String cameraName = "12345";

	public static String forgetpassword_username = "qaforgotpasstest";
	public static String forgetpassword_email = "qaforgotpasstest@gmail.com";
	public static String forgetpassword_emailPassword = "Aaaa1111";
	public static String forgetpassword_password = "Aaaa1111";

	public static String appPackage;

	public static String getKodakUri(String server) {
		if (server.toUpperCase().equals("HK"))
			return kodak_hk_URI;
		else if (server.toUpperCase().equals("US"))
			return kodak_us_URI;
		else if (server.toUpperCase().equals("EU"))
			return kodak_eu_URI;
		else
			return "";
	}
}
