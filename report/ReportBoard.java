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
    private Font ft = new Font("���� ���", Font.PLAIN, 15);
    private JTextField keywordField;
    private JComboBox<String> searchOptions;

    public ReportBoard() {
        setTitle("���� �Խ���");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##jhm13", "1234");
            reportDAO = new ReportDAO();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "�����ͺ��̽� ���� ����", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // ��� �˻� �г�
        JPanel searchPanel = new JPanel();
        searchOptions = new JComboBox<>(new String[] { "����", "�ۼ���", "�޴���" });
        searchOptions.setFont(ft);
        keywordField = new JTextField(20);
        keywordField.setFont(ft);
        JButton searchButton = new JButton("�˻�");
        searchButton.setFont(ft);
        
        JButton backButton = new JButton("�Խ������� �̵�");
        backButton.setFont(ft);

        JLabel searchLabel = new JLabel(":");
        searchLabel.setFont(ft);

        searchPanel.add(searchOptions);
        searchPanel.add(searchLabel);
        searchPanel.add(keywordField);
        searchPanel.add(searchButton);
        searchPanel.add(backButton);
        add(searchPanel, BorderLayout.NORTH);

        // ������� ���̺�
        tableModel = new DefaultTableModel(new String[] { "����", "�ۼ���", "�޴���", "�ۼ� ��¥" }, 0) {
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

        // �ϴ� ��ư �г�
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("������� �ۼ�");
        addButton.setFont(ft);
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // ������� �ۼ� ��ư Ŭ�� �̺�Ʈ
        addButton.addActionListener(e -> openReportDialog());

        // �˻� ��ư Ŭ�� �̺�Ʈ
        searchButton.addActionListener(e -> {
            String keyword = keywordField.getText();
            searchReports(keyword);
        });

        // �Խ������� �̵� ��ư Ŭ�� �̺�Ʈ
        backButton.addActionListener(e -> loadReports());

        // ���̺� �� Ŭ�� �̺�Ʈ
        reportTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && reportTable.getSelectedRow() != -1) {
                int row = reportTable.getSelectedRow();
                String title = (String) tableModel.getValueAt(row, 0);
                showReportDetails(title);
            }
        });

        loadReports();
    }

    // ������� �ε�
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

    // ������� �˻�
    private void searchReports(String keyword) {
        String selectedOption = (String) searchOptions.getSelectedItem();
        String sql = "SELECT * FROM reports WHERE ";

        switch (selectedOption) {
            case "����":
                sql += "reportTitle LIKE ?";
                break;
            case "�ۼ���":
                sql += "fromId LIKE ?";
                break;
            case "�޴���":
                sql += "toId LIKE ?";
                break;
            default:
                return; // ���õ��� ���� ��� �ƹ��͵� ���� ����
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

    // ������� �ۼ�
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
                JOptionPane.showMessageDialog(this, "������� �߰� �� ���� �߻�", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ������� �� ����
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
