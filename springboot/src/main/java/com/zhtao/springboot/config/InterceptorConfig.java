package com.zhtao.springboot.config;

import com.zhtao.springboot.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author ZhTao
 * @PackageName:com.zhtao.springboot.config
 * @ClassName: InterceptorConfig
 * @Date 2022/6/6 22:51
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")    // 拦截所有请求，通过判断token是否合法 来 决定是否需要登录
                .excludePathPatterns("/user/login","/user/register","**/export","**/import");   // 排除的请求
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
