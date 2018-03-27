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
     * @return conn the connection to database
     * @throws IOException 
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    public Connection getConn() throws DBException, IOException, ClassNotFoundException, SQLException {
        props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            logger.error("Unable to load the file: {}", e1.getMessage(), e1);
            throw e1;
        }
        try {
            Class.forName(props.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            logger.error("Unable to find the class: " + e1.getMessage());
            throw e1;
        }
        try {
            conn = DriverManager.getConnection(props.getProperty("jdbc.url"), props.getProperty("jdbc.username"),
                    props.getProperty("jdbc.password"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error("Unable to connect to the database: " + e.getMessage());
            throw e;
        }
        return conn;
    }
}
