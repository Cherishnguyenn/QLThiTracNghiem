package quizmanagementsystem.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class StudentFrame {
    public StudentFrame(){
        // Frame
        JFrame f = new JFrame("Quiz Management System");
        f.setSize(800, 600);
        f.setLayout(null);

        // Thanh hiển thị tên người dùng
        JPanel userpanel = new JPanel();
        userpanel.setBackground(Color.decode("#C3F5FF"));
        userpanel.setBounds(0, 0, 800, 50);
        userpanel.setLayout(null);

        // Thanh hiển thị studentframe
        JLabel userlabel = new JLabel("Màn hình chính");
        userlabel.setBounds(40, 11, 200, 30);
        userlabel.setFont(new Font("Arial", Font.BOLD, 20));
        userlabel.setForeground(Color.WHITE);
        userpanel.add(userlabel);

        //Lable tên người dùng


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

        // Vào lớp
        JPanel accountpanel = new JPanel();
        accountpanel.setBackground(Color.decode("#C3F5FF"));
        accountpanel.setBounds(50, 90, 180, 250);
        accountpanel.setLayout(null);

        // Hình ảnh của panel vào lớp
        JLabel accountimage = new JLabel();
        ImageIcon image = new ImageIcon("quizmanagementsystem/src/main/resources/img/teacher.png");

        // Xử lý hình ảnh
        Image img = image.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedimage = new ImageIcon(img);
        accountimage.setIcon(resizedimage);
        accountimage.setBounds(10, 30, 150, 150);
        accountpanel.add(accountimage);

        // Tên panel vào lớp
        JLabel accountlabel = new JLabel("Vào lớp");
        accountlabel.setBounds(60, 110, 200, 200);
        accountlabel.setFont(new Font("Arial", Font.BOLD, 15));
        accountpanel.add(accountlabel);
        mainpanel.add(accountpanel);

        // Quản lý xem lớp và đề thi
        JPanel classtopicpanel = new JPanel();
        classtopicpanel.setBackground(Color.decode("#C3F5FF"));
        classtopicpanel.setBounds(300, 90, 180, 250);
        classtopicpanel.setLayout(null);

        // Hình ảnh của panel xem lớp và đề thi
        JLabel questionimage = new JLabel();
        ImageIcon image2 = new ImageIcon("quizmanagementsystem/src/main/resources/img/graduation.png");
        Image img1 = image2.getImage().getScaledInstance(170, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedImage2 = new ImageIcon(img1);
        questionimage.setIcon(resizedImage2);
        questionimage.setBounds(15, 30, 150, 150);
        classtopicpanel.add(questionimage);

        // Tên panel xem lớp và đề thi
        JLabel classtopiclabel = new JLabel("Xem lớp và đề thi");
        classtopiclabel.setBounds(30, 110, 200, 200);
        classtopiclabel.setFont(new Font("Arial", Font.BOLD, 15));
        classtopicpanel.add(classtopiclabel);
        mainpanel.add(classtopicpanel);

        //Panel quản lý thống kê
        JPanel statisticpanel = new JPanel();
        statisticpanel.setBackground(Color.decode("#C3F5FF"));
        statisticpanel.setBounds(550, 90, 180, 250);
        statisticpanel.setLayout(null);
        
        //Hình ảnh của panel quản lý thống kê
        JLabel statisticimage = new JLabel();
        ImageIcon image3 = new ImageIcon("quizmanagementsystem/src/main/resources/img/analytics.png");
        Image img3 = image3.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedImage3 = new ImageIcon(img3);
        statisticimage.setIcon(resizedImage3);
        statisticimage.setBounds(15, 30, 150, 150);
        statisticpanel.add(statisticimage);

        //Lable quản lý thống kê
        JLabel statisticlabel = new JLabel("Xem thống kê");
        statisticlabel.setBounds(40, 110, 200, 200);
        statisticlabel.setFont(new Font("Arial", Font.BOLD, 15));
        statisticpanel.add(statisticlabel);
        mainpanel.add(statisticpanel);

        f.add(mainpanel);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
