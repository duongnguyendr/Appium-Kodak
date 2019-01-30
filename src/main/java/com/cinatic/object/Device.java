package com.cinatic.object;

public class Device {
	private String name;
	private String device_id;	
	private String is_online;
	private String mqtt_topic;
	private String local_ip;
	private String firmware_version;	

	public Device() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getIs_online() {
		return is_online;
	}

	public void setIs_online(String is_online) {
		this.is_online = is_online;
	}

	public String getMqtt_topic() {
		return mqtt_topic;
	}

	public void setMqtt_topic(String mqtt_topic) {
		this.mqtt_topic = mqtt_topic;
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
}
