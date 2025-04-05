package quizmanagementsystem.GUI;

import quizmanagementsystem.BUS.UserBUS;
import javax.swing.*;

import java.awt.event.MouseEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class LoginGUI extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public LoginGUI() {
        setTitle("Quiz Management System");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // Panel trái
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(173, 232, 244));
        leftPanel.setLayout(null);

        JLabel imgLabel = new JLabel();
        ImageIcon icon = new ImageIcon("quizmanagementsystem/src/main/resources/img/online-learning.png");
        Image img = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        imgLabel.setIcon(new ImageIcon(img));
        imgLabel.setBounds(40, 50, 250, 250);
        leftPanel.add(imgLabel);

        // Panel phải (form)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.white);
        rightPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Đăng nhập");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 0, 204));
        titleLabel.setBounds(120, 30, 150, 30);
        rightPanel.add(titleLabel);

        JLabel userLabel = new JLabel("Tên tài khoản");
        userLabel.setBounds(20, 90, 100, 25);
        rightPanel.add(userLabel);

        userField = new JTextField();
        userField.setBounds(120, 90, 180, 25);
        rightPanel.add(userField);

        JLabel passLabel = new JLabel("Mật khẩu");
        passLabel.setBounds(20, 130, 100, 25);
        rightPanel.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(120, 130, 180, 25);
        rightPanel.add(passField);

        JLabel forgotLabel = new JLabel("Quên mật khẩu");
        forgotLabel.setForeground(new Color(0, 0, 204));
        forgotLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        forgotLabel.setBounds(215, 160, 100, 20);
        forgotLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));

                JLabel userLabel = new JLabel("Tên tài khoản:");
                JTextField userField = new JTextField();
                JLabel passLabel = new JLabel("Mật khẩu mới:");
                JPasswordField passField = new JPasswordField();

                panel.add(userLabel);
                panel.add(userField);
                panel.add(passLabel);
                panel.add(passField);

                int result = JOptionPane.showConfirmDialog(
                        null, panel, "Quên mật khẩu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String email = userField.getText();
                    String newPassword = new String(passField.getPassword());

                    boolean isUpdated = UserBUS.updatePassword(email, newPassword);

                    if (isUpdated) {
                        JOptionPane.showMessageDialog(null, "Thay đổi mật khẩu thành công!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Tên tài khoản không tồn tại hoặc có lỗi xảy ra!");
                    }
                }
            }
        });
        rightPanel.add(forgotLabel);

        JButton loginBtn = new JButton("Đăng nhập");
        loginBtn.setBackground(new Color(173, 232, 244));
        loginBtn.setForeground(Color.white);
        loginBtn.setBounds(120, 200, 120, 35);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));

        // Xử lý đăng nhập
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        rightPanel.add(loginBtn);
        add(leftPanel);
        add(rightPanel);
        setVisible(true);
    }

    private void handleLogin() {
        String email = userField.getText();
        String password = new String(passField.getPassword());

        // Lấy role của user
        String role = UserBUS.loginUser(email, password);

        if (role == null) {
            JOptionPane.showMessageDialog(this, "Sai email hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userID = UserBUS.getUserID(email); // Lấy ID cho mọi role
        // Kiểm tra role và chuyển giao diện tương ứng
        // Kiểm tra role và chuyển giao diện tương ứng
        switch (role) {
            case "Student":
                new StudentFrameGUI(userID);
                break;
            case "Teacher":
                new TeacherFrameGUI(userID);
                break;
            case "admin":
                new AdminFrameGUI(userID);
                break;
            default:
                break;
        }

        // Đóng cửa sổ đăng nhập sau khi thành công
        this.dispose();
    }
}