/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.configs;

import com.busplanner.formatter.BusFormatter;
import com.busplanner.formatter.RouteFormatter;
import com.busplanner.validator.UsernameValidator;
import com.busplanner.validator.WebAppValidator;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 *
 * @author Admin
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.busplanner.controllers",
    "com.busplanner.repositories",
    "com.busplanner.services",
    "com.busplanner.component",
    "com.busplanner.validator",})
public class WebApplicationContext implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // Basic ViewResolver for template
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    // Specify Property Files
    public MessageSource messageSource() {
        ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
        resource.setBasenames("beanValidator", "webapp", "adminPage");
        return resource;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new BusFormatter());
        registry.addFormatter(new RouteFormatter());
    }

    // bean validator configuration
    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    // for uploading file
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }

    // Spring Validation 
//    @Bean
//    @DependsOn("validator")  // Đảm bảo rằng "validator" đã được khởi tạo trước
//    public WebAppValidator applicationValidator() {
//        Set<Validator> springValidators = new HashSet<>();
//        springValidators.add(new UsernameValidator());
//
//        WebAppValidator validator = new WebAppValidator();
//        validator.setSpringValidators(springValidators);
//        return validator;
//    }
//    @Override   //thiết lập trả về json thay vì xml
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//        jsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
//        converters.add(jsonConverter);
//    }
    
    //static resource
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/resources/images/");
        
    }
    
    /* Nó cung cấp một API đơn giản để tương tác với các dịch vụ web RESTful,
        giúp việc gửi và nhận dữ liệu qua HTTP trở nên dễ dàng hơn
        - gửi các yêu cầu HTTP như GET, POST, PUT, DELETE đến các server khác.
        - xử lý các phản hồi từ server, bao gồm việc chuyển đổi dữ liệu JSON/XML 
                thành các đối tượng Java (deserialize) */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    

}
