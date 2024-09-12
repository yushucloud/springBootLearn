package com.hjp.ftpmanager.controller;

import com.hjp.ftpmanager.domain.bean.ResultBean;
import com.hjp.ftpmanager.service.ContentManageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


/**
 * @program: ftp-manager
 * @description: 这是一个测试类
 * @author: jumper Hou
 * @create: 2022/9/8 17:04
 **/
@RestController
@RequestMapping("/Ftp")
public class TestController {

    @Resource
    private ContentManageService contentManageService;


    /**
     * 这是一个测试接口
     *
     * @return null
     */
    @GetMapping("/test")
    public ResultBean<String> test() {
        return ResultBean.success("hello world!");
    }

    /**
     * 测试上传文件
     *
     * @param file 文件
     * @return 文件上传成功后的路径
     */
    @PostMapping("/upload")
    public ResultBean<String> testUpload(@RequestBody MultipartFile file) {
        return ResultBean.success(contentManageService.uploadPicture(file));
    }

    /**
     * 测试下载文件
     *
     * @return null 没什么必要返回，根据resultBean的code去判断即可
     */
    @PostMapping("/download")
    public ResultBean<String> testDownload() {
        return ResultBean.success(contentManageService.downloadPicture());
    }

    /**
     * FTP复制
     * 测试从A服务器读文件复制到B服务器
     *
     * @return 没什么必要返回
     */
    @PostMapping("/readAndWrite")
    public ResultBean<String> testReadAndWrite() {
        return ResultBean.success(contentManageService.readFileAndWrite());
    }
}
