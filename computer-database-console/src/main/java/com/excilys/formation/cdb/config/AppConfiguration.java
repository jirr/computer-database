package com.excilys.formation.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("CLI")
@Import(ServiceConfig.class)
@ComponentScan(basePackages = "com.excilys.formation.cdb")
public class AppConfiguration {
}