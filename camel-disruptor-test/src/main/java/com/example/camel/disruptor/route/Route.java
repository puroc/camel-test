package com.example.camel.disruptor.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Route extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("direct:start").to("disruptor:next");
	}

}
