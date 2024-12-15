package login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Admin.AdminPage;
import Commute.Service;
import User.UserPage;

public class loginGUI extends JFrame {
	public loginGUI() {
		setTitle("로그인");
		setLayout(null);
		setBounds(0, 0, 380, 500);

		JPanel backPanel = new JPanel();
		backPanel.setSize(380, 500);
		backPanel.setBackground(new Color(150, 195, 222));
		backPanel.setLayout(null);
		add(backPanel);

		JPanel logoPanel = new JPanel();
		logoPanel.setBounds(115, 80, 140, 130);
		logoPanel.setBackground(new Color(150, 195, 222));
		ImageIcon icon = new ImageIcon("./image/logo2.png");
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(140, 100, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		JLabel logo = new JLabel(changeIcon);
//		logo.setPreferredSize(new Dimension(50, 50));

		logoPanel.add(logo);
		backPanel.add(logoPanel);

		Font ft = new Font("맑은 고딕", Font.PLAIN, 15);
		Font ft2 = new Font("맑은 고딕", Font.PLAIN, 10); // 버튼, 비밀번호 정규식 패턴 폰트

		// 아이디 입력란
		JPanel idPanel = new JPanel();
		idPanel.setBounds(110, 200, 150, 30);
		idPanel.setBackground(new Color(150, 195, 222));

		JTextField idField = new JTextField("아이디", 11);
		idField.setFont(ft);
		idField.setForeground(Color.GRAY);
		idPanel.add(idField);
		backPanel.add(idPanel);
		idField.addFocusListener(new FocusAdapter() {

			public void focusGained(FocusEvent e) {
				if (idField.getText().equals("아이디")) {
					idField.setText("");
					idField.setForeground(Color.BLACK);
				}
			}

			public void focusLost(FocusEvent e) {
				if (idField.getText().isEmpty()) {
					idField.setForeground(Color.GRAY);
					idField.setText("아이디");
				}
			}
		});

		// 비밀번호 입력란
		JPanel pwPanel = new JPanel();
		pwPanel.setBounds(110, 230, 150, 30);
		pwPanel.setBackground(new Color(150, 195, 222));
		JPasswordField pwField = new JPasswordField("비밀번호", 11);
		pwField.setEchoChar((char) 0);
		pwField.setFont(ft);
		pwField.setForeground(Color.GRAY);

		pwPanel.add(pwField);
		backPanel.add(pwPanel);

		pwField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (new String(pwField.getPassword()).equals("비밀번호")) {
					pwField.setText("");
					pwField.setEchoChar('*');
					pwField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (pwField.getPassword().length == 0) {
					pwField.setEchoChar((char) 0); // 텍스트가 보이도록 설정
					pwField.setForeground(Color.GRAY);
					pwField.setText("비밀번호");
				}
			}

		});

		// 아이디 찾기 버튼
		JPanel btnIdFindPanel = new JPanel();
		btnIdFindPanel.setBackground(new Color(150, 195, 222));
		btnIdFindPanel.setBounds(40, 400, 150, 50);
		JButton btnIdFind = new JButton("아이디 찾기");
		btnIdFind.setPreferredSize(new Dimension(89, 20));
		btnIdFind.setFont(ft2);
		btnIdFind.setBackground(new Color(150, 195, 222));
		btnIdFind.setBorderPainted(false);
		btnIdFindPanel.add(btnIdFind);
		backPanel.add(btnIdFindPanel);

		// 비밀번호 찾기 버튼
		JPanel btnPwFindPanel = new JPanel();
		btnPwFindPanel.setBackground(new Color(150, 195, 222));
		btnPwFindPanel.setBounds(160, 400, 150, 50);
		JButton btnPwFind = new JButton("비밀번호 찾기");
		btnPwFind.setPreferredSize(new Dimension(98, 20));
		btnPwFind.setFont(ft2);
		btnPwFind.setBackground(new Color(150, 195, 222));
		btnPwFind.setBorderPainted(false);
		btnPwFindPanel.add(btnPwFind);
		backPanel.add(btnPwFindPanel);

		// 로그인 버튼
		JPanel btnLoginPanel = new JPanel();
		btnLoginPanel.setBackground(new Color(150, 195, 222));
		btnLoginPanel.setBounds(110, 270, 150, 200);
		JButton btnLogin = new JButton("로그인");
		btnLogin.setPreferredSize(new Dimension(150, 30));
		btnLogin.setFont(ft);
		btnLogin.setBackground(Color.white);
		btnLoginPanel.add(btnLogin);
		backPanel.add(btnLoginPanel);

		// 아이디 찾기 버튼 클릭
		btnIdFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new idFind();
			}
		});

		// 비밀번호 찾기 버튼 클릭
		btnPwFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new pwFind();
			}
		});

		// 로그인 버튼 클릭
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Service ser = new Service();

				if (idField.getText().equals("아이디")) { // 아이디 입력 안했을 시
					JOptionPane.showMessageDialog(btnLogin, "아이디를 입력하세요.");
				} else if (pwField.getText().equals("비밀번호")) { // 비밀번호 입력 안했을 시
					JOptionPane.showMessageDialog(btnLogin, "비밀번호를 입력하세요.");
				} else { // 둘 다 입력했을 시
					try {
						Connection conn;
						PreparedStatement pt1, pt2;
						ResultSet rs1, rs2;

						String sql1 = "select EMP_BIRTH, AUTHORITY from EMPLOYEE order by EMP_ID";
						String sql2 = "select * from LOGIN";
						conn = DBUtil.getConnection();

						pt1 = conn.prepareStatement(sql1);
						rs1 = pt1.executeQuery();

						pt2 = conn.prepareStatement(sql2);
						rs2 = pt2.executeQuery();

						// 입력한 아이디와 비밀번호
						int inputId = Integer.parseInt(idField.getText());
						String inputPw = pwField.getText();

						while (rs2.next() && rs1.next()) {
							String birth = rs1.getString("EMP_BIRTH");
							// 실제 아이디와 비밀번호, 권한
							String authority = rs1.getString("AUTHORITY");
							int id = rs2.getInt("EMP_ID");
							String pw = rs2.getString("EMP_PW");

							// 1. 첫 로그인 : 비밀번호 변경 팝업
							if ((id == inputId) && (pw.equals(inputPw)) && (pw.equals(birth))) {
								ChangePw(id, pw);

								// 로그인 성공 시 출근 시간 등록
								try {

									ser.service_login(id);

								} catch (Exception e1) {

									e1.printStackTrace();
								}
								return;
							}
							// 2. Admin 로그인
							else if ((id == inputId) && (pw.equals(inputPw))) {
								dispose();
								// Admin인지 User인지 확인
								while (rs1.next()) {
									// Admin Page로 이동
									if (authority.equals("ADMIN")) {
										System.out.println("Admin Login");
										new AdminPage("관리자 모드", 0, 0, 1000, 750); // 관리자 페이지 이동
										// 로그인 성공 시 출근 시간 등록
										try {
											ser.service_login(id);

										} catch (Exception e1) {

											e1.printStackTrace();
										}
										return;
									}
									// User Page로 이동
									else if (authority.equals("USER")) {
										System.out.println("User Login");
										new UserPage(inputId, "사원 모드", 0, 0, 1000, 750);
										// 로그인 성공 시 출근 시간 등록
										try {
											ser.service_login(id);

										} catch (Exception e1) {

											e1.printStackTrace();
										}
										return; // 유저 페이지 이동
									}
								}
							}
						}
						// 계정 없을 시 : 아이디 또는 패스워드가 일치하지 않습니다. 확인 후 입력해주세요 팝업 출력
						JOptionPane.showMessageDialog(btnLogin, "아이디 또는 패스워드가 일치하지 않습니다. 확인 후 입력해주세요.");

					} catch (Exception e2) {
						e2.printStackTrace();
					}

				}

			}

			// 초기 비밀번호 변경
			private void ChangePw(int id, String pw) {
				JFrame j = new JFrame();

				j.setTitle("비밀번호 변경");
				j.setLayout(null);
				j.setBounds(200, 100, 500, 300);
				j.getContentPane().setBackground(new Color(150, 195, 222));

				Font ft = new Font("맑은 고딕", Font.BOLD, 15);

				// 현재 비밀번호 입력란
				JPanel nowPwPanel = new JPanel();
				nowPwPanel.setBounds(0, 30, 500, 30);
				nowPwPanel.setBackground(new Color(150, 195, 222));
				JLabel nowPwLabel = new JLabel("현재 비밀번호 ");
				nowPwLabel.setFont(ft);
				nowPwPanel.add(nowPwLabel);

				JTextField nowPwField = new JTextField(15);
				nowPwField.setFont(ft);
				nowPwPanel.add(nowPwField);

				j.add(nowPwPanel);

				// 새 비밀번호 입력란
				JPanel newPwPanel1 = new JPanel();
				newPwPanel1.setBounds(10, 90, 500, 30);
				newPwPanel1.setBackground(new Color(150, 195, 222));
				JLabel newPwLabel1 = new JLabel("새 비밀번호");
				newPwLabel1.setFont(ft);
				newPwPanel1.add(newPwLabel1);

				JTextField newPwField1 = new JTextField(15);
				newPwField1.setFont(ft);
				newPwPanel1.add(newPwField1);

				j.add(newPwPanel1);

				// 비밀번호 정규식 설명란
				JPanel pwExpPanel = new JPanel();
				pwExpPanel.setBounds(0, 120, 500, 30);
				pwExpPanel.setBackground(new Color(150, 195, 222));
				JLabel pwExpLabel = new JLabel("최소 8자에서 최대 16자 숫자, 문자, 특수문자 포함");
				pwExpLabel.setFont(ft2);
				pwExpLabel.setForeground(Color.GRAY);
				pwExpPanel.add(pwExpLabel);

				j.add(pwExpPanel);

				// 새 비밀번호 재입력란
				JPanel newPwPanel2 = new JPanel();
				newPwPanel2.setBounds(-10, 150, 500, 30);
				newPwPanel2.setBackground(new Color(150, 195, 222));
				JLabel newPwLabel2 = new JLabel("새 비밀번호 확인");
				newPwLabel2.setFont(ft);
				newPwPanel2.add(newPwLabel2);

				JTextField newPwField2 = new JTextField(15);
				newPwField2.setFont(ft);
				newPwPanel2.add(newPwField2);

				j.add(newPwPanel2);

				// 변경 버튼
				JPanel btnPanel = new JPanel();
				btnPanel.setBounds(0, 200, 500, 40);
				btnPanel.setBackground(new Color(150, 195, 222));
				JButton btn = new JButton("변경");
				btn.setFont(ft);
				btn.setBackground(Color.white);
				btnPanel.add(btn);
				j.add(btnPanel);

				// 버튼 누를 시
				btn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// 빈칸 입력 안했을 시
						if (nowPwField.getText().isEmpty() || newPwField1.getText().isEmpty()
								|| newPwField2.getText().isEmpty()) {
							JOptionPane.showMessageDialog(btn, "비밀번호를 입력하세요.");
							// 현재 비밀번호 값이 다르거나, 새비밀번호와 새비밀번호 재입력 값이 다를 시
						} else if (!nowPwField.getText().equals(pw)
								|| !newPwField1.getText().equals(newPwField2.getText())) {
							JOptionPane.showMessageDialog(btn, "비밀번호가 일치하지 않습니다.");
						} else if (newPwField1.getText().equals(newPwField2.getText())) {
							// 비밀번호 변경할 때 정규표현식을 지켰는지 확인
							boolean result = false;

							// 정규표현식 숫자 최소 1개, 문자 최소 1개, 특수 문자 최소 8자 최대 16자
							String regExp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";

							// 정규표현식을 지켰으면 ture, 아니면 false
							result = newPwField1.getText().matches(regExp);

							if (result == false) {
								JOptionPane.showMessageDialog(btn, "비밀번호는 숫자, 문자, 특수문자를 포함한 8자에서 16자의 문자여야 합니다.");
							} else if (result == true) {
								// 새 비밀번호를 LOGIN 테이블 EMP_PW에 저장해야됨
								Connection conn;
								PreparedStatement pt;
								ResultSet rs;
								conn = DBUtil.getConnection();

								try {
									String sql = "update LOGIN set EMP_PW = ? WHERE EMP_ID = ?";
									pt = conn.prepareStatement(sql);
									pt.setString(1, newPwField1.getText());
									pt.setInt(2, id);
									pt.executeUpdate();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}

								JOptionPane.showMessageDialog(btn, "비밀번호가 변경되었습니다.");

								j.dispose();
							}

						}
					}
				});

				j.setVisible(true);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			}

		});

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    setLocation(screenSize.width / 2 - getSize().width / 2, screenSize.height / 2 - getSize().height / 2);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
