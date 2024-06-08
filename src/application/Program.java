package application;

import db.DB;
import db.DbException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {
    public static void main(String[] args) {

        // Transaction
        Connection conn = null;
        Statement st = null;

        try {
            conn = DB.getConnection();
            st = conn.createStatement();

            // Autocommit disabled
            conn.setAutoCommit(false);

            int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");

            // Fake exception
            if (1 == 1) {
                throw new SQLException("Fake error");
            }

            int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");

            // Save changes in database
            conn.commit();

            System.out.println("Done! Rows1: " + rows1);
            System.out.println("Done! Rows2: " + rows2);
        }
        catch (SQLException e) {
            // Exception caught during update
            try {
                // Changes were undone
                conn.rollback();
                throw new DbException("Transaction rolled back! Cause: " + e.getMessage());
            } catch (SQLException ex) {
                throw new DbException("Rolled back failed! Cause: " + ex.getMessage());
            }
        }
        finally {
            DB.closeStatement(st);
            DB.getConnection();
        }
    }
}
