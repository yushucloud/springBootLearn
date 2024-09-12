package com.hjp.ftpmanager.domain.bo;

import lombok.Data;

import java.io.InputStream;

/**
 * @program: ftp-manager
 * @description: 上传ftpBO，专用于工具类传参
 * @author: jumper Hou
 * @create: 2022/9/9 17:48
 **/
@Data
public class FtpUploadBO {
    /**
     * FTP服务器host
     */
    private String host;
    /**
     * FTP服务器端口
     */
    private String port;
    /**
     * FTP登录账号
     */
    private String username;
    /**
     * FTP登录密码
     */
    private String password;
    /**
     * FTP服务器基础目录
     */
    private String basePath;
    /**
     * FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     */
    private String filePath;
    /**
     * 上传到FTP服务器上的文件名
     */
    private String filename;
    /**
     * 文件流--》输入流，即为用户传参
     */
    private InputStream inputStream;
}
