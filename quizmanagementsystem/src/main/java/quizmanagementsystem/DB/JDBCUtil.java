package quizmanagementsystem.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/ql_thitracnghiem";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn != null) {
                System.out.println("Thanh cong");
            }
            return conn;
        } catch (SQLException e) {
            System.err.println("That bai");
            return null; // Trả về null nếu kết nối thất bại
        }
    }
}
