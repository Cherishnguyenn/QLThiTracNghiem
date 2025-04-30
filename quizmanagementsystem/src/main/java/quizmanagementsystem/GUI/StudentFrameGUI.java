package quizmanagementsystem.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import quizmanagementsystem.BUS.UserBUS;

public class StudentFrameGUI {
    JFrame f;
    private int userID; // Biến lưu userID của sinh viên

    public StudentFrameGUI(int userID) {
        this.userID = userID; // Lưu userID vào biến toàn cục

        // Frame
        f = new JFrame("Quiz Management System");
        f.setSize(1040, 450);
        f.setLayout(null);

        // Thanh hiển thị tên người dùng
        JPanel userpanel = new JPanel();
        userpanel.setBackground(Color.decode("#C3F5FF"));
        userpanel.setBounds(0, 0, 1040, 50);
        userpanel.setLayout(null);

        // Lấy tên sinh viên từ userID
        String studentName = UserBUS.getUserName(userID);

        // Label hiển thị tên người dùng
        JLabel userlabel = new JLabel(studentName);
        userlabel.setBounds(600, 11, 300, 30);
        userlabel.setFont(new Font("Arial", Font.BOLD, 17));
        userlabel.setForeground(Color.WHITE);
        userpanel.add(userlabel);

        // Label tên màn hình
        JLabel mainlabel = new JLabel("Màn hình chính");
        mainlabel.setBounds(30, 11, 200, 30);
        mainlabel.setForeground(Color.WHITE);
        mainlabel.setFont(new Font("Arial", Font.BOLD, 17));
        userpanel.add(mainlabel);

        // Label user
        JLabel user = new JLabel();
        user.setBounds(730, 11, 30, 30);

        ImageIcon usersign = new ImageIcon("quizmanagementsystem/src/main/resources/img/user.png");
        Image imguser = usersign.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        user.setIcon(new ImageIcon(imguser));
        userpanel.add(user);

        // Mainframe
        JLayeredPane mainpanel = new JLayeredPane();
        mainpanel.setBounds(0, 50, 800, 550);
        mainpanel.setLayout(null);
        f.add(userpanel);

        // Panel 1: Vào lớp học
        JPanel joinClassPanel = createPanel("Vào lớp học", "quizmanagementsystem/src/main/resources/img/teacher.png",
                60, 90);
        mainpanel.add(joinClassPanel);

        // Panel 2: Danh sách lớp và bài thi
        JPanel classListPanel = createPanel("Danh sách lớp và bài thi",
                "quizmanagementsystem/src/main/resources/img/assigment.png", 300, 90);
        mainpanel.add(classListPanel);

        // Panel 3: Xem thống kê
        JPanel testResultPanel = createPanel("Xem thống kê",
                "quizmanagementsystem/src/main/resources/img/analytics.png", 550, 90);
        mainpanel.add(testResultPanel);

        // Thêm sự kiện chuyển sang StudentScoreGUI
        testResultPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f.dispose(); // Đóng StudentFrameGUI
                new StudentScoreGUI(userID); // Mở giao diện mới
            }
        });
        
        // Thêm sự kiện chuyển sang ExamGUI
        testResultPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f.dispose(); // Đóng StudentFrameGUI
                try {
                    new ExamGUI(userID); // Mở giao diện mới
                } catch (SQLException ex) {
                    Logger.getLogger(StudentFrameGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        f.add(mainpanel);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private JPanel createPanel(String title, String imagePath, int x, int y) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#C3F5FF"));
        panel.setBounds(x, y, 180, 250);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Căn giữa

        // Hình ảnh
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image img = imageIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));

        gbc.gridy = 0;
        panel.add(imageLabel, gbc);

        // Tiêu đề
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.TOP); // Căn chỉnh theo chiều dọc
        titleLabel.setBorder(new EmptyBorder(10, 0, 0, 0)); // Thêm khoảng cách giữa hình và chữ
        gbc.gridy = 1;
        panel.add(titleLabel, gbc);

        return panel;
    }

    public void showFrame() {
        f.setVisible(true);
    }
}