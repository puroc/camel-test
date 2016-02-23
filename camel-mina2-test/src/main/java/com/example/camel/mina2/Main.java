/*******************************************************************************
 * @(#)Main.java 2016年2月23日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * emrubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.camel.mina2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author <a href="mailto:pud@emrubik.com">pu dong</a>
 * @version $Revision 1.0 $ 2016年2月23日 上午11:00:00
 */
public class Main {

	private static Logger Log = LoggerFactory.getLogger(Main.class);

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			new ClassPathXmlApplicationContext("main.xml");
		} catch (Throwable t) {
			Log.error("", t);
		}
	}
}
