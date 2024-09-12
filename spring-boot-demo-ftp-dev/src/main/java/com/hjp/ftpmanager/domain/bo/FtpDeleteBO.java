package com.hjp.ftpmanager.domain.bo;

import lombok.Data;

/**
 * @program: cms-server
 * @description: ftp删除文件bo
 * @author: jumper Hou
 * @create: 2022/9/16 14:52
 **/
@Data
public class FtpDeleteBO {
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
     * 要删除的文件名
     */
    private String fileName;
}
