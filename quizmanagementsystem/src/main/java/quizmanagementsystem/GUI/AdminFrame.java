package quizmanagementsystem.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class AdminFrame {
    public AdminFrame() {
        // Frame
        JFrame f = new JFrame("Quiz Management System");
        f.setSize(800, 600);
        f.setLayout(null);

        // Thanh hiển thị tên người dùng
        JPanel userpanel = new JPanel();
        userpanel.setBackground(Color.decode("#C3F5FF"));
        userpanel.setBounds(0, 0, 800, 50);
        userpanel.setLayout(null);

        //Label tên người dùng

        // Thanh hiển thị adminframe
        JLabel userlabel = new JLabel("Màn hình chính");
        userlabel.setBounds(40, 11, 200, 30);
        userlabel.setFont(new Font("Arial", Font.BOLD, 20));
        userlabel.setForeground(Color.WHITE);
        userpanel.add(userlabel);

        // Label tro ve trang truoc
        JLabel backsignlabel = new JLabel();
        backsignlabel.setBounds(10, 11, 30, 30);

        ImageIcon backsign = new ImageIcon("quizmanagementsystem/src/main/resources/img/back.png");
        Image img2 = backsign.getImage().getScaledInstance(20, 25, Image.SCALE_SMOOTH);
        ImageIcon resizedImage = new ImageIcon(img2);
        backsignlabel.setIcon(resizedImage);
        userpanel.add(backsignlabel);

        // Mainframe
        JLayeredPane mainpanel = new JLayeredPane();
        mainpanel.setBounds(0, 50, 800, 550);
        mainpanel.setLayout(null);
        f.add(userpanel);

        // Quản lý tài khoản panel
        JPanel accountpanel = new JPanel();
        accountpanel.setBackground(Color.decode("#C3F5FF"));
        accountpanel.setBounds(170, 90, 180, 250);
        accountpanel.setLayout(null);

        // Hình ảnh của panel quản lý tài khoản
        JLabel accountimage = new JLabel();
        ImageIcon image = new ImageIcon("quizmanagementsystem/src/main/resources/img/verified-account.png");

        // Xử lý hình ảnh
        Image img = image.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedimage = new ImageIcon(img);
        accountimage.setIcon(resizedimage);
        accountimage.setBounds(15, 30, 150, 150);
        accountpanel.add(accountimage);

        // Tên panel quản lý tài khoản
        JLabel accountlabel = new JLabel("Quản lý tài khoản");
        accountlabel.setBounds(25, 110, 200, 200);
        accountlabel.setFont(new Font("Arial", Font.BOLD, 15));
        accountpanel.add(accountlabel);
        mainpanel.add(accountpanel);

        // Quản lý câu hỏi panel
        JPanel questionpanel = new JPanel();
        questionpanel.setBackground(Color.decode("#C3F5FF"));
        questionpanel.setBounds(450, 90, 180, 250);
        questionpanel.setLayout(null);

        // Hình ảnh của panel quản lý câu hỏi
        JLabel questionimage = new JLabel();
        ImageIcon image2 = new ImageIcon("quizmanagementsystem/src/main/resources/img/question-mark.png");
        Image img1 = image2.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedImage2 = new ImageIcon(img1);
        questionimage.setIcon(resizedImage2);
        questionimage.setBounds(15, 30, 150, 150);
        questionpanel.add(questionimage);

        // Tên panel quản lý câu hỏi
        JLabel questionlabel = new JLabel("Quản lý câu hỏi");
        questionlabel.setBounds(30, 110, 200, 200);
        questionlabel.setFont(new Font("Arial", Font.BOLD, 15));
        questionpanel.add(questionlabel);

        mainpanel.add(questionpanel);

        f.add(mainpanel);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
