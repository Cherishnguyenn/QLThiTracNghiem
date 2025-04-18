package DAO;

import JDBC.DatabaseConnection;
import Model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static List<User> selectAll() {
        var list = new ArrayList<User>();
        var query = "SELECT UserID, Name, Email, Password, Role, ClassID FROM users";
        try (var statement = DatabaseConnection.getConnection().createStatement()) {
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(new User(
                                resultSet.getInt("UserID"),
                                resultSet.getString("Name"),
                                resultSet.getString("Email"),
                                resultSet.getString("Password"),
                                resultSet.getString("Role"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static User selectByEmail(String email) {
        var query = "SELECT UserID, Name, Email, Password, Role, ClassID FROM users WHERE Email = ?";
        try (var ps = DatabaseConnection.getConnection().prepareStatement(query)) {
            ps.setString(1, email);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("UserID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        resultSet.getString("Role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static User selectByID(int userID) {
        var query = "SELECT UserID, Name, Email, Password, Role, ClassID FROM users WHERE UserID = ?";
        try (var ps = DatabaseConnection.getConnection().prepareStatement(query)) {
            ps.setInt(1, userID);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("UserID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        resultSet.getString("Role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean insert(User user) {
        var query = "INSERT INTO users (Name, Email, Password, Role, ClassID) VALUES (?, ?, ?, ?, ?)";
        try (var ps = DatabaseConnection.getConnection().prepareStatement(query)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getClassID());
            var count = ps.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(User user) {
        var query = "UPDATE users SET Name = ?, Email = ?, Password = ?, Role = ?, ClassID = ? WHERE UserID = ?";
        try (var ps = DatabaseConnection.getConnection().prepareStatement(query)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword()); 
            ps.setString(4, user.getRole());
            ps.setString(5, user.getClassID());
            ps.setInt(6, user.getUserID());
            var count = ps.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(int userID) {
        var query = "DELETE FROM users WHERE UserID = ?";
        try (var ps = DatabaseConnection.getConnection().prepareStatement(query)) {
            ps.setInt(1, userID);
            var count = ps.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<User> allUsers = UserDAO.selectAll();
        allUsers.forEach(System.out::println);

        User userByEmail = UserDAO.selectByEmail("test@example.com");
        System.out.println("User by email: " + userByEmail);

        User userByID = UserDAO.selectByID(1);
        System.out.println("User by ID: " + userByID);

        if (userByID != null) {
            userByID.setName("Updated Name");
            if (UserDAO.update(userByID)) {
                System.out.println("Cập nhật người dùng thành công.");
            } else {
                System.out.println("Cập nhật người dùng thất bại.");
            }
        }
        
    }

    public static boolean checkPassword(String password, String password_encrypted_db) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
