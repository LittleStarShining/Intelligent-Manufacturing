package com.gene.IM.JWT.config;

import com.gene.IM.JWT.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    // 生成拦截器对象
    @Bean
    public JWTInterceptor createJWTInterceptor() {
        return new JWTInterceptor();
    }

    // 注册拦截器（添加）
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createJWTInterceptor())
                .addPathPatterns("/**") // 谁生效？
                .excludePathPatterns("/login"); // 谁排除在外？
    }
}
