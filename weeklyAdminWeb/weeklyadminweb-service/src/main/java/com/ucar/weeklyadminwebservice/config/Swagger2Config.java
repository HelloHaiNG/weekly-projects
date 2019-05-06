package com.ucar.weeklyadminwebservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置
 *
 * @author chuang.ma
 * @since 2018年08月07日
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 创建Swagger文档
     * @return
     */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ucar.weeklyadminwebservice.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
         return new ApiInfoBuilder()
                 .title("周报书写平台")
                 .description("周报书写平台api接口文档,简单优雅的RESTFULL风格")
                 .termsOfServiceUrl("http://localhost:5004")
                 .version("1.0")
                 .build();
    }

}
