package com.example.camel.disruptor.performance;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RouteDefinition;

import com.example.camel.disruptor.Counter;

public class TestMultiRoutePerformance {

	private static final String DISRUPTOR_NEXT = "disruptor:next?multipleConsumers=true";
	// private static final String DISRUPTOR_NEXT =
	// "disruptor:next?multipleConsumers=true&concurrentConsumers=50&size=50000&blockWhenFull=false";

	private static Counter counter = new Counter();

	static class MyRoute extends RouteBuilder {

		private String name;

		public MyRoute(String name) {
			this.name = name;
		}

		@Override
		public void configure() throws Exception {
			RouteDefinition routeDefinition = from(DISRUPTOR_NEXT)
					.process(new Processor() {

						@Override
						public void process(Exchange exchange)
								throws Exception {
							// String thread = Thread.currentThread().getName();
							// String body = (String)
							// exchange.getIn().getBody();
							// System.out.println("thread:" + thread + " route:"
							// + name + " receive " + body);
							if (name.equals("0")) {
								counter.add();
							}
						}
					});
			if (routeDefinition.getId() == null) {
				routeDefinition.setId(name);
			}

		}
	}

	public static void main(String[] args) {
		try {
			counter.start();
			final CamelContext cc = new DefaultCamelContext();
			for (int i = 0; i < 50; i++) {
				cc.addRoutes(new MyRoute(i + ""));
			}
			ProducerTemplate template = cc.createProducerTemplate();
			cc.start();
			// for (int i = 0; i < 50; i++) {
			// new Thread(new Runnable() {
			// public void run() {
			// while (true) {
			// template.sendBody(DISRUPTOR_NEXT, 1+"");
			// }
			// // try {
			// // Thread.sleep(1);
			// // } catch (InterruptedException e) {
			// // e.printStackTrace();
			// // }
			// }
			// }).start();
			// }
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					try {
						for (int i = 0; true; i++) {
							template.sendBody(DISRUPTOR_NEXT, i + "");
							// Thread.sleep(1000);
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}, 3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
