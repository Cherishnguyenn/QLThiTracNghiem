package GUI;

import DTO.AnswerDTO;
import DTO.QuestionDTO;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TakeQuizGUI extends JFrame {
    private final int userID;
    private final int examID;
    private List<QuestionDTO> questions;
    private int currentQuestionIndex = 0;
    private final JPanel questionPanel;
    private final JLabel questionLabel;
    private final JPanel optionsPanel;
    private ButtonGroup answerGroup;
    private final List<JRadioButton> answerOptions;
    private final JButton nextButton;
    private final JButton previousButton;
    private final JButton submitButton;
    private final List<Integer> selectedAnswerIDs; 

    public TakeQuizGUI(int userID, int examID) throws SQLException {
        this.userID = userID;
        this.examID = examID;
        this.questions = loadQuestions(examID);
        this.selectedAnswerIDs = new ArrayList<>(questions.size());
        for (QuestionDTO question : questions) {
            selectedAnswerIDs.add(-1); 
        }

        setTitle("Làm Bài Thi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        questionPanel = new JPanel(new BorderLayout());
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionPanel.add(questionLabel, BorderLayout.NORTH);

        optionsPanel = new JPanel(new GridLayout(0, 1));
        answerGroup = new ButtonGroup();
        answerOptions = new ArrayList<>();
        questionPanel.add(optionsPanel, BorderLayout.CENTER);

        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        previousButton = new JButton("Trước");
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                    displayQuestion();
                }
            }
        });
        nextButton = new JButton("Tiếp");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentQuestionIndex < questions.size() - 1) {
                    currentQuestionIndex++;
                    displayQuestion();
                }
            }
        });
        submitButton = new JButton("Nộp Bài");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitQuiz();
            }
        });

        navigationPanel.add(previousButton);
        navigationPanel.add(nextButton);
        navigationPanel.add(submitButton);

        add(questionPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);

        displayQuestion();
        setVisible(true);
    }

    private List<QuestionDTO> loadQuestions(int examID) throws SQLException {
        List<QuestionDTO> questionList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String DB_URL = "jdbc:mysql://localhost:3306/ql_thitracnghiem?serverTimezone=Asia/Ho_Chi_Minh";
            String DB_USER = "root";
            String DB_PASSWORD = "Thanh@1810";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String questionQuery = "SELECT QuestionID, QuestionText, QuestionType FROM questionbank";
            preparedStatement = connection.prepareStatement(questionQuery);
            resultSet = preparedStatement.executeQuery();
            
            List<QuestionDTO> allQuestions = new ArrayList<>();
            while (resultSet.next()) {
                QuestionDTO questionDTO = new QuestionDTO(
                        resultSet.getInt("QuestionID"),
                        resultSet.getString("QuestionText"),
                        resultSet.getString("QuestionType")
                );
                questionDTO.setAnswers(loadAnswersForQuestion(connection, questionDTO.getQuestionID()));
                questionList.add(questionDTO);
            }

            List<QuestionDTO> filteredQuestions = new ArrayList<>();
            for (QuestionDTO question : questionList) {
                if ((examID == 1 && question.getQuestionType().equals("Vocabulary")) ||
                    (examID == 2 && question.getQuestionType().equals("Grammar")) ||
                    (examID == 3 && (question.getQuestionType().equals("Phrases and idioms") || question.getQuestionType().equals("Tenses")))) {
                    filteredQuestions.add(question);
                } else if (examID > 3) { 
                    filteredQuestions.add(question);
                }
            }
            return filteredQuestions;

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy JDBC Driver: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải câu hỏi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (preparedStatement != null) preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (connection != null) connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return new ArrayList<>(); 
    }

    private List<AnswerDTO> loadAnswersForQuestion(Connection connection, int questionID) throws SQLException {
        List<AnswerDTO> answerList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String answerQuery = "SELECT AnswerID, AnswerText, IsCorrect FROM answers WHERE QuestionID = ?";
            preparedStatement = connection.prepareStatement(answerQuery);
            preparedStatement.setInt(1, questionID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AnswerDTO answerDTO = new AnswerDTO(
                        resultSet.getInt("AnswerID"),
                        questionID,
                        resultSet.getString("AnswerText"),
                        resultSet.getBoolean("IsCorrect")
                );
                answerList.add(answerDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải câu trả lời cho câu hỏi " + questionID + ": " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (preparedStatement != null) preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return answerList;
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            QuestionDTO currentQuestion = questions.get(currentQuestionIndex);
            questionLabel.setText("Câu " + (currentQuestionIndex + 1) + ": " + currentQuestion.getQuestionText());

            optionsPanel.removeAll();
            answerGroup = new ButtonGroup();
            answerOptions.clear();

            for (AnswerDTO answer : currentQuestion.getAnswers()) {
                JRadioButton option = new JRadioButton(answer.getAnswerText());
                option.setActionCommand(String.valueOf(answer.getAnswerID())); 
                option.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedAnswerIDs.set(currentQuestionIndex, Integer.parseInt(e.getActionCommand()));
                    }
                });
                answerOptions.add(option);
                answerGroup.add(option);
                optionsPanel.add(option);
                
                if (selectedAnswerIDs.get(currentQuestionIndex) == answer.getAnswerID()) {
                    option.setSelected(true);
                }
            }

            optionsPanel.revalidate();
            optionsPanel.repaint();

            previousButton.setEnabled(currentQuestionIndex > 0);
            nextButton.setEnabled(currentQuestionIndex < questions.size() - 1);
            submitButton.setEnabled(currentQuestionIndex == questions.size() - 1);
        }
    }

    private void submitQuiz() {
        int score = calculateScore();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String DB_URL = "jdbc:mysql://localhost:3306/ql_thitracnghiem?serverTimezone=Asia/Ho_Chi_Minh";
            String DB_USER = "root";
            String DB_PASSWORD = "Thanh@1810";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String insertSubmissionQuery = "INSERT INTO studentsubmissions (StudentID, ExamID, StartTime, EndTime, Score, CorrectAnswers, TotalQuestions) VALUES (?, ?, NOW(), NOW(), ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertSubmissionQuery);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, examID);
            preparedStatement.setDouble(3, (double) score / questions.size() * 10); 
            preparedStatement.setInt(4, score);
            preparedStatement.setInt(5, questions.size());
            preparedStatement.executeUpdate();

            
            JOptionPane.showMessageDialog(this, "Đã nộp bài thành công! Điểm của bạn là: " + (double) score / questions.size() * 10, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy JDBC Driver: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi nộp bài thi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (preparedStatement != null) preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (connection != null) connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private int calculateScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            QuestionDTO question = questions.get(i);
            for (AnswerDTO answer : question.getAnswers()) {
                if (answer.isCorrect() && selectedAnswerIDs.get(i) == answer.getAnswerID()) {
                    score++;
                    break; 
                }
            }
        }
        return score;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    TakeQuizGUI takeQuizGUI; 
                    takeQuizGUI = new TakeQuizGUI(2, 1);
                } catch (SQLException e) {
                    Logger.getLogger(TakeQuizGUI.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        });
    }
}
