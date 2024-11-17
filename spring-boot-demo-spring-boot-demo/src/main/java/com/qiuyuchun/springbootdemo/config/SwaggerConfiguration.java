package com.qiuyuchun.springbootdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/12/30 下午7:30
 * @desc Swagger配置类.
 */

@EnableSwagger2//Swagger的开关，表示我们在项目中启用Swagger
@Configuration//声名这是一个配置类
@ConfigurationProperties(prefix = "swagger")//SpringBoot中提供的属性自动赋值。也可以直接在属性上使用@Value("${swagger.属性名}")进行赋值
public class SwaggerConfiguration {

    //controller接口所在的包
    private String basePackage = "com.kingboy.controller";

    //当前文档的标题
    private String title = "他很懒，什么都没有留下";

    //当前文档的详细描述
    private String description = "他很懒，什么都没有留下";

    //当前文档的版本
    private String version = "V1.0";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }



    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
