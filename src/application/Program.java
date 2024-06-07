package application;

import db.DB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Retrieving data from db

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DB.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery("select * from department");

            while (rs.next()) {
                System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

        // Inserting data into db

        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(
                    "INSERT INTO seller " +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, "Bob Purple");
            pst.setString(2, "bobpurple@gmail.com");
            pst.setDate(3, new java.sql.Date(sdf.parse("28/04/1985").getTime()));
            pst.setDouble(4, 3000.0);
            pst.setInt(5, 1);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKey = pst.getGeneratedKeys();
                while (generatedKey.next()) {
                    int id = generatedKey.getInt(1);
                    System.out.println("Done! New Id: " + id);
                }
            }
            else {
                System.out.println("Rows affected: " + rowsAffected);
            }
        }
        catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        finally {
            DB.closeStatement(pst);
            DB.closeConnection();
        }
    }
}
