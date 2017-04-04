package com.jh.myweb;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jh.myweb.interceptor.SEInterceptor1;
import com.jh.myweb.interceptor.SEInterceptor2;
import com.jh.myweb.interceptor.SEInterceptor3;
import com.jh.myweb.service.Architect;
import com.jh.myweb.service.Programmer;
import com.jh.myweb.service.impl.SoftwareEngineer;

import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import net.sf.cglib.proxy.Enhancer;

public class ProxyTest {

	private static Logger log = LoggerFactory.getLogger(ProxyTest.class);

	@Test
	public void testFoo() {
		byte[] bs = new byte[1];
		log.info(bs.getClass().getCanonicalName());
	}

	@Test
	public void testProxy() {
		SoftwareEngineer se = new SoftwareEngineer();
		InvocationHandler handler = new SEInterceptor1(se);
		Class<?>[] interfaces = new Class[] { Programmer.class, Architect.class };
		Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, handler);
		Programmer programmer = (Programmer) proxy;
		Architect architect = (Architect) proxy;
		String s1 = programmer.code("function");
		String s2 = architect.design("system");
		log.info(s1);
		log.info(s2);
	}

	@Test
	public void testCglib() {
		SEInterceptor2 handler = new SEInterceptor2();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(SoftwareEngineer.class);
		enhancer.setCallback(handler);
		Object proxy = enhancer.create();
		Programmer programmer = (Programmer) proxy;
		Architect architect = (Architect) proxy;
		String s1 = programmer.code("function");
		String s2 = architect.design("system");
		log.info(s1);
		log.info(s2);
	}

	@Test
	public void testJavassist() {
		try {
			SoftwareEngineer se = new SoftwareEngineer();
			SEInterceptor3 handler = new SEInterceptor3(se);
			ProxyFactory proxyFactory = new ProxyFactory();
			proxyFactory.setSuperclass(SoftwareEngineer.class);
			Class<?> clazz = proxyFactory.createClass();
			ProxyObject proxy = (ProxyObject) clazz.newInstance();
			proxy.setHandler(handler);
			Programmer programmer = (Programmer) proxy;
			Architect architect = (Architect) proxy;
			String s1 = programmer.code("function");
			String s2 = architect.design("system");
			log.info(s1);
			log.info(s2);
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
