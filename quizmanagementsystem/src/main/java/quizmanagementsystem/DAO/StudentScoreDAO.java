package quizmanagementsystem.DAO;

import quizmanagementsystem.DB.JDBCUtil;
import quizmanagementsystem.DTO.StudentScoreDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentScoreDAO {
    public static List<StudentScoreDTO> getStudentScores(int studentID, String startDate, String endDate) {
        List<StudentScoreDTO> scores = new ArrayList<>();
        System.out.println("Fetching scores for studentID: " + studentID + " from " + startDate + " to " + endDate); // Debug statement
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM studentsubmissions WHERE StudentID = ? AND StartTime BETWEEN ? AND ?")) {
            stmt.setInt(1, studentID);
            stmt.setString(2, startDate + " 00:00:00");
            stmt.setString(3, endDate + " 23:59:59");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                StudentScoreDTO score = new StudentScoreDTO();
                score.setExamID(rs.getInt("ExamID"));
                score.setScore(rs.getDouble("Score"));
                scores.add(score);
            }
            System.out.println("Fetched scores: " + scores); // Debug statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public static String getStartDate(int userID) {
        String startDate = null;
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT MIN(StartTime) AS start_date FROM studentsubmissions WHERE StudentID = ?")) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                startDate = rs.getString("start_date");
            }
            System.out.println("Fetched startDate: " + startDate); // Debug statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public static String getEndDate(int userID) {
        String endDate = null;
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT MAX(StartTime) AS end_date FROM studentsubmissions WHERE StudentID = ?")) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                endDate = rs.getString("end_date");
            }
            System.out.println("Fetched endDate: " + endDate); // Debug statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return endDate;
    }
}