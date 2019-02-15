package com.ken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ken.service.IRedisService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class RedisController {

	@Autowired
	private IRedisService redisService;

	@ApiOperation(value = "getName", notes = "测试Redis缓存")
	@ApiImplicitParam(name = "code", value = "Code", paramType = "query", required = true, dataType = "string")
	@GetMapping("/getName")
	public String getName(String code) {
		return redisService.getName(code);
	}
}
