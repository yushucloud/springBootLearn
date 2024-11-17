package com.chacca.util;

import com.chacca.entity.Voice;
import com.chacca.mapper.VoiceMapper;
import org.apache.ibatis.session.SqlSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 爬取阿里接口说明中的配置
 *
 */
public class HTMLPageData {
    static SqlSession session = MysqlTool.getSession();
    static VoiceMapper voiceMapper = session.getMapper(VoiceMapper.class);
    static Voice voice = null;
    public static void main(String[] args) throws IOException {
        getURL("https://help.aliyun.com/document_detail/84435.html","UTF-8");
    }
    public static void getURL(String url,String charset) throws IOException {
        URL url1 = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String str = null,result = "";
        while ((str = reader.readLine())!=null){
            //这里是对链接进行处理
            str = str.replaceAll("</?a[^>]*>", "");
            //这里是对样式进行处理
            str = str.replaceAll("<(\\w+)[^>]*>", "<$1>");
            result+=str;
        }
        System.out.println(result);
        String[] split = result.split("<td><p>");
        int count = 0;
        for (String s : split) {
            if(s.contains("html"))
                continue;
            if(count==8)
                count=0;
            count++;
            switch (count){
                case 1:
                    voice = new Voice();
                    voice.setUname(s.substring(0,s.indexOf("<")));
                    break;
                case 2:
                    voice.setVname(s.substring(0,s.indexOf("<")));
                    break;
                case 3:
                    voice.setType(s.substring(0,s.indexOf("<")));
                    break;
                case 4:
                    voice.setScene(s.substring(0,s.indexOf("<")));
                    break;
                case 5:
                    voice.setLanguage(s.substring(0,s.indexOf("<")));
                    voiceMapper.insertSelective(voice);
                    voice = null;
                    break;
            }
            if (s.contains("</table>"))
                break;
        }
        session.commit();
    }
}
