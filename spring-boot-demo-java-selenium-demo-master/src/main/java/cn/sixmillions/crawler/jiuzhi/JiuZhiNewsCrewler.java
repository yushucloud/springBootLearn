package cn.sixmillions.crawler.jiuzhi;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.sixmillions.crawler.util.DBUtil;
import com.alibaba.fastjson.JSON;
import org.apache.poi.util.StringUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: 爬取尼斯数据：https://ipr1.cn/
 * @author: sixmillions
 * @since: 2021/11/08
 **/
public class JiuZhiNewsCrewler {

    /**
     * 驱动路径
     */
    public static final String DRIVER_PATH = "D:\\chromedriver_win32\\chromedriver.exe";

    /**
     * exce导出路径
     */
    public static final String EXCEL_EXPORT_PATH = "D:\\test.xlsx";

    /**
     * 时间格式化
     */
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    /**
     * 超长html的文章id
     * 32767 characters
     * 超过这个长度会报错，excel导出
     * https://github.com/alibaba/easyexcel/issues/1352
     * 使用csv导出
     */
    public static List<Long> maxList = new ArrayList<>();

    /**
     * 程序入口
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        //Chrome浏览器
        WebDriver driver = new ChromeDriver();
        List<News> list = new ArrayList<>();
        //按照页去爬取(一共23页)
        for (int i = 1; i < 24; i++) {
            List<News> page = pageHandler(driver, "http://www.jiuzhizixun.com/list/?20_" + i + ".html");
            //爬取一页数据就保存到数据库
            saveToDataBase(page);
            //保存到总list中（导入excel中）
            list.addAll(page);
        }
        //导出excel（如果太多可以分批次爬取）
        System.out.println("爬取资讯总条数：" + list.size());
        exportExcel(list);
        driver.close();
        System.out.println("超长html的文章（32767 characters）id：" + JSON.toJSONString(maxList));
    }

    /**
     * 单页信息爬取处理
     *
     * @param driver
     * @param url    某一页链接
     * @return
     */
    private static List<News> pageHandler(WebDriver driver, String url) {
        List<News> results = new ArrayList<>();
        //String url = "http://www.jiuzhizixun.com/list/?20_1.html";
        driver.get(url);
        List<WebElement> newsList = driver.findElements(By.cssSelector(".news_list_con li"));
        for (WebElement webElement : newsList) {
            News news = new News();
            news.setCreatedTime(LocalDateTime.now());
            results.add(news);
            WebElement ddElement = webElement.findElement(By.tagName("dd"));
            //跳转详情的element
            WebElement detailElement = ddElement.findElement(By.tagName("h2")).findElement(By.tagName("a"));
            //跳转详情的链接
            String detailHref = detailElement.getAttribute("href");
            System.out.printf("跳转详情的链接：%s%n", detailHref);
            //id
            Long newsId = Long.valueOf(detailHref.substring(detailHref.lastIndexOf("?") + 1, detailHref.lastIndexOf(".")));
            System.out.printf("newsId：%s%n", newsId);
            news.setNewsId(newsId);
            //标题
            String title = ddElement.findElement(By.tagName("h2")).getText();
            System.out.printf("标题：%s%n", title);
            news.setTitle(title);
            //描述
            String description = ddElement.findElement(By.tagName("h3")).getText();
            System.out.printf("描述：%s%n", description);
            news.setDescription(description);
            //封面图片
            WebElement imgElement = webElement.findElement(By.tagName("img"));
            String cover = imgElement.getAttribute("src");
            System.out.printf("封面图片：%s%n", cover);
            news.setCover(cover);
            //详情界面处理
            detailHandler(ddElement.findElement(By.linkText("查看详情")), driver, news);
            //break;
        }
        //System.out.println(JSON.toJSONString(results));
        return results;
    }

    /**
     * 详情界面爬取处理
     *
     * @param detailLinkElement
     * @param driver
     * @param news
     */
    private static void detailHandler(WebElement detailLinkElement, WebDriver driver, News news) {
        //跳转到详情界面
        Actions actions = new Actions(driver);
        //ctrl + 点击：从新标签页打开链接
        actions.keyDown(Keys.CONTROL).click(detailLinkElement).keyUp(Keys.CONTROL).perform();
        Set<String> windowHandles = driver.getWindowHandles();
        Object[] objects = windowHandles.toArray();
        //跳转
        driver.switchTo().window(objects[objects.length - 1].toString());
        try {
            //等待页面加载
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        WebElement contentElement = driver.findElement(By.cssSelector(".sub_content"));
        WebElement titleContentElement = contentElement.findElement(By.cssSelector(".news_view_title h2"));
        //获取：来源 浏览量 发布时间
        String other = titleContentElement.getText();
        System.out.printf("来源 浏览量 发布时间：%s%n", other);
        String[] otherArr = other.split("  ");
        for (String item : otherArr) {
            item = item.trim();
            if (item.contains("来源")) {
                String source = item.substring(item.lastIndexOf("：") + 1);
                System.out.printf("来源：%s%n", source);
                news.setSource(source.trim());
            }
            if (item.contains("浏览量")) {
                String counts = item.substring(item.lastIndexOf("：") + 1, item.lastIndexOf("次"));
                System.out.printf("浏览量：%s%n", counts);
                news.setWatchesCount(Integer.valueOf(counts.trim()));
            }
            if (item.contains("发布时间")) {
                String publishTime = item.substring(item.lastIndexOf("：") + 1);
                System.out.printf("发布时间：%s%n", publishTime);
                LocalDateTime time = LocalDateTime.parse(timeStrHandle(publishTime), FORMATTER);
                news.setPublishedTime(time);
            }
        }
        //上一篇资讯id 下一篇资讯id（从a标签的href中获取）
        List<WebElement> preAndNextElement = driver.findElements(By.cssSelector(".news_fenye a"));
        String href = preAndNextElement.get(0).getAttribute("href");
        Long newsId = Long.valueOf(href.substring(href.lastIndexOf("?") + 1, href.lastIndexOf(".")));
        if (newsId > 100 && preAndNextElement.size() == 1) {
            //只有一个上一篇id
            System.out.printf("上一篇id：%s%n", newsId);
            news.setPreId(newsId);
        } else if (newsId < 100 && preAndNextElement.size() == 1) {
            //只有一个下一篇id
            System.out.printf("下一篇id：%s%n", newsId);
            news.setNextId(newsId);
        } else {
            //上一篇id，下一篇id都有
            System.out.printf("上一篇id：%s%n", newsId);
            news.setPreId(newsId);
            String nextHref = preAndNextElement.get(1).getAttribute("href");
            Long nextNewsId = Long.valueOf(nextHref.substring(nextHref.lastIndexOf("?") + 1, nextHref.lastIndexOf(".")));
            System.out.printf("下一篇id：%s%n", nextNewsId);
            news.setNextId(nextNewsId);
        }
        //文本内容（没有标题，只有正文内容）
        WebElement content = driver.findElement(By.cssSelector(".news_view_cn"));
        news.setTextContent(content.getText());
        String innerHTML = content.getAttribute("innerHTML");
        if (innerHTML.length() > 32000) {
            maxList.add(news.getNewsId());
            //innerHTML = innerHTML.substring(0, 32000);
        }
        news.setHtmlContent(innerHTML);
        //完整文本内容
        WebElement originContent = driver.findElement(By.cssSelector(".sub_content"));
        news.setOriginContent(originContent.getText());
        String originInnerHTML = originContent.getAttribute("innerHTML");
        news.setOriginHtmlContent(originInnerHTML);

        //附件URL （文章中所有a标签除去上一篇下一篇的a标签）
        List<String> attachmentList = new ArrayList<>();
        for (WebElement attachmentElement : content.findElements(By.tagName("a"))) {
            String attachementHref = attachmentElement.getAttribute("href");
            if (attachementHref != null && !attachementHref.contains("jiuzhizixun")) {
                //去掉上一下下一页链接
                attachmentList.add(attachementHref);
            }
        }
        news.setAttachment(String.join(",", attachmentList));

        //文本内容不（不带分页html标签）
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //js去掉标签
        js.executeScript("var content = document.getElementsByClassName(\"news_fenye\");content[0].parentNode.removeChild(content[0])");
        WebElement textContentExcludePage = driver.findElement(By.cssSelector(".news_view_cn"));
        news.setTextContentExcludePage(textContentExcludePage.getText());
        String excludePageInnerHTML = textContentExcludePage.getAttribute("innerHTML");
        news.setHtmlContentExcludePage(excludePageInnerHTML);

        //返回上一个界面
        driver.close();
        //切换tab
        driver.switchTo().window(objects[0].toString());
    }

    /**
     * 补零
     *
     * @param timeStr
     * @return
     */
    private static CharSequence timeStrHandle(String timeStr) {

        String[] s = timeStr.split(" ");
        String[] s1 = s[0].split("/");
        if (s1[1].length() < 2) {
            s1[1] = "0" + s1[1];
        }
        if (s1[2].length() < 2) {
            s1[2] = "0" + s1[2];
        }
        String[] s2 = s[1].split(":");
        if (s2[0].length() < 2) {
            s2[0] = "0" + s2[0];
        }
        if (s2[1].length() < 2) {
            s2[1] = "0" + s2[1];
        }
        if (s2[2].length() < 2) {
            s2[2] = "0" + s2[2];
        }

        String formatTimeStr1 = StringUtil.join(s1, "/");
        String formatTimeStr2 = StringUtil.join(s2, ":");
        return formatTimeStr1 + " " + formatTimeStr2;
    }

    /**
     * excel导出
     *
     * @param list
     */
    private static void exportExcel(List<News> list) {
        //指定路径和编码(windows下用GBK，不然中文乱码)
        CsvWriter writer = CsvUtil.getWriter(EXCEL_EXPORT_PATH, CharsetUtil.CHARSET_GBK);
        //数据写入
        writer.writeBeans(list);
    }

    /**
     * 直接将数据保存到数据库
     *
     * @param list
     */
    private static void saveToDataBase(List<News> list) {
        Connection conn = DBUtil.connPg();
        //关闭自动提交
        String sql = "INSERT INTO jiuzhi_news values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(sql);
            //参数设置
            for (News news : list) {
                statement.setLong(1, news.getNewsId());
                statement.setString(2, news.getTitle());
                statement.setString(3, news.getDescription());
                statement.setString(4, news.getCover());
                statement.setInt(5, news.getWatchesCount());
                statement.setString(6, news.getSource());
                //空值处理
                if (news.getPreId() == null) {
                    statement.setNull(7, Types.BIGINT);
                } else {
                    statement.setLong(7, news.getPreId());
                }
                if (news.getNextId() == null) {
                    statement.setNull(8, Types.BIGINT);
                } else {
                    statement.setLong(8, news.getNextId());
                }
                statement.setString(9, news.getTextContent());
                statement.setString(10, news.getHtmlContent());
                statement.setString(11, news.getTextContentExcludePage());
                statement.setString(12, news.getHtmlContentExcludePage());
                statement.setString(13, news.getOriginContent());
                statement.setString(14, news.getOriginHtmlContent());
                if (news.getAttachment() == null) {
                    statement.setNull(15, Types.VARCHAR);
                } else {
                    statement.setString(15, news.getAttachment());
                }

                statement.setTimestamp(16, DateUtil.date(news.getPublishedTime()).toTimestamp());
                statement.setTimestamp(17, DateUtil.date(news.getCreatedTime()).toTimestamp());
                statement.addBatch();
            }
            //批量插入
            int[] result = statement.executeBatch();
            System.out.println("批量插入结果：" + JSON.toJSONString(result));
            //提交事务
            conn.commit();
            //关闭连接
            conn.close();
        } catch (Exception e) {
            System.out.println("插入数据库出错,出错数据id: " + JSON.toJSONString(list.stream().map(News::getNewsId).collect(Collectors.toList())));
            e.printStackTrace();
        }
    }
}
