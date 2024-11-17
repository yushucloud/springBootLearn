package cn.sixmillions.crawler.nisi;

import cn.hutool.core.collection.ListUtil;
import cn.sixmillions.crawler.util.DBUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @description: 爬取尼斯数据：https://ipr1.cn/
 * @author: sixmillions
 * @since: 2021/11/08
 **/
public class Action {

    /**
     * 驱动路径
     */
    public static final String DRIVER_PATH = "D:\\OpenSources\\chromedriver_win32\\chromedriver.exe";

    /**
     * exce导出路径
     */
    public static final String EXCEL_EXPORT_PATH = "D:\\test.xlsx";

    /**
     * 每次向数据库保存多少条
     */
    public static final int BATCH_SIZE = 500;

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
        driver.get("https://ipr1.cn");
        List<Brand> list = handler(driver);
        //分批保存数据库
        List<List<Brand>> partition = ListUtil.partition(list, BATCH_SIZE);
        for (int i = 0; i < partition.size(); i++) {
            //保存数据库
            saveToDataBase(partition.get(i), i);
        }
        //导出excel
        //exportExcel(list);
        driver.close();
    }

    /**
     * excel导出
     *
     * @param list
     */
    private static void exportExcel(List<Brand> list) {
        EasyExcel.write(EXCEL_EXPORT_PATH, Brand.class)
                .sheet("尼斯数据")
                .doWrite(list);
    }

    /**
     * 主要处理
     *
     * @param driver
     * @return
     */
    private static List<Brand> handler(WebDriver driver) {
        List<Brand> list = new ArrayList<>();
        //获取所有一级分类元素
        List<WebElement> level1Eles = driver.findElements(By.cssSelector(".dw a"));
        //一级分类循环处理
        for (WebElement level1Ele : level1Eles) {
            Brand brand = new Brand();
            list.add(brand);
            String href = level1Ele.getAttribute("href");
            //一级分类的id
            Integer id = Integer.valueOf(href.substring(href.lastIndexOf("l") + 1));
            // 一级分类设置参数：名字需要去掉顿号
            brand.setClassId(id).setClassName(level1Ele.getText().replace("、", " ")).setLevel(1).setLevel1Id(0).setLevel2Id(0);
            //获取一级分类描述 //*[@id="fl1"]/div/div[1]
            WebElement briefEle = driver.findElement(By.xpath("//*[@id='fl" + id + "']/div/div[1]"));
            brand.setFirstDescrib(briefEle.getText());
            //一级分类注释 //*[@id="fl1"]/div/div[2]/div[2]
            WebElement notesEle = driver.findElement(By.xpath("//*[@id='fl" + id + "']/div/div[2]/div[2]"));
            brand.setFirstNotes(notesEle.getText());
        }

        //二级分类（当前一级分类只有46类，这里写死了）
        for (int level1Id = 0; level1Id < 45; level1Id++) {
            //该一级分类下所有二级分类的链接元素
            List<WebElement> level2Links = driver.findElements(By.xpath("//*[@id='fl" + (level1Id + 1) + "']/div/div[2]/div[1]/a"));
            //循环处理
            for (WebElement level2Link : level2Links) {
                Brand brand = new Brand();
                list.add(brand);
                String text = level2Link.getText();
                //名称截取（后面用这个名字去查找元素）
                if (text.length() > 15) {
                    text = text.substring(0, 15);
                }
                //二级分类id
                String href = level2Link.getAttribute("href");
                Integer level2Id = Integer.valueOf(href.substring(href.lastIndexOf("/") + 1));
                brand.setClassId(level2Id);
                //进入二级分类页面
                level2Link.click();
                Set<String> windowHandles = driver.getWindowHandles();
                Object[] objects = windowHandles.toArray();
                //切换到新tab
                driver.switchTo().window(objects[objects.length - 1].toString());
                //二级分类名称
                WebElement level2NameEle = driver.findElement(By.partialLinkText(text));
                //brand.setClassName(level2NameEle.getAttribute("title"));
                //二级分类名称带着组号
                brand.setClassName(level2NameEle.getText().replace("⊙ ", "").replace("-", ""));
                brand.setLevel2Id(0).setLevel1Id(level1Id + 1).setLevel(2);
                //三级分类处理
                level3(list, driver, (level1Id + 1), level2Id);
                //关闭二级分类页面
                driver.close();
                //切换tab
                driver.switchTo().window(objects[0].toString());
            }
        }
        return list;
    }

    /**
     * 三级分类
     *
     * @param list
     * @param driver
     * @param level1Id
     * @param level2Id
     */
    private static void level3(List<Brand> list, WebDriver driver, Integer level1Id, Integer level2Id) {
        //三级分类内容
        WebElement level3Ele = driver.findElement(By.cssSelector(".sbflnr"));
        if (StringUtils.isBlank(level3Ele.getText())) {
            //空的内容直接返回
            return;
        }
        //按照分号拆分三级分类
        String[] level3NameArr = level3Ele.getText().split("；");
        for (int i = 0; i < level3NameArr.length; i++) {
            Brand brand = new Brand();
            list.add(brand);
            brand.setClassId(i + 1).setClassName(level3NameArr[i]).setLevel1Id(level1Id).setLevel2Id(level2Id).setLevel(3);
        }
    }


    /**
     * 直接将数据保存到数据库
     */
    private static void saveToDataBase(List<Brand> list, int batch) {
        Connection conn = DBUtil.connPg();
        //关闭自动提交
        String sql = "INSERT INTO tm_brand (class_id, class_name, first_describ, first_notes, level1_id, level2_id, level_code, price, version_code) values(?,?,?,?,?,?,?,?,?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(sql);
            //参数设置
            for (int i = 0; i < list.size(); i++) {
                Brand brand = list.get(i);
                if (brand.getClassId() == null) {
                    statement.setNull(1, Types.BIGINT);
                } else {
                    statement.setLong(1, brand.getClassId());
                }
                if (brand.getClassName() == null) {
                    statement.setNull(2, Types.VARCHAR);
                } else {
                    statement.setString(2, brand.getClassName());
                }
                if (brand.getFirstDescrib() == null) {
                    statement.setNull(3, Types.VARCHAR);
                } else {
                    statement.setString(3, brand.getFirstDescrib());
                }
                if (brand.getFirstNotes() == null) {
                    statement.setNull(4, Types.VARCHAR);
                } else {
                    statement.setString(4, brand.getFirstNotes());
                }
                if (brand.getLevel1Id() == null) {
                    statement.setNull(5, Types.BIGINT);
                } else {
                    statement.setLong(5, brand.getLevel1Id());
                }
                if (brand.getLevel2Id() == null) {
                    statement.setNull(6, Types.BIGINT);
                } else {
                    statement.setLong(6, brand.getLevel2Id());
                }
                if (brand.getLevel() == null) {
                    statement.setNull(7, Types.BIGINT);
                } else {
                    statement.setLong(7, brand.getLevel());
                }
                statement.setInt(8, batch * BATCH_SIZE + i);
                statement.setString(9, "INIT");
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
            e.printStackTrace();
        }
    }

}
