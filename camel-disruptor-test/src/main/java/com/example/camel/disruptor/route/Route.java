package com.example.camel.disruptor.route;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import com.example.camel.disruptor.Counter;

public class Route extends RouteBuilder {

	private static Counter counter = new Counter();

	@Override
	public void configure() throws Exception {
		from("direct:start").to("disruptor:next");
		from("disruptor:next").process(new Processor() {

			public void process(Exchange exchange) throws Exception {
				// System.out.println(exchange.getIn().getBody());
				counter.add();
			}
		});
	}

	public static void main(String[] args) {
		try {
			counter.start();
			CamelContext context = new DefaultCamelContext();
			context.addRoutes(new Route());
			ProducerTemplate template = context.createProducerTemplate();
			context.start();
			for (int i = 0; true; i++) {
				template.sendBody("direct:start", "Hello World");
				// Thread.sleep(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
