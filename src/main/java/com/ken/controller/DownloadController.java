package com.ken.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping(value = "/download")
public class DownloadController {

    @ApiOperation(value = "file1", notes = "模板下载测试", produces = "application/octet-stream")
    @GetMapping("/file1")
    public String downloadZip(HttpServletRequest request, HttpServletResponse response) {

        try {
            downTemplate(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "S";
    }

    // 注：produces="application/octet-stream"如不添加，swagger下载会出问题
    @ApiOperation(value = "file2", notes = "模板下载测试", produces = "application/octet-stream")
    @GetMapping("/file2")
    public ResponseEntity<Resource> download() throws Exception {
        File file = ResourceUtils.getFile("classpath:template/downloadDemo.txt");
        String fileName = new String("下载文件.txt".getBytes("UTF-8"), "ISO8859-1");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        ResponseEntity<Resource> result = ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
        return result;
    }

    // 下载文件
    private String downTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File f = ResourceUtils.getFile("classpath:template/downloadDemo.txt");
        try {
            String fileName = new String("下载文件.txt".getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentType("application/octet-stream");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
/*			bis = new BufferedInputStream(new FileInputStream(f));
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();*/
            IOUtils.copy(new FileInputStream(f), out);
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }
}
