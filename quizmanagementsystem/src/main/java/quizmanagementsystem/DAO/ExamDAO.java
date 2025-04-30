package quizmanagementsystem.DAO;

import quizmanagementsystem.DTO.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExamDAO {
    private Connection connection;

    public ExamDAO() throws SQLException {
        this.connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/ql_thitracnghiem",
            "root",
            "metalgearsolid12");
    }

    // 1. Get exam, questions, and answers by exam ID
    public ExamWithQuestionsDTO getExamWithQuestions(int examId) throws SQLException {
        ExamWithQuestionsDTO examDTO = new ExamWithQuestionsDTO();
        
        // Get exam info
        String examSql = "SELECT * FROM exams WHERE ExamID = ?";
        try (PreparedStatement examStmt = connection.prepareStatement(examSql)) {
            examStmt.setInt(1, examId);
            ResultSet examRs = examStmt.executeQuery();
            if (examRs.next()) {
                examDTO.setExamId(examRs.getInt("ExamID"));
                examDTO.setExamName(examRs.getString("ExamName"));
                examDTO.setExamDate(examRs.getTimestamp("ExamDate"));
                examDTO.setExamType(examRs.getString("ExamType"));
                examDTO.setExamTime(examRs.getTime("ExamTime"));
            }
        }
        
        // Get questions and answers
        String questionsSql = "SELECT q.*, a.* FROM examquestions eq " +
                             "JOIN questionbank q ON eq.QuestionID = q.QuestionID " +
                             "JOIN answers a ON q.QuestionID = a.QuestionID " +
                             "WHERE eq.ExamID = ?";
        try (PreparedStatement questionsStmt = connection.prepareStatement(questionsSql)) {
            questionsStmt.setInt(1, examId);
            ResultSet rs = questionsStmt.executeQuery();
            
            List<QuestionWithAnswersDTO> questions = new ArrayList<>();
            QuestionWithAnswersDTO currentQuestion = null;
            int currentQuestionId = -1;
            
            while (rs.next()) {
                int questionId = rs.getInt("QuestionID");
                if (questionId != currentQuestionId) {
                    if (currentQuestion != null) {
                        questions.add(currentQuestion);
                    }
                    currentQuestion = new QuestionWithAnswersDTO();
                    currentQuestion.setQuestionId(questionId);
                    currentQuestion.setQuestionText(rs.getString("QuestionText"));
                    currentQuestion.setQuestionType(rs.getString("QuestionType"));
                    currentQuestion.setAnswers(new ArrayList<>());
                    currentQuestionId = questionId;
                }
                
                AnswerDTO answer = new AnswerDTO();
                answer.setAnswerId(rs.getInt("AnswerID"));
                answer.setAnswerText(rs.getString("AnswerText"));
                answer.setCorrect(rs.getBoolean("IsCorrect"));
                currentQuestion.getAnswers().add(answer);
            }
            
            if (currentQuestion != null) {
                questions.add(currentQuestion);
            }
            
            examDTO.setQuestions(questions);
        }
        
        return examDTO;
    }

    // 2. Get exam, questions, answers, and user's answers by user ID and exam ID
    public ExamWithUserAnswersDTO getExamWithUserAnswers(int userId, int examId) throws SQLException {
        ExamWithUserAnswersDTO examDTO = new ExamWithUserAnswersDTO();
        
        // Get exam info
        String examSql = "SELECT * FROM exams WHERE ExamID = ?";
        try (PreparedStatement examStmt = connection.prepareStatement(examSql)) {
            examStmt.setInt(1, examId);
            ResultSet examRs = examStmt.executeQuery();
            if (examRs.next()) {
                examDTO.setExamId(examRs.getInt("ExamID"));
                examDTO.setExamName(examRs.getString("ExamName"));
                examDTO.setExamDate(examRs.getTimestamp("ExamDate"));
                examDTO.setExamType(examRs.getString("ExamType"));
                examDTO.setExamTime(examRs.getTime("ExamTime"));
            }
        }
        
        // Get questions, answers, and user's selected answers
        String questionsSql = "SELECT q.*, a.*, sa.AnswerID AS SelectedAnswerID " +
                             "FROM examquestions eq " +
                             "JOIN questionbank q ON eq.QuestionID = q.QuestionID " +
                             "JOIN answers a ON q.QuestionID = a.QuestionID " +
                             "LEFT JOIN studentanswers sa ON sa.QuestionID = q.QuestionID " +
                             "AND sa.SubmissionID = (SELECT SubmissionID FROM studentsubmissions " +
                             "WHERE StudentID = ? AND ExamID = ?) " +
                             "WHERE eq.ExamID = ?";
        try (PreparedStatement questionsStmt = connection.prepareStatement(questionsSql)) {
            questionsStmt.setInt(1, userId);
            questionsStmt.setInt(2, examId);
            questionsStmt.setInt(3, examId);
            ResultSet rs = questionsStmt.executeQuery();
            
            List<QuestionWithUserAnswersDTO> questions = new ArrayList<>();
            QuestionWithUserAnswersDTO currentQuestion = null;
            int currentQuestionId = -1;
            
            while (rs.next()) {
                int questionId = rs.getInt("QuestionID");
                if (questionId != currentQuestionId) {
                    if (currentQuestion != null) {
                        questions.add(currentQuestion);
                    }
                    currentQuestion = new QuestionWithUserAnswersDTO();
                    currentQuestion.setQuestionId(questionId);
                    currentQuestion.setQuestionText(rs.getString("QuestionText"));
                    currentQuestion.setQuestionType(rs.getString("QuestionType"));
                    currentQuestion.setAnswers(new ArrayList<>());
                    currentQuestionId = questionId;
                }
                
                AnswerWithSelectionDTO answer = new AnswerWithSelectionDTO();
                answer.setAnswerId(rs.getInt("AnswerID"));
                answer.setAnswerText(rs.getString("AnswerText"));
                answer.setCorrect(rs.getBoolean("IsCorrect"));
                answer.setSelected(rs.getInt("SelectedAnswerID") == rs.getInt("AnswerID"));
                currentQuestion.getAnswers().add(answer);
            }
            
            if (currentQuestion != null) {
                questions.add(currentQuestion);
            }
            
            examDTO.setQuestions(questions);
        }
        
        return examDTO;
    }

    // 3. Get ExamSubmission info by exam ID and user ID
    public ExamSubmissionDTO getExamSubmission(int userId, int examId) throws SQLException {
        ExamSubmissionDTO submission = null;
        
        String sql = "SELECT * FROM studentsubmissions " +
                     "WHERE StudentID = ? AND ExamID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, examId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                submission = new ExamSubmissionDTO();
                submission.setSubmissionId(rs.getInt("SubmissionID"));
                submission.setStudentId(rs.getInt("StudentID"));
                submission.setExamId(rs.getInt("ExamID"));
                submission.setStartTime(rs.getTimestamp("StartTime"));
                submission.setEndTime(rs.getTimestamp("EndTime"));
                submission.setScore(rs.getDouble("Score"));
                submission.setCorrectAnswers(rs.getInt("CorrectAnswers"));
                submission.setTotalQuestions(rs.getInt("TotalQuestions"));
            }
        }
        
        return submission;
    }
    
     public UserWithExamsDTO getUserWithExamHistory(int userId) throws SQLException {
        UserWithExamsDTO userDTO = new UserWithExamsDTO();
    
        System.out.println("getUserWithExamHistory");

        // 1. Get user basic info
        String userSql = "SELECT * FROM users WHERE UserID = ?";
        
        if(connection == null){
            System.out.println("It IS null");
        }
        try {
            PreparedStatement userStmt2 = connection.prepareStatement(userSql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("yeah");

        }                                   
        
        try (PreparedStatement userStmt = connection.prepareStatement(userSql)) {
            System.out.println("in try");
            userStmt.setInt(1, userId);

            ResultSet userRs = userStmt.executeQuery();
            
            if (userRs.next()) {
                userDTO.setUserId(userId);
                userDTO.setUserName(userRs.getString("Name"));
                userDTO.setEmail(userRs.getString("Email"));
                userDTO.setRole(userRs.getString("Role"));
            }
        }
        
        // 2. Get all exams the user has taken
        String examSql = "SELECT DISTINCT e.* FROM exams e " +
                        "JOIN studentsubmissions ss ON e.ExamID = ss.ExamID " +
                        "WHERE ss.StudentID = ?";
        
        try (PreparedStatement examStmt = connection.prepareStatement(examSql)) {
            examStmt.setInt(1, userId);
            ResultSet examRs = examStmt.executeQuery();
            
            while (examRs.next()) {
                int examId = examRs.getInt("ExamID");
                // Reuse existing method to get exam with user answers
                ExamWithUserAnswersDTO examDTO = getExamWithUserAnswers(userId, examId);
                userDTO.addExam(examDTO);
            }
        }
        
        return userDTO;
    }
     
    public List<ExamWithQuestionsDTO> getAvailableExams(int userId) throws SQLException {
        List<ExamWithQuestionsDTO> exams = new ArrayList<>();

        String sql = "SELECT e.* FROM exams e " +
                    "JOIN classes c ON e.ClassID = c.ClassID " +
                    "WHERE e.ExamID NOT IN " +
                    "(SELECT ExamID FROM studentsubmissions WHERE StudentID = ?) " +
                    "AND (c.TeacherID = ? OR c.ClassID IN " +
                    "(SELECT ClassID FROM users WHERE UserID = ?))";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            stmt.setInt(3, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int examId = rs.getInt("ExamID");
                exams.add(getExamWithQuestions(examId));
            }
        }
        return exams;
    }
    
    // Add these methods to ExamDAO.java

public List<ExamWithUserAnswersDTO> getCompletedExams(int userId) throws SQLException {
    List<ExamWithUserAnswersDTO> completedExams = new ArrayList<>();
    
    String sql = "SELECT DISTINCT e.* FROM exams e " +
                 "JOIN studentsubmissions ss ON e.ExamID = ss.ExamID " +
                 "WHERE ss.StudentID = ?";
    
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            int examId = rs.getInt("ExamID");
            completedExams.add(getExamWithUserAnswers(userId, examId));
        }
    }
    
    return completedExams;
}

public void submitExam(int userId, int examId, Map<Integer, Integer> questionAnswers) throws SQLException {
    // Start transaction
    connection.setAutoCommit(false);
    
    try {
        // Create submission record
        String submissionSql = "INSERT INTO studentsubmissions (StudentID, ExamID, StartTime, EndTime, CorrectAnswers, TotalQuestions) " +
                              "VALUES (?, ?, NOW(), NOW(), 0, (SELECT COUNT(*) FROM examquestions WHERE ExamID = ?))";
        
        int submissionId;
        try (PreparedStatement submissionStmt = connection.prepareStatement(submissionSql, Statement.RETURN_GENERATED_KEYS)) {
            submissionStmt.setInt(1, userId);
            submissionStmt.setInt(2, examId);
            submissionStmt.setInt(3, examId);
            submissionStmt.executeUpdate();
            
            ResultSet rs = submissionStmt.getGeneratedKeys();
            if (rs.next()) {
                submissionId = rs.getInt(1);
            } else {
                throw new SQLException("Failed to get submission ID");
            }
        }
        
        // Insert answers and calculate score
        int correctAnswers = 0;
        String answerSql = "INSERT INTO studentanswers (SubmissionID, QuestionID, AnswerID) VALUES (?, ?, ?)";
        String checkCorrectSql = "SELECT IsCorrect FROM answers WHERE AnswerID = ?";
        
        try (PreparedStatement answerStmt = connection.prepareStatement(answerSql);
             PreparedStatement checkStmt = connection.prepareStatement(checkCorrectSql)) {
            
            for (Map.Entry<Integer, Integer> entry : questionAnswers.entrySet()) {
                int questionId = entry.getKey();
                int answerId = entry.getValue();
                
                // Record student's answer
                answerStmt.setInt(1, submissionId);
                answerStmt.setInt(2, questionId);
                answerStmt.setInt(3, answerId);
                answerStmt.addBatch();
                
                // Check if answer is correct
                checkStmt.setInt(1, answerId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getBoolean("IsCorrect")) {
                    correctAnswers++;
                }
            }
            
            answerStmt.executeBatch();
        }
        
        // Update submission with score
        String updateSql = "UPDATE studentsubmissions SET CorrectAnswers = ?, Score = ? " +
                           "WHERE SubmissionID = ?";
        try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
            int totalQuestions = questionAnswers.size();
            double score = (double) correctAnswers / totalQuestions * 10; // Scale to 10
            
            updateStmt.setInt(1, correctAnswers);
            updateStmt.setDouble(2, score);
            updateStmt.setInt(3, submissionId);
            updateStmt.executeUpdate();
        }
        
        connection.commit();
    } catch (SQLException e) {
        connection.rollback();
        throw e;
    } finally {
        connection.setAutoCommit(true);
    }
}
}