package com.ken.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CustomService {

	public void processTask() {
		
		System.out.println("Start thread pool.");
		
		List<String> contentList = new ArrayList<String>();
		for(int i = 0; i < 10; i++) {
			contentList.add("test" + i);
		}
		
		// 赋值到queue
		contentList.stream().forEach(c-> {
			CustomQueue.queue.offer(c);
		});
		
		CustomQueue.countDownLatch = new CountDownLatch(contentList.size());
		
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
		//scheduledThreadPool.schedule(new CustomExecutor(), 3, TimeUnit.SECONDS);
		
		scheduledThreadPool.scheduleAtFixedRate(new CustomExecutor(), 1, 5, TimeUnit.SECONDS);
		
		
		System.out.println("Await thread pool.");
		try {
			CustomQueue.countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		scheduledThreadPool.shutdown();
		
		System.out.println("End thread pool.");
	}
	
	public static void main(String args[]) {
		
//		Thread t = new Thread(new CustomExecutor());
//		t.start();
		
		CustomService cs = new CustomService();
		cs.processTask();
	}
}
