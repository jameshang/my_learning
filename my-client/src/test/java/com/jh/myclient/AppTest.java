package com.jh.myclient;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppTest {

	private static final Logger log = LoggerFactory.getLogger(AppTest.class);

	@Before
	public void setUp() {
		log.info("Begin test");
	}

	@After
	public void tearDown() {
		log.info("After test");
	}

}
