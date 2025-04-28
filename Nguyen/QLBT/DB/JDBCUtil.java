package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/ql_thitracnghiem";
    private static final String USER = "root";
    private static final String PASSWORD = "Thanh@1810";
    private static Connection connection;

    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load the MySQL JDBC driver (explicitly, although DriverManager might do it)
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Kết nối database thành công.");
            } catch (ClassNotFoundException e) {
                System.err.println("Lỗi: Không tìm thấy driver JDBC MySQL!");
                throw new SQLException("MySQL JDBC driver not found", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Đóng kết nối database thành công."); 
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection = null;
            }
        }
    }

    private static class SQLExeception {
        public SQLExeception() {
        }
    }

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DB.JDBCUtil.getConnection();
            if (conn != null) {
                System.out.println("Kết nối thành công trong main method.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối trong main method: " + e.getMessage());
        } finally {
            DB.JDBCUtil.closeConnection();
        }
    }
}
