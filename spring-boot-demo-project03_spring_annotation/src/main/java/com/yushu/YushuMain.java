package com.yushu;

import com.yushu.bean.Person;
import com.yushu.config.YuShuConfig;
import com.yushu.controller.BookController;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

@SpringBootApplication
public class YushuMain {
    public static void main(String[] args) {
       /* ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person person = (Person) applicationContext.getBean("person");
        String[] strings = applicationContext.getBeanDefinitionNames();
        System.out.println(strings);
        System.out.println(person.getName());
        System.out.println(person.getAge());*/
    }

    @Test
    public void testAnno() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(YuShuConfig.class);
        BookController helloController = applicationContext.getBean(BookController.class);
        helloController.test();
    }

}
