package com.yushu.config;

import com.yushu.bean.Color;
import com.yushu.bean.Person;
import com.yushu.bean.Red;
import com.yushu.condition.LinuxCondition;
import com.yushu.condition.MyImportSelector;
import com.yushu.condition.WindowsCondition;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

/*告诉Spring这是一个配置类*/

@Configurable
@ComponentScan(value = "com.yushu"
        ,
        useDefaultFilters = false
)
//@Import(Color.class)
@Import({Color.class, Red.class, MyImportSelector.class})
/*Import 快速导入一个组件，ID默认是类的全类名*/
public class YuShuConfig2 {

    //    @Scope("singleton")
    /*
     * prototype 多实例，IOC容器启动并不会去吊笼方法创建对象放到容器中，
     * 每次获取的时候才会调用方法创建对象
     * singleton 单实例（默认值），
     * IOC容器启动会调用方法创建对象放到IOC容器中
     * 以后每次获取就是直接从容器中去拿
     * request 同一请求创建一个实例
     * session 同一个session创建一个实例
     * 懒加载
     *  单实例bean：默认在容器启动的时候创建对象
     * 容器启动时不创建对象，第一次获取Bean创建对象，并初始化
     *
     * */

    /*
     * @Conditional，按照一定的条件进行判断，满足条件给容器注册bean
     *
     *
     * */

    /*
     * 给容器中注册组件
     *1、包扫描+组件标注注解（@Controller/@Service/@Repository/@Component）
     *2、@Bean[导入地方包里面的组件]
     *3、@Import【快速的给容器中导入一个组件】
     *      1、@Import（要导入到容器中的组件），容器中就会自动注册这个组件，，ID默认是类的全类名
     *      2、ImportSelector：
     *
     * */
//    @Scope
//    @Scope("prototype")
    @Lazy
    @Bean("person04")
    @Conditional({LinuxCondition.class})
    public Person person04() {
        System.out.println("容器中添加Person");
        return new Person("李四", 20);
    }

    @Lazy
    @Bean("person05")
    @Conditional({WindowsCondition.class})
    public Person person05() {
        System.out.println("容器中添加Person");
        return new Person("王五", 30);
    }

    @Test
    public void testAnno() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(YuShuConfig2.class);
       /* Person person = (Person) applicationContext.getBean("person04");
        Person person2 = (Person) applicationContext.getBean("person04");

        System.out.println(person.hashCode());
        System.out.println(person2.hashCode());*/
        String[] strings = applicationContext.getBeanDefinitionNames();
        for (String string : strings) {
            System.out.println(string);
        }
    }
}
