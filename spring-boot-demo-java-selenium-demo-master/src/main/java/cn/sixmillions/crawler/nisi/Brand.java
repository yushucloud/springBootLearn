package cn.sixmillions.crawler.nisi;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 商标分类
 * @author: sixmillions
 * @since: 2021/11/14
 **/
@Data
@Accessors(chain = true)
public class Brand {

    @ExcelProperty("分类id")
    private Integer classId;

    @ExcelProperty("分类名称")
    private String className;

    @ExcelProperty("一级分类描述")
    private String firstDescrib;

    @ExcelProperty("一级分类注释")
    private String firstNotes;

    @ExcelProperty("一级节点id")
    private Integer level1Id;

    @ExcelProperty("二级几点id")
    private Integer level2Id;

    @ExcelProperty("层级")
    private Integer level;

}
