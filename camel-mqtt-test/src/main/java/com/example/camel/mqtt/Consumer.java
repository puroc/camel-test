package com.example.camel.mqtt;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Consumer {

	private static final String TOPIC = "mqtt.topic";

	public static void main(String[] args) {
		try {
			IMqttClient m_client = new MqttClient("tcp://localhost:1883",
					"Consumer");
			m_client.setCallback(new MqttCallback() {

				@Override
				public void messageArrived(String topic, MqttMessage message)
						throws Exception {
					System.out.println("xixi");
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					System.out.println("deliveryComplete");
				}

				@Override
				public void connectionLost(Throwable cause) {
					System.out.println("connectionLost");
				}
			});
			m_client.connect();
			m_client.subscribe(TOPIC, 0);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
