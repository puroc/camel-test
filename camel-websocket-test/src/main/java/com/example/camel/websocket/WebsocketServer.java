package com.example.camel.websocket;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class WebsocketServer {

	public static void main(String[] args) {
		try {
			CamelContext cc = new DefaultCamelContext();
			cc.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					from("websocket://echo?sendToAll=true")
							.log(">>> Message received from WebSocket Client : ${body}")
							.transform().simple("${body}${body}")
							.to("websocket://echo");
					from("direct:start").to("websocket://echo?sendToAll=true");
				}
			});
			cc.start();
			Thread.sleep(10 * 1000);
			while(true){
				cc.createProducerTemplate().sendBody("direct:start", "hello");
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
