package cn.sixmillions.crawler.jiuzhi;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 新闻咨询实体类
 */
@Data
public class News {

    /**
     * 新闻Id
     * 从九智copy过来的id
     */
    @ExcelProperty("新闻Id")
    private Long newsId;

    /**
     * 标题
     */
    @ExcelProperty("标题")
    private String title;

    /**
     * 简介 描述
     */
    @ExcelProperty("简介")
    private String description;

    /**
     * 封面图片url
     */
    @ExcelProperty("封面图片url")
    private String cover;

    /**
     * 浏览量
     */
    @ExcelProperty("浏览量")
    private Integer watchesCount;

    /**
     * 来源
     */
    @ExcelProperty("来源")
    private String source;

    /**
     * 上一篇id
     */
    @ExcelProperty("上一篇id")
    private Long preId;

    /**
     * 下一篇id
     */
    @ExcelProperty("下一篇id")
    private Long nextId;

    /**
     * 文本内容（没有标题，只有正文内容）
     */
    @ExcelProperty("文本内容")
    private String textContent;

    /**
     * html内容（没有标题，只有正文内容）
     */
    @ExcelProperty("html内容")
    private String htmlContent;

    /**
     * 文本内容去掉分页（没有标题，只有正文内容）
     */
    @ExcelProperty("文本内容（去掉分页）")
    private String textContentExcludePage;

    /**
     * html内容去掉分页html标签（没有标题，只有正文内容）
     */
    @ExcelProperty("html内容（去掉分页）")
    private String htmlContentExcludePage;


    /**
     * 完整文本内容
     */
    @ExcelProperty("完整文本内容")
    private String originContent;

    /**
     * 完整html内容
     */
    @ExcelProperty("完整html内容")
    private String originHtmlContent;

    /**
     * 附件URL
     * 多个则以逗号分隔
     */
    @ExcelProperty("附件URL")
    private String attachment;

    /**
     * 文章发布时间
     */
    @ExcelProperty("文章发布时间")
    private LocalDateTime publishedTime;

    /**
     * 爬虫爬取时间
     * 创建时间
     */
    @ExcelProperty("创建时间")
    private LocalDateTime createdTime;

}
