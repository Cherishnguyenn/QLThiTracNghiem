package quizmanagementsystem.DAO;

import quizmanagementsystem.DB.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static String authenticateUser(String email, String password) {
        String role = null;
        Connection conn = JDBCUtil.getConnection();

        if (conn == null) {
            System.out.println("❌ Lỗi kết nối DB!");
            return null;
        }

        String sql = "SELECT Role FROM Users WHERE Email = ? AND Password = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                role = rs.getString("Role");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Lỗi UserDAO: " + e.getMessage());
        }
        return role;
    }

    public static int getUserID(String email) {
        int userID = -1;
        Connection conn = JDBCUtil.getConnection();
        
        if (conn == null) return -1;
    
        String sql = "SELECT UserID FROM Users WHERE Email = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                userID = rs.getInt("UserID");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Lỗi lấy UserID: " + e.getMessage());
        }
        return userID;
    }

    public static String getUserName(int userID) {
        String name = "Không xác định";
        Connection conn = JDBCUtil.getConnection();
    
        if (conn == null) return name;
    
        String sql = "SELECT Name FROM Users WHERE UserID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                name = rs.getString("Name");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Lỗi lấy tên người dùng: " + e.getMessage());
        }
        return name;
    }
    public static boolean updatePassword(String email, String newPassword) {
        // Kết nối đến cơ sở dữ liệu và thực hiện cập nhật
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "UPDATE users SET password = ? WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, email);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0; // Trả về true nếu cập nhật thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }
}
