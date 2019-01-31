package com.ken.funning;

/**
 * Null对象的参数
 * 
 * @author ken
 */
public class Null {

	public static String test = "abc";

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Null n = null;
		System.out.println(n.test);
	}
}
