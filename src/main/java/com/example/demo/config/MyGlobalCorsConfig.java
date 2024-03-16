package com.example.demo.config;

/**
 * @author QiYongChuan
 * @Version 1.0
 * 2024/3/16
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class MyGlobalCorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // 允许任何来源
        configuration.addAllowedMethod("*"); // 允许任何HTTP方法
        configuration.addAllowedHeader("*"); // 允许任何HTTP头
        configuration.setAllowCredentials(true); // 允许证书（cookies）

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 应用于所有端点
        return source;
    }
}

