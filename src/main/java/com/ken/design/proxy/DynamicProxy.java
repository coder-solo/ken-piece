package com.ken.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy<T> implements InvocationHandler {

	private T object;

	public DynamicProxy(T object) {
		this.object = object;
	}

	public T getNewInstance(Class c) {
		return (T) Proxy.newProxyInstance(c.getClassLoader(), new Class[]{c}, this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		doBefore();
		method.invoke(object, args);
		doAfter();
		return null;
	}

	public void doBefore() {
		System.out.println("DynamicProxy: do something before.");
	}

	public void doAfter() {
		System.out.println("DynamicProxy: do something after.");
	}
}
