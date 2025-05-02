package GUI;

import BUS.ExamBUS;
import DTO.ExamsDTO;
import DTO.QuestionDTO;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateExamGUI extends JFrame {
    private final int teacherID;
    private JTextField newExamNameTextField;
    private JTextField newExamIdTextField;
    private JComboBox<String> classComboBox;
    private JSpinner examDateField;
    private JComboBox<String> examTypeComboBox;
    private JSpinner examTimeField;
    private final JFrame f;
    private JList<String> selectedQuestionList;
    private DefaultListModel<String> selectedQuestionListModel;
    private JButton browseQuestionsButton;
    private JButton assignUsersButton;
    private JButton saveExamButton;
    private List<QuestionDTO> questionsFromBank = new ArrayList<>();
    private List<QuestionDTO> selectedQuestions = new ArrayList<>();

    public CreateExamGUI(int teacherID) {
        this.teacherID = teacherID;
        f = new JFrame("Tạo Bài Thi");
        f.setSize(500, 450);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Tạo bài thi mới");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel);
        f.add(headerPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel newExamNameLabel = new JLabel("Tên bài thi:");
        newExamNameTextField = new JTextField(15);
        inputPanel.add(newExamNameLabel);
        inputPanel.add(newExamNameTextField);

        JLabel newExamIdLabel = new JLabel("ID bài thi:");
        newExamIdTextField = new JTextField(8);
        inputPanel.add(newExamIdLabel);
        inputPanel.add(newExamIdTextField);

        JLabel classLabel = new JLabel("Lớp:");
        classComboBox = new JComboBox<>();
        inputPanel.add(classLabel);
        inputPanel.add(classComboBox);

        JLabel examDateLabel = new JLabel("Ngày thi:");
        examDateField = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(examDateField, "yyyy-MM-dd");
        examDateField.setEditor(dateEditor);
        inputPanel.add(examDateLabel);
        inputPanel.add(examDateField);

        JLabel examTypeLabel = new JLabel("Loại bài thi:");
        examTypeComboBox = new JComboBox<>(new String[]{"Kiểm tra 15 phút", "Kiểm tra 45 phút", "Học kì 1", "Học kì 2"});
        inputPanel.add(examTypeLabel);
        inputPanel.add(examTypeComboBox);

        JLabel examTimeLabel = new JLabel("Thời gian:");
        examTimeField = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(examTimeField, "HH:mm:ss");
        examTimeField.setEditor(timeEditor);
        inputPanel.add(examTimeLabel);
        inputPanel.add(examTimeField);

        JLabel questionSelectionLabel = new JLabel("Câu hỏi:");
        inputPanel.add(questionSelectionLabel);
        selectedQuestionListModel = new DefaultListModel<>();
        selectedQuestionList = new JList<>(selectedQuestionListModel);
        JScrollPane selectedQuestionScrollPane = new JScrollPane(selectedQuestionList);
        selectedQuestionScrollPane.setPreferredSize(new Dimension(150, 80));
        inputPanel.add(selectedQuestionScrollPane);

        browseQuestionsButton = new JButton("Thêm câu hỏi");
        browseQuestionsButton.addActionListener(e -> {
            try {
                showQuestionBankDialog();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(CreateExamGUI.this, "Lỗi tải câu hỏi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        inputPanel.add(new JLabel(""));
        inputPanel.add(browseQuestionsButton);

        saveExamButton = new JButton("Lưu bài thi");
        saveExamButton.addActionListener(e -> {
            luuBaiThi();
        });
        inputPanel.add(new JLabel(""));
        inputPanel.add(saveExamButton);

        assignUsersButton = new JButton("Phân công");
        assignUsersButton.addActionListener(e -> {
            try {
                openAssignUsersGUI();
            } catch (SQLException ex) {
                Logger.getLogger(CreateExamGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        inputPanel.add(new JLabel(""));
        inputPanel.add(assignUsersButton);

        f.add(new JScrollPane(inputPanel), BorderLayout.CENTER);

        // Load initial data
        loadClasses();

        f.setVisible(true);
    }

    private void openAssignUsersGUI() throws SQLException {
        String examName = newExamNameTextField.getText();
        String examIdStr = newExamIdTextField.getText();
        if (!examName.isEmpty() && !examIdStr.isEmpty()) {
            try {
                int examID = Integer.parseInt(examIdStr);
                new AssignUsersToExamGUI(teacherID, examName, examID, selectedQuestions);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(CreateExamGUI.this, "ID phải là số.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(CreateExamGUI.this, "Nhập tên và ID bài thi.");
        }
    }

    private void loadClasses() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ql_thitracnghiem", "root", "Thanh@1810")) {
            String query = "SELECT ClassName FROM classes";
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    classComboBox.addItem(resultSet.getString("ClassName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải lớp học: " + e.getMessage());
        }
    }

    private void showQuestionBankDialog() throws SQLException {
        ExamBUS examBUS = new ExamBUS();
        questionsFromBank = examBUS.getAllQuestionsFromBank();

        JDialog questionDialog = new JDialog(this, "Chọn câu hỏi", true);
        questionDialog.setLayout(new BorderLayout());

        DefaultListModel<String> availableQuestionListModel = new DefaultListModel<>();
        for (QuestionDTO q : questionsFromBank) {
            availableQuestionListModel.addElement(q.getQuestionText());
        }
        JList<String> availableQuestionList = new JList<>(availableQuestionListModel);
        JScrollPane availableScrollPane = new JScrollPane(availableQuestionList);
        questionDialog.add(availableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Thêm");
        addButton.addActionListener(e -> {
            int[] selectedIndices = availableQuestionList.getSelectedIndices();
            for (int index : selectedIndices) {
                QuestionDTO selectedQ = questionsFromBank.get(index);
                if (!selectedQuestions.contains(selectedQ)) {
                    selectedQuestions.add(selectedQ);
                    selectedQuestionListModel.addElement(selectedQ.getQuestionText());
                }
            }
        });
        buttonPanel.add(addButton);

        JButton removeButton = new JButton("Xóa");
        removeButton.addActionListener(e -> {
            int selectedIndex = selectedQuestionList.getSelectedIndex();
            if (selectedIndex >= 0) {
                selectedQuestions.remove(selectedIndex);
                selectedQuestionListModel.remove(selectedIndex);
            }
        });
        buttonPanel.add(removeButton);

        JButton doneButton = new JButton("Xong");
        doneButton.addActionListener(e -> questionDialog.dispose());
        buttonPanel.add(doneButton);

        questionDialog.add(buttonPanel, BorderLayout.SOUTH);
        questionDialog.setSize(400, 300);
        questionDialog.setLocationRelativeTo(this);
        questionDialog.setVisible(true);
    }

    private void luuBaiThi() {
        // Lấy dữ liệu từ các thành phần giao diện
        String examName = newExamNameTextField.getText();
        String examIdStr = newExamIdTextField.getText();
        String className = (String) classComboBox.getSelectedItem();
        Date examDate = (Date) examDateField.getValue();
       
        String examType = (String) examTypeComboBox.getSelectedItem();
        Date examTime = (Date) examTimeField.getValue();

        try {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ql_thitracnghiem", "root", "Thanh@1810")) {
                // Kiểm tra dữ liệu đầu vào
                if (examName.isEmpty() || examIdStr.isEmpty() || className == null || examDate == null || examType == null || examTime == null || selectedQuestions.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bài thi và chọn ít nhất một câu hỏi.");
                    return;
                }

                // Lấy ClassID từ tên lớp
                String classIdQuery = "SELECT ClassID FROM classes WHERE ClassName = ?";
                String classId;
                try (PreparedStatement classIdStatement = connection.prepareStatement(classIdQuery)) {
                    classIdStatement.setString(1, className);
                    try (ResultSet classIdResultSet = classIdStatement.executeQuery()) {
                        if (classIdResultSet.next()) {
                            classId = classIdResultSet.getString("ClassID");
                        } else {
                            JOptionPane.showMessageDialog(this, "Không tìm thấy lớp học.");
                            return;
                        }
                    }
                }

                // Lưu bài thi
                String examQuery = "INSERT INTO exams (ExamID, ExamName, ClassID, ExamDate, ExamType, ExamTime) VALUES (?, ?, ?, ?, ?, ?)";
                int examId = Integer.parseInt(examIdStr);
                try (PreparedStatement examStatement = connection.prepareStatement(examQuery)) {
                    examStatement.setInt(1, examId);
                    examStatement.setString(2, examName);
                    examStatement.setString(3, classId);
                    examStatement.setDate(4, new java.sql.Date(examDate.getTime()));
                    examStatement.setString(5, examType);
                    examStatement.setTime(6, new java.sql.Time(examTime.getTime()));
                    examStatement.executeUpdate();
                }

                // Lưu câu hỏi vào examquestions
                for (QuestionDTO question : selectedQuestions) {
                    String examQuestionQuery = "INSERT INTO examquestions (ExamID, QuestionID) VALUES (?, ?)";
                    try (PreparedStatement examQuestionStatement = connection.prepareStatement(examQuestionQuery)) {
                        examQuestionStatement.setInt(1, examId);
                        examQuestionStatement.setInt(2, question.getQuestionID());
                        examQuestionStatement.executeUpdate();
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Bài thi đã được lưu.");
            dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu bài thi: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID bài thi phải là một số.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CreateExamGUI(1));
    }
}
