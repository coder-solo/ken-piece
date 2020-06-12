package com.ken.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.ken.service.IRedisService;
import com.ken.utils.CharacterUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedisServiceImpl implements IRedisService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	@Cacheable(value = "userNames",key = "'code_' + #code")
	public String getName(String code) {
		
		String randomName = "Name" + CharacterUtils.randomNumber(3);
		System.out.println("getName:" + randomName);
		return randomName;
	}

	// 删除keys(e.dict:*)
	private void deleteKeys(String keys) {
		List<String> keyList = scanKeys(keys);
		stringRedisTemplate.delete(keyList);
	}

	// 遍历获取keys
	private List<String> scanKeys(String keys) {
		return stringRedisTemplate.execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) {

				Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(keys + "*").count(10000).build());
				List<String> keyList = new ArrayList<>();
				while (cursor.hasNext()) {
					byte[] key = cursor.next();
					String keyStr = new String(key, StandardCharsets.UTF_8);
					keyList.add(keyStr);
				}
				return keyList;
			}
		});
	}
}
