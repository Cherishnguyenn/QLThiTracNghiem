package GUI;

import BUS.UserBUS;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExamManagement {

    private int teacherID;
    private JFrame f;

    public ExamManagement(int teacherID) throws SQLException {
        this.teacherID = teacherID;
        f = new JFrame("Quản Lý Đề Thi");
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

        JLabel mainlabel = new JLabel("Quản Lý Bài Thi");
        mainlabel.setBounds(30, 11, 200, 30);
        mainlabel.setForeground(Color.WHITE);
        mainlabel.setFont(new Font("Arial", Font.BOLD, 17));
        userpanel.add(mainlabel);

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

        // Panel tạo bài thi
        JPanel createpanel = createHeaderPanel("Tạo Bài Thi", "quizmanagementsystem/src/main/resources/img/question-mark.png",
                50, 90);
        mainpanel.add(createpanel);

        // Panel sửa/xóa bài thi 
        JPanel editdeletepanel = createHeaderPanel("Sửa/ Xóa Bài Thi", "quizmanagementsystem/src/main/resources/img/search.png",
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

    private JPanel createHeaderPanel(String title, String imagePath, int x, int y) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#C3F5FF"));
        panel.setBounds(x, y, 180, 250);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

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
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        titleLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
        gbc.gridy = 1;
        panel.add(titleLabel, gbc);

        return panel;
    }

    public void showFrame() {
        f.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ExamManagement(5);
            } catch (SQLException ex) {
                Logger.getLogger(ExamManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
