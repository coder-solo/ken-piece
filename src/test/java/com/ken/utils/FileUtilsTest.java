package com.ken.utils;

import org.junit.Assert;
import org.junit.Test;

public class FileUtilsTest {

	@Test
	public void testDownLoadFromUrl() {

		String url= "https://iopen-uat.ccic-net.com.cn/management-service/rest/v1/epolicy/download/PJPF20310904009099990002-BB1E6257EF384436B229CD8981A9295A00E647D02219E14A5135CC558215E619";
//		String url= "http://ccicf-img-test.oss-cn-shanghai-finance-1-pub.aliyuncs.com/UW/2020/06/09/14/TJPF20310904009099990002/DEBB952366984951AA7F670647815C4A.pdf?Expires=1592210844&OSSAccessKeyId=LTAIuHOPWRzmt1dK&Signature=r%2FSWkhAIlTfOFb73SIHVioxiYOk%3D";
		String fileName = "testNameNew.pdf";
		String filePath = "D:\\mybat\\insurace\\";
		FileUtils.downLoadFromUrl(url, fileName, filePath);
	}

	// 下载链接会重定向一次
	@Test
	public void testDownLoadFromRedirectUrl() {

		String url= "https://iopen-uat.ccic-net.com.cn/management-service/rest/v1/epolicy/download/PJPF20310904009099990002-BB1E6257EF384436B229CD8981A9295A00E647D02219E14A5135CC558215E619";
//		String url= "http://ccicf-img-test.oss-cn-shanghai-finance-1-pub.aliyuncs.com/UW/2020/06/09/14/TJPF20310904009099990002/DEBB952366984951AA7F670647815C4A.pdf?Expires=1592210844&OSSAccessKeyId=LTAIuHOPWRzmt1dK&Signature=r%2FSWkhAIlTfOFb73SIHVioxiYOk%3D";
		String fileName = "testNameNew.pdf";
		String filePath = "D:\\mybat\\insurace\\";
		FileUtils.downLoadFromRedirectUrl(url, fileName, filePath);
	}
}
