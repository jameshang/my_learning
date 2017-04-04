package com.jh.myweb.interceptor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javassist.util.proxy.MethodHandler;

public class SEInterceptor3 implements MethodHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private Object instance;

	public SEInterceptor3(Object instance) {
		this.instance = instance;
	}

	@Override
	public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
		Object rtn = null;
		log.info("Before ===");
		rtn = thisMethod.invoke(this.instance, args);
		log.info("After ===");
		return rtn;
	}

}
