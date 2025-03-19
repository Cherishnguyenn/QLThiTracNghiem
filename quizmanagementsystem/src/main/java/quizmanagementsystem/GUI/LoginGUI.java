package quizmanagementsystem.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginGUI {
    public LoginGUI() {
        // Frame
        JFrame f = new JFrame("Quiz Management System");
        f.setSize(800, 600);
        f.setLayout(new BorderLayout());
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        // Panel hình
        JPanel panelimage = new JPanel();
        panelimage.setLayout(new GridBagLayout()); // Giúp căn giữa ảnh
        panelimage.setPreferredSize(new Dimension(350, 600));
        panelimage.setBackground(Color.decode("#C3F5FF"));

        // xử lý hình ảnh
        ImageIcon image = new ImageIcon("quizmanagementsystem/src/main/resources/img/online-learning.png");
        Image img = image.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedimage = new ImageIcon(img);

        panelimage.add(new JLabel(resizedimage)); // thêm hình vào panel
        f.add(panelimage, BorderLayout.WEST);

        // Tạo JLayeredPane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(450, 600)); // Kích thước của layeredPane

        // Panel form
        JPanel panelform = new JPanel();
        panelform.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panelform.setBounds(43, 180, 350, 200); // Đặt vị trí và kích thước của panel
        panelform.setLayout(null); // Căn giữa các component trong panel

        // Thêm panel vào layeredPane
        layeredPane.add(panelform, JLayeredPane.DEFAULT_LAYER);
        f.add(layeredPane);

        // lable
        JLabel loginLabel = new JLabel("Đăng nhập");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginLabel.setForeground(Color.BLUE);
        loginLabel.setBounds(125, 10, 220, 30);

        JLabel usernameLabel = new JLabel("Tên đăng nhập");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameLabel.setBounds(10, 50, 220, 30);

        JLabel passwordLabel = new JLabel("Mật khẩu");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordLabel.setBounds(10, 90, 220, 30);

        JTextField usernameField = new JTextField(10);
        usernameField.setBounds(130, 50, 180, 30);
        JTextField passwordField = new JTextField(10);
        passwordField.setBounds(130, 90, 180, 30);

        JLabel forgetpasswordLabel = new JLabel("Quên mật khẩu");
        forgetpasswordLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        forgetpasswordLabel.setBounds(245, 115, 220, 30);
        forgetpasswordLabel.setForeground(Color.BLUE);

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.setBounds(125, 150, 100, 30);
        loginButton.setBackground(Color.decode("#9DEFFF"));

        // Thêm các lable vào panel
        panelform.add(loginLabel);
        panelform.add(usernameLabel);
        panelform.add(usernameField);
        panelform.add(passwordLabel);
        panelform.add(passwordField);
        panelform.add(forgetpasswordLabel);
        panelform.add(loginButton);

        f.setVisible(true);
    }
}
