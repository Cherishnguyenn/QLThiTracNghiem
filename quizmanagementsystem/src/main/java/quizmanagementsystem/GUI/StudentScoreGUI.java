package quizmanagementsystem.GUI;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.*;
import java.util.List;
import quizmanagementsystem.BUS.StudentScoreBUS;
import quizmanagementsystem.BUS.UserBUS;
import quizmanagementsystem.DTO.StudentScoreDTO;

public class StudentScoreGUI {
    private int userID;

    public StudentScoreGUI(int userID) {
        this.userID = userID;

        // Frame
        JFrame f = new JFrame("Quiz Management System");
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Thanh hiển thị tên người dùng
        JPanel userpanel = new JPanel();
        userpanel.setBackground(Color.decode("#C3F5FF"));
        userpanel.setBounds(0, 0, 800, 50);
        userpanel.setLayout(null);

        // Lấy tên học sinh từ userID
        String studentName = UserBUS.getUserName(userID);

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
                StudentFrameGUI studentframe = new StudentFrameGUI(userID);
                studentframe.showFrame();

                // Đóng frame hiện tại
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(backlabel);
                if (currentFrame != null) {
                    currentFrame.dispose();
                }
            }
        });

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

        // Panel chứa biểu đồ
        JPanel chartPanel = new JPanel();
        chartPanel.setBounds(0, 50, 800, 550);
        chartPanel.setLayout(new BorderLayout());

        // Lấy startDate và endDate từ database
        String startDate = StudentScoreBUS.getStartDate(userID);
        String endDate = StudentScoreBUS.getEndDate(userID);

        // Lấy dữ liệu và hiển thị biểu đồ
        updateChart(chartPanel, startDate, endDate);

        // Thêm vào frame
        f.add(userpanel);
        f.add(chartPanel);

        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

    private void updateChart(JPanel chartPanel, String startDate, String endDate) {
        // Lấy dữ liệu điểm số của học sinh
        List<StudentScoreDTO> scores = StudentScoreBUS.getStudentScores(userID, startDate, endDate);

        // Kiểm tra nếu không có dữ liệu
        if (scores.isEmpty()) {
            JOptionPane.showMessageDialog(chartPanel, "Không có dữ liệu điểm số trong khoảng thời gian này.",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Tạo dataset cho biểu đồ
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (StudentScoreDTO score : scores) {
            dataset.addValue(score.getScore(), "Scores", String.valueOf(score.getExamID()));
        }

        // Tạo biểu đồ
        JFreeChart chart = ChartFactory.createLineChart(
                "Thống kê điểm số",
                "Mã bài làm",
                "Điểm số",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Thiết lập màu nền của plot
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);

        // Thiết lập màu các đường biểu diễn
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED); // Thiết lập màu cho series đầu tiên

        // Thiết lập phạm vi cho trục y
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0.0, 10.0); // Thiết lập phạm vi từ 0.0 đến 10.0
        rangeAxis.setAutoRangeIncludesZero(true); // Đảm bảo trục y bao gồm giá trị 0
        rangeAxis.setTickUnit(new NumberTickUnit(0.5)); // Thiết lập khoảng cách giữa các tick là 0.5

        // Thiết lập autoRange cho trục x
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryMargin(0.1); // Thiết lập khoảng cách giữa các cột

        // Hiển thị biểu đồ
        chartPanel.removeAll();
        chartPanel.setLayout(null);
        ChartPanel chartPanelComponent = new ChartPanel(chart);
        chartPanelComponent.setBounds(0, 0, 800, 600); // Thiết lập vị trí và kích thước mong muốn
        chartPanel.add(chartPanelComponent);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

}