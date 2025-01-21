package org.example.autoreview.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Ori_BE API")
                                .version("v1")
                                .description("Ori_BE API 명세서_v1")
                )
                // SecurityRequirement를 추가하여 인증 요구사항을 설정
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .addSecurityItem(new SecurityRequirement().addList("refreshToken"))
                .components(new io.swagger.v3.oas.models.Components() // SecurityScheme을 추가하여 인증 스키마를 설정
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP) // HTTP 타입의 인증 스키마
                                        .scheme("bearer")
                                        .bearerFormat("JWT") // JWT 형식의 토큰
                                        .in(SecurityScheme.In.HEADER) // 헤더에 포함
                                        .name("Authorization") // 헤더 이름
                        )
                        .addSecuritySchemes("refreshToken",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY) // API Key 타입의 인증 스키마
                                        .in(SecurityScheme.In.HEADER) // 헤더에 포함
                                        .name("Refresh") // 헤더 이름
                        )
                );
    }
}
