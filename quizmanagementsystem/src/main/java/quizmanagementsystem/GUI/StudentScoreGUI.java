package quizmanagementsystem.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import quizmanagementsystem.BUS.UserBUS;

public class StudentScoreGUI {
    private int userID;

    public StudentScoreGUI(int userID) {
        this.userID = userID;

        // Frame
        JFrame f = new JFrame("Quiz Management System");
        f.setSize(800, 600);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Thanh hiển thị tên người dùng
        JPanel userpanel = new JPanel();
        userpanel.setBackground(Color.decode("#C3F5FF"));
        userpanel.setBounds(0, 0, 800, 50);
        userpanel.setLayout(null);

        // Lấy tên học sinh từ userID
        String studentName = UserBUS.getUserName(userID);

        //Label và icon back
        JLabel backlabel = new JLabel();
        backlabel.setBounds(10, 13, 30, 25);
        ImageIcon backsign = new ImageIcon("quizmanagementsystem/src/main/resources/img/back.png");
        Image imgback = backsign.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        backlabel.setIcon(new ImageIcon(imgback));
        userpanel.add(backlabel);

        // Label tên màn hình
        JLabel mainlabel = new JLabel("Xem thống kê");
        mainlabel.setBounds(40, 11, 200, 30);
        mainlabel.setForeground(Color.WHITE);
        mainlabel.setFont(new Font("Arial", Font.BOLD, 17));
        userpanel.add(mainlabel);

        // Label hiển thị tên người dùng
        JLabel userlabel = new JLabel(studentName);
        userlabel.setBounds(600, 11, 300, 30);
        userlabel.setFont(new Font("Arial", Font.BOLD, 17));
        userlabel.setForeground(Color.WHITE);
        userpanel.add(userlabel);

        // Label user icon
        JLabel user = new JLabel();
        user.setBounds(730, 11, 30, 30);
        ImageIcon usersign = new ImageIcon("quizmanagementsystem/src/main/resources/img/user.png");
        Image imguser = usersign.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        user.setIcon(new ImageIcon(imguser));
        userpanel.add(user);

        // Panel chứa bộ lọc
        JPanel filterPanel = new JPanel();
        filterPanel.setBounds(0, 50, 800, 70);
        filterPanel.setLayout(null);

        //Label tìm kiếm
        JLabel searchlabel = new JLabel("Tìm kiếm");
        searchlabel.setBounds(20,20,100,30);
        filterPanel.add(searchlabel);

        // Textfield cho thanh tìm kiếm
        JTextField searchtext = new JTextField();
        searchtext.setBounds(100, 20, 400, 30);
        searchtext.setForeground(Color.GRAY);
        filterPanel.add(searchtext);

        //Icon và label lọc
        JLabel filterlabel = new JLabel();
        filterlabel.setBounds(510, 20, 50, 30);
        ImageIcon filtersign = new ImageIcon("quizmanagementsystem/src/main/resources/img/filter.png");
        Image imgfilter = filtersign.getImage().getScaledInstance(25, 25,Image.SCALE_SMOOTH);
        ImageIcon resizedfilter = new ImageIcon(imgfilter);
        filterlabel.setIcon(resizedfilter);
        filterPanel.add(filterlabel);

        //Icon và label sắp xếp
        JLabel arrangelabel = new JLabel();
        arrangelabel.setBounds(550, 20, 50, 30);
        ImageIcon arrangesign = new ImageIcon("quizmanagementsystem/src/main/resources/img/arrows.png");
        Image imgarrange = arrangesign.getImage().getScaledInstance(25, 25,Image.SCALE_SMOOTH);
        ImageIcon resizedarrange = new ImageIcon(imgarrange);
        arrangelabel.setIcon(resizedarrange);
        filterPanel.add(arrangelabel);


        // Thêm vào frame
        f.add(userpanel);
        f.add(filterPanel);

        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }
}
