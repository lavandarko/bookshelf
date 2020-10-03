package DAO;

import entities.Book;

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {

    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/BookShelfDB";
    static final String USER = "postgres";
    static final String PASS = "1234";
    Connection connection = null;


    protected Connection getConnection() throws Exception {
        if (connection == null) {
            Class.forName("org.postgresql.Driver");
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
        }
        return connection;
    }

    protected void closeConnection() throws SQLException {

        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}
