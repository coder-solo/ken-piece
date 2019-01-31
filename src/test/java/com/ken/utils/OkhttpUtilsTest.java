package com.ken.utils;

import org.junit.Test;

import com.ken.utils.OkhttpUtils;

public class OkhttpUtilsTest {

	// 同步Get
	@Test
	public void testGetSync() {
		String url = "http://ip.taobao.com/service/getIpInfo.php?ip=119.119.115.25";
		String result = OkhttpUtils.getSync(url);
		System.out.println(result);
	}

	// 异步Get
	@Test
	public void testHttpBase() {
		String url = "http://ip.taobao.com/service/getIpInfo.php?ip=119.119.115.25";
		OkhttpUtils.getAsyn(url);
		// 等待异步消息返回
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 同步Post
	@Test
	public void testPostSync() {
		// 注，此url无post方式，请换成自己使用的post
		String url = "http://ip.taobao.com/service/getIpInfo.php";
		String params = "{\"ip\":\"119.119.115.25\"}";
		String result = OkhttpUtils.post(url, params);
		System.out.println(result);
	}
}
