package notice_admin;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class NoticeDetailGUI extends JFrame {
    private Font ft = new Font("맑은 고딕", Font.PLAIN, 15);

    public NoticeDetailGUI(String title, String content, int writer_id, LocalDateTime write_date, String noticeType) {
        setTitle("공지사항 상세");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        String noticeTypeStr = noticeType.equals("필독 공지") ? "필독 공지" : "일반 공지";
        Color noticeColor = noticeType.equals("필독 공지") ? Color.RED : Color.BLUE;

        // 상단 패널
        JPanel topPanel = new JPanel(new GridLayout(4, 1));
        JLabel titleLabel = new JLabel("제목: " + title);
        titleLabel.setFont(ft);
        JLabel writerLabel = new JLabel("작성자: " + writer_id);
        writerLabel.setFont(ft);
        JLabel dateLabel = new JLabel("작성 날짜: " + write_date.toString());
        dateLabel.setFont(ft);
        JLabel noticeTypeLabel = new JLabel("공지 유형: " + noticeTypeStr);
        noticeTypeLabel.setFont(ft);
        noticeTypeLabel.setForeground(noticeColor);

        topPanel.add(titleLabel);
        topPanel.add(writerLabel);
        topPanel.add(dateLabel);
        topPanel.add(noticeTypeLabel);

        // 중앙 패널
        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(ft);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(contentArea);

        // 하단 패널에 닫기 버튼 추가
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("닫기");
        closeButton.setFont(ft);
        closeButton.addActionListener(e -> dispose());
        bottomPanel.add(closeButton);

        // 패널 추가
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    
    }

	public NoticeDetailGUI(String title) {
		// TODO Auto-generated constructor stub
	
	}

	public NoticeDetailGUI(noticeVO notice) {
		// TODO Auto-generated constructor stub
	}
}

