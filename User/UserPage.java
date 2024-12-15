package User;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Admin.AdminPage;
import AnnualPaidLeave.AnnualPDAO;
import AnnualPaidLeave.AnnualPGUI;
import AnnualPaidLeave.appFormVO;
import BusinessTrip.BusinessTripDAO;
import BusinessTrip.BusinessTripGUI;
import BusinessTrip.BusinessTripVO;
import Commute.CommuteDAO;
import Commute.CommuteVO;
import Commute.Service;
import login.loginGUI;
import notice_user.NoticeBoardGUI;
import report.ReportBoard;

public class UserPage extends JFrame {

	static JPanel mMenuPanel, mPagePanel, mMenubarPanel;
	UserController controller = new UserController();
	private ArrayList<CommuteVO> commList = new ArrayList<CommuteVO>();
	private ArrayList<appFormVO> appList = new ArrayList<appFormVO>();
	private ArrayList<BusinessTripVO> list = new ArrayList<BusinessTripVO>();
	private JTable table;
	private DefaultTableModel model;
	private JPanel contentPane;
	private JTextField titleField, idField, nameField, contentField, useDateField;
	private JLabel tiLabel, idLabel, idlabel, namelabel, nameLabel;
	private JButton btn, btn1, btn2, btn3;
	private int inputId;
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	static Font ft = new Font("���� ���", Font.BOLD, 15);
	static Color color = new Color(150, 195, 222);
	static Color Backgroundcolor = Color.white;

	public UserPage(int inputId, String title, int x, int y, int width, int height) {

		this.inputId = inputId;
		initContainer(title, x, y, width, height);
		initMenu();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	private void initContainer(String title, int x, int y, int width, int height) {

		setLayout(null);
		setTitle(title);
		setBounds(x, y, width, height);
		mPagePanel = new JPanel();
		mPagePanel.setLayout(null);
		mPagePanel.setBounds(0, 150, width, 450);
		mPagePanel.setBackground(Backgroundcolor);
		add(mPagePanel);

		information();
		line();
		logo();
		logout();
	}

	private void logo() {

		JPanel logoPanel = new JPanel();
		logoPanel.setBackground(Color.white);
		logoPanel.setBounds(30, 30, 140, 100);

		// ���� �̹��� �ε�
		ImageIcon logoIcon = new ImageIcon("./image/logo.png");

		// ���ϴ� ũ��� �̹��� ũ�� ����
		Image originalImage = logoIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(140, 100, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel logoLabel = new JLabel(scaledIcon);
		logoPanel.add(logoLabel);
		add(logoPanel);

	}

	private void logout() {
		JPanel logoutPanel = new JPanel();
		logoutPanel.setLayout(null);
		logoutPanel.setBounds(800, 30, 100, 30);
		logoutPanel.setBackground(Color.white);
		ImageIcon logoutIcon = new ImageIcon("./image/logout.png");

		// ���ϴ� ũ��� �̹��� ũ�� ����
		Image originalImage = logoutIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(100, 30, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		// logoutbtn = new JButton(scaledIcon);
		JButton logoutbtn = new JButton("�α׾ƿ�");
		logoutbtn.setFont(ft);
		logoutbtn.setBackground(new Color(150, 195, 222));
		// logoutbtn.setBorderPainted(false);
		logoutbtn.setSize(100, 30);
		logoutPanel.add(logoutbtn);
		add(logoutPanel);

		logoutbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Service ser = new Service();
				try {
					ser.service_logout(inputId);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
	}

	private void information() {
		JPanel inforPanel = new JPanel();
		inforPanel.setBounds(0, 660, 820, 100);
		inforPanel.setBackground(Color.white);

		Font ft = new Font("���� ���", Font.PLAIN, 12);
		JLabel inforLabel1 = new JLabel(
				"Anchae                                  ��ǥ�̻� : �踸�� 	               	 ����� ��Ϲ�ȣ : 123-45-67891 			                             �̸��� : anchae@an.net");
		inforLabel1.setFont(ft);
		inforLabel1.setForeground(Color.LIGHT_GRAY);
		inforPanel.add(inforLabel1);

		JLabel inforLabel2 = new JLabel(
				"�ּ� : (06626) ���� ���ʱ� ���ʴ�α�74�� 45, 3��(���ʵ�, ������Ÿ��)	                           ����ó : 02-1234-5678                      Fax : 02-1234-5679");
		inforLabel2.setFont(ft);
		inforLabel2.setForeground(Color.LIGHT_GRAY);
		inforPanel.add(inforLabel2);

		add(inforPanel);
	}

	private void line() {
		JPanel linePanel = new JPanel();
		linePanel.setBounds(0, 590, 1000, 110);
		linePanel.setBackground(Color.pink);

		// ���� �̹��� �ε�
		ImageIcon lineIcon = new ImageIcon("./image/Line.png");

		// ���ϴ� ũ��� �̹��� ũ�� ����
		Image originalImage = lineIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(1000, 110, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel llineLabel = new JLabel(scaledIcon);

		linePanel.add(llineLabel);
		add(linePanel);
	}

	private void initMenu() {

		JPanel barpanel = new JPanel(new BorderLayout());
		getContentPane().setBackground(Color.white);
		JMenuBar menubar = new JMenuBar();
		menubar.setBackground(new Color(150, 195, 222));

		Font font = new Font("���� ���", Font.BOLD, 28);

		logo();

		// 1. �������
		JMenu menu01 = new JMenu("��� ����");
		menu01.setFont(font);

		// ������� Ŭ�� �̺�Ʈ
		menu01.addMouseListener((MouseListener) new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				mPagePanel.removeAll();
				controller.info(inputId);
				mPagePanel.add(UserController.findPanel);
				mPagePanel.revalidate();
				mPagePanel.repaint();
			}
		});

		// 2. �޿� ����
		JMenu menu02 = new JMenu("�޿� ����");
		menu02.setFont(font);
		menu02.addMouseListener((MouseListener) new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mPagePanel.removeAll();
				SalaryPage salaryPage = new SalaryPage(inputId);
				mPagePanel.add(salaryPage.getTablePanel());
				mPagePanel.add(salaryPage.getTravelDetailsPanel());

				mPagePanel.revalidate();
				mPagePanel.repaint();
			}
		});

		// 3. ���°���
		JMenu menu03 = new JMenu("���� ����");

		JMenuItem item05 = new JMenuItem("����� ��ȸ");
		JMenuItem item06 = new JMenuItem("���� ��û/��ȸ");
		JMenuItem item07 = new JMenuItem("���� ��û/��ȸ");

		menu03.setFont(font);
		// ����� ��ȸ Ŭ�� �̺�Ʈ
		item05.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				mPagePanel.removeAll();

				commList = CommuteDAO.selectCommute(inputId);

				model = new DefaultTableModel();

				// �� �̸�
				model.addColumn("���");
				model.addColumn("�����");
				model.addColumn("��¥");
				model.addColumn("����");
				model.addColumn("���");

				// ���̺� ���� �� �� ����
				table = new JTable(model);

				for (CommuteVO temp : commList) {

					// ���� ���� ��������
//					int leaveCount = leaveCount(); // ������ ���� ������ �������� �޼��� ȣ��

					// �� �྿ �߰��ϱ�
					model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getMONTH_DATE(),
							temp.getComm(), temp.getEnd() });

				}

				// ��ũ�� ������ ���̺� �г� ����
				JScrollPane scroll = new JScrollPane(table);
				scroll.setBounds(20, 0, 940, 350); // ���̺� ��ġ �� ũ�� ����
				add(scroll);

				JPanel panel = new JPanel();
//				add(panel, BorderLayout.SOUTH);

				btn1 = new JButton("Ȯ��");
				btn1.setBounds(400, 420, 100, 30); // ��ư ��ġ �� ũ�� ����
				btn1.setBackground(color);

				panel.add(btn1);

				mPagePanel.add(btn1);
				mPagePanel.add(scroll);

				btn1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});

				refreshTable_Comm(inputId);

				mPagePanel.revalidate();
				mPagePanel.repaint();

			}
		});

		// ���� Ŭ�� �̺�Ʈ
		item06.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				mPagePanel.removeAll();

				list = BusinessTripDAO.select(inputId);

				model = new DefaultTableModel();

				// �� �̸�
				model.addColumn("no");
				model.addColumn("�� ��");
				model.addColumn("��� �̸�");
				model.addColumn("��û ����");
				model.addColumn("��û ��¥");
				model.addColumn("���� ����");

				// ���̺� ���� �� �� ����
				table = new JTable(model);
				JTableHeader header = table.getTableHeader();
				header.setReorderingAllowed(false);
				header.setBackground(Color.LIGHT_GRAY);
				for (BusinessTripVO temp : list) {

					// �� �྿ �߰��ϱ�
					model.addRow(new Object[] { temp.getNum(), temp.getEmp_id(), temp.getEmp_name(), temp.getPlace(),
							temp.getWHATDAY(), temp.getProcessing() });
				}

				// ��ũ�� �� ���� ���̺� �г� ����
				JScrollPane scroll = new JScrollPane(table);
				scroll.setBounds(20, 0, 940, 400); // ���̺� ��ġ �� ũ�� ����
				mPagePanel.add(scroll);

				// ��ư ���� �� �߰�
				JButton btnRefresh = new JButton("���ΰ�ħ");
				btnRefresh.setBounds(350, 420, 100, 30); // ��ư ��ġ �� ũ�� ����
				btnRefresh.setBackground(new Color(150, 195, 222));
				mPagePanel.add(btnRefresh); // �гο� ��ư �߰�

				JButton btnApply = new JButton("��û�ϱ�");
				btnApply.setBounds(450, 420, 100, 30); // ��ư ��ġ �� ũ�� ����
				btnApply.setBackground(color);
				mPagePanel.add(btnApply); // �гο� ��ư �߰�

				JButton btnDelete = new JButton("�����ϱ�");
				btnDelete.setBounds(550, 420, 100, 30); // ��ư ��ġ �� ũ�� ����
				btnDelete.setBackground(color);
				mPagePanel.add(btnDelete);

				// ���ΰ�ħ ��ư �׼�
				btnRefresh.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						repaint();

					}

				});

				btnApply.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						JFrame frame = new JFrame();
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setBounds(100, 0, 1000, 745);
						contentPane = new JPanel();
						contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

						frame.setContentPane(contentPane);
						contentPane.setLayout(null);

						JPanel panel = new JPanel();
						panel.setBounds(223, 10, 516, 686);
						panel.setBorder(new LineBorder(Color.BLACK));
						panel.setBackground(Color.WHITE);
						contentPane.add(panel);
						panel.setLayout(null);

						Font mainFt = new Font("����", Font.BOLD, 30);
						Font ft = new Font("����", Font.BOLD, 14);

						JLabel titleLabel = new JLabel("���� ��û��");
						titleLabel.setBounds(176, 25, 188, 100);
						titleLabel.setFont(mainFt);
						panel.add(titleLabel, BorderLayout.CENTER);

						String id = String.valueOf(inputId);
						idlabel = new JLabel(id);
						idlabel.setBounds(213, 157, 116, 21);
						panel.add(idlabel);

						idLabel = new JLabel("�� ��");
						idLabel.setBounds(144, 160, 57, 15);
						panel.add(idLabel);

						String name = BusinessTripDAO.sreach(inputId);
						namelabel = new JLabel(name);
						namelabel.setBounds(213, 202, 116, 21);
						panel.add(namelabel);
						
//						idField = new JTextField();
//						idField.setBounds(213, 157, 116, 21);
//						panel.add(idField);
//						idField.setColumns(10);
//
//						idLabel = new JLabel("�� ��");
//						idLabel.setBounds(144, 160, 57, 15);
//						panel.add(idLabel);
//
//						nameField = new JTextField();
//						nameField.setBounds(213, 202, 116, 21);
//						panel.add(nameField);
//						nameField.setColumns(10);
//
						nameLabel = new JLabel("�� ��");
						nameLabel.setBounds(144, 205, 57, 15);
						panel.add(nameLabel);

						String[] box = { "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8" };
						JComboBox comboBox = new JComboBox(box);
						comboBox.setBounds(213, 242, 116, 23);
						panel.add(comboBox);

						JLabel comboLabel = new JLabel("������");
						comboLabel.setBounds(145, 246, 57, 15);
						panel.add(comboLabel);

						JLabel useDateLabel = new JLabel("���� ��¥");
						useDateLabel.setBounds(145, 287, 57, 15);
						panel.add(useDateLabel);

						useDateField = new JTextField(4);
						useDateField.setBounds(214, 284, 116, 21);
						panel.add(useDateField);
						useDateField.setColumns(4);

						JButton btn1 = new JButton("����");
						btn1.setBounds(147, 512, 76, 50);
						btn1.setPreferredSize(new Dimension(90, 30));
						btn1.setFont(ft);
						panel.add(btn1);

						JButton btn2 = new JButton("���");
						btn2.setBounds(275, 512, 76, 50);
						btn2.setPreferredSize(new Dimension(90, 30));
						btn2.setFont(ft);
						panel.add(btn2);

						frame.add(panel);

						frame.setVisible(true);
						panel.repaint();


						btn1.addActionListener(new ActionListener() {

							 @Override
							public void actionPerformed(ActionEvent e) {

							if (idlabel != null) {
							int emp_id = inputId;
							String name = BusinessTripDAO.sreach(inputId);
							String type = (String) comboBox.getSelectedItem();
									 
							String usedate = useDateField.getText();
									 
							 if (isValidDateFormat(usedate)) {
								 if (isAfterCurrentDate(usedate)) {
								 JOptionPane.showMessageDialog(null, "����Ǿ����ϴ�.");
								 } else {
								 JOptionPane.showMessageDialog(null, "���� ��¥ ������ ��¥�� �Է� �����մϴ�.");
								 }
								 } else {
								 JOptionPane.showMessageDialog(null, "�ùٸ� ��¥ ������ �ƴմϴ�. yyyy-MM-dd �������� �Է����ּ���.");
								 }
							 // ���� ���ǹ����� ���ϵ��� �ʾҴٸ� ��ȿ�� ��¥ �����̹Ƿ� ������ �ڵ� ����
							 BusinessTripVO vo = new BusinessTripVO(emp_id, name, type, usedate);
							 BusinessTripDAO.insert(vo);
							 }
									 
								
									 
									 
								// ��¥
								System.out.println("��¥ : " + useDateField.getText());

								// ���
								System.out.println("��� : " + idField.getText());

								// �̸�
								System.out.println("�̸� : " + nameField.getText());

								// ��û���� : �޺��ڽ� ���� String������ ��������
								System.out.println("������ : " + comboBox.getSelectedItem().toString());

								// �߰� �� ���ΰ�ħ
								refreshTable_Business(inputId);

								dispose();
								initMenu();

							}

						});

						// ���
						btn2.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								initMenu();

							}

						});
					}

				});

				btnDelete.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// ������ ���� �ε��� ��������
						int index = table.getSelectedRow();

						// ���� ���õ� ���
						if (index >= 0) {
							// ���̺� �𵨿��� �� ����
							model.removeRow(index);

							// ��Ͽ��� �ش� ��ü ����
							list.remove(index);

							// ���̺� ǥ�� ����
							table.revalidate();
							table.repaint();

							// �����ͺ��̽����� �� �׸��� �����ϴ� DAO �޼��� ȣ��
							BusinessTripDAO.delete(index);

							refreshTable_Business(inputId);

						} else {
							// ���� ���õ��� ���� ��� ����ڿ��� �޽��� ǥ��
							JOptionPane.showMessageDialog(null, "������ ���� �����ϼ���.", "���õ� �� ����", JOptionPane.ERROR_MESSAGE);
						}
					}

				});

				mPagePanel.revalidate();
				mPagePanel.repaint();

			}
		});

		// ���� Ŭ�� �̺�Ʈ
		item07.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				mPagePanel.removeAll();

				commList = CommuteDAO.leaveCount(inputId);

				// ���̺� �� ����
				model = new DefaultTableModel();

				// �� �̸�
				model.addColumn("NO");
				model.addColumn("������");
				model.addColumn("��û ����");
				model.addColumn("��û ��¥");
				model.addColumn("�� ��");
				model.addColumn("��� �̸�");
				model.addColumn("�ۼ���");
				model.addColumn("�������");
				model.addColumn("����");

				// ���̺� ���� �� �� ����
				table = new JTable(model);
				JTableHeader header = table.getTableHeader();
				header.setReorderingAllowed(false);
				header.setBackground(Color.LIGHT_GRAY);
//				for (CommuteVO temp2 : commList) {
//
//					for (appFormVO temp : appList) {
//
//						if (temp2.getEMP_ID() == temp.getEmp_id()) {
//
//							// �� �྿ �߰��ϱ�
//							model.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(),
//									temp.getUseDate(), temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(),
//									temp.getProcessing(), temp2.getLeaveCount() });
//						}
//					}
//				}

				for (appFormVO temp : appList) {

					// �� �྿ �߰��ϱ�
					model.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(), temp.getUseDate(),
							temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(), temp.getProcessing(),
							temp.getLeave() });

				}

				// ��ũ�� �� ���� ���̺� �г� ����
				JScrollPane scroll = new JScrollPane(table);
				scroll.setBounds(30, 0, 940, 400); // ���̺� ��ġ �� ũ�� ����
				mPagePanel.add(scroll);

				// ��ư ���� �� �߰�
				JButton btnRefresh = new JButton("���ΰ�ħ");
				btnRefresh.setBounds(350, 420, 100, 30); // ��ư ��ġ �� ũ�� ����
				btnRefresh.setBackground(color);
				mPagePanel.add(btnRefresh); // �гο� ��ư �߰�

				JButton btnApply = new JButton("��û�ϱ�");
				btnApply.setBounds(450, 420, 100, 30); // ��ư ��ġ �� ũ�� ����
				btnApply.setBackground(color);
				mPagePanel.add(btnApply); // �гο� ��ư �߰�

				JButton btnDelete = new JButton("�����ϱ�");
				btnDelete.setBounds(550, 420, 100, 30); // ��ư ��ġ �� ũ�� ����
				btnDelete.setBackground(color);
				mPagePanel.add(btnDelete);

				// ���ΰ�ħ ��ư �׼�
				btnRefresh.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						repaint();

					}

				});

				btnApply.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						setVisible(true);

						setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						setBounds(100, 100, 1000, 745);
						setBackground(Color.WHITE);
						contentPane = new JPanel();
						contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

						setContentPane(contentPane);
						contentPane.setLayout(null);

						JPanel panel = new JPanel();
						panel.setBounds(223, 10, 516, 686);
						panel.setBorder(new LineBorder(Color.BLACK));
						panel.setBackground(Color.WHITE);
						contentPane.add(panel);
						panel.setLayout(null);

						Font mainFt = new Font("����", Font.BOLD, 28);
						Font ft = new Font("����", Font.BOLD, 14);

						JLabel titleLabel = new JLabel("���� ��û��");
						titleLabel.setBounds(176, 25, 188, 100);
						titleLabel.setFont(mainFt);
						panel.add(titleLabel, BorderLayout.CENTER);

						titleField = new JTextField();
						titleField.setBounds(181, 93, 224, 21);
						panel.add(titleField);
						titleField.setColumns(10);

						tiLabel = new JLabel("�� ��");
						tiLabel.setBounds(112, 96, 57, 15);
						panel.add(tiLabel);

						
						idlabel = new JLabel(String.valueOf(inputId));
						idlabel.setBounds(181, 138, 116, 21);
						panel.add(idlabel);

						nameLabel = new JLabel("�����");
						nameLabel.setBounds(113, 183, 57, 15);
						panel.add(nameLabel);

						String name = BusinessTripDAO.sreach(inputId);
						namelabel = new JLabel(name);
						namelabel.setBounds(181, 180, 116, 21);
						panel.add(namelabel);
						
						
						idLabel = new JLabel("�� ��");
						idLabel.setBounds(112, 141, 57, 15);
						panel.add(idLabel);

//						idField = new JTextField();
//						idField.setBounds(181, 138, 116, 21);
//						panel.add(idField);
//						idField.setColumns(10);

						nameLabel = new JLabel("�� ��");
						nameLabel.setBounds(113, 183, 57, 15);
						panel.add(nameLabel);
//
//						nameField = new JTextField();
//						nameField.setBounds(181, 180, 116, 21);
//						panel.add(nameField);
//						nameField.setColumns(10);

						String[] box = { "����", "��������", "���Ĺ���" };
						JComboBox comboBox = new JComboBox(box);
						comboBox.setBounds(181, 222, 116, 23);
						panel.add(comboBox);

						JLabel comboLabel = new JLabel("��� ����");
						comboLabel.setBounds(113, 226, 57, 15);
						panel.add(comboLabel);

						JLabel useDateLabel = new JLabel("��� ��¥");
						useDateLabel.setBounds(112, 271, 57, 15);
						panel.add(useDateLabel);

						useDateField = new JTextField(4);
						useDateField.setBounds(181, 268, 116, 21);
						panel.add(useDateField);
						useDateField.setColumns(4);

						JLabel contentLabel = new JLabel("��� ����");
						contentLabel.setBounds(113, 314, 57, 15);
						panel.add(contentLabel);

						contentField = new JTextField();
						contentField.setBounds(113, 339, 292, 59);
						panel.add(contentField);
						contentField.setColumns(10);

						JButton btn1 = new JButton("����");
						btn1.setBounds(147, 512, 76, 50);
						btn1.setPreferredSize(new Dimension(90, 30));
						btn1.setFont(ft);
						panel.add(btn1);

						JButton btn2 = new JButton("���");
						btn2.setBounds(275, 512, 76, 50);
						btn2.setPreferredSize(new Dimension(90, 30));
						btn2.setFont(ft);
						panel.add(btn2);

						panel.repaint();

						// Ȯ��
						btn1.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								
								if (idlabel != null) {
									int emp_id = inputId;
								    String name = BusinessTripDAO.sreach(inputId);

									String title = titleField.getText();
									String useDate = useDateField.getText();
									String type = (String) comboBox.getSelectedItem();
									String content = contentField.getText();

									appFormVO vo = new appFormVO(title, type, useDate, emp_id, name, content);
									AnnualPDAO.insert(vo);
								
								} else {
									JOptionPane.showMessageDialog(btn1, "��ĭ�� �ֽ��ϴ�");
									return;
								}

								// �Է� ��ȿ�� �˻� �� ���� ���
								if (titleField.getText().isEmpty()) {
									JOptionPane.showMessageDialog(btn2, "������ �Է��ϼ���.");
								} else if (idField.getText().isEmpty()) {
									JOptionPane.showMessageDialog(btn2, "����� �Է��ϼ���.");
								} else if (nameField.getText().isEmpty()) {
									JOptionPane.showMessageDialog(btn2, "�̸��� �Է��ϼ���.");
								} else if (contentField.getText().isEmpty()) {
									JOptionPane.showMessageDialog(btn2, "��û ������ �Է��ϼ���.");
								} else {
									JOptionPane.showMessageDialog(btn2, "����Ǿ����ϴ�.");
								}

								// ����
								System.out.println("���� : " + titleField.getText());

								// ��¥
								System.out.println("��¥ : " + useDateField.getText());

								// ���
								System.out.println("��� : " + idField.getText());

								// �̸�
								System.out.println("�̸� : " + nameField.getText());

								// ��û���� : �޺��ڽ� ���� String������ ��������
								System.out.println("��û ���� : " + comboBox.getSelectedItem().toString());

								// ��û ����
								System.out.println("��û ���� : " + contentField.getText());

								// �߰� �� ���ΰ�ħ
								refreshTable_Annual(inputId);

								JOptionPane.showMessageDialog(btn, "��û�� �Ϸ� �Ǿ����ϴ�.");

								dispose();
								initMenu();

							}

						});

						// ���
						btn2.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								initMenu();

							}

						});

//						newFrame.setVisible(true);
					}

				});

				btnDelete.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// ������ ���� �ε��� ��������
						int index = table.getSelectedRow();

						// ���� ���õ� ���
						if (index >= 0) {
							// ���̺� �𵨿��� �� ����
							model.removeRow(index);

							// ��Ͽ��� �ش� ��ü ����
							appList.remove(index);

							// ���̺� ǥ�� ����
							table.revalidate();
							table.repaint();

							// �����ͺ��̽����� �� �׸��� �����ϴ� DAO �޼��� ȣ��
							AnnualPDAO.delete(index);

							refreshTable_Annual(inputId);

						} else {
							// ���� ���õ��� ���� ��� ����ڿ��� �޽��� ǥ��
							JOptionPane.showMessageDialog(null, "������ ���� �����ϼ���.", "���õ� �� ����", JOptionPane.ERROR_MESSAGE);
						}

					}

				});

				mPagePanel.revalidate();
				mPagePanel.repaint();
			}
		});

		menu03.add(item05);
		menu03.add(item06);
		menu03.add(item07);


		// 4. ���� ����
		JMenu menu04 = new JMenu("���� ����");
		JMenuItem item08 = new JMenuItem("���� ����");
		JMenuItem item09 = new JMenuItem("��������(�Խñ�) ��ȸ");

				menu04.setFont(font);
				// ���� ���� Ŭ�� �̺�Ʈ
				item08.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						SwingUtilities.invokeLater(() -> new ReportBoard().setVisible(true)); /// ����
					}
				});///


		// �������� ��ȸ Ŭ�� �̺�Ʈ
		item09.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new NoticeBoardGUI().setVisible(true));

			}
		});
		menu04.add(item08);
		menu04.add(item09);

		// �޴����� �޴� �ٿ� �߰�
		menubar.add(menu01);
		menubar.add(menu02);
		menubar.add(menu03);
		menubar.add(menu04);

		mMenubarPanel = new JPanel();
		mMenubarPanel.setBounds(200, 80, 550, 50);
		mMenubarPanel.setBackground(Color.white);
		mMenubarPanel.add(menubar);

		add(mMenubarPanel);

	}

//	private void refreshTable() {
//		// �����͸� ���ΰ�ħ�ϰ� ���̺� �𵨿� ���ο� �����͸� ������.
//
//		appList = AnnualPDAO.select();
//
//		// ��� ���� ������.
//		model.setRowCount(0);
//
//		// �ٽ� ������
//		for (appFormVO vo : appList) {
//			// �� �྿ �߰��ϱ�
//			model.addRow(new Object[] { vo.getTitle(), vo.getType(), vo.getUseDate(), vo.getEmp_id(), vo.getEmp_name(),
//					vo.getContent() });
//		}
//
//	}
	// �����
	private void refreshTable_Comm(int inputId) {
		commList = CommuteDAO.selectCommute(inputId);

		for (CommuteVO temp : commList) {

			// �� �྿ �߰��ϱ�
			model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getMONTH_DATE(), temp.getComm(),
					temp.getEnd() });

		}
	}

	// ����
	private void refreshTable_Business(int inputId) {

		list = BusinessTripDAO.select(inputId);

		model.setRowCount(0);

		for (BusinessTripVO temp : list) {

			// �� �྿ �߰��ϱ�
			model.addRow(new Object[] { temp.getNum(), temp.getEmp_id(), temp.getEmp_name(), temp.getPlace(),
					temp.getWHATDAY(), temp.getProcessing() });
		}

	}

	// ����
	private void refreshTable_Annual(int inputId) {

		// �����͸� ���ΰ�ħ�ϰ� ���̺� �𵨿� ���ο� �����͸� ������.
		commList = CommuteDAO.leaveCount(inputId);

		// ��� ���� ������.
		model.setRowCount(0);

		for (appFormVO temp : appList) {

			// �� �྿ �߰��ϱ�
			model.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(), temp.getUseDate(),
					temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(), temp.getProcessing(), temp.getLeave() });
		}

	}
	public static boolean isValidDateFormat(String dateStr) {
		 // ��¥ ������ ��Ÿ���� ���� ǥ����
		 String regex = "\\d{4}-\\d{2}-\\d{2}";

		 // ���� ǥ������ ����Ͽ� ������ Ȯ��
		 return dateStr.matches(regex);
		 }

		 public static boolean isAfterCurrentDate(String dateStr) {
		 try {
		 LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
		 LocalDate today = LocalDate.now();

		 // ���� ��¥ �����̸� true ��ȯ
		 return !date.isBefore(today);
		 } catch (DateTimeParseException e) {
		 return false;
		 }
		 }

	public static void main(String[] args) {
		// UserPage up = new UserPage("��� ���", 0,0,1000,750);

	}
}