package com.enjoyu.admin.controller;

import com.enjoyu.admin.common.CommonResponse;
import org.apache.commons.io.IOUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

@Controller
@RequestMapping("file")
public class UploadController {

    @Value("${file.upload.path}")
    private String uploadFilePath;

    @PostMapping("upload")
    @ResponseBody
    public CommonResponse<String> upload(@RequestParam("file") MultipartFile file) throws URISyntaxException, IOException {
        if (file.isEmpty()) {
            return CommonResponse.error("文件内容为空");
        }
        String fileName = file.getOriginalFilename();
        File destFile = new File(uploadFilePath, fileName);
        file.transferTo(destFile);
        return CommonResponse.success("文件上传成功");
    }

    @RequestMapping("download")
    public void download(HttpServletResponse response, @RequestParam String fileName) throws IOException {
        File file = new File(uploadFilePath, fileName);
        if (!file.exists()) {
            return;
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ServletOutputStream outputStream = response.getOutputStream()) {
            IOUtil.copy(fileInputStream, outputStream);
            outputStream.flush();
        }
    }
}
