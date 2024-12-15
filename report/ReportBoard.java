package report;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import login.DBUtil;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;

public class ReportBoard extends JFrame {
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private ReportDAO reportDAO;
    private Font ft = new Font("맑은 고딕", Font.PLAIN, 15);
    private JTextField keywordField;
    private JComboBox<String> searchOptions;

    public ReportBoard() {
        setTitle("보고 게시판");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##jhm13", "1234");
            reportDAO = new ReportDAO();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "데이터베이스 연결 실패", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // 상단 검색 패널
        JPanel searchPanel = new JPanel();
        searchOptions = new JComboBox<>(new String[] { "제목", "작성자", "받는이" });
        searchOptions.setFont(ft);
        keywordField = new JTextField(20);
        keywordField.setFont(ft);
        JButton searchButton = new JButton("검색");
        searchButton.setFont(ft);
        
        JButton backButton = new JButton("게시판으로 이동");
        backButton.setFont(ft);

        JLabel searchLabel = new JLabel(":");
        searchLabel.setFont(ft);

        searchPanel.add(searchOptions);
        searchPanel.add(searchLabel);
        searchPanel.add(keywordField);
        searchPanel.add(searchButton);
        searchPanel.add(backButton);
        add(searchPanel, BorderLayout.NORTH);

        // 보고사항 테이블
        tableModel = new DefaultTableModel(new String[] { "제목", "작성자", "받는이", "작성 날짜" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        reportTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setFont(ft);
                return c;
            }
        };
        reportTable.setFont(ft);
        reportTable.getTableHeader().setFont(ft);
        reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(reportTable);
        add(scrollPane, BorderLayout.CENTER);

        // 하단 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("보고사항 작성");
        addButton.setFont(ft);
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 보고사항 작성 버튼 클릭 이벤트
        addButton.addActionListener(e -> openReportDialog());

        // 검색 버튼 클릭 이벤트
        searchButton.addActionListener(e -> {
            String keyword = keywordField.getText();
            searchReports(keyword);
        });

        // 게시판으로 이동 버튼 클릭 이벤트
        backButton.addActionListener(e -> loadReports());

        // 테이블 행 클릭 이벤트
        reportTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && reportTable.getSelectedRow() != -1) {
                int row = reportTable.getSelectedRow();
                String title = (String) tableModel.getValueAt(row, 0);
                showReportDetails(title);
            }
        });

        loadReports();
    }

    // 보고사항 로드
    void loadReports() {
        try {
            List<Report> reports = reportDAO.getAllReports();
            tableModel.setRowCount(0);
            for (Report report : reports) {
                Vector<Object> row = new Vector<>();
                row.add(report.getReportTitle());
                row.add(report.getFromId());
                row.add(report.getToId());
                row.add(report.getReportDate());
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 보고사항 검색
    private void searchReports(String keyword) {
        String selectedOption = (String) searchOptions.getSelectedItem();
        String sql = "SELECT * FROM reports WHERE ";

        switch (selectedOption) {
            case "제목":
                sql += "reportTitle LIKE ?";
                break;
            case "작성자":
                sql += "fromId LIKE ?";
                break;
            case "받는이":
                sql += "toId LIKE ?";
                break;
            default:
                return; // 선택되지 않은 경우 아무것도 하지 않음
        }

        try (Connection conn = DBUtil.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(rs.getString("reportTitle"));
                    row.add(rs.getString("fromId"));
                    row.add(rs.getString("toId"));
                    row.add(rs.getTimestamp("reportDate").toLocalDateTime());
                    tableModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 보고사항 작성
    private void openReportDialog() {
        ReportDialog dialog = new ReportDialog();
        dialog.setVisible(true);

        if (dialog.isSubmit()) {
            Report report = dialog.getReport();
            try {
                reportDAO.addReport(report);
                loadReports();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "보고사항 추가 중 오류 발생", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 보고사항 상세 보기
    private void showReportDetails(String title) {
        try (Connection conn = DBUtil.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM reports WHERE reportTitle = ?")) {
            pstmt.setString(1, title);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String content = rs.getString("reportContent");
                    String fromId = rs.getString("fromId");
                    String toId = rs.getString("toId");
                    LocalDateTime reportDate = rs.getTimestamp("reportDate").toLocalDateTime();

                    Report report = new Report(title, content, fromId, toId, reportDate);
                    ReportDetailGUI detailGUI = new ReportDetailGUI(report);
                    detailGUI.setVisible(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReportBoard().setVisible(true));
    }
}
