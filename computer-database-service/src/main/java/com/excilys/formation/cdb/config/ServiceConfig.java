package com.excilys.formation.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.formation.cdb.persistence.config.DataSourceConfig;

@Configuration
@Import(DataSourceConfig.class)
@ComponentScan({"com.excilys.formation.cdb.service", 
    "com.excilys.formation.cdb.validator",
    "com.excilys.formation.cdb.pagination"})
public class ServiceConfig {

}