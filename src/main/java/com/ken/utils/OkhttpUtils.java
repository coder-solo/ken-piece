package com.ken.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpUtils {

	/**
	 * 同步Get请求
	 * 
	 * @param url
	 * @return
	 */
	public static String getSync(String url) {

		OkHttpClient client = new OkHttpClient.Builder().build();
		// 注：User-Agent为ip.taobao接口所必须内容，其他接口不需要
		Request request = new Request.Builder().url(url).addHeader("User-Agent", "").build();
		Call call = client.newCall(request);

		String result = null;
		// call.execute 阻塞方式
		try (Response response = call.execute()) {
			result = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 异步Get请求
	 * 
	 * @param url
	 * @return
	 */
	public static void getAsyn(String url) {

		OkHttpClient client = new OkHttpClient.Builder().build();
		Request request = new Request.Builder().url(url).addHeader("User-Agent", "").build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				System.out.println("onFailure: " + e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				System.out.println("onResponse: " + response.body().string());
			}
		});
	}

	/**
	 * 同步post<br/>
	 * <p>
	 * params可修改为文件、流等格式<br/>
	 * 异步方式同Get
	 * </p>
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, String params) {

		OkHttpClient client = new OkHttpClient.Builder().build();

		MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
		Request request = new Request.Builder().url(url).addHeader("User-Agent", "")
				.post(RequestBody.create(mediaType, params)).build();

		Call call = client.newCall(request);

		String result = null;
		// call.execute 阻塞方式
		try (Response response = call.execute()) {
			result = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
