package quizmanagementsystem.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import quizmanagementsystem.BUS.UserBUS;

public class AdminFrameGUI {
    private int userID; // Biến lưu userID của admin

    public AdminFrameGUI(int userID) {
        this.userID = userID; // Lưu userID vào biến toàn cục

        // Frame
        JFrame f = new JFrame("Quiz Management System");
        f.setSize(800, 600);
        f.setLayout(null);

        // Thanh hiển thị tên người dùng
        JPanel userpanel = new JPanel();
        userpanel.setBackground(Color.decode("#C3F5FF"));
        userpanel.setBounds(0, 0, 800, 50);
        userpanel.setLayout(null);

        // Lấy tên admin từ userID
        String adminName = UserBUS.getUserName(userID);

        // Label hiển thị tên người dùng
        JLabel userlabel = new JLabel(adminName);
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

        // Panel quản lý tài khoản
        JPanel accountpanel = createPanel("Quản lý tài khoản",
                "quizmanagementsystem/src/main/resources/img/verified-account.png", 170, 90);
        mainpanel.add(accountpanel);

        // Panel quản lý câu hỏi
        JPanel questionpanel = createPanel("Quản lý câu hỏi",
                "quizmanagementsystem/src/main/resources/img/question-mark.png", 450, 90);
        mainpanel.add(questionpanel);

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

        // Tiêu đề (căn giữa + tự xuống dòng)
        gbc.gridy = 1;
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>" + title + "</div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // **Thêm khoảng cách giữa hình & chữ**
        titleLabel.setBorder(new EmptyBorder(10, 0, 0, 0)); // Cách hình 10px

        panel.add(titleLabel, gbc);

        return panel;
    }
}
