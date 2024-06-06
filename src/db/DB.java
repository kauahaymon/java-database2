package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    // Static methods to connect and disconnect from db

    private static Connection conn = null;

    // Connect to db
    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props= loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            }
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    // Disconnect from db
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            }catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    // Method to load db.properties
    private static Properties loadProperties() {
        try (FileInputStream fileInput = new FileInputStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(fileInput);
            return properties;
        }
        catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    // Method to close Statement
    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    // Method to close ResultSet
    public static void closeResultSet(ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
