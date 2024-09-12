package com.hjp.ftpmanager.controller;

import com.hjp.ftpmanager.service.impl.SftpManageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @program: ftp-manager
 * @description:
 * @author: jumper Hou
 * @create: 2022/10/27 14:07
 **/
@RestController
@Slf4j
@RequestMapping("/Sftp")
public class SftpController {
    @Resource
    private SftpManageServiceImpl sftpManageService;

    @GetMapping("/download")
    public void download(@RequestParam(required = true) String file, @RequestParam(required = true)String path,
                         HttpServletResponse response){
        //设置响应信息
        response.setContentType("application/octet-stream");
        // filename为文件下载后保存的文件名，可自行设置，但是注意文件名后缀，要和原来的保持一致
        response.setHeader("Content-Disposition", "attachment; filename=" + file);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            // 输出到客户端
            out.write(sftpManageService.download(path, file));
        } catch (Exception e) {
            log.error("",e);
        }
    }

    /**
     * 上传文件到服务器
     * @param file 要上传到服务器的文件，注意此处的path必须在结尾添加 /
     * @param path 上传到服务器的路径
     */
    @PostMapping("/upload")
    public void upload(@RequestBody(required = true) MultipartFile file, @RequestParam(required = true) String path){
        try {
            sftpManageService.upload(file, path);
        } catch (Exception e) {
            log.error("",e);
        }
    }

}
