package DAO;

import JDBC.DatabaseConnection;
import Model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentClassDAO {

    public static List<User> selectStudentsByClassID(String classID) {
        var list = new ArrayList<User>();
        var query = "SELECT UserID, Name, Email, Password, Role, ClassID FROM users WHERE Role = 'Student' AND ClassID = ?";
        try (var connection = DatabaseConnection.getConnection();
             var ps = connection.prepareStatement(query)) {
            ps.setString(1, classID);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                var user = new User(resultSet.getInt("UserID"), "user", "user", "user", "Teacher");
                user.setUserID(resultSet.getInt("UserID"));
                user.setName(resultSet.getString("Name"));
                user.setEmail(resultSet.getString("Email"));
                user.setPassword(resultSet.getString("Password"));
                user.setRole(resultSet.getString("Role"));
                user.setClassID(resultSet.getString("ClassID"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void main(String[] args) {
        String targetClassID = "CLS06028"; 
        List<User> students = StudentClassDAO.selectStudentsByClassID(targetClassID);
        if (!students.isEmpty()) {
            System.out.println("Danh sách sinh viên trong lớp " + targetClassID + ":");
            for (User student : students) {
                System.out.println("ID: " + student.getUserID() + ", Tên: " + student.getName());
            }
        } else {
            System.out.println("Không có sinh viên nào trong lớp " + targetClassID + ".");
        }
    }
}
