package com.yushu;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

public class SmbUtil {
    // 1. 声明属性
    private String url = "smb://yushu:ys0120@\\\\YUSHU\\zookeeper-3.4.6";
    private SmbFile smbFile = null;
    private SmbFileOutputStream smbOut = null;
    private static SmbUtil smbUtil = null; // 共享文件协议

    private SmbUtil(String url) {
        this.url = url;
        this.init();
    }

    // 2. 得到SmbUtil和连接的方法
    public static synchronized SmbUtil getInstance(String url) {
        if (smbUtil == null)
            return new SmbUtil(url);
        return smbUtil;
    }


    // 3.smbFile连接
    public void init() {
        try {
            System.out.println("开始连接...url：" + this.url);
            smbFile = new SmbFile(this.url);
            smbFile.connect();
            System.out.println("连接成功...url：" + this.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.print(e);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print(e);
        }
    }

    // 4.上传文件到服务器
    public int uploadFile(File file) {
        int flag = -1;
        BufferedInputStream bf = null;
        try {
            this.smbOut = new SmbFileOutputStream(this.url + "/"
                    + file.getName(), false);
            bf = new BufferedInputStream(new FileInputStream(file));
            byte[] bt = new byte[8192];
            int n = bf.read(bt);
            while (n != -1) {
                this.smbOut.write(bt, 0, n);
                this.smbOut.flush();
                n = bf.read(bt);
            }
            flag = 0;
            System.out.println("文件传输结束...");
        } catch (SmbException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("找不到主机...url：" + this.url);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            try {
                if (null != this.smbOut)
                    this.smbOut.close();
                if (null != bf)
                    bf.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return flag;
    }

    // 5. 在main方法里面测试
    public static void main(String[] args) throws MalformedURLException, SmbException {
        // 服務器地址 格式為 smb://电脑用户名:电脑密码@电脑IP地址/IP共享的文件夹
//        \\192.168.3.38\zookeeper-3.4.6
//        String url = "smb://yushuys0120@192.168.3.38/zookeeper-3.4.6/";
//        String remoteUrl= "smb//domain;user:passwd@1.1.1.1/smb/package.zip";
//        String url= "smb//192.168.3.38;yushu:ys0120@192.168.3.38/zookeeper-3.4.6/";
////
//        String localFile = "C:\\Users\\Administrator\\Desktop\\***.txt"; // 本地要上传的文件
//        File file = new File(localFile);
//        SmbUtil smb = SmbUtil.getInstance(url);
//        smb.uploadFile(file);// 上传文件

        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("smb://192.168.3.38","yushu", "ys0120");
        SmbFile smbToFile = new SmbFile("smb://192.168.3.38/zookeeper-3.4.6/", auth);
        if(smbToFile.isDirectory()){
            for(SmbFile smbfile: smbToFile.listFiles("*")){
                System.out.println("getName: " +smbToFile.getName());
            }
        }
        else{
            System.out.println("SupplierDrawingList: Directory Name: Not a Directory: ");
        }

    }
}
