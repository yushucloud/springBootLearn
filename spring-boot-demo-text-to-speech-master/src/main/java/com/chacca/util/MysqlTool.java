package com.chacca.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MysqlTool {
    public static SqlSessionFactory sessionFactory;
    //这里读取文件一直出问题 所以就写死了
    static String path = "/Users/zhaowenxuan/记录文档/文字/TextToSpeech/src/main/resources/mybatis-config-HTML.xml";
    static {
        InputStream stream = null;
        try {
            stream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sessionFactory = new SqlSessionFactoryBuilder().build(stream);
    }
    public static SqlSession getSession(){
       return sessionFactory.openSession();
    }
}
