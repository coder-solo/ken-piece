package com.ken.thread;

public class CustomExecutor implements Runnable {

	@Override
	public void run() {
		System.out.println("Start Runnable.");

		if (!CustomQueue.queue.isEmpty()) {
			String task = CustomQueue.queue.poll();
			CustomQueue.countDownLatch.countDown();
			
			try {
				System.out.println("Runnable:" + task + "停止10秒钟");
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runnable:" + task);
		}
//		System.out.println("End Runnable.");
	}
}
