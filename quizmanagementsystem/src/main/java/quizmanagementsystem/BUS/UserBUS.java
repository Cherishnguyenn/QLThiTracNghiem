package quizmanagementsystem.BUS;

import quizmanagementsystem.DAO.UserDAO;

public class UserBUS {
    public static String loginUser(String email, String password) {
        return UserDAO.authenticateUser(email, password);
    }

    // Dùng chung UserID cho tất cả role
    public static int getUserID(String email) {
        return UserDAO.getUserID(email);
    }

    public static String getUserName(int userID) {
        return UserDAO.getUserName(userID);
    }

    public static boolean updatePassword(String email, String newPassword) {
        // Có thể thêm các logic nghiệp vụ khác ở đây nếu cần
        return UserDAO.updatePassword(email, newPassword);
    }
}
