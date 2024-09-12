package com.hjp.ftpmanager.service.impl;

import com.hjp.ftpmanager.service.SftpManageService;
import com.jcraft.jsch.ChannelSftp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * @program: ftp-manager
 * @description:
 * @author: jumper Hou
 * @create: 2022/10/27 14:08
 **/
@Service
@Slf4j
public class SftpManageServiceImpl implements SftpManageService {
    @Resource
    private ChannelSftp channelSftp;

    /**
     * 从服务器获取文件并返回字节数组
     * @param path 要下载文件的路径
     * @param file 要下载的文件
     */
    public byte[] download(String path, String file) throws Exception {
        // 切换到文件所在目录
        channelSftp.cd(path);
        //获取文件并返回给输入流,若文件不存在该方法会抛出常
        InputStream is = channelSftp.get(file);
        byte[] fileData = IOUtils.toByteArray(is);
        if(is != null){
            is.close();
        }
        return fileData;
    }

    /**
     * 将输入流的数据上传到sftp作为文件
     *
     * @param path
     *            上传到该目录
     * @param uploadFile
     *           服务器保存的文件
     * @throws Exception
     */
    public void upload(MultipartFile uploadFile, String path) throws Exception{
        String fileName = uploadFile.getOriginalFilename();
        // 用uuid + 原来的文件名生成新名字，防止文件名重复也可以辨识上传的文件是哪个,可以省略这一步
        String newName = UUID.randomUUID().toString().replaceAll("-","") + fileName;
        File file = new File(path + newName);
        //将MultipartFilez转换为File，会生成文件
        FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), file);
        // 如果该目录不存在则直接创建新的目录，并切换到该目录
        try {
            channelSftp.cd(path);
        } catch (Exception e) {
            channelSftp.mkdir(path);
            channelSftp.cd(path);
        }
        channelSftp.put(new FileInputStream(file), newName);
        // 操作完成，删除刚刚生成的文件
        file.delete();
    }
}
