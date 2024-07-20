package com.tek.config.filter;
import com.tek.config.filter.LocaleFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LocaleFilter> localeFilterRegistration() {
        FilterRegistrationBean<LocaleFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LocaleFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1); // Ensure this is ordered before other filters if necessary
        return registrationBean;
    }
}
