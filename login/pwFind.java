package login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// 비밀번호 찾기 버튼 누른 후
public class pwFind extends JFrame {

	public pwFind() {
		setTitle("비밀번호 찾기");
		setLayout(null);
		setBounds(200, 100, 500, 250);
		getContentPane().setBackground(new Color(150, 195, 222));

		Font ft = new Font("맑은 고딕", Font.BOLD, 15);

		// 아이디 입력란
		JPanel idPanel = new JPanel();
		idPanel.setBounds(0, 30, 500, 30);
		idPanel.setBackground(new Color(150, 195, 222));

		JLabel idLabel = new JLabel("아이디 ");
		idLabel.setFont(ft);
		idPanel.add(idLabel);

		JTextField idField = new JTextField(15);
		idField.setFont(ft);
		idPanel.add(idField);

		add(idPanel);

		// 전화번호 입력란
		JPanel phonePanel = new JPanel();
		phonePanel.setBounds(-7, 80, 500, 30);
		phonePanel.setBackground(new Color(150, 195, 222));

		JLabel phoneLabel = new JLabel("전화번호 ");
		phoneLabel.setFont(ft);
		phonePanel.add(phoneLabel);

		JTextField phoneField = new JTextField(15);
		phoneField.setFont(ft);
		phonePanel.add(phoneField);

		add(phonePanel);

		// 비밀번호 찾기 버튼
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(new Color(150, 195, 222));
		btnPanel.setBounds(0, 140, 500, 40);
		JButton btn = new JButton("비밀번호 찾기");
		btn.setFont(ft);
		btn.setBackground(Color.white);
		btnPanel.add(btn);
		add(btnPanel);

		// 버튼 누를 시
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (idField.getText().isEmpty()) { // 이름 입력 안했을 시
					JOptionPane.showMessageDialog(btn, "아이디를 입력하세요.");
				} else if (phoneField.getText().isEmpty()) { // 전화번호 입력 안했을 시
					JOptionPane.showMessageDialog(btn, "전화번호를 입력하세요.");
				} else { // 둘 다 입력했을 시
					Connection conn;
					PreparedStatement pt1, pt2;
					ResultSet rs1, rs2;

					try {
						conn = DBUtil.getConnection();
						String sql1 = "select EMP_ID, EMP_PHONE from EMPLOYEE ";

						pt1 = conn.prepareStatement(sql1);
						rs1 = pt1.executeQuery();

						while (rs1.next()) {
							// 실제 아이디, 전화번호
							String id = rs1.getString("EMP_ID");
							String phone = rs1.getString("EMP_PHONE");

							// 입력한 아이디, 전화번호
							String inputId = idField.getText();
							String inputPhone = phoneField.getText();

							// 실제 아이디, 전화번호와 입력한 아이디, 전화번호가 같을 시 비밀번호 팝업
							if (id.equals(inputId) && phone.equals(inputPhone)) {
								String sql2 = "select EMP_PW from LOGIN WHERE EMP_ID = ? ";
								pt2 = conn.prepareStatement(sql2);
								pt2.setInt(1, Integer.parseInt(id));
								rs2 = pt2.executeQuery();

								while (rs2.next()) {
									String pw = rs2.getString("EMP_PW");
									JOptionPane.showMessageDialog(btn, "비밀번호 : " + pw);
									return;
								}
							}
						}
						// 실제 아이디, 전화번호와 입력한 아이디, 전화번호가 다를 시
					  	JOptionPane.showMessageDialog(btn, "일치하지 않은 정보입니다.");

					} catch (Exception e2) { 
						e2.printStackTrace();
					}

				}

			}
		});

		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
