package com.hjp.ftpmanager.service.impl;


import com.hjp.ftpmanager.domain.bean.ErrorCode;
import com.hjp.ftpmanager.domain.bean.FtpProperties;
import com.hjp.ftpmanager.domain.bo.FtpCopyBO;
import com.hjp.ftpmanager.domain.bo.FtpDownloadBO;
import com.hjp.ftpmanager.domain.bo.FtpUploadBO;
import com.hjp.ftpmanager.service.ContentManageService;
import com.hjp.ftpmanager.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * @program: ftp-manager
 * @description: 内容管理service
 * @author: jumper Hou
 * @create: 2022/9/8 17:37
 **/
@Service
@Slf4j
public class ContentManageServiceImpl implements ContentManageService {

    @Resource
    private FtpUtil ftpUtil;
    @Resource
    private FtpProperties ftpProperties;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");


    /**
     * 上传文件
     *
     * @param uploadFile 文件
     * @return 上传成功的路径
     */
    @Override
    public String uploadPicture(MultipartFile uploadFile) {

        log.info("上传文件=====start");
        FtpUploadBO ftpUploadBO = new FtpUploadBO();
        ftpUploadBO.setHost(ftpProperties.getProviderFtpAddress());
        ftpUploadBO.setPort(ftpProperties.getProviderFtpPort());
        ftpUploadBO.setUsername(ftpProperties.getProviderFtpUserName());
        ftpUploadBO.setPassword(ftpProperties.getProviderFtpPassword());
        ftpUploadBO.setBasePath(ftpProperties.getProviderFtpBasePath());
        ftpUploadBO.setFilePath(simpleDateFormat.format(new Date()));

        //生成一个新的文件名
        String oldName = uploadFile.getOriginalFilename();
        String suffix = oldName.substring(oldName.lastIndexOf("."));
        String newName = UUID.randomUUID() + suffix;
        ftpUploadBO.setFilename(newName);

        try {
            //这个流本来应该处理异常的，这里取巧直接放在try里
            ftpUploadBO.setInputStream(uploadFile.getInputStream());
            boolean result = ftpUtil.uploadFile(ftpUploadBO);
            if (!result) {
                log.error(ErrorCode.FtpUploadErrorCode.FILE_UPLOAD_FAILED);
            }
            log.info("上传文件=====end");
            return ftpProperties.getProviderImageBaseUrl() + ftpUploadBO.getFilePath() + "/" + newName;
        } catch (Exception e) {
            log.error(ErrorCode.FtpUploadErrorCode.FILE_UPLOAD_ERROR_OCCURRED, e);
            return null;
        }
    }

    /**
     * 测试下载文件
     */
    @Override
    public String downloadPicture() {
        log.info("下载文件=====start");
        FtpDownloadBO ftpDownloadBO = new FtpDownloadBO();
        ftpDownloadBO.setHost(ftpProperties.getProviderFtpAddress());
        ftpDownloadBO.setPort(ftpProperties.getProviderFtpPort());
        ftpDownloadBO.setUsername(ftpProperties.getProviderFtpUserName());
        ftpDownloadBO.setPassword(ftpProperties.getProviderFtpPassword());
        //这个路径先用基础目录，后面有需要自行改动
        ftpDownloadBO.setRemotePath(ftpProperties.getProviderFtpBasePath());
        ftpDownloadBO.setFileName("111.jpg");
        ftpDownloadBO.setLocalPath("D:\\Documents\\Desktop\\testFtp");
        try {
            //调用工具类下载方法
            boolean result = ftpUtil.downloadFile(ftpDownloadBO);
            if (!result) {
                log.error(ErrorCode.FtpDownloadErrorCode.FILE_DOWNLOAD_FAILED);
            }
        } catch (Exception e) {
            log.error(ErrorCode.FtpDownloadErrorCode.FILE_DOWNLOAD_ERROR_OCCURRED, e);
        }
        log.info("下载文件=====end");
        return null;
    }

    /**
     * FTP复制
     * 从A服务器读取文件流写入到B服务器
     *
     * @return null
     */
    public String readFileAndWrite() {
        log.info("复制文件=====start");
        FtpCopyBO ftpCopyBO = new FtpCopyBO();
        ftpCopyBO.setProviderHost(ftpProperties.getProviderFtpAddress());
        ftpCopyBO.setProviderPort(ftpProperties.getProviderFtpPort());
        ftpCopyBO.setProviderUsername(ftpProperties.getProviderFtpUserName());
        ftpCopyBO.setProviderPassword(ftpProperties.getProviderFtpPassword());
        ftpCopyBO.setConsumerHost(ftpProperties.getConsumerFtpAddress());
        ftpCopyBO.setConsumerPort(ftpProperties.getConsumerFtpPort());
        ftpCopyBO.setConsumerUsername(ftpProperties.getConsumerFtpUserName());
        ftpCopyBO.setConsumerPassword(ftpProperties.getConsumerFtpPassword());
        //先用基础路径，有需要自行改动
        ftpCopyBO.setRemotePath(ftpProperties.getProviderFtpBasePath());
        ftpCopyBO.setReadName("111.jpg");
        ftpCopyBO.setBasePath(ftpProperties.getConsumerFtpBasePath());
        ftpCopyBO.setFilePath(simpleDateFormat.format(new Date()));
        ftpCopyBO.setSaveName("if_you_can_view_me_That_is_SUCCESS.jpg");

        boolean b = ftpUtil.uploadFileWithoutDownload(ftpCopyBO);
        log.info("复制文件=====end");
        if (!b) {
            log.error(ErrorCode.FtpCopyErrorCode.FILE_COPY_FAILED);
        }
        return null;

    }
}
