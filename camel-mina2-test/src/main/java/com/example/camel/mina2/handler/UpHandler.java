/*******************************************************************************
 * @(#)UpHandler.java 2016年2月23日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * emrubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.camel.mina2.handler;

import org.apache.camel.Header;
import org.apache.camel.component.mina2.Mina2Constants;
import org.apache.mina.core.session.IoSession;

import com.example.camel.mina2.Counter;

/**
 * @author <a href="mailto:pud@emrubik.com">pu dong</a>
 * @version $Revision 1.0 $ 2016年2月23日 上午11:36:00
 */
public class UpHandler {

	private Counter counter = new Counter();

	public UpHandler() {
		counter.start();
	}

	public void handle(String body,
			@Header(Mina2Constants.MINA_IOSESSION) IoSession session) {
		// System.out.println(body);
		// session.write("888");
		counter.add();
	}

}
