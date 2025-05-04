package quizmanagementsystem.GUI;

import quizmanagementsystem.BUS.ExamBUS;
import quizmanagementsystem.BUS.UserBUS;
import quizmanagementsystem.DAO.UserDAO;
import quizmanagementsystem.DTO.QuestionDTO;
import quizmanagementsystem.DTO.UserDTO;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssignUsersToExamGUI extends JFrame {
    private JFrame f;
    private int teacherID;
    private final String examName;
    private JList<String> availableUserList;
    private DefaultListModel<String> availableUserModel;
    private JList<String> assignedUserList;
    private DefaultListModel<String> assignedUserModel;
    private JButton assignButton;
    private JButton removeButton;
    private JButton saveAssignmentsButton;
    private List<UserDTO> allUsers = new ArrayList<>();
    private List<UserDTO> assignedUsers = new ArrayList<>();
    private int examIDToAssign = -1;
    private static List<QuestionDTO> selectedQuestions;

    public AssignUsersToExamGUI(int teacherID, String examName, int examID, List<QuestionDTO> selectedQuestions) {
        this.teacherID = teacherID;
        this.examName = examName;
        this.examIDToAssign = examID; 
        this.selectedQuestions = selectedQuestions;
        initialize();
    }

    private void initialize() {
        f = new JFrame("Phân công người dùng cho bài thi: " + examName);
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Phân công người dùng cho bài thi: " + examName);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        f.add(headerPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Available Users List
        JPanel availablePanel = new JPanel(new BorderLayout());
        JLabel availableLabel = new JLabel("Người dùng có sẵn:");
        availableUserModel = new DefaultListModel<>();
        availableUserList = new JList<>(availableUserModel);
        JScrollPane availableScrollPane = new JScrollPane(availableUserList);
        availablePanel.add(availableLabel, BorderLayout.NORTH);
        availablePanel.add(availableScrollPane, BorderLayout.CENTER);
        mainPanel.add(availablePanel);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 5, 10, 5);
        assignButton = new JButton(">>");
        assignButton.addActionListener(e -> moveUsers(availableUserList, availableUserModel, assignedUserList, assignedUserModel, false));
        buttonPanel.add(assignButton, gbc);
        gbc.gridy++;
        removeButton = new JButton("<<");
        removeButton.addActionListener(e -> moveUsers(assignedUserList, assignedUserModel, availableUserList, availableUserModel, true));
        buttonPanel.add(removeButton, gbc);
        mainPanel.add(buttonPanel);

        // Assigned Users List
        JPanel assignedPanel = new JPanel(new BorderLayout());
        JLabel assignedLabel = new JLabel("Người dùng đã phân công:");
        assignedUserModel = new DefaultListModel<>();
        assignedUserList = new JList<>(assignedUserModel);
        JScrollPane assignedScrollPane = new JScrollPane(assignedUserList);
        assignedPanel.add(assignedLabel, BorderLayout.NORTH);
        assignedPanel.add(assignedScrollPane, BorderLayout.CENTER);
        mainPanel.add(assignedPanel);

        f.add(mainPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveAssignmentsButton = new JButton("Lưu phân công");
        saveAssignmentsButton.addActionListener(e -> saveUserAssignments());
        bottomPanel.add(saveAssignmentsButton);
        f.add(bottomPanel, BorderLayout.SOUTH);

        try {
            loadUsers();
        } catch (SQLException ex) {
            Logger.getLogger(AssignUsersToExamGUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Lỗi khi tải người dùng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        f.setVisible(true);
    }

    private void loadUsers() throws SQLException {
    availableUserModel.clear();
    allUsers.clear();
    List<UserDTO> users = UserDAO.getAllUsers();
    for (UserDTO user : users) {
        if (!user.getRole().equalsIgnoreCase("teacher") && !user.getRole().equalsIgnoreCase("admin")) { // Chỉ liệt kê học sinh
            allUsers.add(user);
            availableUserModel.addElement(user.getName() + " (" + user.getEmail() + ")");
        }
    }
}

    private void moveUsers(JList<String> sourceList, DefaultListModel<String> sourceModel, JList<String> destList, DefaultListModel<String> destModel, boolean removing) {
        List<String> selectedUsersDisplay = sourceList.getSelectedValuesList();
        if (!selectedUsersDisplay.isEmpty()) {
            for (String userDisplay : selectedUsersDisplay) {
                destModel.addElement(userDisplay);
                sourceModel.removeElement(userDisplay);
                String email = userDisplay.substring(userDisplay.lastIndexOf("(") + 1, userDisplay.lastIndexOf(")"));
                for (UserDTO user : new ArrayList<>(allUsers)) { 
                    if (user.getEmail().equals(email)) {
                        if (removing) {
                            assignedUsers.remove(user);
                            allUsers.add(user); 
                        } else if (!assignedUsers.contains(user)) {
                            assignedUsers.add(user);
                            allUsers.remove(user); 
                        }
                        break;
                    }
                }
            }
            sourceList.clearSelection();
        }
    }

    private void saveUserAssignments() {
        if (examIDToAssign == -1) {
            JOptionPane.showMessageDialog(this, "Không có ID bài thi để phân công.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (assignedUsers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng để phân công.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ExamBUS examBUS = new ExamBUS();
        examBUS.assignExamToUsers(examIDToAssign, assignedUsers); 
        JOptionPane.showMessageDialog(this, "Đã phân công bài thi '" + examName + "' cho " + assignedUsers.size() + " người dùng.");
        f.dispose(); 
        JOptionPane.showMessageDialog(this, "Đã lưu phân công thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }
}
