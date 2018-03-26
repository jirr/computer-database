package com.excilys.formation.cdb.persistence;

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

    /**
     * @return the conn connection to database
     * @throws SQLException if the connection goes wrong
     * @throws ClassNotFoundException if the forName can't reach the driver
     * @throws IOException if the loading file fail
     */
    public Connection getConn() {
        props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("db.properties"));
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
