package com.ken.thread;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class CustomQueue {

	public static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
	
	public static CountDownLatch countDownLatch = new CountDownLatch(10);
}
