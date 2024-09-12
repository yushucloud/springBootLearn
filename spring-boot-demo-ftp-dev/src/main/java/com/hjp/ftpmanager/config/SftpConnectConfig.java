package com.hjp.ftpmanager.config;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @program: ftp-manager
 * @description: 配置类
 * 注意：JSch登录sftp会进行Kerberos username 身份验证提示
 * 如果需要跳过，需要添加配置如下：
 * config.put("PreferredAuthentications","publickey,keyboard-interactive,password");
 * @author: jumper Hou
 * @create: 2022/10/27 14:05
 **/

@Configuration
@Slf4j
public class SftpConnectConfig {

    /**
     * FTP 登录用户名
     */
    @Value("${remoteserver.username}")
    private String username;
    /**
     * FTP 登录密码
     */
    @Value("${remoteserver.password}")
    private String password;

    /**
     * FTP 服务器地址IP地址
     */
    @Value("${remoteserver.host}")
    private String host;

    /**
     * FTP 端口
     */
    @Value("${remoteserver.port}")
    private String strPort;

    private Session getSession() throws JSchException {
        JSch jsch = new JSch();
        int port = Integer.parseInt(strPort.trim());
        Session session = jsch.getSession(username, host, port);
        if (password != null) {
            session.setPassword(password);
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        // JSch登录sftp，跳过 Kerberos username 身份验证提示
        config.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
        session.setConfig(config);
        session.connect();
        return session;
    }

    /**
     * 连接sftp服务器,返回的是sftp连接通道，用来操纵文件
     *
     * @throws Exception
     */
    @Bean
    public ChannelSftp channelSftp() {
        ChannelSftp sftp = null;
        try {
            Session session = getSession();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            log.error("连接失败", e);
        }
        return sftp;
    }

    /**
     * 连接sftp服务器，返回exec连接通道，可以远程执行命令
     *
     * @throws Exception
     */
    @Bean
    public ChannelExec channelExec() {
        ChannelExec sftp = null;
        try {
            Session session = getSession();
            Channel channel = null;
            channel = session.openChannel("exec");
            channel.connect();
            sftp = (ChannelExec) channel;
        } catch (JSchException e) {
            log.error("连接失败", e);
            System.out.println("连接失败");
        }
        return sftp;
    }
}