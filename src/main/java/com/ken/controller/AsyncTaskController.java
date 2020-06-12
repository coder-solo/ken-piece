package com.ken.controller;

import com.ken.service.IAsyncTaskService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.stream.Stream;

@RestController
public class AsyncTaskController {

	@Autowired
	private IAsyncTaskService asyncTaskService;

	@ApiOperation(value = "handle-async-task", notes = "异步线程池处理")
	@ApiImplicitParam(name = "value", value = "内容，多个使用逗号隔开", paramType = "query", required = true, dataType = "string")
	@GetMapping("/handle-async-task")
	public void handleAsyncTask(String value) {

		String[] valueArrays = Optional.ofNullable(value).map(v -> v.split(",")).orElse(new String[]{});
		Stream.of(valueArrays).forEach(v -> asyncTaskService.doSomething(v));
	}
}
