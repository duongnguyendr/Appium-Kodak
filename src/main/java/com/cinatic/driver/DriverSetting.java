package com.cinatic.driver;

public class DriverSetting {
	private String apk;
	private String platformName;
	private String deviceName;
	private String deviceVersion;
	private String deviceUDID;
	private String remoteURL;

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getDeviceUDID() {
		return deviceUDID;
	}
	
	public void setDeviceUDID(String deviceUDID) {
		this.deviceUDID = deviceUDID;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getRemoteURL() {
		return remoteURL;
	}

	public void setRemoteURL(String remoteURL) {
		this.remoteURL = remoteURL;
	}

	public String getApk() {
		return apk;
	}

	public void setApk(String apk) {
		this.apk = apk;
	}
}
