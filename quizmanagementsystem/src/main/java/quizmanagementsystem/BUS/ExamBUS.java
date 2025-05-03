package BUS;

import DB.JDBCUtil;
import DTO.AnswerDTO;
import DTO.ExamsDTO;
import DTO.QuestionDTO;
import DTO.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamBUS {

    public List<QuestionDTO> getAllQuestionsFromBank() throws SQLException {
    List<QuestionDTO> questionList = new ArrayList<>();
    Connection connection = null;
    PreparedStatement questionStmt = null;
    ResultSet questionRs = null;
    PreparedStatement answerStmt = null;
    ResultSet answerRs = null;

    try {
        connection = JDBCUtil.getConnection();
        String questionSql = "SELECT QuestionID, QuestionText, QuestionType FROM questionbank";
        questionStmt = connection.prepareStatement(questionSql);
        questionRs = questionStmt.executeQuery();

        String answerSql = "SELECT AnswerID, QuestionID, AnswerText, IsCorrect FROM answers WHERE QuestionID = ?";
        answerStmt = connection.prepareStatement(answerSql);

        while (questionRs.next()) {
            QuestionDTO question = new QuestionDTO();
            question.setQuestionID(questionRs.getInt("QuestionID"));
            question.setQuestionText(questionRs.getString("QuestionText"));
            question.setQuestionType(questionRs.getString("QuestionType"));

            answerStmt.setInt(1, question.getQuestionID());
            answerRs = answerStmt.executeQuery();
            while (answerRs.next()) {
                AnswerDTO answer = new AnswerDTO();
                answer.setAnswerId(answerRs.getInt("AnswerId"));
                answer.setQuestionId(answerRs.getInt("QuestionId")); 
                answer.setAnswerText(answerRs.getString("AnswerText"));
                answer.setCorrect(answerRs.getBoolean("IsCorrect"));
                question.addAnswer(answer);
            }
            if (answerRs != null) {
                answerRs.close();
            }
            questionList.add(question);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
    return questionList;
}

    public int addExam(ExamsDTO exam, List<QuestionDTO> questions) throws SQLException {
        Connection connection = null;
        PreparedStatement examStmt = null;
        ResultSet generatedKeys = null;
        int newExamID = -1;

        try {
            connection = JDBCUtil.getConnection();
            String examSql = "INSERT INTO exams (ClassID, ExamName, ExamDate, ExamTime, ExamType, NumberOfQuestions, TotalScore) VALUES (?, ?, ?, ?, ?, ?, ?)";
            examStmt = connection.prepareStatement(examSql, PreparedStatement.RETURN_GENERATED_KEYS);
            examStmt.setString(1, exam.getClassID());
            examStmt.setString(2, exam.getExamName());
            examStmt.setString(3, exam.getExamDate());
            examStmt.setString(4, exam.getExamTime());
            examStmt.setString(5, exam.getExamType());
            examStmt.setInt(6, questions.size());
            examStmt.setDouble(7, questions.size()); 

            int rowsAffected = examStmt.executeUpdate();
            if (rowsAffected > 0) {
                generatedKeys = examStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    newExamID = generatedKeys.getInt(1);
                    linkQuestionsToExam(connection, newExamID, questions);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } 
        
        return newExamID;
    }

    private void linkQuestionsToExam(Connection connection, int examID, List<QuestionDTO> questions) throws SQLException {
        PreparedStatement examQuestionStmt = null;
        try {
            String sql = "INSERT INTO examquestions (ExamID, QuestionID) VALUES (?, ?)";
            examQuestionStmt = connection.prepareStatement(sql);
            for (QuestionDTO question : questions) {
                examQuestionStmt.setInt(1, examID);
                examQuestionStmt.setInt(2, question.getQuestionID());
                examQuestionStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (examQuestionStmt != null) {
                examQuestionStmt.close();
            }
        }
    }

    public List<ExamsDTO> getAllExams() throws SQLException {
    List<ExamsDTO> examList = new ArrayList<>();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        connection = JDBCUtil.getConnection();
        String sql = "SELECT ExamID, ClassID, ExamName, ExamDate, ExamType, ExamTime FROM exams";
        preparedStatement = connection.prepareStatement(sql);
        if (preparedStatement != null) {
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    ExamsDTO exam = new ExamsDTO();
                    exam.setExamID(resultSet.getInt("ExamID"));
                    exam.setClassID(resultSet.getString("ClassID"));
                    exam.setExamName(resultSet.getString("ExamName"));
                    exam.setExamDate(resultSet.getString("ExamDate"));
                    exam.setExamType(resultSet.getString("ExamType"));
                    exam.setExamTime(resultSet.getString("ExamTime"));
                    examList.add(exam);
                }
            } else {
                System.err.println("Error: executeQuery() returned null ResultSet.");
            }
        } else {
            System.err.println("Error: prepareStatement() returned null PreparedStatement. Check your SQL query or connection.");
        }
    } catch (SQLException e) {
        System.err.println("SQLException in getAllExams: " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
    return examList;
}

    public void assignExamToUsers(int examIDToAssign, List<UserDTO> assignedUsers) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO exam_users (ExamID, UserID) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            for (UserDTO user : assignedUsers) {
                preparedStatement.setInt(1, examIDToAssign);
                preparedStatement.setInt(2, user.getUserID());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
}
