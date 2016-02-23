/*******************************************************************************
 * @(#)Consumer.java 2016年2月23日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * emrubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.camel.disruptor.consumer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.example.camel.disruptor.Counter;

/**
 * @author <a href="mailto:pud@emrubik.com">pu dong</a>
 * @version $Revision 1.0 $ 2016年2月23日 下午4:54:03
 */
@Component
public class Consumer extends RouteBuilder {

	private static Counter counter = new Counter();

	@Override
	public void configure() throws Exception {
		counter.start();
		from("disruptor:next").process(new Processor() {

			public void process(Exchange exchange) throws Exception {
				// System.out.println(exchange.getIn().getBody());
				counter.add();
			}
		});
	}

}
