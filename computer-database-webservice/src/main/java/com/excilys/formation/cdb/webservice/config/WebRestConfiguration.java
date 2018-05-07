package com.excilys.formation.cdb.webservice.config;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.excilys.formation.cdb.config.ServiceConfig;

@Configuration
@Import(ServiceConfig.class)
@EnableWebMvc
@PropertySource(value = "classpath:db.properties")
@ComponentScan(basePackages = "com.excilys.formation.cdb")
public class WebRestConfiguration implements WebMvcConfigurer {
    
    @Override
    public void configureMessageConverters(
      List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}