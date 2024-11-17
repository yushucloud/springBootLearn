package com.yushu.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;

public class WindowsCondition implements Condition {

    /**
     * Determine if the condition matches.
     *
     * @param context  the condition context 判断条件能使用的上下文环境
     * @param metadata the metadata of the {@link AnnotationMetadata class}
     *                 or {@link MethodMetadata method} being checked
     *                 注释信息
     * @return {@code true} if the condition matches and the component can be registered,
     * or {@code false} to veto the annotated component's registration
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        /*获取到IOC使用的beanfactory*/
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        /*获取到类加载器*/
        ClassLoader classLoader = context.getClassLoader();
        /*获取到当前环境信息*/
        Environment environment = context.getEnvironment();
        String property = environment.getProperty("os.name");
        if(property.contains("Windows")){
            return true;
        }else{
            return false;
        }

    }
}