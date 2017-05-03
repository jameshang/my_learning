package com.jh.myweb.interceptor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class SEInterceptor2 implements MethodInterceptor {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Object intercept(Object instance, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object rtn = null;
		log.info("Before ===");
		rtn = proxy.invokeSuper(instance, args);
		log.info("After ===");
		return rtn;
	}

}
