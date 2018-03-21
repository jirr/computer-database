package com.excilys.formation.cdb.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ConnexionManager {
    INSTANCE;

    private final Logger logger = LoggerFactory.getLogger(ConnexionManager.class);

    private Connection conn;
    private Properties props;
    private FileInputStream file;

    /**
     * @return the conn
     */
    public Connection getConn() {
        props = new Properties();
        try {
            file = new FileInputStream("src/main/resources/db.properties");
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            logger.error("Unable find the file: " + e1.getMessage());
        }
        try {
            props.load(file);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            logger.error("Unable to load the file: " + e1.getMessage());
        }
        try {
            Class.forName(props.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            logger.error("Unable to find the class: " + e1.getMessage());
        }
        try {
            conn = DriverManager.getConnection(props.getProperty("jdbc.url"), props.getProperty("jdbc.username"),
                    props.getProperty("jdbc.password"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error("Unable to connect to the database: " + e.getMessage());
        }
        return conn;
    }
}
