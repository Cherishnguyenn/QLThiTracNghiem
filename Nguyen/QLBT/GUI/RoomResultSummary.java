package GUI; 

import DAO.EnrollmentDAO;
import Model.User;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class RoomResultSummary extends JFrame {
    private final User loginUser;
    private JPanel panelViewRoomResultSummary; 
    private JTextField textfieldFindViewRoomResultSummary;
    private JTable tableViewRoomResultSummary;
    private JButton buttonBackViewRoomResultSummary;
    private JLabel labelFindViewRoomResultSummary;

    private DefaultTableModel columnModel;
    private DefaultTableModel rowModel;
    private TableRowSorter<TableModel> rowSorter;

    public RoomResultSummary(User loginUser) {
        this.loginUser = loginUser;
        this.setTitle("Tổng kết điểm");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panelViewRoomResultSummary = new JPanel(new BorderLayout(10, 10)); 
        this.setContentPane(panelViewRoomResultSummary);

        initComponents();
        addActionEvent();
        fillDataToTable();
        makeTableSearchable();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        
        User admin = new User(1, "Admin User", "admin@example.com", "admin123", "admin", "AD01");
        EventQueue.invokeLater(() -> new RoomResultSummary(admin));
    }

    private void initComponents() {
        labelFindViewRoomResultSummary = new JLabel("Tìm kiếm (StudentID hoặc ExamID):");
        textfieldFindViewRoomResultSummary = new JTextField(20);
        buttonBackViewRoomResultSummary = new JButton("Quay lại");
        tableViewRoomResultSummary = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableViewRoomResultSummary);

        tableViewRoomResultSummary.setDefaultEditor(Object.class, null);
        tableViewRoomResultSummary.getTableHeader().setReorderingAllowed(false);
        columnModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"SubmissionID", "StudentID", "ExamID", "Điểm thi"}
        );
        tableViewRoomResultSummary.setModel(columnModel);
        rowModel = (DefaultTableModel) tableViewRoomResultSummary.getModel();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(labelFindViewRoomResultSummary);
        topPanel.add(textfieldFindViewRoomResultSummary);

        panelViewRoomResultSummary.add(topPanel, BorderLayout.NORTH);
        panelViewRoomResultSummary.add(scrollPane, BorderLayout.CENTER);
        panelViewRoomResultSummary.add(buttonBackViewRoomResultSummary, BorderLayout.SOUTH);
    }

    private void addActionEvent() {
        buttonBackViewRoomResultSummary.addActionListener(event -> {
            this.dispose();
            System.out.println("Quay lại màn hình quản lý phòng thi (chức năng chưa hoàn thiện trong ví dụ này)");
        });
    }

    private void fillDataToTable() {
        List<EnrollmentDAO.StudentSubmission> list = EnrollmentDAO.selectAll();
        rowModel.setRowCount(0);
        for (EnrollmentDAO.StudentSubmission submission : list) {
            rowModel.addRow(new Object[]{
                    submission.getSubmissionId(),
                    submission.getStudentId(),
                    submission.getExamId(),
                    String.format("%.2f", submission.getScore())
            });
        }
    }

    private void makeTableSearchable() {
        rowSorter = new TableRowSorter<>(rowModel);
        tableViewRoomResultSummary.setRowSorter(rowSorter);
        textfieldFindViewRoomResultSummary
                .getDocument()
                .addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        filterTable();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        filterTable();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                    }

                    private void filterTable() {
                        String text = textfieldFindViewRoomResultSummary.getText().trim().toLowerCase();
                        if (text.isEmpty()) {
                            rowSorter.setRowFilter(null);
                        } else {
                            rowSorter.setRowFilter(RowFilter.regexFilter(text));
                        }
                    }
                });
    }
}
