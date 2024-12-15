package notice_user;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import login.DBUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Vector;

public class NoticeBoardGUI extends JFrame {
	private JTable noticeTable;
	private DefaultTableModel tableModel;
	private JTextField keywordField;
	private JComboBox<String> searchOptions;
	private Font ft = new Font("맑은 고딕", Font.PLAIN, 15);

	public NoticeBoardGUI() {
		setTitle("공지 게시판");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		//add(buttonPanel, BorderLayout.SOUTH);

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
		checkUnreadMandatoryNotices(); // 필독 공지사항 확인 및 팝업
	}

	// 공지사항 로드
	void loadNotices() {
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

	// 필독 공지사항 확인
	private void checkUnreadMandatoryNotices() {
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT * FROM Board WHERE noticeType = '필독 공지' AND isRead = 0")) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String title = rs.getString("title");
					String content = rs.getString("content");
					int writer_id = rs.getInt("writer_id");
					LocalDateTime write_date = rs.getTimestamp("write_date").toLocalDateTime();
					String noticeType = rs.getString("noticeType");

					NoticeDetailGUI detailGUI = new NoticeDetailGUI(title, content, writer_id, write_date, noticeType);
					detailGUI.setVisible(true);

					// 공지사항을 읽음 상태로 업데이트
					markAsRead(title);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 공지사항을 읽음 상태로 업데이트
	private void markAsRead(String title) {
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Board SET isRead = 1 WHERE title = ?")) {
			pstmt.setString(1, title);
			pstmt.executeUpdate();
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
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Board WHERE title = ?")) {
			pstmt.setString(1, title);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					String content = rs.getString("content");
					int writer_id = rs.getInt("writer_id");
					LocalDateTime write_date = rs.getTimestamp("write_date").toLocalDateTime();
					String noticeType = rs.getString("noticeType");

					NoticeDetailGUI detailGUI = new NoticeDetailGUI(title, content, writer_id, write_date, noticeType);
					detailGUI.setVisible(true);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new NoticeBoardGUI().setVisible(true));
	}
}
