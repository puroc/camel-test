/*******************************************************************************
 * @(#)PyScriptTest.java 2016年2月27日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * emrubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.camel.script;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class PyScriptTest {

	private static ProducerTemplate template;

	public static void main(String[] args) {
		try {
			CamelContext context = new DefaultCamelContext();
			context.addRoutes(new RouteBuilder() {
				public void configure() throws Exception {
					from("direct:start").choice().when()
							.python("request.headers['foo']=='foo'")
							.to("direct:right").otherwise().to("direct:wrong");
					from("direct:right").process(new Processor() {

						@Override
						public void process(Exchange exchange)
								throws Exception {
							System.out.println("right");

						}
					});
					from("direct:wrong").process(new Processor() {

						@Override
						public void process(Exchange exchange)
								throws Exception {
							System.out.println("wrong");

						}
					});
				}
			});
			context.start();
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("foo", "foo");

			template = context.createProducerTemplate();
			sendBody("direct:start", "hello", headers);
			Thread.sleep(1000000000);
			context.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	protected static void sendBody(String endpointUri, final Object body,
			final Map<String, Object> headers) {
		template.send(endpointUri, new Processor() {
			public void process(Exchange exchange) {
				Message in = exchange.getIn();
				in.setBody(body);
				for (Map.Entry<String, Object> entry : headers.entrySet()) {
					in.setHeader(entry.getKey(), entry.getValue());
				}
			}
		});
	}

}
