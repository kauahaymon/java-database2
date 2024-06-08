package application;

import db.DB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {
    public static void main(String[] args) {

        // Deleting data from db
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DB.getConnection();
            st = conn.prepareStatement("DELETE FROM department " +
                    "WHERE id = ? ");
            st.setInt(1, 2);

            int rowsAffected = st.executeUpdate();
            System.out.println("Done ! Rows affected: " + rowsAffected);
        }
        catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.getConnection();
        }
    }
}
