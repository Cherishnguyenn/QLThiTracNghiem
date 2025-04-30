/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quizmanagementsystem.GUI;

import quizmanagementsystem.DAO.ExamDAO;
import quizmanagementsystem.DTO.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamGUI {
    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final ExamDAO examDAO;
    private final int currentUserId;
    
    // Panel references
    private final JList<String> completedExamsList;
    private final JList<String> availableExamsList;
    private final JTextArea examReviewContent;
    private final JTextArea examTakingContent;
    private final JPanel examTakingAnswersPanel;
    
    // Exam tracking
    private Map<Integer, Integer> currentAnswers;
    private int currentExamId = -1;

    public ExamGUI(int userId) throws SQLException {
        this.currentUserId = userId;
        this.examDAO = new ExamDAO();
        
        // Initialize components
        this.completedExamsList = new JList<>();
        this.availableExamsList = new JList<>();
        this.examReviewContent = new JTextArea();
        this.examTakingContent = new JTextArea();
        this.examTakingAnswersPanel = new JPanel(new GridLayout(0, 1));
        
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        this.frame = new JFrame("Exam Management System");
        
        initializeComponents();
    }

    private void initializeComponents() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        // Create all panels
        mainPanel.add(createMainMenuPanel(), "mainMenu");
        mainPanel.add(createCompletedExamsPanel(), "completedExams");
        mainPanel.add(createAvailableExamsPanel(), "availableExams");
        mainPanel.add(createExamReviewPanel(), "examReview");
        mainPanel.add(createExamTakingPanel(), "examTaking");
        
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton viewCompletedBtn = new JButton("View Completed Exams");
        viewCompletedBtn.addActionListener(e -> {
            refreshCompletedExams();
            cardLayout.show(mainPanel, "completedExams");
        });
        
        JButton takeExamBtn = new JButton("Take Available Exams");
        takeExamBtn.addActionListener(e -> {
            refreshAvailableExams();
            cardLayout.show(mainPanel, "availableExams");
        });
        
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> System.exit(0));
        
        panel.add(viewCompletedBtn);
        panel.add(takeExamBtn);
        panel.add(exitBtn);
        
        return panel;
    }

    private JPanel createCompletedExamsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        completedExamsList.setModel(new DefaultListModel<>());
        JScrollPane scrollPane = new JScrollPane(completedExamsList);
        
        completedExamsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = completedExamsList.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                        try {
                            List<ExamWithUserAnswersDTO> exams = examDAO.getCompletedExams(currentUserId);
                            showExamReview(exams.get(index));
                        } catch (SQLException ex) {
                            showError("Error loading exam: " + ex.getMessage());
                        }
                    }
                }
            }
        });
        
        JButton backBtn = new JButton("Back to Main Menu");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "mainMenu"));
        
        panel.add(new JLabel("Your Completed Exams (double-click to view):"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(backBtn, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createAvailableExamsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        availableExamsList.setModel(new DefaultListModel<>());
        JScrollPane scrollPane = new JScrollPane(availableExamsList);
        
        availableExamsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = availableExamsList.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                        try {
                            List<ExamWithQuestionsDTO> exams = examDAO.getAvailableExams(currentUserId);
                            startExamTaking(exams.get(index));
                        } catch (SQLException ex) {
                            showError("Error starting exam: " + ex.getMessage());
                        }
                    }
                }
            }
        });
        
        JButton backBtn = new JButton("Back to Main Menu");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "mainMenu"));
        
        panel.add(new JLabel("Available Exams (double-click to take):"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(backBtn, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createExamReviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(examReviewContent);
        
        JButton backBtn = new JButton("Back to Completed Exams");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "completedExams"));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(backBtn, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createExamTakingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane contentScroll = new JScrollPane(examTakingContent);
        JScrollPane answersScroll = new JScrollPane(examTakingAnswersPanel);
        
        JButton submitBtn = new JButton("Submit Exam");
        submitBtn.addActionListener(e -> submitExam());
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> {
            currentExamId = -1;
            currentAnswers = null;
            cardLayout.show(mainPanel, "availableExams");
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(submitBtn);
        buttonPanel.add(cancelBtn);
        
        panel.add(contentScroll, BorderLayout.NORTH);
        panel.add(answersScroll, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private void refreshCompletedExams() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        try {
            List<ExamWithUserAnswersDTO> exams = examDAO.getCompletedExams(currentUserId);
            for (ExamWithUserAnswersDTO exam : exams) {
                ExamSubmissionDTO submission = examDAO.getExamSubmission(currentUserId, exam.getExamId());
                if (submission != null) {
                    listModel.addElement(String.format("%s (Score: %.1f/%d)", 
                        exam.getExamName(), 
                        submission.getScore(), 
                        submission.getTotalQuestions()));
                }
            }
        } catch (SQLException e) {
            showError("Error loading exams: " + e.getMessage());
        }
        completedExamsList.setModel(listModel);
    }

    private void refreshAvailableExams() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        try {
            List<ExamWithQuestionsDTO> exams = examDAO.getAvailableExams(currentUserId);
            for (ExamWithQuestionsDTO exam : exams) {
                listModel.addElement(String.format("%s (%s, %d questions)", 
                    exam.getExamName(), 
                    exam.getExamType(), 
                    exam.getQuestions().size()));
            }
        } catch (SQLException e) {
            showError("Error loading exams: " + e.getMessage());
        }
        availableExamsList.setModel(listModel);
    }

    private void showExamReview(ExamWithUserAnswersDTO exam) {
        StringBuilder sb = new StringBuilder();
        sb.append("Exam: ").append(exam.getExamName()).append("\n");
        sb.append("Type: ").append(exam.getExamType()).append("\n");
        sb.append("Date: ").append(exam.getExamDate()).append("\n");
        
        try {
            ExamSubmissionDTO submission = examDAO.getExamSubmission(currentUserId, exam.getExamId());
            if (submission != null) {
                sb.append("Score: ").append(submission.getScore()).append("/10\n");
                sb.append("Correct Answers: ").append(submission.getCorrectAnswers())
                  .append("/").append(submission.getTotalQuestions()).append("\n\n");
            }
        } catch (SQLException e) {
            sb.append("Score: N/A\n\n");
        }
        
        for (QuestionWithUserAnswersDTO question : exam.getQuestions()) {
            sb.append("Question: ").append(question.getQuestionText()).append("\n");
            
            for (AnswerWithSelectionDTO answer : question.getAnswers()) {
                sb.append(" - ");
                if (answer.isSelected()) sb.append("[YOUR CHOICE] ");
                if (answer.isCorrect()) sb.append("[CORRECT] ");
                sb.append(answer.getAnswerText()).append("\n");
            }
            sb.append("\n");
        }
        
        examReviewContent.setText(sb.toString());
        cardLayout.show(mainPanel, "examReview");
    }

    private void startExamTaking(ExamWithQuestionsDTO exam) {
        this.currentExamId = exam.getExamId();
        this.currentAnswers = new HashMap<>();
        
        examTakingContent.setText(String.format(
            "Exam: %s (ID: %d)%nType: %s%nTime: %s%n%n", 
            exam.getExamName(),
            exam.getExamId(),
            exam.getExamType(), 
            exam.getExamTime()));
        
        examTakingAnswersPanel.removeAll();
        List<QuestionWithAnswersDTO> L = exam.getQuestions();
        for (QuestionWithAnswersDTO question : L) {
            JPanel questionPanel = new JPanel(new BorderLayout());
            questionPanel.setBorder(BorderFactory.createTitledBorder(question.getQuestionText()));
            
            ButtonGroup group = new ButtonGroup();
            JPanel optionsPanel = new JPanel(new GridLayout(0, 1));
            
            for (AnswerDTO answer : question.getAnswers()) {
                JRadioButton radio = new JRadioButton(answer.getAnswerText());
                radio.addActionListener(e -> 
                    currentAnswers.put(question.getQuestionId(), answer.getAnswerId()));
                group.add(radio);
                optionsPanel.add(radio);
            }
            
            questionPanel.add(optionsPanel, BorderLayout.CENTER);
            examTakingAnswersPanel.add(questionPanel);
        }
        
        examTakingAnswersPanel.revalidate();
        examTakingAnswersPanel.repaint();
        cardLayout.show(mainPanel, "examTaking");
    }

    private void submitExam() {
        if (currentExamId == -1) {
            showError("No exam selected for submission");
            return;
        }

        if (currentAnswers == null || currentAnswers.isEmpty()) {
            int result = JOptionPane.showConfirmDialog(frame,
                "You haven't answered any questions. Submit anyway?",
                "Confirm Submission",
                JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            examDAO.submitExam(currentUserId, currentExamId, currentAnswers);
            JOptionPane.showMessageDialog(frame, 
                "Exam submitted successfully!\n" +
                "Answered " + (currentAnswers != null ? currentAnswers.size() : 0) + " questions",
                "Submission Complete",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Reset exam tracking
            currentExamId = -1;
            currentAnswers = null;
            
            cardLayout.show(mainPanel, "mainMenu");
        } catch (SQLException e) {
            showError("Error submitting exam: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}