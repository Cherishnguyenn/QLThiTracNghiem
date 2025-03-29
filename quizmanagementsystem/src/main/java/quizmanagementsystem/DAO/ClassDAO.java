package quizmanagementsystem.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import quizmanagementsystem.DB.JDBCUtil;
import quizmanagementsystem.DTO.ClassDTO;

public class ClassDAO {

    // Kiểm tra mã lớp có bị trùng không
    public static boolean checkClassCodeExists(String classID) {
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM classes WHERE ClassID = ?")) {
            stmt.setString(1, classID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu COUNT > 0 tức là mã lớp đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm lớp vào database
    public static boolean insertClass(String classID, String className, int teacherID) {
        String sql = "INSERT INTO classes (ClassID, ClassName, TeacherID) VALUES (?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, classID);
            stmt.setString(2, className);
            stmt.setInt(3, teacherID);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     // Hàm xóa
     public static boolean deleteClass(String classID) {
        try {
            String query = "DELETE FROM classes WHERE classID = ?";
            PreparedStatement stmt = JDBCUtil.getConnection().prepareStatement(query);
            stmt.setString(1, classID);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Lấy danh sách lớp từ DB
    public static List<ClassDTO> getAllClasses() {
        List<ClassDTO> classList = new ArrayList<>();
        String sql = "SELECT * FROM classes";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ClassDTO c = new ClassDTO(rs.getString("ClassID"),
                        rs.getString("ClassName"));
                classList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classList;
    }
    //Hàm cập nhật tên lớp
    public static boolean updateClassName(String classID, String newClassName) {
        Connection conn = JDBCUtil.getConnection();
        String query = "UPDATE Classes SET ClassName = ? WHERE ClassID = ?";
    
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, newClassName);
            ps.setString(2, classID);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
   // Phương thức tìm kiếm lớp theo tên
   public static List<ClassDTO> searchClassByName(String keyword) {
    List<ClassDTO> classList = new ArrayList<>();
    String sql = "SELECT * FROM Classes WHERE className LIKE ?";
    
    try (PreparedStatement ps = JDBCUtil.getConnection().prepareStatement(sql)) {
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            ClassDTO classDTO = new ClassDTO();
            classDTO.setClassID(rs.getString("classID"));
            classDTO.setClassName(rs.getString("className"));
            classDTO.setTeacherID(rs.getInt("teacherID"));
            classList.add(classDTO);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return classList;
}
}
