package com.ken.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisSubscriber extends MessageListenerAdapter {

	// // 订阅模式消息消费者监听配置
	@Override
	public void onMessage(Message message, byte[] pattern) {

		String patternStr = new String(pattern);
		log.info("Receive order info, pattern:" + patternStr + ",message:" + message);
		if (RedisConfig.CUSTOM_TOPIC.equalsIgnoreCase(patternStr)) {
			// do something
		}
	}
}
