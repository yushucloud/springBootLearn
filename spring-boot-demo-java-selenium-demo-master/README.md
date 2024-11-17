# 项目介绍

使用selenium爬取简单数据，将数据通过easyexcel导入excel

# 实践

## 尼斯数据爬虫

爬取商标分类数据：

1. 45类一级分类
2. 每个一级分类下的二级分类
3. 每个二级分类的三级分类（内容）

尼斯数据网站：

> https://ipr1.cn/

---

## 九智新闻资讯爬取

截至项目发布，爬取网站下面的23页新闻资讯文章

1. 文章的标题，封面，发布时间
2. 文章的文本内容，html格式内容
3. 文章的附件

> http://www.jiuzhizixun.com/list/?20_1.html

---

# 驱动下载

selenium驱动下载（下载对应版本）：

> http://chromedriver.storage.googleapis.com/index.html

---

# 使用

1. 根据实际情况修改Action类的DRIVER_PATH和EXCEL_EXPORT_PATH
2. 运行main方法
3. 手动将excel导入数据库，也可以通过jdbc直接保存到数据库（JiuZhiNewsCrewler类中有demo）