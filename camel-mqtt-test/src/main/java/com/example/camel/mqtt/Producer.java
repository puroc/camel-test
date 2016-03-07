package com.example.camel.mqtt;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Producer {

	private static final String HOST = "192.168.1.124:1883";

	private static final String TOPIC = "mqtt.topic";

	public static void main(String[] args) {
		try {
			IMqttClient m_client = new MqttClient("tcp://" + HOST, "Producer",new MemoryPersistence());
			m_client.connect();
			m_client.subscribe(TOPIC, 1);
			while (true) {
				m_client.publish(TOPIC, "shake shake~~".getBytes(), 1, false);
				Thread.sleep(1000);
			}
			// m_client.disconnect();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
