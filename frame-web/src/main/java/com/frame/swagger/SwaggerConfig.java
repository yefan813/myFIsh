package com.frame.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by yefan on 2017/6/30.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${host.prefix}")
    private String HOST;

    @Value("${DEV_MODE}")
    private String devMode;

    @Bean
    public Docket buildDocket(){

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .select().apis(RequestHandlerSelectors.basePackage("com.frame.web.controller"))//controller路径
                .paths(PathSelectors.any())
                .build();

        if("DEBUG".equals(devMode)){
            return docket;
        }

        return docket.host(HOST);
    }

    private ApiInfo buildApiInf(){
        return new ApiInfoBuilder()
                .title("Fish")
                .termsOfServiceUrl("").version("1.0")//版本
                .description("钓鱼大仙API接口描述及测试")
                .contact(new Contact("yefan", "394818428@qq.com", ""))
                .build();

    }
}