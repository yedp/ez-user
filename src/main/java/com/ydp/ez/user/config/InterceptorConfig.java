package com.ydp.ez.user.config;

import com.ydp.ez.user.common.Interceptor.CrossDomainInterceptor;
import com.ydp.ez.user.common.Interceptor.AuthorizeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Autowired
    private CrossDomainInterceptor crossDomainInterceptor;
    @Autowired
    private AuthorizeInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(crossDomainInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}