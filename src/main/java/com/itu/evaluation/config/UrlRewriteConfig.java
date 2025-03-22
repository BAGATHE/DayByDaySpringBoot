package com.itu.evaluation.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

@Configuration
public class UrlRewriteConfig {
    @Bean
    public FilterRegistrationBean<UrlRewriteFilter> urlRewriteFilter() {
        FilterRegistrationBean<UrlRewriteFilter> filterRegistrationBean = new FilterRegistrationBean<>(new UrlRewriteFilter());
        filterRegistrationBean.addInitParameter("confPath", "urlrewrite.xml"); // Indique où trouver la config
        filterRegistrationBean.addUrlPatterns("/*"); // Appliquer sur toutes les URLs
        return filterRegistrationBean;
    }
}
