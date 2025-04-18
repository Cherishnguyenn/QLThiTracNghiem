package JDBC;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ql_thitracnghiem";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Thanh@1810";
    private static Connection instance;

    public static Connection getConnection() throws SQLException {
    if (instance == null || instance.isClosed()) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            instance = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy JDBC Driver", e);
        }
    }
    return instance;
}

    public static Object getConnectionInstance() {
        throw new UnsupportedOperationException("Not supported yet.");     }

    private DatabaseConnection() {
    }

    public static void closeConnection() {
        if (instance != null) {
            try {
                instance.close();
                System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
                instance = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            DatabaseConnection.closeConnection();
        }
    }
}
