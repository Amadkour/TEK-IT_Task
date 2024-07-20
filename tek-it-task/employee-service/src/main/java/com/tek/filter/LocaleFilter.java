package com.tek.filter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.IOException;
import java.util.Locale;

@Component
public class LocaleFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String language = httpRequest.getHeader("Accept-Language");

        Locale locale = (language != null && !language.isEmpty()) ? Locale.forLanguageTag(language) : Locale.ENGLISH;
// Get LocaleResolver from the application context
        LocaleResolver localeResolver = WebApplicationContextUtils
                .getRequiredWebApplicationContext(httpRequest.getServletContext())
                .getBean(LocaleResolver.class);
        localeResolver.setLocale(httpRequest, null, locale);

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
