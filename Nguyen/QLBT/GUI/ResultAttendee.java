package GUI;

import DTO.UserDTO;
import BUS.UserBUS;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ResultAttendee extends JFrame {
    private final int userID;
    private final JFrame f;
    private JTextField textfieldFindViewResultAttendee;
    private JTable tableViewResultAttendee;
    private JButton buttonBackViewResutlAttendee;
    private DefaultTableModel rowModel;
    private TableRowSorter<TableModel> rowSorter;
    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ResultAttendee(int userID) throws SQLException {
        this.userID = userID;
        f = new JFrame("Xem điểm thi");
        f.setSize(900, 600); 
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(new BorderLayout());

        
        JPanel userPanel = createUserPanel();
        f.add(userPanel, BorderLayout.NORTH);

        
        JPanel mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(880, 550)); 

        
        JPanel searchPanel = createSearchPanel();
        searchPanel.setBounds(30, 30, 820, 50); 
        mainPanel.add(searchPanel);

        
        tableViewResultAttendee = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableViewResultAttendee);
        tableViewResultAttendee.setDefaultEditor(Object.class, null);
        tableViewResultAttendee.getTableHeader().setReorderingAllowed(false);
        DefaultTableModel columnModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"SubmissionID", "Mã bài thi", "Thời gian bắt đầu", "Thời gian kết thúc", "Điểm thi", "Số câu đúng", "Tổng số câu"} // Added more columns
        );
        tableViewResultAttendee.setModel(columnModel);
        rowModel = (DefaultTableModel) tableViewResultAttendee.getModel();
        scrollPane.setBounds(30, 100, 820, 350); 
        mainPanel.add(scrollPane);

        f.add(mainPanel, BorderLayout.CENTER);

        
        buttonBackViewResutlAttendee = new JButton("Quay lại");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(buttonBackViewResutlAttendee);
        f.add(buttonPanel, BorderLayout.SOUTH);
        buttonBackViewResutlAttendee.addActionListener((ActionEvent event) -> {
            ResultAttendee.this.dispose();
            try {
                new MenuAttendee(userID);
            } catch (SQLException ex) {
                Logger.getLogger(ResultAttendee.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        fillDataToTable();
        makeTableSearchable();

        f.pack();
        f.setVisible(true);
    }

    private JPanel createUserPanel() throws SQLException {
        JPanel userPanel = new JPanel(null);
        userPanel.setBackground(Color.decode("#A0E7E7"));
        userPanel.setPreferredSize(new Dimension(880, 50)); 

        String userName = UserBUS.getUserName(userID);

        JLabel titleLabel = new JLabel("Kết quả thi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 17));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(30, 11, 200, 30);
        userPanel.add(titleLabel);

        JLabel userNameLabel = new JLabel("Học sinh: " + userName);
        userNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userNameLabel.setForeground(Color.WHITE);
        userNameLabel.setBounds(600, 11, 300, 30);
        userPanel.add(userNameLabel);

        return userPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelFindViewResultAttendee = new JLabel("Tìm kiếm (Mã bài thi):");
        textfieldFindViewResultAttendee = new JTextField(20);
        searchPanel.add(labelFindViewResultAttendee);
        searchPanel.add(textfieldFindViewResultAttendee);
        return searchPanel;
    }

    private void fillDataToTable() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String DB_URL = "jdbc:mysql://localhost:3306/ql_thitracnghiem?serverTimezone=Asia/Ho_Chi_Minh";
            String DB_USER = "root";
            String DB_PASSWORD = "Thanh@1810";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT SubmissionID, ExamID, StartTime, EndTime, Score, CorrectAnswers, TotalQuestions FROM studentsubmissions WHERE StudentID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            System.out.println("Tìm kiếm điểm cho StudentID: " + userID);
            resultSet = preparedStatement.executeQuery();

            rowModel.setRowCount(0);
            while (resultSet.next()) {
                int submissionID = resultSet.getInt("SubmissionID");
                int examID = resultSet.getInt("ExamID");
                Timestamp startTime = resultSet.getTimestamp("StartTime");
                Timestamp endTime = resultSet.getTimestamp("EndTime");
                double score = resultSet.getDouble("Score");
                int correctAnswers = resultSet.getInt("CorrectAnswers");
                int totalQuestions = resultSet.getInt("TotalQuestions");

                System.out.println("Đã tìm thấy: SubmissionID=" + submissionID + ", ExamID=" + examID + ", Score=" + score);
                rowModel.addRow(new Object[]{
                        submissionID,
                        examID,
                        startTime != null ? dateTimeFormat.format(startTime) : null,
                        endTime != null ? dateTimeFormat.format(endTime) : null,
                        score,
                        correctAnswers,
                        totalQuestions
                });
            }
            System.out.println("Số lượng hàng đã thêm vào bảng: " + rowModel.getRowCount());

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy JDBC Driver: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi truy vấn điểm thi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (preparedStatement != null) preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (connection != null) connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private void makeTableSearchable() {
        rowSorter = new TableRowSorter<>(rowModel);
        rowSorter.setRowFilter(new RowFilter<TableModel, Integer>() {
            @Override
            public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                String searchText = textfieldFindViewResultAttendee.getText().trim();
                if (searchText.isEmpty()) {
                    return true;
                }
                TableModel model = entry.getModel();
                Integer row = entry.getIdentifier();
                
                String examID = model.getValueAt(row, 1).toString();
                return examID.toLowerCase().contains(searchText.toLowerCase());
            }
        });
        tableViewResultAttendee.setRowSorter(rowSorter);

        textfieldFindViewResultAttendee.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                rowSorter.setRowFilter(rowSorter.getRowFilter());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                rowSorter.setRowFilter(rowSorter.getRowFilter());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    public void showFrame() {
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ResultAttendee resultsGUI = new ResultAttendee(1);
                resultsGUI.showFrame();
            } catch (SQLException ex) {
                Logger.getLogger(ResultAttendee.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void createUIComponents() {
    }
}
