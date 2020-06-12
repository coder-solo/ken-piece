package com.ken.service.impl;

import com.ken.service.IAsyncTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncTaskServiceImpl implements IAsyncTaskService {

	@Override
	@Async("taskExecutor")
	public void doSomething(String value) {

		// 线程池会依次调用，有多个线程则同时进行调用
		log.info("TaskExecutor start:" + Thread.currentThread().getName() + "-" + value);
		try {
			Thread.sleep(4* 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("TaskExecutor end:" + Thread.currentThread().getName() + "-" + value);
	}
}
