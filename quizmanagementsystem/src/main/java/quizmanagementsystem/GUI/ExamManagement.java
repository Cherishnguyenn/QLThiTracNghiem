package GUI;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExamManagement extends JFrame {

    private final int teacherID;
    private final JFrame f;

    public ExamManagement(int teacherID) {
        this.teacherID = teacherID;
        f = new JFrame("Quản lý đề thi");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(600, 400);
        f.setLocationRelativeTo(null);
        f.setLayout(new BorderLayout());

        JPanel headerPanel = createHeaderPanel();
        f.add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        f.add(buttonPanel, BorderLayout.CENTER);

        f.setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Quản lý bài thi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;

        JButton createExamButton = new JButton("Tạo bài thi");
        createExamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mở giao diện CreateExamGUI
                new CreateExamGUI(teacherID);
            }
        });
        buttonPanel.add(createExamButton, gbc);

        JButton editDeleteExamButton = new JButton("Sửa/Xóa bài thi");
        editDeleteExamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mở giao diện EditDeleteExamGUI
                new EditDeleteExamGUI(teacherID);
            }
        });
        buttonPanel.add(editDeleteExamButton, gbc);

        return buttonPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExamManagement(1); 
        });
    }
}
