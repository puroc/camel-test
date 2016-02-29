/*******************************************************************************
 * @(#)Producer.java 2016年2月23日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * emrubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.camel.disruptor.producer;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spi.UriEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:pud@emrubik.com">pu dong</a>
 * @version $Revision 1.0 $ 2016年2月23日 下午3:45:53
 */
@Component
public class Producer {

	@Autowired
	private CamelContext camelContext;

	@PostConstruct
	public void init() {
		try {
			ProducerTemplate template = camelContext.createProducerTemplate();
			// 延迟3秒启动是为了等待camel加载完Route规则，否认这调用template.sendBody时会提示没有消费者，就是因为消费者的规则没有被camel加载呢
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					try {
						for (int i = 0; true; i++) {
							template.sendBody("direct:start", "Hello World");
							// Thread.sleep(1000);
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}, 3000);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
