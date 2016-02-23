/*******************************************************************************
 * @(#)TcpClient.java 2016年2月23日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * emrubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.emrubik.iot.traffic.tcp.client;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:pud@emrubik.com">pu dong</a>
 * @version $Revision 1.0 $ 2016年2月23日 下午6:12:32
 */
@Component
public class TcpClient extends IoHandlerAdapter {

	private static final int PORT = 9999;

	private IoConnector connector;

	private IoSession session;

	@PostConstruct
	public void init() {
		new Thread(new Runnable() {
			public void run() {
				try {
					connector = new NioSocketConnector();
					connector.setHandler(TcpClient.this);
					// DefaultIoFilterChainBuilder chain =
					// connector.getFilterChain();
					// chain.addLast("codec", new ProtocolCodecFilter(new
					// TextLineCodecFactory()));
					connect();
					while (true) {
						if (session == null || !session.isConnected()) {
							Thread.sleep(5 * 1000);
							connect();
						} else {
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();
	}

	private void connect() {
		ConnectFuture connFuture = connector
				.connect(new InetSocketAddress("localhost", PORT));
		connFuture.awaitUninterruptibly();
		if (connFuture.isConnected()) {
			this.session = connFuture.getSession();
		}
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
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		super.exceptionCaught(session, cause);
	}
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		super.messageReceived(session, message);
	}

	public void sent(byte[] msg) {
		if (!session.isConnected()) {
			throw new RuntimeException(
					"connection is close,can not sent message");
		}
		IoBuffer buffer = IoBuffer.allocate(msg.length).put(msg).flip();
		session.write(buffer).isWritten();
	}

}
