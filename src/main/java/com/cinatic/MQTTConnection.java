package com.cinatic;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public class MQTTConnection implements MqttCallback {
	int state = BEGIN;

	static final int BEGIN = 0;
	static final int CONNECTED = 1;
	static final int PUBLISHED = 2;
	static final int SUBSCRIBED = 3;
	static final int DISCONNECTED = 4;
	static final int FINISH = 5;
	static final int ERROR = 6;
	static final int DISCONNECT = 7;	

	// Private instance variables
	MqttAsyncClient client;
	String brokerUrl;
	private boolean quietMode;
	private MqttConnectOptions conOpt;
	private boolean clean;
	Throwable ex = null;
	Object waiter = new Object();
	boolean donext = false;
	private String password;
	private String userName;

	public MQTTConnection(String brokerUrl, String clientId, boolean cleanSession, boolean quietMode,
			String userName, String password) throws MqttException {
		this.brokerUrl = brokerUrl;
		this.quietMode = quietMode;
		this.clean = cleanSession;
		this.password = password;
		this.userName = userName;	
		String tmpDir = System.getProperty("java.io.tmpdir");
		MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

		try {
			conOpt = new MqttConnectOptions();
			conOpt.setCleanSession(clean);
			if (password != null) {
				conOpt.setPassword(this.password.toCharArray());
			}
			if (userName != null) {
				conOpt.setUserName(this.userName);
			}
			client = new MqttAsyncClient(this.brokerUrl, clientId, dataStore);
			client.setCallback(this);

		} catch (MqttException e) {
			e.printStackTrace();
			log("Unable to set up client: " + e.toString());
			System.exit(1);
		}
	}

	public void publish(String topicName, int qos, byte[] payload) throws Throwable {		
		while (state != FINISH) {
			switch (state) {
			case BEGIN: 
				MqttConnector con = new MqttConnector();
				con.doConnect();
				break;
			case CONNECTED:				
				Publisher pub = new Publisher();
				pub.doPublish(topicName, qos, payload);
				break;
			case PUBLISHED:
				state = DISCONNECT;
				donext = true;
				break;
			case DISCONNECT:
				Disconnector disc = new Disconnector();
				disc.doDisconnect();
				break;
			case ERROR:
				throw ex;
			case DISCONNECTED:
				state = FINISH;
				donext = true;
				break;
			}
		
			waitForStateChange(10000);
		}
	}

	private void waitForStateChange(int maxTTW) throws MqttException {
		synchronized (waiter) {
			if (!donext) {
				try {
					waiter.wait(maxTTW);
				} catch (InterruptedException e) {
					log("timed out");
					e.printStackTrace();
				}

				if (ex != null) {
					throw (MqttException) ex;
				}
			}
			donext = false;
		}
	}

	public void subscribe(String topicName, int qos) throws Throwable {
		// Use a state machine to decide which step to do next. State change
		// occurs
		// when a notification is received that an MQTT action has completed
		while (state != FINISH) {
			switch (state) {
			case BEGIN:
				// Connect using a non-blocking connect
				MqttConnector con = new MqttConnector();
				con.doConnect();
				break;
			case CONNECTED:
				// Subscribe using a non-blocking subscribe
				Subscriber sub = new Subscriber();
				sub.doSubscribe(topicName, qos);
				break;
			case SUBSCRIBED:
				// Block until Enter is pressed allowing messages to arrive
				log("Press <Enter> to exit");
				try {
					System.in.read();
				} catch (IOException e) {
					// If we can't read we'll just exit
				}
				state = DISCONNECT;
				donext = true;
				break;
			case DISCONNECT:
				Disconnector disc = new Disconnector();
				disc.doDisconnect();
				break;
			case ERROR:
				throw ex;
			case DISCONNECTED:
				state = FINISH;
				donext = true;
				break;
			}

			// if (state != FINISH && state != DISCONNECT) {
			waitForStateChange(10000);
		}
		// }
	}

	void log(String message) {
		if (!quietMode) {
			System.out.println(message);
		}
	}
	
	public void connectionLost(Throwable cause) {
		log("Connection to " + brokerUrl + " lost!" + cause);
		System.exit(1);
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		log("Delivery complete callback: Publish Completed " + Arrays.toString(token.getTopics()));
	}
	
	public void messageArrived(String topic, MqttMessage message) throws MqttException {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println("Time:\t" + time + "  Topic:\t" + topic + "  Message:\t" + new String(message.getPayload())
				+ "  QoS:\t" + message.getQos());
	}

	public class MqttConnector {

		public MqttConnector() {
		}

		public void doConnect() {
			log("Connecting to " + brokerUrl + " with client ID " + client.getClientId());

			IMqttActionListener conListener = new IMqttActionListener() {
				public void onSuccess(IMqttToken asyncActionToken) {
					log("Connected");
					state = CONNECTED;
					carryOn();
				}

				public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
					ex = exception;
					state = ERROR;
					log("connect failed" + exception);
					carryOn();
				}

				public void carryOn() {
					synchronized (waiter) {
						donext = true;
						waiter.notifyAll();
					}
				}
			};

			try {

				client.connect(conOpt, "Connect sample context", conListener);
			} catch (MqttException e) {

				state = ERROR;
				donext = true;
				ex = e;
			}
		}
	}

	public class Publisher {
		public void doPublish(String topicName, int qos, byte[] payload) {
			MqttMessage message = new MqttMessage(payload);
			message.setQos(qos);

			String time = new Timestamp(System.currentTimeMillis()).toString();
			log("Publishing at: " + time + " to topic \"" + topicName + "\" qos " + qos);

			IMqttActionListener pubListener = new IMqttActionListener() {
				public void onSuccess(IMqttToken asyncActionToken) {
					log("Publish Completed");
					state = PUBLISHED;
					carryOn();
				}

				public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
					ex = exception;
					state = ERROR;
					log("Publish failed" + exception);
					carryOn();
				}

				public void carryOn() {
					synchronized (waiter) {
						donext = true;
						waiter.notifyAll();
					}
				}
			};

			try {
				client.publish(topicName, message, "Pub sample context", pubListener);
			} catch (MqttException e) {
				state = ERROR;
				donext = true;
				ex = e;
			}
		}
	}
	
	public class Subscriber {
		public void doSubscribe(String topicName, int qos) {
			log("Subscribing to topic \"" + topicName + "\" qos " + qos);

			IMqttActionListener subListener = new IMqttActionListener() {
				public void onSuccess(IMqttToken asyncActionToken) {
					log("Subscribe Completed");
					state = SUBSCRIBED;
					carryOn();
				}

				public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
					ex = exception;
					state = ERROR;
					log("Subscribe failed" + exception);
					carryOn();
				}

				public void carryOn() {
					synchronized (waiter) {
						donext = true;
						waiter.notifyAll();
					}
				}
			};

			try {
				client.subscribe(topicName, qos, "Subscribe sample context", subListener);
			} catch (MqttException e) {
				state = ERROR;
				donext = true;
				ex = e;
			}
		}
	}
	
	public class Disconnector {
		public void doDisconnect() {
			log("Disconnecting");

			IMqttActionListener discListener = new IMqttActionListener() {
				public void onSuccess(IMqttToken asyncActionToken) {
					log("Disconnect Completed");
					state = DISCONNECTED;
					carryOn();
				}

				public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
					ex = exception;
					state = ERROR;
					log("Disconnect failed" + exception);
					carryOn();
				}

				public void carryOn() {
					synchronized (waiter) {
						donext = true;
						waiter.notifyAll();
					}
				}
			};

			try {
				client.disconnect("Disconnect sample context", discListener);
			} catch (MqttException e) {
				state = ERROR;
				donext = true;
				ex = e;
			}
		}
	}
}
