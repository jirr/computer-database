package com.excilys.formation.cdb.persistence;

import java.sql.SQLException;
import java.sql.Connection;

public class AutoRollback implements AutoCloseable {

    private Connection connection;
    private boolean committed;

    public AutoRollback(Connection connection) throws SQLException {
        this.connection = connection;        
    }

    public void commit() throws SQLException {
        connection.commit();
        committed = true;
    }

    @Override
    public void close() throws SQLException {
        if(!committed) {
            connection.rollback();
        }
    }
}