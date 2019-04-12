package com.ken.controller;

import com.ken.service.ICacheService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cache")
public class CacheController {

	@Autowired
	private ICacheService cacheService;

	@ApiOperation(value = "name", notes = "测试Redis缓存")
	@ApiImplicitParam(name = "code", value = "Code", paramType = "path", required = true, dataType = "string")
	@GetMapping("/name/{code}")
	public String getName(@PathVariable(name = "code") String code) {
		return cacheService.getCacheName(code);
	}
}
