//package com.safetycar.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
//import org.springframework.web.servlet.i18n.SessionLocaleResolver;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//import org.springframework.web.servlet.view.JstlView;
//
//import java.util.Locale;
//
//import static com.safetycar.util.Constants.ConfigConstants.APPLICATION_PROPERTIES;
//
//@Configuration
//@PropertySource(APPLICATION_PROPERTIES)
//public class EmailConfig extends WebMvcConfigurerAdapter {
//
//    @Bean(name = "viewResolver")
//    public InternalResourceViewResolver getViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setViewClass(JstlView.class);
//        resolver.setPrefix("/views/");
//        resolver.setSuffix(".jsp");
//        resolver.setContentType("text/html; charset=UTF-8");
//        return resolver;
//    }
//
//    @Bean(name = "localeResolver")
//    public SessionLocaleResolver getSessionLocaleResolver() {
//        SessionLocaleResolver resolver = new SessionLocaleResolver();
//        resolver.setDefaultLocale(Locale.ENGLISH);
//        return resolver;
//    }
//
//    @Bean(name = "localeChangeInterceptor")
//    public LocaleChangeInterceptor getLocaleChangeInterceptor() {
//        //TODO
//        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
//        interceptor.setParamName("language");
//        return interceptor;
//
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/css*").addResourceLocations("/resources/css/");
//    }
//
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(getLocaleChangeInterceptor());
//    }
//}