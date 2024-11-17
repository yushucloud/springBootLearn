package com.yushu.project04_spring_boot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * 将配置文件配置的每一个属性的值，映射到这个组建中
 * @ConfigurationPropertie 告诉SpirngBoot将本类中的所有属性和配置文件中相关的皮遏制进行绑定
 *  只用这个组件是容器中的组件，才能使用容器提供的ConfigurationProperties功能
 * */
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private Integer age;
    @Getter
    @Setter
    private Boolean boss;
    @Getter
    @Setter
    private Date birth;
    @Getter
    @Setter
    private Map<String, Object> maps;
    @Getter
    @Setter
    private List<Object> lists;
    @Getter
    @Setter
    private Dog dog;

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", age=" + age +
                ", boss=" + boss +
                ", birth=" + birth +
                ", maps=" + maps +
                ", lists=" + lists +
                ", dog=" + dog +
                '}';
    }
}
