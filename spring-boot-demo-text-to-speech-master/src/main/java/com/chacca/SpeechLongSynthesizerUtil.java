package com.chacca;

import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.OutputFormatEnum;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizer;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizerListener;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizerResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此示例演示了：
 * 长文本语音合成API调用（setLongText）。
 * 流式合成TTS。
 * 首包延迟计算。
 * <p>
 * 说明：该示例和nls-example-tts下的SpeechSynthesizerLongTextDemo不完全相同，长文本语音合成是单独的产品功能，是将一长串文本直接发送给服务端去合成，
 * 而SpeechSynthesizerLongTextDemo演示的是将一长串文本在调用方处切割然后分段调用语音合成接口。
 */
public class SpeechLongSynthesizerUtil {
    private static final Logger logger = LoggerFactory.getLogger(SpeechLongSynthesizerUtil.class);
    private static long startTime;
    private String appKey;
    NlsClient client;

    public SpeechLongSynthesizerUtil(String appKey, String token, String url) {
        this.appKey = appKey;
        //创建NlsClient实例应用全局创建一个即可。生命周期可和整个应用保持一致，默认服务地址为阿里云线上服务地址。
        if (url.isEmpty()) {
            client = new NlsClient(token);
        } else {
            client = new NlsClient(url, token);
        }
    }

    private static SpeechSynthesizerListener getSynthesizerListener(String fileName) {
        SpeechSynthesizerListener listener = null;
        try {
            listener = new SpeechSynthesizerListener() {
                File f = new File(fileName + ".mp3");
                FileOutputStream fout = new FileOutputStream(f);
                private boolean firstRecvBinary = true;

                //语音合成结束
                @Override
                public void onComplete(SpeechSynthesizerResponse response) {
                    // 调用onComplete时，表示所有TTS数据已经接收完成，因此为整个合成数据的延迟。该延迟可能较大，不一定满足实时场景。
                    System.out.println("name: " + response.getName() + ", status: " + response.getStatus() + ", output file :" + f.getAbsolutePath());
                }

                //语音合成的语音二进制数据
                @Override
                public void onMessage(ByteBuffer message) {
                    try {
                        if (firstRecvBinary) {
                            // 此处计算首包语音流的延迟，收到第一包语音流时，即可以进行语音播放，以提升响应速度（特别是实时交互场景下）。
                            firstRecvBinary = false;
                            long now = System.currentTimeMillis();
                            logger.info("tts first latency : " + (now - SpeechLongSynthesizerUtil.startTime) + " ms");
                        }
                        byte[] bytesArray = new byte[message.remaining()];
                        message.get(bytesArray, 0, bytesArray.length);
                        //System.out.println("write array:" + bytesArray.length);
                        fout.write(bytesArray);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(SpeechSynthesizerResponse response) {
                    // task_id是调用方和服务端通信的唯一标识，当遇到问题时，需要提供此task_id以便排查。
                    System.out.println(
                            "task_id: " + response.getTaskId() +
                                    //状态码
                                    ", status: " + response.getStatus() +
                                    //错误信息
                                    ", status_text: " + response.getStatusText());
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listener;
    }

    public void process(String text, String person, String fileName) {
        SpeechSynthesizer synthesizer = null;
        try {
            //创建实例，建立连接。
            synthesizer = new SpeechSynthesizer(client, getSynthesizerListener(fileName));
            synthesizer.setAppKey(appKey);
            //设置返回音频的编码格式。
            synthesizer.setFormat(OutputFormatEnum.MP3);
            //设置返回音频的采样率。
            synthesizer.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
            //发音人。注意Java SDK不支持调用超高清场景对应的发音人（例如"zhiqi"），如需调用请使用restfulAPI方式。
            synthesizer.setVoice(person);
            //语调，范围是-500~500，可选，默认是0。
            synthesizer.setPitchRate(0);
            //语速，范围是-500~500，默认是0。
            synthesizer.setSpeechRate(0);
            //设置用于语音合成的文本
            // 此处调用的是setLongText接口（原语音合成接口是setText）。
            synthesizer.setLongText(text);
            //此方法将以上参数设置序列化为JSON发送给服务端，并等待服务端确认。
            long start = System.currentTimeMillis();
            synthesizer.start();
            logger.info("tts start latency " + (System.currentTimeMillis() - start) + " ms");
            SpeechLongSynthesizerUtil.startTime = System.currentTimeMillis();
            //等待语音合成结束
            synthesizer.waitForComplete();
            logger.info("tts stop latency " + (System.currentTimeMillis() - start) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            if (null != synthesizer) {
                synthesizer.close();
            }
        }
    }

    public void shutdown() {
        client.shutdown();
    }

    private static String UpdateInfo(String content, int charLength) {
//        content = content.replaceAll("[\\t\\n\\r]", "@@@@@@@@@@");
//        content = content.replaceAll("@@@@@@@@@@", "\n");
//        content = content.replaceAll("\n\n", "\n");
        String[] s1 = content.split("\n\n|\r\n\r\n");
        List<String> stringList = new ArrayList<String>();
        for (int i = 0; i < s1.length; i++) {
            if (StringUtils.isNotBlank((s1[i].trim()))) {
                stringList.add(s1[i]);
            }
        }
        String allInfo = "<speak>";
        for (int i = 0; i < s1.length; i++) {
            Map voiceLength = getZiMuLength(s1[i], charLength);
            String info = (String) voiceLength.get("info");
            int length = (Integer) voiceLength.get("length");
            allInfo = allInfo + info + " <break time='" + length + "ms'/>\n";
//            System.out.println(length+"\t"+info);

        }
        allInfo = allInfo + "\n</speak>";
        return allInfo;
    }

    private static Map getZiMuLength(String s, int charLength) {
        String s2 = s.trim();
        String s3[] = s2.split("\n");

        if(s3.length>3){
            s3[2]= s3[2]+s3[3];

        }
        String info = null;
        try {
            info = s3[2].trim();
        } catch (Exception e) {
            System.out.println(s);
            e.printStackTrace();
        }
        int infoLength = s3[2].trim().length() * charLength;
        String s4[] = s3[1].split("-->");
        String s5[] = s4[0].split(":|,|\\.");
        String s6[] = s4[1].split(":|,|\\.");

        int startTimesH = Integer.parseInt(s5[0].trim());
        int startTimesM = Integer.parseInt(s5[1].trim());
        int startTimesS = Integer.parseInt(s5[2].trim());
        int startTimesSS = Integer.parseInt(s5[3].trim());
        int startTimes = startTimesSS + startTimesS * 1000 + startTimesM * 1000 * 60 + startTimesH * 1000 * 60 * 60;

        int endTimesH = Integer.parseInt(s6[0].trim());
        int endTimesM = Integer.parseInt(s6[1].trim());
        int endTimesS = Integer.parseInt(s6[2].trim());
        int endTimesSS = Integer.parseInt(s6[3].trim());
        int endTimes = endTimesSS + endTimesS * 1000 + endTimesM * 1000 * 60 + endTimesH * 1000 * 60 * 60;

        int lastLength = endTimes - startTimes - infoLength;
        if (lastLength < 0) {
            lastLength = 0;
        }
        Map map = new HashMap();
        map.put("info", info);
        map.put("length", lastLength);
        return map;
    }

    public static void main1() throws Exception {


        String voice = "ninger,ruilin,aina,yina,sijing,sitong,xiaobei,aitong,aiwei,aibao,lydia,qingqing,cuijie,xiaoze,guijie,stella,stanley,kenny,rosa,mashu,zhiqi,zhichu,xiaoxian,yuer,maoxiaomei,zhixiang,zhijia,zhinan,zhiqian,zhiru,zhide,zhifei,aifei,yaqun,qiaowei,dahu,zhilun,ailun,jielidou,zhiwei,laotie,laomei,aikan";
        String personName = "宁儿,瑞琳,艾娜,伊娜,思婧,思彤,小北,艾彤,艾薇,艾宝,Lydia,青青,翠姐,小泽,柜姐,Stella,Stanley,Kenny,Rosa,马树,知琪,知厨,小仙,悦儿,猫小美,知祥,知佳,知楠,知倩,知茹,知德,知飞,艾飞,亚群,巧薇,大虎,知伦,艾伦,杰力豆,知薇,老铁,老妹,艾侃";
        String type = "标准女声,标准女声,浙普女声,浙普女声,严厉女声,儿童音,萝莉女声,儿童音,萝莉女声,萝莉女声,英中双语女声,中国台湾话女声,东北话女声,湖南重口音男声,亲切女声,知性女声,沉稳男声,沉稳男声,自然女声,儿童剧男声,温柔女声,舌尖男声,亲切女声,儿童剧女声,活力女声,磁性男声,标准女声,广告男声,资讯女声,新闻女声,新闻男声,激昂解说,激昂解说,卖场广播,卖场广播,东北话男声,悬疑解说,悬疑解说,治愈童声,萝莉女声,东北老铁,吆喝女声,天津话男声";


        String appKey = "3QIQP6EAEVVhWxY6";
        String token = "3ea82953ab3e481a8b05ca581f36c5bb";
        // url取默认值
        String url = "wss://nls-gateway.cn-shanghai.aliyuncs.com/ws/v1";

        SpeechLongSynthesizerUtil demo = new SpeechLongSynthesizerUtil(appKey, token, url);
        voice = "zhifei";
        personName = "知飞";
        type = "激昂解说";


        File file = new File("D:\\youtube\\The world’s slimiest animal\\[中字]The world’s slimiest animal - Noah R. Bressman and Douglas Fudge (1).srt");
        String fileName = file.getName();
        String content = FileUtils.readFileToString(file).trim();
        for (int j = 80; j <90; j += 10) {
            String allInfo = UpdateInfo(content, j);
            String[] s = voice.split(",");
            for (int i = 0; i < s.length; i++) {
                demo.process(allInfo, voice.split(",")[i], "D:\\youtube\\The world’s slimiest animal\\" + j + "___" + fileName + "【" + personName.split(",")[i] + "--" + type.split(",")[i] + "】");
            }
        }

        demo.shutdown();
    }


    public static void main2() throws Exception {


        String voice = "ninger,ruilin,aina,yina,sijing,sitong,xiaobei,aitong,aiwei,aibao,lydia,qingqing,cuijie,xiaoze,guijie,stella,stanley,kenny,rosa,mashu,zhiqi,zhichu,xiaoxian,yuer,maoxiaomei,zhixiang,zhijia,zhinan,zhiqian,zhiru,zhide,zhifei,aifei,yaqun,qiaowei,dahu,zhilun,ailun,jielidou,zhiwei,laotie,laomei,aikan";
        String personName = "宁儿,瑞琳,艾娜,伊娜,思婧,思彤,小北,艾彤,艾薇,艾宝,Lydia,青青,翠姐,小泽,柜姐,Stella,Stanley,Kenny,Rosa,马树,知琪,知厨,小仙,悦儿,猫小美,知祥,知佳,知楠,知倩,知茹,知德,知飞,艾飞,亚群,巧薇,大虎,知伦,艾伦,杰力豆,知薇,老铁,老妹,艾侃";
        String type = "标准女声,标准女声,浙普女声,浙普女声,严厉女声,儿童音,萝莉女声,儿童音,萝莉女声,萝莉女声,英中双语女声,中国台湾话女声,东北话女声,湖南重口音男声,亲切女声,知性女声,沉稳男声,沉稳男声,自然女声,儿童剧男声,温柔女声,舌尖男声,亲切女声,儿童剧女声,活力女声,磁性男声,标准女声,广告男声,资讯女声,新闻女声,新闻男声,激昂解说,激昂解说,卖场广播,卖场广播,东北话男声,悬疑解说,悬疑解说,治愈童声,萝莉女声,东北老铁,吆喝女声,天津话男声";


        String appKey = "3QIQP6EAEVVhWxY6";
        String token = "3ea82953ab3e481a8b05ca581f36c5bb";
        // url取默认值
        String url = "wss://nls-gateway.cn-shanghai.aliyuncs.com/ws/v1";

        SpeechLongSynthesizerUtil demo = new SpeechLongSynthesizerUtil(appKey, token, url);
//        voice = "zhifei";
//        personName = "知飞";
//        type = "激昂解说";

//        舌尖男声	知厨	zhichu

        voice = "zhichu";
        personName = "知厨";
        type = "舌尖男声";




        File file = new File("D:\\youtube\\ted\\[中字]What happens to your body at the top of Mount Everest - Andrew Lovering.srt");
        String outputPath = "D:\\youtube\\ted\\";
        String fileName = file.getName();
        String content = FileUtils.readFileToString(file).trim();

        for (int j = 260; j < 320; j += 10) {
            String allInfo = UpdateInfo(content, j);
            String[] s = voice.split(",");
            for (int i = 0; i < s.length; i++) {
                demo.process(allInfo, voice.split(",")[i], outputPath + j + "___" + fileName + "【" + personName.split(",")[i] + "--" + type.split(",")[i] + "】");
            }
        }

        demo.shutdown();
    }


    public static void main(String[] args) throws Exception {
        main2();
    }

}
