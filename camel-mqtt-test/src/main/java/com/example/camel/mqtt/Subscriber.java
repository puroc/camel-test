package com.example.camel.mqtt;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/*******************************************************************************
 * @(#)Subscriber.java 2016年3月7日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * emrubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/

/**
 * @author <a href="mailto:pud@emrubik.com">pu dong</a>
 * @version $Revision 1.0 $ 2016年3月7日 上午10:40:57
 */
public class Subscriber {

	public static void main(String[] args) {
		try {
			CamelContext cc = new DefaultCamelContext();
			cc.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					from("mqtt:bar?subscribeTopicName=mqtt.topic")
							.process(new Processor() {

						public void process(Exchange exchange)
								throws Exception {
							System.out.println("haha");
						}
					});
				}
			});
			cc.start();
			Thread.sleep(999999999);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
