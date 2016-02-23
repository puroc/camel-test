/*******************************************************************************
 * @(#)TcpServer.java 2016年2月23日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * emrubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.emrubik.iot.traffic.tcp.server;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emrubik.iot.traffic.Counter;
import com.emrubik.iot.traffic.codec.TrafficCodec;

/**
 * @author <a href="mailto:pud@emrubik.com">pu dong</a>
 * @version $Revision 1.0 $ 2016年2月23日 下午6:12:18
 */
@Component
public class TcpServer extends IoHandlerAdapter {

	private static final int PORT = 18567;

	@Autowired
	private CamelContext camelContext;

	private ProducerTemplate template;

	@PostConstruct
	public void init() {
		template = camelContext.createProducerTemplate();
		new Thread(new Runnable() {
			public void run() {
				try {
					NioSocketAcceptor acceptor = new NioSocketAcceptor();
					acceptor.setHandler(TcpServer.this);
					DefaultIoFilterChainBuilder chain = acceptor
							.getFilterChain();

					ProtocolCodecFactory codecFactory = new TrafficCodec();
					chain.addLast("codec",
							new ProtocolCodecFilter(codecFactory));

					// chain.addLast("codec", new ProtocolCodecFilter(
					// new TextLineCodecFactory()));
					acceptor.bind(new InetSocketAddress(PORT));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		super.exceptionCaught(session, cause);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		super.messageReceived(session, message);
		template.sendBody("direct:up", (String) message);
	}

}
