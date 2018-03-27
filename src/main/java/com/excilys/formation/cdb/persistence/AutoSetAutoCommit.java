package com.excilys.formation.cdb.persistence;

import java.sql.SQLException;
import java.sql.Connection;

public class AutoSetAutoCommit implements AutoCloseable {

    private Connection connection;
    private boolean originalAutoCommit;

    public AutoSetAutoCommit(Connection connection, boolean autoCommit) throws SQLException {
        this.connection = connection;
        originalAutoCommit = connection.getAutoCommit();
        connection.setAutoCommit(autoCommit);
    }

    @Override
    public void close() throws SQLException {
        connection.setAutoCommit(originalAutoCommit);
    }
}