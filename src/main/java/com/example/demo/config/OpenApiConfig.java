package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info()
                                    .title("Produto API")
                                    .version("1.0")
                                    .license(new License()
                                            .name("Apache 2.0")
                                            .url("http://www.ifmg.com.br")));
    }
}
