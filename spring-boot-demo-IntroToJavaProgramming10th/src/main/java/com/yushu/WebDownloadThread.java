package com.yushu;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class WebDownloadThread extends Thread {
    private String url;
    private String name;

    public WebDownloadThread(String url, String name) {
        this.url = url;
        this.name = name;
    }

    //图片下载线程的执行体
    public void run() {
        WebDownloader webDownloader = new WebDownloader();
        webDownloader.downloader(url, name);
        System.out.println("下载的文件名为：" + name);
    }

    public static void main(String[] args) {
        //创建多个图片下载线程并启动

        WebDownloadThread t1 = new WebDownloadThread("https://csdnimg.cn/medal/fenxiangxuetu@240.png", "1.png");
        WebDownloadThread t2 = new WebDownloadThread("https://csdnimg.cn/medal/chizhiyiheng@240.png", "2.png");
        WebDownloadThread t3 = new WebDownloadThread("https://csdnimg.cn/70592b2299594e37aedcaa91fc52a294.png", "3.png");
        WebDownloadThread t4 = new WebDownloadThread("https://csdnimg.cn/medal/qixiebiaobing4@240.png", "4.jpg");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

//下载器
class WebDownloader {
    //下载方法
    public void downloader(String url, String name) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        }
    }
}
