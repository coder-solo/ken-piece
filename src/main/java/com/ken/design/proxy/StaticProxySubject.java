package com.ken.design.proxy;

public class StaticProxySubject implements ISubject {

	private RealSubject realSubject;

	public StaticProxySubject(ISubject iSubject) {

		if (iSubject instanceof RealSubject) {
			this.realSubject = (RealSubject) iSubject;
		}
	}

	@Override
	public void visit() {
		System.out.println("StaticProxySubject: do something before.");
		realSubject.visit();
		System.out.println("StaticProxySubject: do something after.");
	}
}
