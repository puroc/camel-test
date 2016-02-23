/*******************************************************************************
 * @(#)MyFilter.java 2016年2月23日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * emrubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.camel.mina2;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:pud@emrubik.com">pu dong</a>
 * @version $Revision 1.0 $ 2016年2月23日 下午1:44:59
 */
public class TcpListener extends IoFilterAdapter {

	private static Logger Log = LoggerFactory.getLogger(TcpListener.class);

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session,
			Object message) throws Exception {
		super.messageReceived(nextFilter, session, message);
	}

	@Override
	public void sessionClosed(NextFilter nextFilter, IoSession session)
			throws Exception {
		super.sessionClosed(nextFilter, session);
		Log.error("sessionClosed!!!!!!!");
	}

	@Override
	public void exceptionCaught(NextFilter nextFilter, IoSession session,
			Throwable cause) throws Exception {
		super.exceptionCaught(nextFilter, session, cause);
		Log.error("filter exceptionCaught", cause);
	}
}
