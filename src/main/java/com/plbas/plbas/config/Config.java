package com.plbas.plbas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public OpenAPI myConfig()
    {
        return new OpenAPI().info(new Info().title("记账系统")
                .description("基于Spring Boot + JPA 的简易记账系统")
                .version("v1.0.0")
        );
    }
}
