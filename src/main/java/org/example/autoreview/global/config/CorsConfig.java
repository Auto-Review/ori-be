package org.example.autoreview.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Value("${cors.url}")
    private String url;

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(url); // 허용할 출처
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("accessToken");
        config.addExposedHeader("refreshToken");

        source.registerCorsConfiguration("/v1/api/**", config);
        return new CorsFilter(source);
    }
}
