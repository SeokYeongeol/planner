package org.example.planner.config;

import jakarta.servlet.Filter;
import org.example.planner.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 로그인 필터 Bean 등록
    @Bean
    public FilterRegistrationBean<Filter> loginFilter() {
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new LoginFilter());
        filterRegistration.setOrder(1);
        filterRegistration.addUrlPatterns("/");

        return filterRegistration;
    }
}
