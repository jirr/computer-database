package com.excilys.formation.cdb.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum DataSource {
    INSTANCE;

    private HikariConfig config = new HikariConfig();
    private HikariDataSource dataSource;
    private Properties properties;

    public Connection getConnection() throws SQLException, IOException {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("datasource.properties"));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            throw e1;
        }
        config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("dataSource.url"));
        config.setDriverClassName(properties.getProperty("driverClassName"));
        config.setUsername(properties.getProperty("dataSource.user"));
        config.setPassword(properties.getProperty("dataSource.password"));
        config.setMaximumPoolSize(2);
        dataSource = new HikariDataSource(config);
        return dataSource.getConnection();
    }
}