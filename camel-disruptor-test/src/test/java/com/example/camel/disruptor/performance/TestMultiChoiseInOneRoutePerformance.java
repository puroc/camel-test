package com.example.camel.disruptor.performance;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExpressionClause;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ChoiceDefinition;
import org.apache.camel.model.RouteDefinition;

import com.example.camel.disruptor.Counter;

public class TestMultiChoiseInOneRoutePerformance {

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
			ChoiceDefinition definition = from(DISRUPTOR_NEXT)
					.process(new Processor() {

						@Override
						public void process(Exchange exchange)
								throws Exception {
							exchange.getIn().setHeader("terminalId", "12345");
						}
					}).choice()
					// .when()
					// .mvel("request.headers.terminalId=='12345'")
					// .process(new Processor() {
					//
					// @Override
					// public void process(Exchange exchange)
					// throws Exception {
					// // System.out.println("correct");
					// counter.add();
					// }
					// })
					.otherwise().log("otherwise error");

			appendWhen(definition);

			if (definition.getId() == null) {
				definition.setId(name);
			}
		}

		private ChoiceDefinition appendWhen(ChoiceDefinition definition) {
			for (int i = 0; i < 200; i++) {
				definition.when()
						.mvel("request.headers.terminalId=='" + i + "'")
						.log("failed.");
			}
			definition.when().mvel("request.headers.terminalId=='12345'")
					.process(new Processor() {

						@Override
						public void process(Exchange exchange)
								throws Exception {
							// System.out.println("correct");
							if (definition.getId().equals("choice1")) {
								counter.add();
							}
						}
					});
			return definition;
		}
	}

	public static void main(String[] args) {
		try {
			counter.start();
			final CamelContext cc = new DefaultCamelContext();
			for (int i = 0; i < 1; i++) {
				cc.addRoutes(new MyRoute(i + ""));
			}
			ProducerTemplate template = cc.createProducerTemplate();
			cc.start();
			// for (final int i = 0; i < 10; i++) {
			// while (true) {
			// new Thread(new Runnable() {
			// public void run() {
			// cc.createProducerTemplate().sendBody(DISRUPTOR_NEXT,
			// i + "");
			// try {
			// Thread.sleep(1);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			// }
			// }).start();
			// }
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
			}, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
