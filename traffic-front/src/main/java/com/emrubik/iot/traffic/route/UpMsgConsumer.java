/*******************************************************************************
 * @(#)Consumer.java 2016年2月23日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * emrubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.emrubik.iot.traffic.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emrubik.iot.traffic.Counter;
import com.emrubik.iot.traffic.tcp.client.TcpClient;

/**
 * @author <a href="mailto:pud@emrubik.com">pu dong</a>
 * @version $Revision 1.0 $ 2016年2月23日 下午4:54:03
 */
@Component
public class UpMsgConsumer extends RouteBuilder {
	
	@Autowired
	private TcpClient tcpClient;

	@Override
	public void configure() throws Exception {
		from("disruptor:next?concurrentConsumers=20&size=50000")
				.process(new Processor() {

					public void process(Exchange exchange) throws Exception {
						// 解析
						// 鉴权
						// 上线通知
						// 发送到规则引擎
						tcpClient.sent("123".getBytes());
					}
				});
	}

}
