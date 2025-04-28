package GUI;
//done
import BUS.UserBUS;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuAttendee {
    private final int userID;
    private JFrame f;

    public MenuAttendee(int userID) throws SQLException {
        this.userID = userID;

        f = new JFrame("Attendee Menu");
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(new BorderLayout());

        JPanel userPanel = createUserPanel();
        f.add(userPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(800, 550));
        f.add(mainPanel, BorderLayout.CENTER);

        JPanel takeQuizPanel = createPanel("Làm bài thi",
                "C:\\Users\\ASUS\\Downloads\\img java\\assigment.png", 170, 90, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        f.dispose();
                        try {
                            // You'll need to determine the specific exam the student should take here.
                            // For now, let's assume a fixed examID (e.g., 1).
                            int examID = 1;
                            new TakeQuizGUI(userID, examID);
                        } catch (SQLException ex) {
                            Logger.getLogger(MenuAttendee.class.getName()).log(Level.SEVERE, "Error creating TakeQuizGUI", ex);
                            JOptionPane.showMessageDialog(f, "Lỗi khi mở bài thi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
        mainPanel.add(takeQuizPanel);

        JPanel viewHistoryPanel = createPanel("Xem điểm thi",
                "C:\\Users\\ASUS\\Downloads\\img java\\graduation.png", 450, 90, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        f.dispose();
                        try {
                            new ResultAttendee(userID);
                        } catch (SQLException ex) {
                            Logger.getLogger(MenuAttendee.class.getName()).log(Level.SEVERE, "Error creating ResultAttendee", ex);
                            JOptionPane.showMessageDialog(f, "Lỗi khi mở xem điểm thi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
        mainPanel.add(viewHistoryPanel);

        
        JPanel profilePanel = createPanel("Thông tin cá nhân",
                "C:\\Users\\ASUS\\Downloads\\img java\\user.png", 170, 350, null);
        mainPanel.add(profilePanel);

        f.pack();
        f.setVisible(true);
    }

    private JPanel createUserPanel() throws SQLException {
        JPanel userPanel = new JPanel(null);
        userPanel.setBackground(Color.decode("#A0E7E7"));
        userPanel.setPreferredSize(new Dimension(800, 50));
     
        String userName = UserBUS.getUserName(userID);
 
        JLabel userLabel = new JLabel(userName);
        userLabel.setBounds(600, 11, 300, 30);
        userLabel.setFont(new Font("Arial", Font.BOLD, 17));
        userLabel.setForeground(Color.WHITE);
        userPanel.add(userLabel);

        
        JLabel mainLabel = new JLabel("Menu Thí Sinh");
        mainLabel.setBounds(30, 11, 200, 30);
        mainLabel.setForeground(Color.WHITE);
        mainLabel.setFont(new Font("Arial", Font.BOLD, 17));
        userPanel.add(mainLabel);

        
        JLabel userIcon = new JLabel(new ImageIcon(new ImageIcon("C:\\Users\\ASUS\\Downloads\\img java\\user.png")
                .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        userIcon.setBounds(730, 11, 30, 30);
        userPanel.add(userIcon);

        return userPanel;
    }

    private JPanel createPanel(String title, String imagePath, int x, int y, ActionListener actionListener) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#A0E7E7"));
        panel.setBounds(x, y, 180, 250);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        
        JLabel imageLabel = new JLabel(
                new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        gbc.gridy = 0;
        panel.add(imageLabel, gbc);

       
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        gbc.gridy = 1;
        panel.add(titleLabel, gbc);

        JButton button = new JButton("Vào");
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        if (actionListener != null) {
            button.addActionListener(actionListener);
            panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            panel.setCursor(Cursor.getDefaultCursor());
        }
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 20, 20, 20);
        panel.add(button, gbc);

        return panel;
    }

    public void showFrame() {
        f.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuAttendee attendeeFrame = null;
            try {
                attendeeFrame = new MenuAttendee(2);
            } catch (SQLException ex) {
                Logger.getLogger(MenuAttendee.class.getName()).log(Level.SEVERE, null, ex);
            }
            attendeeFrame.showFrame();
        });
    }
}
