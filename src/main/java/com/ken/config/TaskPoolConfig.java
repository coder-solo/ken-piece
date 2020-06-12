package com.ken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class TaskPoolConfig {

	@Bean("taskExecutor")
	public Executor taskExecutor() {

		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(1);
		taskExecutor.setMaxPoolSize(2);
		taskExecutor.setQueueCapacity(100); // 处理的内容超过队列上限时，开通新的线程；如果超过队列 * 最大线程，则采用拒绝策略处理。默认报错
		taskExecutor.setKeepAliveSeconds(60);
		taskExecutor.setThreadNamePrefix("taskExecutor--");
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		taskExecutor.setAwaitTerminationSeconds(60);

		// 拒绝策略
//		ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。默认
//		ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
//		ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
//		ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		return taskExecutor;
	}
}
