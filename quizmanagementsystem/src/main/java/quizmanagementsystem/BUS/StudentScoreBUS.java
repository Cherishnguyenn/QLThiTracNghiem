package quizmanagementsystem.BUS;

import quizmanagementsystem.DAO.StudentScoreDAO;
import quizmanagementsystem.DTO.StudentScoreDTO;
import java.util.List;

public class StudentScoreBUS {
    public static List<StudentScoreDTO> getStudentScores(int studentID, String startDate, String endDate) {
        return StudentScoreDAO.getStudentScores(studentID, startDate, endDate);
    }

    public static String getStartDate(int userID) {
        return StudentScoreDAO.getStartDate(userID);
    }

    public static String getEndDate(int userID) {
        return StudentScoreDAO.getEndDate(userID);
    }
}
