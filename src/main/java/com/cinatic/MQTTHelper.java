package com.cinatic;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class MQTTHelper implements MqttCallback {
	MqttClient myClient;
	MqttConnectOptions connOpt;
	public String output;

	final Boolean subscriber = true;
	final Boolean publisher = true;
	String BROKER_URL = "tcp://mqtt-iot.hubble.in:1883";
	String USERNAME;
	String PASSWORD;
	String CLIENTID;
	String UDID;
	String TOPIC;
	String MQTT_TOPIC;

	public MQTTHelper(String url, String username, String password, String topic, String clientID, String mqttTopic) throws MqttException {
		this.BROKER_URL = url;
		this.USERNAME = username;
		this.PASSWORD = password;
		this.TOPIC = topic;
		this.CLIENTID = clientID;
		this.MQTT_TOPIC = mqttTopic;
	}
	public MQTTHelper(String url, String username, String password, String udid) throws MqttException {
		this.BROKER_URL = url;
		this.USERNAME = username;
		this.PASSWORD = password;
		this.UDID = udid;
	}

	public void runClient(String command) throws Exception {
		output = "";
//		CLIENTID = MqttClient.generateClientId();
		connOpt = new MqttConnectOptions();

		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);
		connOpt.setUserName(USERNAME);
		connOpt.setPassword(PASSWORD.toCharArray());

		try {
			myClient = new MqttClient(BROKER_URL, CLIENTID);
			myClient.setCallback(this);
			myClient.connect(connOpt);
		} catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("Connected to " + BROKER_URL);
		MqttTopic topic = myClient.getTopic(MQTT_TOPIC);
		if (subscriber) {
			try {
				int subQoS = 0;
				myClient.subscribe(TOPIC, subQoS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (publisher) {			
			long time = System.currentTimeMillis();
			String pubMsg = "2app_topic_sub=" + TOPIC + "&time=" + time + "&req=" + command;				
			System.out.println(pubMsg);
			int pubQoS = 0;
			MqttMessage message = new MqttMessage(pubMsg.getBytes());
			message.setQos(pubQoS);
			message.setRetained(false);

			System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
			MqttDeliveryToken token = null;
			try {
				token = topic.publish(message);
				token.waitForCompletion();
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			if (subscriber) {
				Thread.sleep(8000);
			}
			myClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost!");

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {		
		output = new String(message.getPayload());		
		System.out.println(output);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("Delivery Complete!");

	}
}
