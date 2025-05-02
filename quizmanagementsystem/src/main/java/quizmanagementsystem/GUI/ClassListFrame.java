package quizmanagementsystem.GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import quizmanagementsystem.BUS.ClassBUS;
import quizmanagementsystem.BUS.UserBUS;
import quizmanagementsystem.DTO.ClassDTO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Vector;

public class ClassListFrame {
    private int userID;
    private JTextField searchText;
    private JTable classTable;
    private DefaultTableModel tableModel;

    public ClassListFrame(int userID) {
        this.userID = userID;

        // Frame
        JFrame f = new JFrame("Quiz Management System");
        f.setSize(800, 600);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        // ========================== 1️⃣ Thanh hiển thị User ==========================
        JPanel userpanel = new JPanel();
        userpanel.setBackground(Color.decode("#C3F5FF"));
        userpanel.setBounds(0, 0, 800, 50);
        userpanel.setLayout(null);

        // Lấy tên học sinh từ userID
        String studentName = UserBUS.getUserName(userID);

        // Label hiển thị tên người dùng
        JLabel userlabel = new JLabel(studentName);
        userlabel.setBounds(600, 11, 300, 30);
        userlabel.setFont(new Font("Arial", Font.BOLD, 17));
        userlabel.setForeground(Color.WHITE);
        userpanel.add(userlabel);

        // Label back
        JLabel backlabel = new JLabel();
        backlabel.setBounds(10, 11, 20, 30);

        ImageIcon backsign = new ImageIcon("quizmanagementsystem/src/main/resources/img/back.png");
        Image imgbacksign = backsign.getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedbacksign = new ImageIcon(imgbacksign);
        backlabel.setIcon(resizedbacksign);
        userpanel.add(backlabel);

        backlabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TeacherFrameGUI teacherframe = new TeacherFrameGUI(userID);
                teacherframe.showFrame();

                // Đóng frame hiện tại
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(backlabel);
                if (currentFrame != null) {
                    currentFrame.dispose();
                }
            }
        });
        // Label tên màn hình
        JLabel mainlabel = new JLabel("Danh sách lớp");
        mainlabel.setBounds(40, 11, 200, 30);
        mainlabel.setForeground(Color.WHITE);
        mainlabel.setFont(new Font("Arial", Font.BOLD, 17));
        userpanel.add(mainlabel);

        // Label user icon
        JLabel userIcon = new JLabel(new ImageIcon(new ImageIcon("quizmanagementsystem/src/main/resources/img/user.png")
                .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        userIcon.setBounds(725, 11, 30, 30);
        userpanel.add(userIcon);

        //Label đăng xuất
        JLabel logoutLabel = new JLabel(new ImageIcon(new ImageIcon("quizmanagementsystem/src/main/resources/img/enter.png")
                .getImage().getScaledInstance(25, 30, Image.SCALE_SMOOTH)));
        logoutLabel.setBounds(750, 11, 25, 30);
        userpanel.add(logoutLabel);

        logoutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                    f.dispose();
                    new LoginGUI();
            }
        });

        // ==========================Bộ lọc - Tìm kiếm - Button
        // ==========================
        JPanel filterPanel = new JPanel();
        filterPanel.setBounds(0, 50, 800, 70);
        filterPanel.setLayout(null);

        // Button Thêm
        JButton addButton = new JButton("Thêm");
        addButton.setBounds(20, 20, 70, 25);
        addButton.setBackground(Color.decode("#9EFFD7"));
        filterPanel.add(addButton);

        // ==========================Action
        // thêm============================================
        addButton.addActionListener(e -> {
            // Sinh mã lớp tự động
            String generatedClassID = ClassBUS.generateClassCode();

            // Tạo JPanel chứa form nhập thông tin lớp mới
            JPanel addPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            addPanel.setBorder(BorderFactory.createTitledBorder("Thêm lớp"));

            JLabel classCodeLabel = new JLabel("Mã lớp:");
            JTextField classCodeField = new JTextField(generatedClassID);
            classCodeField.setEditable(false);

            JLabel classNameLabel = new JLabel("Tên lớp:");
            JTextField classNameField = new JTextField();

            addPanel.add(classCodeLabel);
            addPanel.add(classCodeField);
            addPanel.add(classNameLabel);
            addPanel.add(classNameField);

            // Hiển thị Dialog nhập thông tin
            int result = JOptionPane.showConfirmDialog(null, addPanel, "Thêm lớp",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String className = classNameField.getText().trim();

                if (!className.isEmpty()) {
                    // Gọi BUS để thêm lớp
                    boolean isInserted = ClassBUS.addClass(className, userID);
                    if (isInserted) {
                        JOptionPane.showMessageDialog(null, "Thêm lớp thành công!", "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                        loadClassData(); //  Cập nhật lại bảng sau khi thêm
                    } else {
                        JOptionPane.showMessageDialog(null, "Lỗi khi thêm lớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Tên lớp không được để trống!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Button Xóa
        JButton deleteButton = new JButton("Xóa");
        deleteButton.setBounds(100, 20, 60, 25);
        deleteButton.setBackground(Color.decode("#FF9EB5"));
        filterPanel.add(deleteButton);

        // Button Sửa
        JButton updateButton = new JButton("Sửa");
        updateButton.setBounds(170, 20, 60, 25);
        updateButton.setBackground(Color.decode("#5440FF"));
        filterPanel.add(updateButton);

        // Label Tìm kiếm
        JLabel searchLabel = new JLabel("Tìm kiếm");
        searchLabel.setBounds(260, 20, 80, 30);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 15));
        filterPanel.add(searchLabel);

        // TextField Tìm kiếm
        searchText = new JTextField();
        searchText.setBounds(350, 20, 300, 30);
        searchText.setForeground(Color.GRAY);
        filterPanel.add(searchText);

        // Label icon Sắp xếp
        JLabel arrangeLabel = new JLabel();
        arrangeLabel.setBounds(670, 20, 50, 30);
        ImageIcon arrangeIcon = new ImageIcon("quizmanagementsystem/src/main/resources/img/arrows.png");
        Image imgArrange = arrangeIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        arrangeLabel.setIcon(new ImageIcon(imgArrange));
        filterPanel.add(arrangeLabel);

        // Action cho label sắp xếp
        arrangeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showSortMenu(evt);
            }
        });

        // ==========================Tạo bảng JTable ==========================
        tableModel = new DefaultTableModel();
        classTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(classTable);
        scrollPane.setBounds(20, 130, 750, 400); // Đặt đúng vị trí để không bị ẩn

        // ==========================Delete Table==========================
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClass();
            }
        });

        // ==========================Update Button==============================
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClass();
            }
        });
        // Action cho searchText
        searchText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTableData();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTableData();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTableData();
            }
        });

        // Khởi tạo bảng với dữ liệu ban đầu
        loadClassData(); // Thay vì loadInitialData()

        // ==========================Thêm vào Frame ==========================
        f.add(userpanel);
        f.add(filterPanel);
        f.add(scrollPane);

        // ==========================Load dữ liệu vào bảng ==========================
        loadClassData();

        // Hiển thị Frame
        f.setVisible(true);
    }

    private void loadClassData() {
        // Lấy danh sách lớp từ BUS
        List<ClassDTO> classList = ClassBUS.getAllClasses();

        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Đặt tên cột
        String[] columnNames = { "Mã Lớp", "Tên Lớp" };
        tableModel.setColumnIdentifiers(columnNames);

        // Đổ dữ liệu vào bảng
        for (ClassDTO classObj : classList) {
            Vector<Object> row = new Vector<>();
            row.add(classObj.getClassID());
            row.add(classObj.getClassName());
            tableModel.addRow(row);
        }
    }

    // =========================Hàm xóa=======================
    private void deleteClass() {
        int selectedRow = classTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn lớp để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String classID = tableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn xóa lớp này?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean isDeleted = ClassBUS.deleteClass(classID);
            if (isDeleted) {
                JOptionPane.showMessageDialog(null, "Xóa lớp thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                loadClassData();
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa lớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // =====================Hàm update=======================
    private void updateClass() {
        int selectedRow = classTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một lớp để sửa.", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String classID = (String) tableModel.getValueAt(selectedRow, 0); // Mã lớp không thay đổi
        String oldClassName = (String) tableModel.getValueAt(selectedRow, 1);

        // Hiển thị hộp thoại nhập tên mới
        String newClassName = JOptionPane.showInputDialog(null, "Nhập tên mới cho lớp:", oldClassName);

        if (newClassName != null && !newClassName.trim().isEmpty()) {
            boolean isUpdated = ClassBUS.updateClassName(classID, newClassName.trim());
            if (isUpdated) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                loadClassData(); // Cập nhật lại bảng
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    // Hàm hiển thị menu sxep
    private void showSortMenu(java.awt.event.MouseEvent evt) {
        JPopupMenu sortMenu = new JPopupMenu();

        JMenuItem ascendingItem = new JMenuItem("Sắp xếp A - Z");
        ascendingItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTableData(true); // Sắp xếp tăng dần
            }
        });

        JMenuItem descendingItem = new JMenuItem("Sắp xếp Z - A");
        descendingItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTableData(false); // Sắp xếp giảm dần
            }
        });

        sortMenu.add(ascendingItem);
        sortMenu.add(descendingItem);

        // Hiển thị menu tại vị trí của chuột
        sortMenu.show(((ComponentEvent) evt).getComponent(), evt.getX(), evt.getY());
    }

    // ======================Hàm sắp xếp=================================
    private void updateTableData(boolean ascending) {
        List<ClassDTO> sortedClasses = ClassBUS.sortClasses(ascending);

        tableModel.setRowCount(0);
        for (ClassDTO classObj : sortedClasses) {
            Vector<Object> row = new Vector<>();
            row.add(classObj.getClassID());
            row.add(classObj.getClassName());
            row.add(classObj.getTeacherID());
            tableModel.addRow(row);
        }
    }

    // Hàm update table
    // Thay vì gọi loadInitialData(), bạn sẽ gọi loadClassData()
    private void updateTableData() {
        String keyword = searchText.getText().trim();
        if (keyword.isEmpty()) {
            // Nếu trường tìm kiếm trống, hiển thị dữ liệu ban đầu
            loadClassData(); // Gọi phương thức đã có để tải dữ liệu
        } else {
            // Tìm kiếm và cập nhật dữ liệu
            ClassBUS.searchClassData(keyword, tableModel);
        }
    }
}
