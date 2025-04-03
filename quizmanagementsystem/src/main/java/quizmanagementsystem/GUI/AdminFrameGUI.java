package quizmanagementsystem.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import quizmanagementsystem.BUS.UserBUS;

public class AdminFrameGUI {
    private int userID;
    private JFrame f;

    public AdminFrameGUI(int userID) {
        this.userID = userID;

        // Frame
        f = new JFrame("Quiz Management System");
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(new BorderLayout());

        // Thanh hiển thị tên người dùng
        JPanel userPanel = createUserPanel();
        f.add(userPanel, BorderLayout.NORTH);

        // Mainframe
        JPanel mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(800, 550));
        f.add(mainPanel, BorderLayout.CENTER);

        // Panel quản lý tài khoản
        JPanel accountPanel = createPanel("Quản lý tài khoản",
                "quizmanagementsystem/src/main/resources/img/verified-account.png", 170, 90);
        mainPanel.add(accountPanel);

        // Panel quản lý câu hỏi
        JPanel questionPanel = createPanel("Quản lý câu hỏi",
                "quizmanagementsystem/src/main/resources/img/question-mark.png", 450, 90);
        mainPanel.add(questionPanel);

        f.pack();
        f.setVisible(true);
    }

    private JPanel createUserPanel() {
        JPanel userPanel = new JPanel(null);
        userPanel.setBackground(Color.decode("#C3F5FF"));
        userPanel.setPreferredSize(new Dimension(800, 50));

        // Lấy tên admin từ userID
        String adminName = UserBUS.getUserName(userID);

        // Label hiển thị tên người dùng
        JLabel userLabel = new JLabel(adminName);
        userLabel.setBounds(600, 11, 300, 30);
        userLabel.setFont(new Font("Arial", Font.BOLD, 17));
        userLabel.setForeground(Color.WHITE);
        userPanel.add(userLabel);

        // Label tên màn hình
        JLabel mainLabel = new JLabel("Màn hình chính");
        mainLabel.setBounds(30, 11, 200, 30);
        mainLabel.setForeground(Color.WHITE);
        mainLabel.setFont(new Font("Arial", Font.BOLD, 17));
        userPanel.add(mainLabel);

        // Label user icon
        JLabel userIcon = new JLabel(new ImageIcon(new ImageIcon("quizmanagementsystem/src/main/resources/img/user.png")
                .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        userIcon.setBounds(730, 11, 30, 30);
        userPanel.add(userIcon);

        return userPanel;
    }

    private JPanel createPanel(String title, String imagePath, int x, int y) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#C3F5FF"));
        panel.setBounds(x, y, 180, 250);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Hình ảnh
        JLabel imageLabel = new JLabel(
                new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
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