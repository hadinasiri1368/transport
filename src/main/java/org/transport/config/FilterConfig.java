package org.transport.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.transport.filter.CheckPermission;
import org.transport.service.AuthenticationServiceProxy;

@Configuration
public class FilterConfig {
    @Autowired
    private AuthenticationServiceProxy authenticationServiceProxy;
    @Bean
    public FilterRegistrationBean<CheckPermission> checkPermissionFilterRegistrationBean() {
        FilterRegistrationBean<CheckPermission> checkPermissionFilterRegistrationBean = new FilterRegistrationBean<>();
        checkPermissionFilterRegistrationBean.setFilter(new CheckPermission(authenticationServiceProxy));
        checkPermissionFilterRegistrationBean.addUrlPatterns("/transport/*");
        return  checkPermissionFilterRegistrationBean;
    }
}