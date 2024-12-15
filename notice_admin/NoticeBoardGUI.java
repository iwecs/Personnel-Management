package notice_admin;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Admin.AdminPage;
import Admin.EmployeeDao;
import Admin.EmployeeVO;
import login.DBUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Vector;

public class NoticeBoardGUI extends JFrame {
    private JTable noticeTable;
    private DefaultTableModel tableModel;
    private JTextField keywordField;
    private JComboBox<String> searchOptions;
    private Font ft = new Font("맑은 고딕", Font.PLAIN, 15);
    private ArrayList<noticeVO> listnotice = new ArrayList<noticeVO>();
    noticeDAO dao = new noticeDAO();

    public NoticeBoardGUI() {
        setTitle("공지 게시판");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 상단 검색 패널
        JPanel searchPanel = new JPanel();
        searchOptions = new JComboBox<>(new String[] { "제목", "작성자", "공지 유형" });
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

        // 공지사항 테이블
        tableModel = new DefaultTableModel(new String[] { "제목", "작성자", "작성 날짜", "공지 유형" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        noticeTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                String noticeType = (String) getValueAt(row, 3);
                if (noticeType.equals("필독 공지")) {
                    c.setForeground(Color.RED);
                } else {
                    c.setForeground(Color.BLUE);
                }
                c.setFont(ft);
                return c;
            }
        };
        noticeTable.setFont(ft);
        noticeTable.getTableHeader().setFont(ft);
        noticeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(noticeTable);
        add(scrollPane, BorderLayout.CENTER);

        // 하단 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("공지사항 작성");
        addButton.setFont(ft);
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 공지사항 작성 버튼 클릭 이벤트
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NoticeFormGUI(NoticeBoardGUI.this).setVisible(true);
            }
        });

        // 검색 버튼 클릭 이벤트
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = keywordField.getText();
                searchNotices(keyword);
            }
        });

        // 공지게시판으로 이동 버튼 클릭 이벤트
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNotices();
            }
        });

        // 테이블 행 클릭 이벤트
        noticeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && noticeTable.getSelectedRow() != -1) {
                int row = noticeTable.getSelectedRow();
                String title = (String) tableModel.getValueAt(row, 0);
                showNoticeDetails(title);
            }
        });

        loadNotices();
    }

    // 공지사항 로드
    public void loadNotices() {
    	
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Board")) {
            tableModel.setRowCount(0);
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("title"));
                row.add(rs.getString("writer_id"));
                row.add(rs.getTimestamp("write_date").toLocalDateTime());
                String noticeType = rs.getString("noticeType");
                row.add(noticeType);
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 공지사항 검색
    private void searchNotices(String keyword) {
        String selectedOption = (String) searchOptions.getSelectedItem();
        String sql = "SELECT title, writer_id, write_date, noticeType FROM Board WHERE ";

        switch (selectedOption) {
            case "제목":
                sql += "title LIKE ?";
                break;
            case "작성자":
                sql += "writer_id LIKE ?";
                break;
            case "공지 유형":
                sql += "noticeType LIKE ?";
                break;
        }

        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(rs.getString("title"));
                    row.add(rs.getInt("writer_id"));
                    row.add(rs.getTimestamp("write_date").toLocalDateTime());
                    String noticeType = rs.getString("noticeType");
                    row.add(noticeType);
                    tableModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 공지사항 상세 보기
    private void showNoticeDetails(String title) {
    	
    	//ResultSet rs = pstmt.executeQuery()
//        try (Connection conn = DBUtil.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Board WHERE title = ?")) {
//            pstmt.setString(1, title);
    	
    	listnotice = dao.select();
    	
    	tableModel.addColumn("제목");
    	tableModel.addColumn("작성자");
    	tableModel.addColumn("작성날짜");
    	tableModel.addColumn("공지유형");
        
    	for (noticeVO temp : listnotice) {
    		tableModel.addRow(new Object[] { temp.getTitle(),
    				temp.getWriter_id(),temp.getWrite_Date(),
    				temp.getNoticeType()
					
			});
    	}
		// 새로운 테이블 생성 및 설정
		JTable viewTable = new JTable(tableModel);
		//viewTable.setFont(AdminPage.ft);
		viewTable.setRowHeight(30);
		
		JScrollPane viewSc = new JScrollPane(viewTable);

		// 추가
		add(viewSc);
    	

//            try {
//            	
//            	connection = DBUtil.getConnection();
//    			String sql = "SELECT * FROM reports";
//    			pstmt = connection.prepareStatement(sql);
//    			rs = pstmt.executeQuery();
//                if (rs.next()) {
//                    String content = rs.getString("content");
//                    int writer_id = rs.getInt("writer_id");
//                    LocalDateTime write_date = rs.getTimestamp("write_date").toLocalDateTime();
//                    String noticeType = rs.getString("noticeType");
//
//                    NoticeDetailGUI detailGUI = new NoticeDetailGUI(title, content, writer_id, write_date, noticeType);
//                    detailGUI.setVisible(true);
//                }
//            
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
            
            
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NoticeBoardGUI().setVisible(true));
    }
}
