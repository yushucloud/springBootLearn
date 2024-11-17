package com.yushu.config;

import com.yushu.bean.Person;
import com.yushu.condition.LinuxCondition;
import com.yushu.condition.WindowsCondition;
import org.springframework.beans.factory.annotation.Configurable;

import org.springframework.context.annotation.*;

/*告诉Spring这是一个配置类*/

/*
* includeFilters 使用 includeFilters 来按照规则只包含某些包的扫描。
* 使用 excludeFilters 来按照规则排除某些包的扫描。


配置里面，应该是只包含 @Controller 注解的类才会被注册到容器中，为什么 @Service 注解的类也被注册了呢？这里涉及到 @ComponentScan 的一个 useDefaultFilters 属性的用法，该属性默认值为 true，也就是说 spring 默认会自动发现被 @Component、@Repository、@Service 和 @Controller 标注的类，并注册进容器中。要达到只包含某些包的扫描效果，就必须将这个默认行为给禁用掉（在 @ComponentScan 中将 useDefaultFilters 设为 false 即可）。

ComponentScan 可以重复书写多次
如果不是Java8 ，可以使用ComponentScans
*
 * */
@Configurable
@ComponentScan(value = "com.yushu"
        ,
//        排除哪些
//        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class)}/*,
        includeFilters = {
//                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class}),
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class, Service.class}),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class}),

        },
        useDefaultFilters = false
)
public class YuShuConfig {
    //
    /*
     * prototype 多实例的
     * singleton 单实例
     * request 同一个请求创建一个实例
     * session 同一个session创建一个实例
     * 懒加载
     * 单实例bean 在容器启动时创建对象，创建完成后，放到容器中
     * 懒加载：容器启动时，不创建对象，第一次获取是，创建对象，并初始化
     * */

    /*给容器注册一个bean，类型为返回值的类型
     * @Conditional 按照一定的条件进行判断，满足条件的容器中注册bean
     *给容器中注册一个bean，类型非返回值类型
     * */
    @Bean("person01")
    @Scope("singleton")
    public Person person01() {
        return new Person("李四", 20);
    }

    @Conditional({WindowsCondition.class})
    @Bean
    public Person person02() {
        return new Person("比尔盖茨", 60);
    }

    @Conditional({LinuxCondition.class})
    @Bean
    public Person person03() {
        return new Person("李纳思", 50);
    }

    @Bean
    public Person person04() {
        return new Person("李纳思", 50);
    }

    @Bean
    public Person person05() {
        return new Person("李纳思", 50);
    }
}
