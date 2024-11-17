package com.yushu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class MyTypeFilter implements TypeFilter {
    /**
     * @param metadataReader        独到当前正在扫描的类的信息
     * @param metadataReaderFactory 获取到其它任何类信息
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
//       获取当前类注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
//        获取到当前正在扫描的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
//        获取当前类资源信息(类的信息)
        Resource resource = metadataReader.getResource();
        String className = annotationMetadata.getClassName();
        if (className.contains("er")) {
            return true;
        } else {
            return false;
        }


    }
}
