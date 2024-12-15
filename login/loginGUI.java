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
		setTitle("�α���");
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

		Font ft = new Font("���� ���", Font.PLAIN, 15);
		Font ft2 = new Font("���� ���", Font.PLAIN, 10); // ��ư, ��й�ȣ ���Խ� ���� ��Ʈ

		// ���̵� �Է¶�
		JPanel idPanel = new JPanel();
		idPanel.setBounds(110, 200, 150, 30);
		idPanel.setBackground(new Color(150, 195, 222));

		JTextField idField = new JTextField("���̵�", 11);
		idField.setFont(ft);
		idField.setForeground(Color.GRAY);
		idPanel.add(idField);
		backPanel.add(idPanel);
		idField.addFocusListener(new FocusAdapter() {

			public void focusGained(FocusEvent e) {
				if (idField.getText().equals("���̵�")) {
					idField.setText("");
					idField.setForeground(Color.BLACK);
				}
			}

			public void focusLost(FocusEvent e) {
				if (idField.getText().isEmpty()) {
					idField.setForeground(Color.GRAY);
					idField.setText("���̵�");
				}
			}
		});

		// ��й�ȣ �Է¶�
		JPanel pwPanel = new JPanel();
		pwPanel.setBounds(110, 230, 150, 30);
		pwPanel.setBackground(new Color(150, 195, 222));
		JPasswordField pwField = new JPasswordField("��й�ȣ", 11);
		pwField.setEchoChar((char) 0);
		pwField.setFont(ft);
		pwField.setForeground(Color.GRAY);

		pwPanel.add(pwField);
		backPanel.add(pwPanel);

		pwField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (new String(pwField.getPassword()).equals("��й�ȣ")) {
					pwField.setText("");
					pwField.setEchoChar('*');
					pwField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (pwField.getPassword().length == 0) {
					pwField.setEchoChar((char) 0); // �ؽ�Ʈ�� ���̵��� ����
					pwField.setForeground(Color.GRAY);
					pwField.setText("��й�ȣ");
				}
			}

		});

		// ���̵� ã�� ��ư
		JPanel btnIdFindPanel = new JPanel();
		btnIdFindPanel.setBackground(new Color(150, 195, 222));
		btnIdFindPanel.setBounds(40, 400, 150, 50);
		JButton btnIdFind = new JButton("���̵� ã��");
		btnIdFind.setPreferredSize(new Dimension(89, 20));
		btnIdFind.setFont(ft2);
		btnIdFind.setBackground(new Color(150, 195, 222));
		btnIdFind.setBorderPainted(false);
		btnIdFindPanel.add(btnIdFind);
		backPanel.add(btnIdFindPanel);

		// ��й�ȣ ã�� ��ư
		JPanel btnPwFindPanel = new JPanel();
		btnPwFindPanel.setBackground(new Color(150, 195, 222));
		btnPwFindPanel.setBounds(160, 400, 150, 50);
		JButton btnPwFind = new JButton("��й�ȣ ã��");
		btnPwFind.setPreferredSize(new Dimension(98, 20));
		btnPwFind.setFont(ft2);
		btnPwFind.setBackground(new Color(150, 195, 222));
		btnPwFind.setBorderPainted(false);
		btnPwFindPanel.add(btnPwFind);
		backPanel.add(btnPwFindPanel);

		// �α��� ��ư
		JPanel btnLoginPanel = new JPanel();
		btnLoginPanel.setBackground(new Color(150, 195, 222));
		btnLoginPanel.setBounds(110, 270, 150, 200);
		JButton btnLogin = new JButton("�α���");
		btnLogin.setPreferredSize(new Dimension(150, 30));
		btnLogin.setFont(ft);
		btnLogin.setBackground(Color.white);
		btnLoginPanel.add(btnLogin);
		backPanel.add(btnLoginPanel);

		// ���̵� ã�� ��ư Ŭ��
		btnIdFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new idFind();
			}
		});

		// ��й�ȣ ã�� ��ư Ŭ��
		btnPwFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new pwFind();
			}
		});

		// �α��� ��ư Ŭ��
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Service ser = new Service();

				if (idField.getText().equals("���̵�")) { // ���̵� �Է� ������ ��
					JOptionPane.showMessageDialog(btnLogin, "���̵� �Է��ϼ���.");
				} else if (pwField.getText().equals("��й�ȣ")) { // ��й�ȣ �Է� ������ ��
					JOptionPane.showMessageDialog(btnLogin, "��й�ȣ�� �Է��ϼ���.");
				} else { // �� �� �Է����� ��
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

						// �Է��� ���̵�� ��й�ȣ
						int inputId = Integer.parseInt(idField.getText());
						String inputPw = pwField.getText();

						while (rs2.next() && rs1.next()) {
							String birth = rs1.getString("EMP_BIRTH");
							// ���� ���̵�� ��й�ȣ, ����
							String authority = rs1.getString("AUTHORITY");
							int id = rs2.getInt("EMP_ID");
							String pw = rs2.getString("EMP_PW");

							// 1. ù �α��� : ��й�ȣ ���� �˾�
							if ((id == inputId) && (pw.equals(inputPw)) && (pw.equals(birth))) {
								ChangePw(id, pw);

								// �α��� ���� �� ��� �ð� ���
								try {

									ser.service_login(id);

								} catch (Exception e1) {

									e1.printStackTrace();
								}
								return;
							}
							// 2. Admin �α���
							else if ((id == inputId) && (pw.equals(inputPw))) {
								dispose();
								// Admin���� User���� Ȯ��
								while (rs1.next()) {
									// Admin Page�� �̵�
									if (authority.equals("ADMIN")) {
										System.out.println("Admin Login");
										new AdminPage("������ ���", 0, 0, 1000, 750); // ������ ������ �̵�
										// �α��� ���� �� ��� �ð� ���
										try {
											ser.service_login(id);

										} catch (Exception e1) {

											e1.printStackTrace();
										}
										return;
									}
									// User Page�� �̵�
									else if (authority.equals("USER")) {
										System.out.println("User Login");
										new UserPage(inputId, "��� ���", 0, 0, 1000, 750);
										// �α��� ���� �� ��� �ð� ���
										try {
											ser.service_login(id);

										} catch (Exception e1) {

											e1.printStackTrace();
										}
										return; // ���� ������ �̵�
									}
								}
							}
						}
						// ���� ���� �� : ���̵� �Ǵ� �н����尡 ��ġ���� �ʽ��ϴ�. Ȯ�� �� �Է����ּ��� �˾� ���
						JOptionPane.showMessageDialog(btnLogin, "���̵� �Ǵ� �н����尡 ��ġ���� �ʽ��ϴ�. Ȯ�� �� �Է����ּ���.");

					} catch (Exception e2) {
						e2.printStackTrace();
					}

				}

			}

			// �ʱ� ��й�ȣ ����
			private void ChangePw(int id, String pw) {
				JFrame j = new JFrame();

				j.setTitle("��й�ȣ ����");
				j.setLayout(null);
				j.setBounds(200, 100, 500, 300);
				j.getContentPane().setBackground(new Color(150, 195, 222));

				Font ft = new Font("���� ���", Font.BOLD, 15);

				// ���� ��й�ȣ �Է¶�
				JPanel nowPwPanel = new JPanel();
				nowPwPanel.setBounds(0, 30, 500, 30);
				nowPwPanel.setBackground(new Color(150, 195, 222));
				JLabel nowPwLabel = new JLabel("���� ��й�ȣ ");
				nowPwLabel.setFont(ft);
				nowPwPanel.add(nowPwLabel);

				JTextField nowPwField = new JTextField(15);
				nowPwField.setFont(ft);
				nowPwPanel.add(nowPwField);

				j.add(nowPwPanel);

				// �� ��й�ȣ �Է¶�
				JPanel newPwPanel1 = new JPanel();
				newPwPanel1.setBounds(10, 90, 500, 30);
				newPwPanel1.setBackground(new Color(150, 195, 222));
				JLabel newPwLabel1 = new JLabel("�� ��й�ȣ");
				newPwLabel1.setFont(ft);
				newPwPanel1.add(newPwLabel1);

				JTextField newPwField1 = new JTextField(15);
				newPwField1.setFont(ft);
				newPwPanel1.add(newPwField1);

				j.add(newPwPanel1);

				// ��й�ȣ ���Խ� �����
				JPanel pwExpPanel = new JPanel();
				pwExpPanel.setBounds(0, 120, 500, 30);
				pwExpPanel.setBackground(new Color(150, 195, 222));
				JLabel pwExpLabel = new JLabel("�ּ� 8�ڿ��� �ִ� 16�� ����, ����, Ư������ ����");
				pwExpLabel.setFont(ft2);
				pwExpLabel.setForeground(Color.GRAY);
				pwExpPanel.add(pwExpLabel);

				j.add(pwExpPanel);

				// �� ��й�ȣ ���Է¶�
				JPanel newPwPanel2 = new JPanel();
				newPwPanel2.setBounds(-10, 150, 500, 30);
				newPwPanel2.setBackground(new Color(150, 195, 222));
				JLabel newPwLabel2 = new JLabel("�� ��й�ȣ Ȯ��");
				newPwLabel2.setFont(ft);
				newPwPanel2.add(newPwLabel2);

				JTextField newPwField2 = new JTextField(15);
				newPwField2.setFont(ft);
				newPwPanel2.add(newPwField2);

				j.add(newPwPanel2);

				// ���� ��ư
				JPanel btnPanel = new JPanel();
				btnPanel.setBounds(0, 200, 500, 40);
				btnPanel.setBackground(new Color(150, 195, 222));
				JButton btn = new JButton("����");
				btn.setFont(ft);
				btn.setBackground(Color.white);
				btnPanel.add(btn);
				j.add(btnPanel);

				// ��ư ���� ��
				btn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// ��ĭ �Է� ������ ��
						if (nowPwField.getText().isEmpty() || newPwField1.getText().isEmpty()
								|| newPwField2.getText().isEmpty()) {
							JOptionPane.showMessageDialog(btn, "��й�ȣ�� �Է��ϼ���.");
							// ���� ��й�ȣ ���� �ٸ��ų�, ����й�ȣ�� ����й�ȣ ���Է� ���� �ٸ� ��
						} else if (!nowPwField.getText().equals(pw)
								|| !newPwField1.getText().equals(newPwField2.getText())) {
							JOptionPane.showMessageDialog(btn, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
						} else if (newPwField1.getText().equals(newPwField2.getText())) {
							// ��й�ȣ ������ �� ����ǥ������ ���״��� Ȯ��
							boolean result = false;

							// ����ǥ���� ���� �ּ� 1��, ���� �ּ� 1��, Ư�� ���� �ּ� 8�� �ִ� 16��
							String regExp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";

							// ����ǥ������ �������� ture, �ƴϸ� false
							result = newPwField1.getText().matches(regExp);

							if (result == false) {
								JOptionPane.showMessageDialog(btn, "��й�ȣ�� ����, ����, Ư�����ڸ� ������ 8�ڿ��� 16���� ���ڿ��� �մϴ�.");
							} else if (result == true) {
								// �� ��й�ȣ�� LOGIN ���̺� EMP_PW�� �����ؾߵ�
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

								JOptionPane.showMessageDialog(btn, "��й�ȣ�� ����Ǿ����ϴ�.");

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
