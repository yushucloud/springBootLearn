package com.yushu.project04_spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/*
 *SpringBootApplication 来标注一个主程序，说明这是一个Spring Boot应用
 *resource 文件夹中的目录结构
 *  static 保存所有的静态资源 js css images
 *  templates 保存所有的模板页面 spring boot 默认的是jar使用嵌入式的Tomcat，默认不支持JSP
 *  但可以使用模板引擎（FreeMarker、thymeleaf）
 * application.properties Spring Boot应用的默认配置文件
 * Spring boot 使用一个全局的配置文件
 * application.properties
 * application.yml
 * Spring 在底层都给我们配置好了
 * YAML（）
 * YAML（YAML Ain't Markup Language）
 * YAML A Markup Language：是一个标记语言
 * YAML isn't Markup Language：不是一个标记语言；
 * YAML：以数据为中心，比json、xml等更适合做配置文件
 * 使用 k:(空格)V
 *  表示一对键值对，空格必须要有，但至少有一个，可以多个
 * 以空格的缩进来控制层级关系，只要左对齐的一列数据，就是同一个层级的
 * 属性和值是大小写敏感的
 * 字面量：普通的值（数字、字符串、布尔）
 *
 * 数组（List、Set）
 * 字符串默认不加单引号或双引号
 * "":双引号，不会转义字符串里面的特殊字符，特殊字符或作为本身
 *  name："zhangshan \n lisi" 则输出 为 zhangsan 换行 lisi
 * '':单引号，会转义字符串里面的特殊字符，特殊字符或作为一个普通字符串输出
 * name： 'zhangshan \n lisi'  则输出 为 zhangsan \n lisi
 *
 *  对象、Map（属性和值）（键值对）
 * 第一种写法
 * friends:
 *  lastName: 张三
 *  age: 20
 * 第二种写法
 * friends: {lastName: 张三,age: 20}
 *
 * 数组（List、Set）
 * pets：
 *  - cat
 *  - dog
 *  - pig
 * pets:[cat,dog,pig]
 * */

@EnableRetry
@SpringBootApplication
public class Project04SpringBootApplication {

    public static void main(String[] args) {
//        让Spring boot 应用启动起来
        SpringApplication.run(Project04SpringBootApplication.class, args);
    }

}
