package LabBookingSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	//static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
static final String DB_URL = "jdbc:mysql://localhost:3306/protocoanpc7699ShrushtiNaik?useSSL=false"; // Fixed the URL format
    //static Connection conn;

    // Database Credentials
    static final String user = "root";
    static final String password = "Shru*2211";

    public static Connection createC() throws SQLException {
        try {
            // Load the driver explicitly before creating the connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Create the connection
            //conn = DriverManager.getConnection(DB_URL, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(DB_URL, user, password);
    }
}
