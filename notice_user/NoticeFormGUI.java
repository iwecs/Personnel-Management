package notice_user;

import javax.swing.*;

import login.DBUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class NoticeFormGUI extends JFrame {
    private JTextField titleField;
    private JTextField writerField;
    private JTextArea contentArea;
    private JComboBox<String> noticeTypeBox;
    private NoticeBoardGUI parent;
    private Font ft = new Font("맑은 고딕", Font.BOLD, 15);
    static Color color = new Color(150,195,222);///
    
    public NoticeFormGUI(NoticeBoardGUI parent) {
        this.parent = parent;
        setTitle("공지사항 작성");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JLabel titleLabel = new JLabel("제목:");
        titleLabel.setFont(ft);
        topPanel.add(titleLabel);
        titleField = new JTextField();
        titleField.setFont(ft);
        topPanel.add(titleField);
        JLabel writerLabel = new JLabel("작성자:");
        writerLabel.setFont(ft);
        topPanel.add(writerLabel);
        writerField = new JTextField();
        writerField.setFont(ft);
        topPanel.add(writerField);

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

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        JPanel noticeTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel noticeTypeLabel = new JLabel("공지 유형:");
        noticeTypeLabel.setFont(ft);
        noticeTypePanel.add(noticeTypeLabel);
        noticeTypeBox = new JComboBox<>(new String[]{"일반 공지", "필독 공지"});
        noticeTypeBox.setFont(ft);
        noticeTypePanel.add(noticeTypeBox);
        bottomPanel.add(noticeTypePanel, BorderLayout.WEST);

        JButton addButton = new JButton("공지사항 추가");
        addButton.setFont(ft);
        addButton.setBackground(color);
        bottomPanel.add(addButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                int writer_id = Integer.parseInt(writerField.getText());
                String content = contentArea.getText();
                String noticeType = (String) noticeTypeBox.getSelectedItem();
                addNotice1(title, writer_id, content, noticeType);
            }
        });
    }

    private void addNotice1(String title, int writer_id, String content, String noticeType) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Board (title, writer_id, content, write_date, noticeType) VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setString(1, title);
            pstmt.setInt(2, writer_id);
            pstmt.setString(3, content);
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(5, noticeType);
            pstmt.executeUpdate();
            parent.loadNotices();
            dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


	private void addNotice(String title, int writer_id, String content, String noticeType) {
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO Board (title, content, writer_id, noticeType, write_date) VALUES (?, ?, ?, ?, ?)")) {
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, writer_id);
			pstmt.setString(4, noticeType);
			pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "공지사항이 추가되었습니다.", "Success", JOptionPane.INFORMATION_MESSAGE);
			  noticeTypeBox.setFont(ft);parent.loadNotices();
			dispose();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "공지사항 추가에 실패했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}