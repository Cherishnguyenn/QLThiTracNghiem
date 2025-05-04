package  quizmanagementsystem.GUI;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import  quizmanagementsystem.DB.JDBCUtil;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class EditDeleteExamGUI extends JFrame{
    private final int teacherID;
    private JFrame f;
    private JTable examTable;
    private DefaultTableModel examTableModel;
    private JButton editButton;
    private JButton deleteButton;
    private Connection conn;
    private String selectedExamID;

    public EditDeleteExamGUI(int teacherID) {
        this.teacherID = teacherID;
        f = new JFrame("Sửa/Xóa bài thi");
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Sửa/Xóa bài thi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        f.add(headerPanel, BorderLayout.NORTH);

        examTableModel = new DefaultTableModel();
        examTable = new JTable(examTableModel);
        JScrollPane scrollPane = new JScrollPane(examTable);
        f.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        editButton = new JButton("Sửa");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editExam();
            }
        });
        buttonPanel.add(editButton);

        deleteButton = new JButton("Xóa");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteExam();
            }
        });
        buttonPanel.add(deleteButton);

        f.add(buttonPanel, BorderLayout.SOUTH);

        conn = JDBCUtil.getConnection();
        loadExams();

        f.addWindowListener(new java.awt.event.WindowAdapter() {
        });

        f.setVisible(true);
    }

    private void loadExams() {
        try {
            examTableModel.setRowCount(0);
            examTableModel.setColumnCount(0);
            examTableModel.addColumn("ExamID");
            examTableModel.addColumn("ClassID");
            examTableModel.addColumn("ExamName");
            examTableModel.addColumn("ExamDate");
            examTableModel.addColumn("ExamType");
            examTableModel.addColumn("ExamTime");

            String query = "SELECT ExamID, ClassID, ExamName, DATE(ExamDate) AS ExamDateOnly, ExamType, ExamTime FROM exams";
            try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
                
                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(rs.getInt("ExamID"));
                    row.add(rs.getString("ClassID"));
                    row.add(rs.getString("ExamName"));
                    row.add(rs.getDate("ExamDateOnly")); // Chỉ lấy phần ngày
                    row.add(rs.getString("ExamType"));
                    row.add(rs.getString("ExamTime"));
                    examTableModel.addRow(row);
                }
                
            }

            examTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && examTable.getSelectedRow() != -1) {
                    selectedExamID = examTable.getValueAt(examTable.getSelectedRow(), 0).toString();
                    System.out.println("Selected ExamID: " + selectedExamID);
                }
            });

        } catch (SQLException ex) {
            Logger.getLogger(EditDeleteExamGUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách bài thi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editExam() {
        if (selectedExamID == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một bài thi để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String query = "SELECT ExamName, DATE(ExamDate) AS ExamDateOnly, ExamType, ExamTime FROM exams WHERE ExamID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, Integer.parseInt(selectedExamID));
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String examName = rs.getString("ExamName");
                        Date examDate = rs.getDate("ExamDateOnly");
                        String examType = rs.getString("ExamType");
                        String examTime = rs.getString("ExamTime");
                        
                        JDialog dialog = new JDialog(f, "Sửa bài thi", true);
                        dialog.setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5, 10, 5, 10);
                        gbc.fill = GridBagConstraints.HORIZONTAL;
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        
                        JLabel nameLabel = new JLabel("Tên bài thi:");
                        JTextField nameTextField = new JTextField(examName, 20);
                        dialog.add(nameLabel, gbc);
                        gbc.gridx = 1;
                        dialog.add(nameTextField, gbc);
                        gbc.gridx = 0;
                        gbc.gridy++;
                        
                        JLabel dateLabel = new JLabel("Ngày thi:");
                        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel(examDate, null, null, Calendar.DAY_OF_MONTH));
                        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
                        dateSpinner.setEditor(dateEditor);
                        dialog.add(dateLabel, gbc);
                        gbc.gridx = 1;
                        dialog.add(dateSpinner, gbc);
                        gbc.gridx = 0;
                        gbc.gridy++;
                        
                        JLabel typeLabel = new JLabel("Loại bài thi:");
                        JTextField typeTextField = new JTextField(examType, 20);
                        dialog.add(typeLabel, gbc);
                        gbc.gridx = 1;
                        dialog.add(typeTextField, gbc);
                        gbc.gridx = 0;
                        gbc.gridy++;
                        
                        JLabel timeLabel = new JLabel("Thời gian thi:");
                        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
                        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
                        timeSpinner.setEditor(timeEditor);
                        try {
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                            timeSpinner.setValue(timeFormat.parse(examTime));
                        } catch (ParseException pe) {
                            timeSpinner.setValue(new Date()); // Set current time if parsing fails
                        }
                        dialog.add(timeLabel, gbc);
                        gbc.gridx = 1;
                        dialog.add(timeSpinner, gbc);
                        gbc.gridx = 0;
                        gbc.gridy++;
                        
                        JButton saveButton = new JButton("Lưu");
                        saveButton.addActionListener((ActionEvent e) -> {
                            try {
                                String newName = nameTextField.getText();
                                Date newDate = (Date) dateSpinner.getValue();
                                String newType = typeTextField.getText();
                                Date newTime = (Date) timeSpinner.getValue();
                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                                String newTimeString = timeFormat.format(newTime);
                                
                                String updateQuery = "UPDATE exams SET ExamName = ?, ExamDate = ?, ExamType = ?, ExamTime = ? WHERE ExamID = ?";
                                int rowsAffected;
                                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                                    updateStmt.setString(1, newName);
                                    updateStmt.setDate(2, new java.sql.Date(newDate.getTime()));
                                    updateStmt.setString(3, newType);
                                    updateStmt.setString(4, newTimeString);
                                    updateStmt.setInt(5, Integer.parseInt(selectedExamID));
                                    rowsAffected = updateStmt.executeUpdate();
                                }
                                if (rowsAffected > 0) {
                                    JOptionPane.showMessageDialog(dialog, "Đã cập nhật thông tin bài thi thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                                    loadExams();
                                    dialog.dispose();
                                } else {
                                    JOptionPane.showMessageDialog(dialog, "Không có bản ghi nào được cập nhật.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                                }
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(EditDeleteExamGUI.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(dialog, "Lỗi khi cập nhật bài thi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                        });
                        dialog.add(saveButton, gbc);
                        
                        dialog.pack();
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(EditDeleteExamGUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin bài thi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteExam() {
    if (selectedExamID == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một bài thi để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa bài thi này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
    if (choice == JOptionPane.YES_OPTION) {
        try {
            String deleteQuestionsQuery = "DELETE FROM examquestions WHERE ExamID = ?";
            try (PreparedStatement deleteQuestionsStmt = conn.prepareStatement(deleteQuestionsQuery)) {
                deleteQuestionsStmt.setInt(1, Integer.parseInt(selectedExamID));
                deleteQuestionsStmt.executeUpdate();
            }
           
            String deleteExamQuery = "DELETE FROM exams WHERE ExamID = ?";
            try (PreparedStatement deleteExamStmt = conn.prepareStatement(deleteExamQuery)) {
                deleteExamStmt.setInt(1, Integer.parseInt(selectedExamID));
                deleteExamStmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Đã xóa bài thi thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadExams();
            selectedExamID = null;
        } catch (SQLException ex) {
            Logger.getLogger(EditDeleteExamGUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa bài thi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditDeleteExamGUI editDeleteExamGUI = new EditDeleteExamGUI(1);
        });
    }
}
