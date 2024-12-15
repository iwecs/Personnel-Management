package login;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.text.BadLocationException;

// 아이디 찾기 버튼 클릭 후
public class idFind extends JFrame{
	
	public idFind() {
		setTitle("아이디 찾기");
		setLayout(null);
		setBounds(200, 100, 500, 250);
		getContentPane().setBackground(new Color(150, 195, 222));
		Font ft = new Font("맑은 고딕", Font.BOLD, 15);
		
		// 이름 입력란
		JPanel namePanel = new JPanel();
		namePanel.setBackground(new Color(150, 195, 222));
		namePanel.setBounds(0, 30, 500, 30);
		
		JLabel nameLabel = new JLabel("이름 ");
		nameLabel.setFont(ft);
		namePanel.add(nameLabel);

		JTextField nameField = new JTextField(15);
		nameField.setFont(ft);
		namePanel.add(nameField);

		add(namePanel);
		
		// 전화번호 입력란
		JPanel phonePanel = new JPanel();
		phonePanel.setBackground(new Color(150, 195, 222));
		phonePanel.setBounds(-15, 80, 500, 30);
		
		JLabel phoneLabel = new JLabel("전화번호 ");
		phoneLabel.setFont(ft);
		phonePanel.add(phoneLabel);

		JTextField phoneField = new JTextField(15);
		phoneField.setFont(ft);
		phonePanel.add(phoneField);

		add(phonePanel);

		
		// 아이디 찾기 버튼
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(new Color(150, 195, 222));
		btnPanel.setBounds(0, 140, 500, 40);
		JButton btn = new JButton("아이디 찾기");
		btn.setPreferredSize(new Dimension(120, 30));
		btn.setFont(ft);
		btn.setBackground(Color.white);
		btnPanel.add(btn);
		add(btnPanel);
		
		// 버튼 누를 시
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nameField.getText().isEmpty()) { 				// 이름 입력 안했을 시
					JOptionPane.showMessageDialog(btn, "이름을 입력하세요.");
				}else if (phoneField.getText().isEmpty()) {		// 전화번호 입력 안했을 시
					JOptionPane.showMessageDialog(btn, "전화번호를 입력하세요.");
				} else { // 둘 다 입력했을 시
					Connection conn;
					PreparedStatement pt;
					ResultSet rs;
					
					try {
						conn = DBUtil.getConnection();
						
						String sql = "select EMP_ID, EMP_NAME, EMP_PHONE from EMPLOYEE ";
						
						
						pt = conn.prepareStatement(sql);
						rs = pt.executeQuery();
						
						
						while(rs.next()) {
							// 실제 아이디, 이름, 전화번호
							String id = rs.getString("EMP_ID");
							String name = rs.getString("EMP_NAME");
							String phone = rs.getString("EMP_PHONE");
							
							// 입력한 이름, 전화번호
							String inputName = nameField.getText(); 
							String inputPhone = phoneField.getText();
							System.out.println(inputName);
							System.out.println(inputPhone);
							// 1. 실제 이름, 전화번호와 입력한 이름, 전화번호가 같을 시 아이디 출력
							if(inputName.equals(name) && inputPhone.equals(phone)) {
								JOptionPane.showMessageDialog(btn, "아이디 : " + id);
								return;
							}

						}
						// 2. 실제 이름, 전화번호와 입력한 이름, 전화번호가 다를 시
						JOptionPane.showMessageDialog(btn, "아이디가 존재하지 않습니다.");
						
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