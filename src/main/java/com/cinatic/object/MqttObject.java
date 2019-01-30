package com.cinatic.object;

public class MqttObject {
	private String subscribeTopic;
	private String clientId;
	private String accessKey;
	private String secretKey;	
	
	public MqttObject(String subscribeTopic, String clientId, String accessKey, String secretKey) {
		this.subscribeTopic = subscribeTopic;
		this.clientId = clientId;
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}
	
	public String getSubscribeTopic() {
		return subscribeTopic;
	}
	public void setSubscribeTopic(String subscribeTopic) {
		this.subscribeTopic = subscribeTopic;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}	
}
