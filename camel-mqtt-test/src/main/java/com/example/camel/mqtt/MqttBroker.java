package com.example.camel.mqtt;
import org.dna.mqtt.moquette.server.Server;

public class MqttBroker {

	public static void main(String[] args) {
		try {
			Server m_server = new Server();
			m_server.startServer();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
