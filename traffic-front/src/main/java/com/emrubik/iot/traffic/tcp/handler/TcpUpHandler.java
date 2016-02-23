/*******************************************************************************
 * @(#)UpHandler.java 2016年2月23日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * emrubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.emrubik.iot.traffic.tcp.handler;

import com.emrubik.iot.traffic.Counter;

/**
 * @author <a href="mailto:pud@emrubik.com">pu dong</a>
 * @version $Revision 1.0 $ 2016年2月23日 上午11:36:00
 */
public class TcpUpHandler {

	private Counter counter = new Counter();

	public TcpUpHandler() {
		counter.start();
	}

}
