package com.yap.ride_project.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().components(new Components()).info(getInfo());
    }

    private Info getInfo(){
        return new Info()
                .title("API 테스트 문서")
                .description("접근 가능한 모든 API의 테스트 문서입니다. \ntry it out을 눌러 테스트해볼 수 있습니다.")
                .version("1.0.0");
    }
}
