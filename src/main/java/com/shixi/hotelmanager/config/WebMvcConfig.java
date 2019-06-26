package com.shixi.hotelmanager.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: oyc
 * @Date: 2019/1/29 11:39
 * @Description:
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 视图控制器
     *
     * @param registry
     */
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/toLogin").setViewName("login");
        registry.addViewController("/admin_test").setViewName("admin_test");
        super.addViewControllers(registry);
    }

    /**
     * 配置静态资源访问
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
        super.addResourceHandlers(registry);
    }


}