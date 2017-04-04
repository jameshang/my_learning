package com.jh.myweb;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaDataTest {

	private static Logger log = LoggerFactory.getLogger(ProxyTest.class);
	private static String SSS = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";

	@Test
	public void testMap() {
		Map<Object, Object> map = new HashMap<>();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 500000; i++) {
			String k = SSS + SSS + i;
			String v = k;
			map.put(k, v);
		}
		log.info("T: " + (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		Object value = map.get(SSS + SSS + 1);
		log.info((System.currentTimeMillis() - startTime) + " -- " + value);
	}

}
