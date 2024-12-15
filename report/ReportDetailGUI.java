package report;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class ReportDetailGUI extends JDialog {
    private JLabel titleLabel;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JLabel dateLabel;
    private JTextArea contentArea;
    private JButton closeButton;
    private Font ft = new Font("맑은 고딕", Font.PLAIN, 15);

    public ReportDetailGUI(Report report) {
        setTitle("보고사항 상세");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        // 상단 패널에 제목, 작성자, 받는이, 작성 날짜 추가
        JPanel topPanel = new JPanel(new GridLayout(4, 1));
        titleLabel = new JLabel("제목: " + report.getReportTitle());
        titleLabel.setFont(ft);
        fromLabel = new JLabel("작성자: " + report.getFromId());
        fromLabel.setFont(ft); 
        toLabel = new JLabel("받는이: " + report.getToId());
        toLabel.setFont(ft);
        dateLabel = new JLabel("작성 날짜: " + report.getReportDate().toString());
        dateLabel.setFont(ft); 

        topPanel.add(titleLabel);
        topPanel.add(fromLabel);
        topPanel.add(toLabel);
        topPanel.add(dateLabel);

        // 중앙 패널에 보고서 내용 추가
        contentArea = new JTextArea(report.getReportContent());
        contentArea.setFont(ft); 
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane contentScrollPane = new JScrollPane(contentArea);

        // 하단 패널에 닫기 버튼 추가
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closeButton = new JButton("닫기");
        closeButton.setFont(ft); 
        closeButton.addActionListener(e -> dispose());
        bottomPanel.add(closeButton);

        add(topPanel, BorderLayout.NORTH);
        add(contentScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

	public ReportDetailGUI(String title) {
		// TODO Auto-generated constructor stub
	}
}
