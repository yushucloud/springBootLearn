package com.hjp.ftpmanager.domain.bo;

import lombok.Data;

/**
 * @program: ftp-manager
 * @description: 下载ftpBO，专用于工具类传参
 * @author: jumper Hou
 * @create: 2022/9/13 9:13
 **/
@Data
public class FtpDownloadBO {
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
     * FTP相对路径--->指定文件夹路径
     */
    private String remotePath;
    /**
     * 要下载的文件名
     */
    private String fileName;
    /**
     * 下载后保存到本地的路径
     */
    private String localPath;
}
