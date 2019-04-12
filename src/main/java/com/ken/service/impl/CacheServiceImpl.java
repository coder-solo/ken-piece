package com.ken.service.impl;

import com.ken.service.ICacheService;
import com.ken.utils.CharacterUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements ICacheService {

	@Override
	@Cacheable(value = "userNames",key = "'code_' + #code")
	public String getCacheName(String code) {
		
		String randomName = "Name" + CharacterUtils.randomNumber(3);
		System.out.println("getName:" + randomName);
		return randomName;
	}
}
