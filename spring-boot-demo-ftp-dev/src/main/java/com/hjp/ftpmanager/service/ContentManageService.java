package com.hjp.ftpmanager.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @program: ftp-manager
 * @description: 内容管理service
 * @author: jumper Hou
 * @create: 2022/9/8 17:36
 **/
public interface ContentManageService {
    /**
     * 上传文件
     *
     * @param uploadFile 文件
     * @return 上传成功的路径
     */
    String uploadPicture(MultipartFile uploadFile);


    /**
     * 侧事故下载文件
     *
     * @return null
     */
    String downloadPicture();

    /**
     * FTP复制
     * 从A服务器读文件写到B服务器
     *
     * @return null
     */
    String readFileAndWrite();
}
