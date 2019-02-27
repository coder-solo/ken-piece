package com.ken.design.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 代理方式测试
 *
 * @author ken
 */
public class ProxyTest {

	@Test
	public void testStaticProxy() {
		StaticProxySubject proxy = new StaticProxySubject(new RealSubject());
		proxy.visit();
	}

	@Test
	public void testDynamicProxy() {

		RealSubject realSubject = new RealSubject();
		InvocationHandler realHandler = new DynamicProxy(realSubject);
		ISubject isubject = (ISubject) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(), new Class[]{ISubject.class}, realHandler);
		isubject.visit();
	}

	@Test
	public void testDynamicProxyEx() {

		RealSubject realSubject = new RealSubject();
		DynamicProxy<ISubject> realHandler = new DynamicProxy<ISubject>(realSubject);
		ISubject isubject = realHandler.getNewInstance(ISubject.class);
		isubject.visit();
	}
}
