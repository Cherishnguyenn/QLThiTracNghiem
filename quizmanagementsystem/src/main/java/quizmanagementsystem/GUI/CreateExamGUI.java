package quizmanagementsystem.GUI;

import quizmanagementsystem.BUS.ExamBUS;
import quizmanagementsystem.DTO.ExamsDTO;
import quizmanagementsystem.DTO.QuestionDTO;
import quizmanagementsystem.DB.JDBCUtil;

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
    private int teacherID;
    private JTextField newExamNameTextField;
    private JTextField newExamIdTextField;
    private JComboBox<String> classComboBox;
    private JSpinner examDateField;
    private JComboBox<String> examTypeComboBox;
    private JSpinner examTimeField;
    private JFrame f;
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
        f.setSize(500, 400);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(new BorderLayout());

        JPanel userpanel = new JPanel();
        userpanel.setBackground(Color.decode("#C3F5FF"));
        userpanel.setBounds(0, 0, 800, 50);
        userpanel.setLayout(null);
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Tạo bài thi mới");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel);
        f.add(headerPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;

        JLabel newExamNameLabel = new JLabel("Tên bài thi:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(newExamNameLabel, gbc);
        newExamNameTextField = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(newExamNameTextField, gbc);

        JLabel newExamIdLabel = new JLabel("ID bài thi:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(newExamIdLabel, gbc);
        newExamIdTextField = new JTextField(8);
        gbc.gridx = 1;
        inputPanel.add(newExamIdTextField, gbc);

        JLabel classLabel = new JLabel("Lớp:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(classLabel, gbc);
        classComboBox = new JComboBox<>();
        gbc.gridx = 1;
        inputPanel.add(classComboBox, gbc);

        JLabel examDateLabel = new JLabel("Ngày thi:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(examDateLabel, gbc);
        examDateField = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(examDateField, "yyyy-MM-dd");
        examDateField.setEditor(dateEditor);
        gbc.gridx = 1;
        inputPanel.add(examDateField, gbc);

        JLabel examTypeLabel = new JLabel("Loại bài thi:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(examTypeLabel, gbc);
        examTypeComboBox = new JComboBox<>(new String[]{"Kiểm tra 15 phút", "Kiểm tra 45 phút", "Học kì 1", "Học kì 2"});
        gbc.gridx = 1;
        inputPanel.add(examTypeComboBox, gbc);

        JLabel examTimeLabel = new JLabel("Thời gian:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(examTimeLabel, gbc);
        examTimeField = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(examTimeField, "HH:mm:ss");
        examTimeField.setEditor(timeEditor);
        gbc.gridx = 1;
        inputPanel.add(examTimeField, gbc);

        JLabel questionSelectionLabel = new JLabel("Câu hỏi:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        inputPanel.add(questionSelectionLabel, gbc);
        selectedQuestionListModel = new DefaultListModel<>();
        selectedQuestionList = new JList<>(selectedQuestionListModel);
        JScrollPane selectedQuestionScrollPane = new JScrollPane(selectedQuestionList);
        selectedQuestionScrollPane.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(selectedQuestionScrollPane, gbc);
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        browseQuestionsButton = new JButton("Thêm câu hỏi");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(browseQuestionsButton, gbc);

        saveExamButton = new JButton("Lưu bài thi");
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(saveExamButton, gbc);

        assignUsersButton = new JButton("Phân công");
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(assignUsersButton, gbc);

        f.add(new JScrollPane(inputPanel), BorderLayout.CENTER);

        // Load initial data
        loadClasses();

        browseQuestionsButton.addActionListener(e -> {
            try {
                showQuestionBankDialog();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi tải câu hỏi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        saveExamButton.addActionListener(e -> {
            luuBaiThi();
        });

        assignUsersButton.addActionListener(e -> {
            try {
                openAssignUsersGUI();
            } catch (SQLException ex) {
                Logger.getLogger(CreateExamGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        f.setVisible(true);
    }

    private void openAssignUsersGUI() throws SQLException {
        String examName = newExamNameTextField.getText();
        String examIdStr = newExamIdTextField.getText();
        if (!examName.isEmpty() && !examIdStr.isEmpty()) {
            try {
                int examID = Integer.parseInt(examIdStr);
                AssignUsersToExamGUI assignUsersToExamGUI = new AssignUsersToExamGUI(teacherID, examName, examID, selectedQuestions);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID phải là số.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nhập tên và ID bài thi.");
        }
    }

    private void loadClasses() {
        try {
            Connection connection = JDBCUtil.getConnection();
            if (connection != null) {
                String query = "SELECT ClassName FROM classes";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        classComboBox.addItem(resultSet.getString("ClassName"));
                    }
                } finally {
                    connection.close(); // Close the connection in a finally block
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải lớp học: " + e.getMessage());
        }
    }

    private void showQuestionBankDialog() throws SQLException {
        ExamBUS examBUS = new ExamBUS();
        questionsFromBank = examBUS.getAllQuestionsFromBank();

        JDialog questionDialog;
        questionDialog = new JDialog(f, "Chọn câu hỏi", true);
        questionDialog.setLayout(new BorderLayout());

        DefaultListModel<String> availableQuestionListModel = new DefaultListModel<>();
        for (int i = 0; i < questionsFromBank.size(); i++) {
            availableQuestionListModel.addElement((i + 1) + ". " + questionsFromBank.get(i).getQuestionText());
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
                boolean alreadySelected = false;
                for (QuestionDTO sq : selectedQuestions) {
                    if (sq.getQuestionID() == selectedQ.getQuestionID()) {
                        alreadySelected = true;
                        break;
                    }
                }
                if (!alreadySelected) {
                    selectedQuestions.add(selectedQ);
                    updateSelectedQuestionListModel();
                }
            }
        });
        buttonPanel.add(addButton);

        JButton removeButton = new JButton("Xóa");
        removeButton.addActionListener(e -> {
            int selectedIndex = selectedQuestionList.getSelectedIndex();
            if (selectedIndex >= 0) {
                selectedQuestions.remove(selectedIndex);
                updateSelectedQuestionListModel();
            }
        });
        buttonPanel.add(removeButton);

        JButton doneButton = new JButton("Xong");
        doneButton.addActionListener(e -> questionDialog.dispose());
        buttonPanel.add(doneButton);

        questionDialog.add(buttonPanel, BorderLayout.SOUTH);
        questionDialog.setSize(400, 300);
        questionDialog.setLocationRelativeTo(null);
        questionDialog.setVisible(true);
    }

    private void updateSelectedQuestionListModel() {
        selectedQuestionListModel.clear();
        for (int i = 0; i < selectedQuestions.size(); i++) {
            selectedQuestionListModel.addElement((i + 1) + ". " + selectedQuestions.get(i).getQuestionText());
        }
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
            Connection connection = JDBCUtil.getConnection(); // Use JDBCUtil
            if (connection != null) {
                try {
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
                    JOptionPane.showMessageDialog(this, "Bài thi đã được lưu.");
                    f.dispose();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi lưu bài thi: " + e.getMessage());
                } finally {
                    connection.close();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID bài thi phải là một số.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CreateExamGUI(1));
    }
}

