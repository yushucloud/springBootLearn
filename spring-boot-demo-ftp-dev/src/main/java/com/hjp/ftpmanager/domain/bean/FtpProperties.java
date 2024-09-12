package com.hjp.ftpmanager.domain.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: ftp-manager
 * @description: ftp基础配置参数，取yml文件中统一配置
 * @author: jumper Hou
 * @create: 2022/9/9 9:46
 **/
@Data
@Component
@ConfigurationProperties(prefix = "ftp")
public class FtpProperties {
    /**
     * 提供方ftp服务器地址
     */
    private String providerFtpAddress;
    /**
     * 提供方ftp服务器端口
     */
    private String providerFtpPort;
    /**
     * 提供方连接ftp服务器的账号
     */
    private String providerFtpUserName;
    /**
     * 提供方连接ftp服务器的密码
     */
    private String providerFtpPassword;
    /**
     * 提供方FTP服务器基础目录 （不写指向的是ftp服务器的根目录）
     */
    private String providerFtpBasePath;
    /**
     * 提供方ftp图片库访问地址
     */
    private String providerImageBaseUrl;
    /**
     * 写入方ftp服务器地址
     */
    private String consumerFtpAddress;
    /**
     * 写入方ftp服务器端口
     */
    private String consumerFtpPort;
    /**
     * 写入方连接ftp服务器的账号
     */
    private String consumerFtpUserName;
    /**
     * 写入方连接ftp服务器的密码
     */
    private String consumerFtpPassword;
    /**
     * 写入方FTP服务器基础目录 （不写指向的是ftp服务器的根目录）
     */
    private String consumerFtpBasePath;
    /**
     * 写入方ftp图片库访问地址
     */
    private String consumerImageBaseUrl;
}
