package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import login.DBUtil;
import notice_admin.NoticeDetailGUI;
import notice_admin.NoticeFormGUI;
import notice_admin.noticeVO;
import report.Report;
import report.ReportDAO;
import report.ReportDetailGUI;
import report.ReportDialog;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ReportManage extends JPanel {

	private ArrayList<noticeVO> list = new ArrayList<>();
	private DefaultTableModel model, model2;
	private JTable noticeTable, reportTable;
	private JTextField keywordField;
	private JComboBox<String> searchOptions;
	private JPanel nodataPanel;


	static Font ft = new Font("맑은 고딕", Font.BOLD, 15);
	static Font ft2 = new Font("맑은 고딕", Font.PLAIN, 15);
	static Color color = new Color(150, 195, 222);

	public ReportManage(JPanel panel) {
		setPreferredSize(panel.getSize());
		setLayout(null);
		setBackground(AdminPage.Backgroundcolor);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1, 10, 40));
		buttonPanel.setBounds(50, 50, 170, 300);
		buttonPanel.setBackground(AdminPage.Backgroundcolor);

		JButton button1 = new JButton("업무 전달");
		JButton button2 = new JButton("공지사항 관리");
		button1.setFont(AdminPage.ft);
		button1.setBackground(AdminPage.color);
		button2.setFont(AdminPage.ft);
		button2.setBackground(AdminPage.color);

		buttonPanel.add(button1);
		buttonPanel.add(button2);

		add(buttonPanel);

		nodataPanel = new JPanel();
		nodataPanel.setBounds(250, 10, 600, 450);
		nodataPanel.setBackground(AdminPage.Backgroundcolor);
		add(nodataPanel);

		// Default display with no data image
		displayNoDataImage();

		///////// 업무 전달 버튼 눌렀을 때
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showReportBoard();
			}
		});

		/////// // 공지사항 관리 버튼 눌렀을 때
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showNoticeBoard();
			}
		});
	}

	private void displayNoDataImage() {
		nodataPanel.removeAll();
		ImageIcon logoIcon = new ImageIcon("./image/nodata.png");
		Image originalImage = logoIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JLabel logoLabel = new JLabel(scaledIcon);
		logoLabel.setBackground(AdminPage.Backgroundcolor);
		nodataPanel.add(logoLabel);
		nodataPanel.revalidate();
		nodataPanel.repaint();
	}

	// 업무 전달 화면 표시
	private void showReportBoard() {
		nodataPanel.removeAll();
		nodataPanel.setLayout(new BorderLayout());

		JPanel searchPanel = new JPanel();
		searchOptions = new JComboBox<>(new String[] { "제목", "작성자", "받는이" });
		searchOptions.setFont(AdminPage.ft);
		keywordField = new JTextField(20);
		keywordField.setFont(AdminPage.ft);
		JButton searchButton = new JButton("검색");
		searchButton.setFont(AdminPage.ft);
		searchButton.setBackground(AdminPage.color);
		JButton backButton = new JButton("메인화면");
		backButton.setFont(AdminPage.ft);
		backButton.setBackground(AdminPage.color);

		JLabel searchLabel = new JLabel("");
		searchLabel.setFont(AdminPage.ft);

		searchPanel.add(searchOptions);
		searchPanel.add(searchLabel);
		searchPanel.add(keywordField);
		searchPanel.add(searchButton);
		searchPanel.add(backButton);
		nodataPanel.add(searchPanel, BorderLayout.NORTH);

		model2 = new DefaultTableModel(new String[] { "제목", "작성자", "받는이", "작성 날짜" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		reportTable = new JTable(model2) {
			@Override
			public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				c.setFont(ft2);
				return c;
			}
		};

		reportTable.setFont(AdminPage.ft);
		reportTable.getTableHeader().setFont(AdminPage.ft);
		reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(reportTable);
		nodataPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(AdminPage.Backgroundcolor);
		JButton addButton = new JButton("보고사항 작성");
		addButton.setFont(AdminPage.ft);
		addButton.setBackground(AdminPage.color);
		buttonPanel.add(addButton);
		nodataPanel.add(buttonPanel, BorderLayout.SOUTH);

		addButton.addActionListener(e -> {
			ReportDialog reportDialog = new ReportDialog();
			reportDialog.setVisible(true);
			reportDialog.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent windowEvent) {
					loadReports();
				}
			});
		});

		searchButton.addActionListener(e -> {
			String keyword = keywordField.getText();
			searchReports(keyword);
		});

		backButton.addActionListener(e -> displayNoDataImage());

		reportTable.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && reportTable.getSelectedRow() != -1) {
				int row = reportTable.getSelectedRow();
				String title = (String) model2.getValueAt(row, 0);
				showReportDetails(title);
			}
		});

		loadReports();
		nodataPanel.revalidate();
		nodataPanel.repaint();
	}

	private void loadReports() {
		try (Connection conn = DBUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM reports")) {
			model2.setRowCount(0);
			while (rs.next()) {
				Vector<Object> row = new Vector<>();
				row.add(rs.getString("reportTitle"));
				row.add(rs.getString("fromId"));
				row.add(rs.getString("toId"));
				row.add(rs.getTimestamp("reportDate").toLocalDateTime());
				model2.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void searchReports(String keyword) {
		String selectedOption = searchOptions.getSelectedItem().toString();
		String query = "";
		if (selectedOption.equals("제목")) {
			query = "SELECT * FROM reports WHERE reportTitle LIKE ?";
		} else if (selectedOption.equals("작성자")) {
			query = "SELECT * FROM reports WHERE fromId LIKE ?";
		} else if (selectedOption.equals("받는이")) {
			query = "SELECT * FROM reports WHERE toId LIKE ?";
		}

		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();
			model2.setRowCount(0);
			while (rs.next()) {
				Vector<Object> row = new Vector<>();
				row.add(rs.getString("reportTitle"));
				row.add(rs.getString("fromId"));
				row.add(rs.getString("toId"));
				row.add(rs.getTimestamp("reportDate").toLocalDateTime());
				model2.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showReportDetails(String reportTitle) {
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM reports WHERE reportTitle = ?")) {
			pstmt.setString(1, reportTitle);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Report report = new Report();
				report.setReportTitle(rs.getString("reportTitle"));
				report.setFromId(rs.getString("fromId"));
				report.setToId(rs.getString("toId"));
				report.setReportDate(rs.getTimestamp("reportDate").toLocalDateTime());
				report.setReportContent(rs.getString("reportContent"));
				new ReportDetailGUI(report).setVisible(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

/////////////////////////////////////////////////////////////////////////////////////
//공지사항 화면 표시
	private void showNoticeBoard() {
		nodataPanel.removeAll();
		nodataPanel.setLayout(new BorderLayout());
		nodataPanel.setBackground(color);

		JPanel searchPanel = new JPanel();
		searchOptions = new JComboBox<>(new String[] { "제목", "작성자", "공지 유형" });
		searchOptions.setFont(AdminPage.ft);
		keywordField = new JTextField(20);
		keywordField.setFont(AdminPage.ft);
		JButton searchButton = new JButton("검색");
		searchButton.setFont(AdminPage.ft);
		searchButton.setBackground(AdminPage.color);
		JButton backButton = new JButton("메인화면");
		backButton.setFont(AdminPage.ft);
		backButton.setBackground(AdminPage.color);

		JLabel searchLabel = new JLabel(":");
		searchLabel.setFont(AdminPage.ft);

		searchPanel.add(searchOptions);
		searchPanel.add(searchLabel);
		searchPanel.add(keywordField);
		searchPanel.add(searchButton);
		searchPanel.add(backButton);
		nodataPanel.add(searchPanel, BorderLayout.NORTH);

		model = new DefaultTableModel(new String[] { "제목", "작성자", "작성 날짜", "공지 유형" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		noticeTable = new JTable(model) {
			@Override
			public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				String noticeType = (String) getValueAt(row, 3);
				if ("필독 공지".equals(noticeType)) {
					c.setForeground(Color.RED);
					c.setFont(ft2);
				} else {
					c.setForeground(Color.BLACK);
					c.setFont(ft2);
				}
				return c;
			}
		};

		noticeTable.setFont(AdminPage.ft);
		noticeTable.getTableHeader().setFont(AdminPage.ft);
		noticeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(noticeTable);
		nodataPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(AdminPage.Backgroundcolor);
		JButton addButton = new JButton("공지사항 작성");
		addButton.setFont(AdminPage.ft);
		addButton.setBackground(AdminPage.color);
		buttonPanel.add(addButton);
		nodataPanel.add(buttonPanel, BorderLayout.SOUTH);

		addButton.addActionListener(e -> {
			NoticeFormGUI noticeForm = new NoticeFormGUI(null);
			noticeForm.setVisible(true);
			noticeForm.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent windowEvent) {
					loadNotices();
				}
			});
		});

		searchButton.addActionListener(e -> {
			String keyword = keywordField.getText();
			searchNotices(keyword);
		});

//backButton.addActionListener(e -> displayNoDataImage());
//
//noticeTable.getSelectionModel().addListSelectionListener(e -> {
//if (!e.getValueIsAdjusting() && noticeTable.getSelectedRow() != -1) {
//int row = noticeTable.getSelectedRow();
//String title = (String) model.getValueAt(row, 0);
//showNoticeDetails(title);
//}
//});

		loadNotices();
		nodataPanel.revalidate();
		nodataPanel.repaint();
	}

	private void loadNotices() {
		try (Connection conn = DBUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM board")) {
			model.setRowCount(0);
			while (rs.next()) {
				Vector<Object> row = new Vector<>();
				row.add(rs.getString("title"));
				row.add(rs.getInt("writer_id"));
				row.add(rs.getTimestamp("write_date").toLocalDateTime());
				row.add(rs.getString("noticeType"));
				model.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void searchNotices(String keyword) {
		String selectedOption = searchOptions.getSelectedItem().toString();
		String query = "";
		if (selectedOption.equals("제목")) {
			query = "SELECT * FROM board WHERE title LIKE ?";
		} else if (selectedOption.equals("작성자")) {
			query = "SELECT * FROM board WHERE writer_id LIKE ?";
		} else if (selectedOption.equals("공지 유형")) {
			query = "SELECT * FROM board WHERE noticeType LIKE ?";
		}

		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();
			model.setRowCount(0);
			while (rs.next()) {
				Vector<Object> row = new Vector<>();
				row.add(rs.getString("title"));
				row.add(rs.getInt("writer_id"));
				row.add(rs.getTimestamp("write_date").toLocalDateTime());
				row.add(rs.getString("noticeType"));
				model.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

/////
	private void showNoticeDetails(String title) {
	try (Connection conn = DBUtil.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM board WHERE title = ? ")) {
		pstmt.setString(1, title);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			noticeVO notice = new noticeVO();
			notice.setTitle(rs.getString("title"));
			notice.setWriter_id(rs.getInt("writer_id"));
			notice.setWrite_Date(rs.getTimestamp("write_date").toLocalDateTime());
			notice.setContent(rs.getString("content"));
			notice.setNoticeType(rs.getString("noticeType"));
			new NoticeDetailGUI(notice).setVisible(true);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}
}
