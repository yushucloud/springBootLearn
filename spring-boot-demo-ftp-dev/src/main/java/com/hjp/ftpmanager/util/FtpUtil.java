package com.hjp.ftpmanager.util;


import com.hjp.ftpmanager.domain.bean.ErrorCode;
import com.hjp.ftpmanager.domain.bo.FtpCopyBO;
import com.hjp.ftpmanager.domain.bo.FtpDeleteBO;
import com.hjp.ftpmanager.domain.bo.FtpDownloadBO;
import com.hjp.ftpmanager.domain.bo.FtpUploadBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @program: ftp-manager
 * @description: ftp工具类
 * 本工具类主要包括的ftp增删查及复制的方法,开箱即用;
 * 在编写的过程中,由于前人的约束,所有的业务异常在此都被以log形式记录并直接返回bool值;因为此方法的最终汇流是在多线程的定时任务中;
 * 部分代码存在冗余的情况,是因为凑代码行数,:) 为了碎银几两,如果在我任职期间内没有进行改造,后人在改造的时候少些抱怨,把代码优化一下
 * @author: jumper Hou
 * @create: 2022/9/8 17:28
 **/
@Component
@Slf4j
public class FtpUtil {

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param bo 上传ftpBO
     * @return 成功返回true，否则返回false
     */
    public boolean uploadFile(FtpUploadBO bo) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        ftp.enterLocalPassiveMode();
        try {
            int reply;
            // 连接FTP服务器
            ftp.connect(bo.getHost(), Integer.parseInt(bo.getPort()));
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            // 登录
            ftp.login(bo.getUsername(), bo.getPassword());
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(bo.getBasePath() + bo.getFilePath())) {
                //如果目录不存在创建目录
                String[] dirs = bo.getFilePath().split("/");
                String tempPath = bo.getBasePath();
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) {
                        continue;
                    }
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //校验文件是否已存在
            if (checkFileExists(ftp, bo.getFilename())) {
                log.error(ErrorCode.FtpCheckErrorCode.FILE_EXISTS);
                return result;
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(bo.getFilename(), bo.getInputStream())) {
                log.error(ErrorCode.FtpUploadErrorCode.FILE_UPLOAD_FAILED);
                return result;
            }
            bo.getInputStream().close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    log.error(ErrorCode.FtpConnectCode.FTP_DISCONNECT_ERROR_OCCURRED, ioe);
                }
            }
        }
        return result;
    }

    /**
     * 从FTP服务器下载文件
     *
     * @param bo 下载ftpBO
     * @return true成功 false失败
     */
    public boolean downloadFile(FtpDownloadBO bo) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(bo.getHost(), Integer.parseInt(bo.getPort()));
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            // 登录
            ftp.login(bo.getUsername(), bo.getPassword());
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            // 转移到FTP服务器目录
            ftp.changeWorkingDirectory(bo.getRemotePath());
            //校验文件是否已存在
            if (!checkFileExists(ftp, bo.getFileName())) {
                log.error(ErrorCode.FtpCheckErrorCode.FILE_NO_EXISTS);
                return result;
            }
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(bo.getFileName())) {
                    File localFile = new File(bo.getLocalPath() + "/" + ff.getName());
                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
            ftp.logout();
            result = true;
        } catch (IOException e) {
            log.error(ErrorCode.FtpDownloadErrorCode.FILE_DOWNLOAD_ERROR_OCCURRED, e);
        } finally {
            //关闭ftpClint链接
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    log.error(ErrorCode.FtpConnectCode.FTP_DISCONNECT_ERROR_OCCURRED, ioe);
                }
            }
        }
        return result;
    }


    /**
     * FTP复制
     * 从A服务器读取文件流写入到B服务器
     *
     * @param bo 复制ftpBO
     * @return 成功true 失败false
     */
    public boolean uploadFileWithoutDownload(FtpCopyBO bo) {
        boolean result = false;
        FTPClient providerFtp = new FTPClient();
        FTPClient consumerFtp = new FTPClient();
        try {
            //连接读服务器
            providerFtp.connect(bo.getProviderHost(), Integer.parseInt(bo.getProviderPort()));
            providerFtp.login(bo.getProviderUsername(), bo.getProviderPassword());
            int providerReply = providerFtp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(providerReply)) {
                providerFtp.disconnect();
                return result;
            }

            //连接写服务器
            consumerFtp.connect(bo.getConsumerHost(), Integer.parseInt(bo.getConsumerHost()));
            consumerFtp.login(bo.getConsumerUsername(), bo.getConsumerUsername());
            int consumerReply = consumerFtp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(consumerReply)) {
                consumerFtp.disconnect();
                return result;
            }

            // 转移到读服务器的指定目录
            providerFtp.changeWorkingDirectory(bo.getRemotePath());
            //读服务器是否有指定文件
            if (!checkFileExists(providerFtp, bo.getReadName())) {
                log.error(ErrorCode.FtpCheckErrorCode.FILE_NO_EXISTS);
                return result;
            }
            FTPFile[] fs = providerFtp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(bo.getReadName())) {
                    //获取指定文件名的文件流
                    InputStream fileInputStream = providerFtp.retrieveFileStream(ff.getName());

                    //切换到写服务器的上传目录
                    if (!consumerFtp.changeWorkingDirectory(bo.getBasePath() + bo.getFilePath())) {
                        //如果目录不存在创建目录
                        String[] dirs = bo.getFilePath().split("/");
                        String tempPath = bo.getBasePath();
                        for (String dir : dirs) {
                            if (null == dir || "".equals(dir)) {
                                continue;
                            }
                            tempPath += "/" + dir;
                            if (!consumerFtp.changeWorkingDirectory(tempPath)) {
                                if (!consumerFtp.makeDirectory(tempPath)) {
                                    return result;
                                } else {
                                    consumerFtp.changeWorkingDirectory(tempPath);
                                }
                            }
                        }
                    }
                    //写服务器是否已有同名文件
                    if (checkFileExists(consumerFtp, bo.getSaveName())) {
                        log.error(ErrorCode.FtpCheckErrorCode.FILE_EXISTS);
                        return result;
                    }
                    //设置上传文件的类型为二进制类型
                    consumerFtp.setFileType(FTP.BINARY_FILE_TYPE);
                    //上传文件
                    if (!consumerFtp.storeFile(bo.getSaveName(), fileInputStream)) {
                        log.error(ErrorCode.FtpUploadErrorCode.FILE_UPLOAD_FAILED);
                        return result;
                    }
                    fileInputStream.close();
                }
            }
            //登出
            providerFtp.logout();
            consumerFtp.logout();
            result = true;
        } catch (Exception e) {
            log.error(ErrorCode.FtpCopyErrorCode.FILE_COPY_ERROR_OCCURRED, e);
        } finally {
            //关闭读ftpClint链接
            if (providerFtp.isConnected()) {
                try {
                    providerFtp.disconnect();
                } catch (IOException ioe) {
                    log.error(ErrorCode.FtpConnectCode.FTP_DISCONNECT_ERROR_OCCURRED, ioe);
                }
            }
            //关闭写ftpClint链接
            if (consumerFtp.isConnected()) {
                try {
                    consumerFtp.disconnect();
                } catch (IOException ioe) {
                    log.error(ErrorCode.FtpConnectCode.FTP_DISCONNECT_ERROR_OCCURRED, ioe);
                }
            }
        }
        return result;
    }

    /**
     * FTP删除指定文件夹下的指定文件名文件
     *
     * @param bo 删除ftpBO
     * @return 成功true 失败false
     */
    public boolean deleteFile(FtpDeleteBO bo) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            // 连接FTP服务器
            ftp.connect(bo.getHost(), Integer.parseInt(bo.getPort()));
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            // 登录
            ftp.login(bo.getUsername(), bo.getPassword());
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //跳转至指定路径
            ftp.changeWorkingDirectory(bo.getRemotePath());
            //检查文件是否存在
            if (!checkFileExists(ftp, bo.getFileName())) {
                log.error(ErrorCode.FtpCheckErrorCode.FILE_NO_EXISTS);
                return result;
            }
            //删除FTP服务器上的文件。当FTP删除完成则返回true。
            boolean deleted = ftp.deleteFile(bo.getFileName());
            if (!deleted) {
                log.error(ErrorCode.FtpDeleteErrorCode.FILE_DELETE_FAILED);
            }
            ftp.logout();
            result = true;
        } catch (IOException e) {
            log.error(ErrorCode.FtpDeleteErrorCode.FILE_DELETE_ERROR_OCCURRED, e);
        } finally {
            //关闭ftpClint链接
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    log.error(ErrorCode.FtpConnectCode.FTP_DISCONNECT_ERROR_OCCURRED, ioe);
                }
            }
        }
        return result;
    }

    /**
     * 检查文件是否存在
     *
     * @param bo 上传bo
     * @return true存在 false不存在
     */
    public boolean checkFileExists(FtpUploadBO bo) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(bo.getHost(), Integer.parseInt(bo.getPort()));
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            // 登录
            ftp.login(bo.getUsername(), bo.getPassword());
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            // 转移到FTP服务器目录
            ftp.changeWorkingDirectory(bo.getBasePath() + bo.getFilePath());
            String[] listNames = ftp.listNames(bo.getFilename());
            boolean existsFlag = listNames.length > 0;
            ftp.logout();
            if (existsFlag) {
                result = true;
            }
        } catch (IOException e) {
            log.error(ErrorCode.FtpDownloadErrorCode.FILE_DOWNLOAD_ERROR_OCCURRED, e);
        } finally {
            //关闭ftpClint链接
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    log.error(ErrorCode.FtpConnectCode.FTP_DISCONNECT_ERROR_OCCURRED, ioe);
                }
            }
        }
        return result;
    }


    /**
     * 公共方法----检查文件是否已存在
     *
     * @param ftp      ftpClient
     * @param fileName 文件名
     * @return true存在 false不存在
     * @throws IOException IOException
     */
    public boolean checkFileExists(FTPClient ftp, String fileName) throws IOException {
        String[] listNames = ftp.listNames(fileName);
        return listNames.length > 0;
    }


}
