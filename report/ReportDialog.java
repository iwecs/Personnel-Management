package report;

import javax.swing.*;

import Admin.AdminPage;
import login.DBUtil;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class ReportDialog extends JDialog {
	private JTextField titleField;
	private JTextField fromField;
	private JTextField toField;
	private JTextArea contentArea;
	private JButton submitButton;
	private boolean isSubmit;
	private ReportDAO dao;
	static Font ft = new Font("맑은 고딕", Font.BOLD, 15);///
	static Color color = new Color(150, 195, 222);///

	public ReportDialog() {
		// super(owner, title, modal);
		setTitle("보고사항 작성");
		setSize(500, 400);
		setLayout(new BorderLayout(10, 10));
		dao=new ReportDAO();
		JPanel topPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		JLabel titleLabel = new JLabel("제목:");
		titleLabel.setFont(ft);
		topPanel.add(titleLabel);
		titleField = new JTextField();
		titleField.setFont(ft);
		topPanel.add(titleField);
		JLabel fromLabel = new JLabel("작성자:");
		fromLabel.setFont(ft);
		topPanel.add(fromLabel);
		fromField = new JTextField();
		fromField.setFont(ft);
		topPanel.add(fromField);
		JLabel toLabel = new JLabel("받는이:");
		toLabel.setFont(ft);
		topPanel.add(toLabel);
		toField = new JTextField();
		toField.setFont(ft);
		topPanel.add(toField);

		JPanel centerPanel = new JPanel(new BorderLayout());
		JLabel contentLabel = new JLabel("내용:");
		contentLabel.setFont(ft);
		centerPanel.add(contentLabel, BorderLayout.NORTH);
		contentArea = new JTextArea(10, 30);
		contentArea.setFont(ft);
		contentArea.setLineWrap(true);
		contentArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(contentArea);
		centerPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		submitButton = new JButton("보고사항 추가");
		submitButton.setFont(ft);///
		submitButton.setBackground(color);///
		bottomPanel.add(submitButton);
		dao=new ReportDAO();
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		submitButton.addActionListener(e -> {
			try {
				Report temp = getReport();
				System.out.println(temp);
				dao.addReport(temp);
				isSubmit = true;
				setVisible(false);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		isSubmit = false;
	}

	public void setReportData(Report report) {
		titleField.setText(report.getReportTitle());
		fromField.setText(report.getFromId());
		toField.setText(report.getToId());
		contentArea.setText(report.getReportContent());
	}

	public Report getReport() {
		String reportTitle = titleField.getText();
		String fromId = fromField.getText();
		String toId = toField.getText();
		String reportContent = contentArea.getText();
		LocalDateTime date = LocalDateTime.now();
		return new Report(reportTitle, reportContent, fromId, toId, date);
	}

	public void setSubmitButtonListener(ActionListener listener) {
		submitButton.addActionListener(listener);
	}

	public boolean isSubmit() {
		return isSubmit;

	}
}
