package com.eventLedger.accountService.config;

import com.eventLedger.accountService.filter.TraceFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilter() {

        FilterRegistrationBean<TraceFilter> bean =
                new FilterRegistrationBean<>();

        bean.setFilter(new TraceFilter());

        bean.addUrlPatterns("/*");

        return bean;
    }
}

