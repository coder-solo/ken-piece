package com.ken.rest;

import com.ken.utils.FileUtils;
import lombok.Cleanup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RestTest {

	@Resource(name = "restTemplate")
	private RestTemplate restTemplate;

	@Test
	public void testGet() {

		String url = "https://iopen.ccic-net.com.cn/management-service/rest/v1/epolicy/download/PJPF20310904000000002569-14FC96AE240266D87EC1C68614317FE2D0E10DFCA5A7EA089290240F96882E85";

		String result = doGet(url);
		System.out.println(result);
	}

	@Test
	public void testDoDownload() {

//		String url= "https://iopen-uat.ccic-net.com.cn/management-service/rest/v1/epolicy/download/PJPF20310904009099990002-BB1E6257EF384436B229CD8981A9295A00E647D02219E14A5135CC558215E619";
		String url= "http://ccicf-img-test.oss-cn-shanghai-finance-1-pub.aliyuncs.com/UW/2020/06/09/14/TJPF20310904009099990002/DEBB952366984951AA7F670647815C4A.pdf?Expires=1592214243&OSSAccessKeyId=LTAIuHOPWRzmt1dK&Signature=uCl3%2BAb%2FKanD8TaA8buPO3FB5qE%3D";
		String fileName = "testNameNew.pdf";
		String filePath = "D:\\mybat\\insurace\\";

		// 403 Forbidden 未解决 TODO
		doDownload(url, fileName, filePath);
	}

	@Test
	public void testUpload() throws IOException {

		String fileName = "ceshi.png";
		File file = new File("D:\\mybat\\esign\\融资租赁合同.pdf");
		MultipartFile mulFile = new MockMultipartFile(
				fileName, //文件名
				fileName, //originalName 相当于上传文件在客户机上的文件名
//				ContentType.APPLICATION_OCTET_STREAM.toString(), //文件类型
				"APPLICATION_OCTET_STREAM", //文件类型
				new FileInputStream(file) //文件流
		);

		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("fileName", fileName);
		param.add("file", transByteArrayOs(fileName, new FileInputStream(file)));

		String url = "";

		doPostUpload(url, param);
	}

	// 文件流传输对象转换
	private ByteArrayResource transByteArrayOs(String fileName, InputStream is) {

		try {
			@Cleanup
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			while (is.read(buffer) > 0) {
				bos.write(buffer);
			}
			return new ByteArrayResource(bos.toByteArray()) {
				@Override
				public String getFilename() throws IllegalStateException {
					return fileName;
				}
			};
		} catch (IOException e) {
			throw new RuntimeException("FILE_TRANS_ERROR");
		}
	}

	private void doDownloadEx(String url, String fileName, String filePath) {

		restTemplate.execute(url, HttpMethod.GET, null, clientHttpResponse -> {
			FileUtils.createDirectory(filePath);
			File file = new File(filePath + File.separator + fileName);
			StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(file));
			return file;
		});
	}

	// 调用接口
	private void doDownload(String url, String fileName, String filePath) {

		FileOutputStream fos = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			List list = new ArrayList<>();
			list.add(MediaType.APPLICATION_PDF);
			headers.setAccept(list);
			// conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//			headers.add("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
			ResponseEntity<byte[]> response = restTemplate.exchange(
					url,
					HttpMethod.GET,
					new HttpEntity<byte[]>(headers),
					byte[].class);
			byte[] result = response.getBody();

			System.out.println("result:" + result);
			FileUtils.createDirectory(filePath);
			File file = new File(filePath + File.separator + fileName);

			fos = new FileOutputStream(file);
			fos.write(result);

			FileUtils.closeStream(fos);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("URL_ERROR");
		} finally {
			FileUtils.closeStream(fos);
		}

	}

	// 调用接口
	private String doGet(String url) {

		String result = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			ResponseEntity<String> response = restTemplate.exchange(
					url,
					HttpMethod.GET,
					new HttpEntity<String>(headers),
					String.class);
			result = response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("URL_ERROR");
		}

		return result;
	}

	// 调用接口
	private String doPut(String url, String requestJson) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

		String result = null;
		try {
			ResponseEntity<String> response = restTemplate.exchange(
					url,
					HttpMethod.PUT,
					entity,
					String.class);
			result = response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("URL_ERROR");
		}

		return result;
	}

	// 调用接口
	private String doPost(String url, String requestJson) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

		String result = null;
		try {
			result = restTemplate.postForObject(url, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("URL_ERROR");
		}

		return result;
	}

	// 调用接口
	private String doPostUpload(String url, MultiValueMap<String, Object> param) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("multipart/form-data; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.ALL_VALUE.toString());
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(param, headers);

		String result = null;
		try {
			ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
			result = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("URL_ERROR");
		}

		return result;
	}
}
