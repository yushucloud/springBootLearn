package com.yushu.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {
    /**
     * Select and return the names of which class(es) should be imported based on
     * the {@link AnnotationMetadata} of the importing @{@link Configuration} class.
     *
     * @param importingClassMetadata 当前标注@Import注解的所有注解信息
     * @return the class names, or an empty array if none 返回值，就是导入到容器中的组件全雷鸣
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.yushu.bean.Blue","com.yushu.bean.Yellow"};
    }
}
