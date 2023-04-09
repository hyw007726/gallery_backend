package cn.batenglish.gallery.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;


@Configuration
public class Beans {
    private final Environment env;

    public Beans(Environment env) {
        this.env = env;
    }
//
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setMaxFileSize(DataSize.parse("10MB"));
//        factory.setMaxRequestSize(DataSize.parse("10MB"));
//
//        // set the file upload directory based on the current environment
//        String uploadDirectory = env.getProperty("upload.directory");
//        if (uploadDirectory == null) {
//            // use a default directory if no directory is specified
//            uploadDirectory = "/uploads";
//        }
//        factory.setLocation(uploadDirectory);
//
//        return factory.createMultipartConfig();
//    }
//
//    @Bean
//    public MultipartResolver multipartResolver(HttpServletRequest request) {
//        return new StandardServletMultipartResolver();
//    }
}
