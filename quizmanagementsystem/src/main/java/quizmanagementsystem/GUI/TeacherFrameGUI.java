package quizmanagementsystem.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import quizmanagementsystem.BUS.UserBUS;

public class TeacherFrameGUI {
    private JFrame f;
    private int userID; // Biến lưu userID của giáo viên

    public TeacherFrameGUI(int userID) {
        this.userID = userID; // Lưu userID vào biến toàn cục

        // Frame
        f = new JFrame("Quiz Management System");
        f.setSize(800, 600);
        f.setLayout(null);

        // Thanh hiển thị tên người dùng
        JPanel userpanel = new JPanel();
        userpanel.setBackground(Color.decode("#C3F5FF"));
        userpanel.setBounds(0, 0, 800, 50);
        userpanel.setLayout(null);

        // Lấy tên giáo viên từ userID
        String teacherName = UserBUS.getUserName(userID);

        // Label hiển thị tên người dùng
        JLabel userlabel = new JLabel(teacherName);
        userlabel.setBounds(590, 11, 300, 30);
        userlabel.setFont(new Font("Arial", Font.BOLD, 17));
        userlabel.setForeground(Color.WHITE);
        userpanel.add(userlabel);

        // Label tên màn hình
        JLabel mainlabel = new JLabel("Màn hình chính");
        mainlabel.setBounds(30, 11, 200, 30);
        mainlabel.setForeground(Color.WHITE);
        mainlabel.setFont(new Font("Arial", Font.BOLD, 17));
        userpanel.add(mainlabel);

// Label user icon
JLabel userIcon = new JLabel(new ImageIcon(new ImageIcon("quizmanagementsystem/src/main/resources/img/user.png")
.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
userIcon.setBounds(720, 11, 30, 30);
userpanel.add(userIcon);

//Label đăng xuất
JLabel logoutLabel = new JLabel(new ImageIcon(new ImageIcon("quizmanagementsystem/src/main/resources/img/enter.png")
.getImage().getScaledInstance(25, 30, Image.SCALE_SMOOTH)));
logoutLabel.setBounds(755, 11, 25, 30);
userpanel.add(logoutLabel);

logoutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseClicked(java.awt.event.MouseEvent evt) {
    f.dispose();
    new LoginGUI();
}
});


        // Mainframe
        JLayeredPane mainpanel = new JLayeredPane();
        mainpanel.setBounds(0, 50, 800, 550);
        mainpanel.setLayout(null);
        f.add(userpanel);

        // Panel danh sách lớp
        JPanel classpanel = createPanel("Danh sách lớp", "quizmanagementsystem/src/main/resources/img/teacher.png",
                50, 90);
        mainpanel.add(classpanel);

        // Panel quản lý đề thi
        JPanel topicpanel = createPanel("Quản lý đề thi", "quizmanagementsystem/src/main/resources/img/assigment.png",
                300, 90);
        mainpanel.add(topicpanel);

        // Panel xem thống kê
        JPanel statisticpanel = createPanel("Xem thống kê", "quizmanagementsystem/src/main/resources/img/analytics.png",
                550, 90);
        mainpanel.add(statisticpanel);

        classpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f.dispose(); // Đóng teacherFrameGUI
                new ClassListFrame(userID); // Mở giao diện mới
            }
        });
        topicpanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                f.dispose(); // Close TeacherFrameGUI
                try {
                    ExamManagement examManagementGUI = new ExamManagement(userID); // Open ExamManagementGUI
                } catch (Exception ex) {
                    Logger.getLogger(TeacherFrameGUI.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
