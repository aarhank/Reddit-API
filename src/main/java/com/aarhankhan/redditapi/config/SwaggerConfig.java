package com.aarhankhan.redditapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket swaggerApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.aarhankhan"))
                .build()
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Reddit-API",
                "Unofficial API for Reddit",
                "v1.0.0",
                "Public API",
                new Contact(
                        "Aarhan Khan",
                        "www.github.com/aarhank",
                        "aarhankhan212@gmail.com"),
                "MIT",
                "",
                Collections.emptyList());
    }
}
