package com.weatherApp.config;

import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?> @Nullable [] getRootConfigClasses() {
        return new Class[] {DatabaseConfig.class}; // корневой контекст (сервисы, DAO)
    }

    @Override
    protected Class<?> @Nullable [] getServletConfigClasses() {
        return new Class[] {WebConfig.class}; // контекст для MVC (контроллеры, ViewResolver)
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"}; // все запросы идут на DispatcherServlet
    }
}
