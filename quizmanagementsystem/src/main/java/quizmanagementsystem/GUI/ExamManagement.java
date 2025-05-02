package GUI;

import BUS.UserBUS;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ExamManagement extends JFrame {

    private final int teacherID;
    private final JFrame f;

    public ExamManagement(int teacherID) {
        this.teacherID = teacherID;
        f = new JFrame("Quản lý đề thi");
        f.setSize(800, 600);
        f.setLayout(null);

        JPanel userpanel = new JPanel();
        userpanel.setBackground(Color.decode("#C3F5FF"));
        userpanel.setBounds(0, 0, 800, 50);
        f.add(userpanel, BorderLayout.NORTH);
        userpanel.setLayout(null);

        String teacherName = UserBUS.getUserName(teacherID);

        JLabel userlabel = new JLabel(teacherName);
        userlabel.setBounds(600, 11, 300, 30);
        userlabel.setFont(new Font("Arial", Font.BOLD, 17));
        userlabel.setForeground(Color.WHITE);
        userpanel.add(userlabel);

        JLabel mainlabel = new JLabel("Quản lý bài thi");
        mainlabel.setBounds(30, 11, 200, 30);
        mainlabel.setForeground(Color.WHITE);
        mainlabel.setFont(new Font("Arial", Font.BOLD, 17));
        userpanel.add(mainlabel);

        JLabel user = new JLabel();
        user.setBounds(730, 11, 30, 30);

        ImageIcon usersign = new ImageIcon("C:\\Users\\ASUS\\Downloads\\img java\\user.png");
        Image imguser = usersign.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        user.setIcon(new ImageIcon(imguser));
        userpanel.add(user);

        // Mainframe
        JLayeredPane mainpanel = new JLayeredPane();
        mainpanel.setBounds(0, 50, 800, 550);
        mainpanel.setLayout(null);
        f.add(userpanel);

        // Panel tạo bài thi
        JPanel createpanel = createHeaderPanel("Tạo Bài Thi", "C:\\Users\\ASUS\\Downloads\\img java\\question-mark.png",
                50, 90);
        mainpanel.add(createpanel);

        // Panel sửa/xóa bài thi 
        JPanel editdeletepanel = createHeaderPanel("Sửa/Xóa Bài Thi", "C:\\Users\\ASUS\\Downloads\\img java\\search.png",
                300, 90); // Changed X coordinate to 300
        mainpanel.add(editdeletepanel);

        createpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f.dispose();
                try{
                    CreateExamGUI createExamGUI = new CreateExamGUI(teacherID);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        editdeletepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f.dispose();
                try{
                    EditDeleteExamGUI editDeleteExamGUI = new EditDeleteExamGUI(teacherID);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        f.add(mainpanel);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExamManagement(5); 
        });
    }
}
