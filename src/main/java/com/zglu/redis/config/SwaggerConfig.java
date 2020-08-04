package com.zglu.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zglu
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("redis")
                .description("redis测试文档")
                .version("1")
                .build();
    }

    @Bean
    public Docket docket(ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalDateTime.class, Date.class)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zglu.redis"))
                .paths(PathSelectors.any())
                .build();
    }

}
