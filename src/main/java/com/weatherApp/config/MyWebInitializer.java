package com.weatherApp.config;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.SessionTrackingMode;
import org.jspecify.annotations.Nullable;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.util.Set;

public class MyWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setSessionTrackingModes(Set.of(SessionTrackingMode.COOKIE));
        super.onStartup(servletContext);
    }

    @Override
    protected Class<?> @Nullable [] getRootConfigClasses() {
        return new Class[] {DatabaseConfig.class, AppConfig.class}; // корневой контекст (сервисы, DAO)
    }

    @Override
    protected Class<?> @Nullable [] getServletConfigClasses() {
        return new Class[] {WebConfig.class}; // контекст для MVC (контроллеры, ViewResolver)
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"}; // все запросы идут на DispatcherServlet
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new DelegatingFilterProxy("authFilter")};
    }
}
