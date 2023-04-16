package cn.batenglish.gallery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class StaticResourceConfiguration implements WebMvcConfigurer {
    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:"+uploadDir);
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/build/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS));
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations("file:/Users/hanyawei/Desktop/gallery/uploads/");
    }
}

