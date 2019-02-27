package com.ken.design.proxy;

public class RealSubject implements ISubject {

	public void visit() {
		System.out.println("Real Subject : visit");
	}
}
