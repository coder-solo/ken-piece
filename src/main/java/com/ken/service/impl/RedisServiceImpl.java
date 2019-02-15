package com.ken.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ken.service.IRedisService;
import com.ken.utils.CharacterUtils;

@Service
public class RedisServiceImpl implements IRedisService {

	@Override
	@Cacheable(value = "userNames",key = "'code_' + #code")
	public String getName(String code) {
		
		String randomName = "Name" + CharacterUtils.randomNumber(3);
		System.out.println("getName:" + randomName);
		return randomName;
	}
}
