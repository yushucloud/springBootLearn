package com.hjp.ftpmanager.domain.bean;

/**
 * @program: ftp-manager
 * @description: 错误信息
 * @author: jumper Hou
 * @create: 2022/9/8 17:46
 **/

public class ErrorCode {

    public static class BusinessErrorCode {
        public static final String MISSING_PARAMETER = "缺少必要参数";
    }

    /**
     * FTP连接配置相关错误
     */
    public static class FtpConnectCode {
        public static final String FTP_CONNECT_FAILED = "ftp服务器连接失败";
        public static final String FTP_DISCONNECT_ERROR_OCCURRED = "ftp服务器断开连接异常";
    }

    /**
     * ftp上传错误相关
     */
    public static class FtpUploadErrorCode {
        public static final String FILE_UPLOAD_FAILED = "文件上传失败";
        public static final String FILE_UPLOAD_ERROR_OCCURRED = "文件上传发生异常";
    }

    /**
     * ftp下载错误相关
     */
    public static class FtpDownloadErrorCode {
        public static final String FILE_DOWNLOAD_ERROR_OCCURRED = "文件下载发生异常";
        public static final String FILE_DOWNLOAD_FAILED = "文件下载失败";
    }

    /**
     * ftp复制错误相关
     */
    public static class FtpCopyErrorCode {
        public static final String FILE_COPY_ERROR_OCCURRED = "文件复制发生异常";
        public static final String FILE_COPY_FAILED = "文件复制失败";
    }

    public static class FtpDeleteErrorCode {
        public static final String FILE_DELETE_ERROR_OCCURRED = "文件删除发生异常";
        public static final String FILE_DELETE_FAILED = "文件删除失败";
    }

    public static class FtpCheckErrorCode {
        public static final String FILE_EXISTS = "文件已存在";
        public static final String FILE_NO_EXISTS = "文件不存在";

    }

}
