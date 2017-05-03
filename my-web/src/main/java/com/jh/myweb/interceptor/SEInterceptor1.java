package com.jh.myweb.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SEInterceptor1 implements InvocationHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private Object instance;

	public SEInterceptor1(Object instance) {
		this.instance = instance;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object rtn = null;
		log.info("Before ===");
		rtn = method.invoke(this.instance, args);
		log.info("After ===");
		return rtn;
	}

}
