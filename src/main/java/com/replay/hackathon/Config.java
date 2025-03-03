package com.replay.hackathon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class Config {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedOrigins(List.of("http://localhost:8233"));
        corsConfig.setAllowedMethods(List.of("POST", "OPTIONS"));
        corsConfig.setAllowedHeaders(List.of("content-type", "x-namespace"));
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsFilter(source);
    }
}
