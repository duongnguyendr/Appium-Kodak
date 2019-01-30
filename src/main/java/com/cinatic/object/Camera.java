package com.cinatic.object;

public class Camera {
	private String name;
	private String registration_id;
	private String plan_id;
	private String host_ssid;
	private String local_ip;
	private String firmware_version;
	private String status;
	private String device_status;
	private String orbweb_password;
	private String orbweb_sid;
	private String mqtt_topic;

	public Camera() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegistration_id() {
		return registration_id;
	}

	public void setRegistration_id(String registration_id) {
		this.registration_id = registration_id;
	}
	
	public String getPlan_id(){
		
		return plan_id;
		
	}
	
	public void setPlan_id(String plan_id){
		
		this.plan_id = plan_id;
	}
	
	public String getHost_ssid() {
		return host_ssid;
	}

	public void setHost_ssid(String host_ssid) {
		this.host_ssid = host_ssid;
	}

	public String getLocal_ip() {
		return local_ip;
	}

	public void setLocal_ip(String local_ip) {
		this.local_ip = local_ip;
	}

	public String getFirmware_version() {
		return firmware_version;
	}

	public void setFirmware_version(String firmware_version) {
		this.firmware_version = firmware_version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDeviceStatus() {
		return device_status;
	}

	public void setDeviceStatus(String device_status) {
		this.device_status = device_status;
	}
	public String getMqtt_topic() {
		return mqtt_topic;
	}
	public void setMqtt_topic(String mqtt_topic) {
		this.mqtt_topic = mqtt_topic;
	}
}
