package com.hjp.ftpmanager.domain.bo;

import lombok.Data;

/**
 * @program: ftp-manager
 * @description: 复制ftpBO，专用于工具类传参
 * @author: jumper Hou
 * @create: 2022/9/13 9:41
 **/
@Data
public class FtpCopyBO {
    /**
     * 读服务器的host
     */
    private String providerHost;
    /**
     * 读服务器的端口
     */
    private String providerPort;
    /**
     * 读服务器的用户名
     */
    private String providerUsername;
    /**
     * 读服务器的密码
     */
    private String providerPassword;
    /**
     * 写服务器的host
     */
    private String consumerHost;
    /**
     * 写服务器的端口
     */
    private String consumerPort;
    /**
     * 写服务器的用户名
     */
    private String consumerUsername;
    /**
     * 写服务器的密码
     */
    private String consumerPassword;
    /**
     * 读服务器上的指定目录相对路径
     */
    private String remotePath;
    /**
     * 要读取的文件名
     */
    private String readName;
    /**
     * 写服务器的基础路径
     */
    private String basePath;
    /**
     * 写服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     */
    private String filePath;
    /**
     * 上传到写服务器上的文件名
     */
    private String saveName;

}
