package com.emrubik.iot.traffic.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UpMsgRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("direct:up").to(
				"disruptor:next?blockWhenFull=false&producerType=single&size=50000");
	}

}
