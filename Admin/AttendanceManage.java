package Admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import AnnualPaidLeave.AnnualPDAO;
import AnnualPaidLeave.AnnualPGUI;
import AnnualPaidLeave.AnnualP_AdminGUI;
import AnnualPaidLeave.appFormVO;
import BusinessTrip.BusinessTriPAdminGUI;
import BusinessTrip.BusinessTripDAO;
import BusinessTrip.BusinessTripGUI;
import BusinessTrip.BusinessTripVO;
import Commute.CommuteAdminGUI;
import Commute.CommuteDAO;
import Commute.CommuteGUI;
import Commute.CommuteVO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AttendanceManage extends JPanel {

	private ArrayList<CommuteVO> list = new ArrayList<CommuteVO>();
	private ArrayList<BusinessTripVO> businessList = new ArrayList<BusinessTripVO>();
	private ArrayList<appFormVO> appList = new ArrayList<appFormVO>();
	private ArrayList<CommuteVO> leaveList = new ArrayList<CommuteVO>();
	private JTable table, table3;
	private DefaultTableModel model, model2, model3;
	private static JPanel tablePanel, buttonPanel;
	private JLabel tiLabel, idLabel, idlabel, nameLabel, namelabel;
	private JButton btn, btn1, btn2, btn3, btn4, button1, button2, button3;
	static Color color = new Color(150, 195, 222);

	public AttendanceManage(JPanel panel) {

		setPreferredSize(panel.getSize());
		setLayout(null); // ���̾ƿ� �Ŵ����� null�� ����
		setBackground(Color.white);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1, 10, 40));
		buttonPanel.setBackground(Color.white);
		buttonPanel.setBounds(50, 50, 170, 300);

		button1 = new JButton("����� �ν�");
		button2 = new JButton("����");
		button3 = new JButton("����");
		
		button1.setBackground(AdminPage.color);
		button2.setBackground(AdminPage.color);
		button3.setBackground(AdminPage.color);
		
		button1.setFont(AdminPage.ft);
		button2.setFont(AdminPage.ft);
		button3.setFont(AdminPage.ft);

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				removeAll();

				buttonPanel.add(button1);
				buttonPanel.add(button2);
				buttonPanel.add(button3);

				add(buttonPanel);

				list = CommuteDAO.selectAdminCommute();

				// ���̺� �� ����
				model = new DefaultTableModel();

				model.setRowCount(0); // ���� �� ����

				// �� �̸�
				model.addColumn("���");
				model.addColumn("�����");
				model.addColumn("��¥");
				model.addColumn("����");
				model.addColumn("���");
				model.addColumn("����");

				// CommuteVO Ŭ������ �� �྿ �߰��ϱ�
				for (CommuteVO temp : list) {
					model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getMONTH_DATE(),
							temp.getComm(), temp.getEnd(), temp.getLeave() });
				}

				System.out.println("CommuteGUI ����");

				JFrame frame = new JFrame();
				frame.setBounds(400, 400, 1000, 750);
				frame.setLocationRelativeTo(null);

				// ���̺� ���� �� �� ����
				table = new JTable(model);
				JTableHeader header = table.getTableHeader();
				header.setReorderingAllowed(false);
				header.setBackground(Color.LIGHT_GRAY);

				// ��ũ�� ������ ���̺� �г� ����
				JScrollPane scroll = new JScrollPane(table);

				// ���� �гο� �߰�
				scroll.setBounds(250, 30, 700, 400);
				add(scroll);

				JPanel panel = new JPanel();
				frame.add(panel, BorderLayout.SOUTH);

				btn1 = new JButton("Ȯ��");
				btn1.setSize(50, 50);

				panel.add(btn1);
				frame.add(panel);

				btn1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
					}
				});

				setVisible(true);
//				updateTableModel();
				revalidate();
				repaint();

//				updateTableModel();
			}
		});

		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				removeAll();

				buttonPanel.add(button1);
				buttonPanel.add(button2);
				buttonPanel.add(button3);

				add(buttonPanel);
				businessList.clear();
				businessList = BusinessTripDAO.selectAdmin();

				// ���̺� �� ����
				model2 = new DefaultTableModel();

				model2.setRowCount(0); // ���� �� ����

				// �� �̸�
				model2.addColumn("no");
				model2.addColumn("�� ��");
				model2.addColumn("��� �̸�");
				model2.addColumn("��û ����");
				model2.addColumn("��û ��¥");
				model2.addColumn("���� ����");

			
				// BusinessTripVO Ŭ������ �� �྿ �߰��ϱ�
				for (BusinessTripVO temp : businessList) {

					// �� �྿ �߰��ϱ�
					model2.addRow(new Object[] { temp.getNum(), temp.getEmp_id(), temp.getEmp_name(), temp.getPlace(),
							temp.getWHATDAY(), temp.getProcessing() });
				}

				System.out.println("CommuteGUI ����");

				JFrame frame = new JFrame();
				frame.setBounds(400, 400, 1000, 750);
				frame.setLocationRelativeTo(null);

				// ���̺� ���� �� �� ����
				table = new JTable(model2);

				// ��ũ�� ������ ���̺� �г� ����
				JScrollPane scroll = new JScrollPane(table);

				// ���� �гο� �߰�
				scroll.setBounds(250, 30, 700, 400);
				add(scroll);

				// ���� �гο� �߰��� �г� ����
				JPanel buttonPanel = new JPanel();
				buttonPanel.setBounds(250, 450, 700, 100); // ũ�� ���� (���̸� 100���� �ø�)
				buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // ��ư���� �߾� �����ϰ� ���� ����
				buttonPanel.setBackground(Color.white);
				add("south", buttonPanel);

				// ��ư ���� �� �гο� �߰�
				JButton btn1 = new JButton("����");
				btn1.setBackground(color);
				JButton btn2 = new JButton("���� ����");
				btn2.setBackground(color);
				JButton btn3 = new JButton("�ݷ�");
				btn3.setBackground(color);
				JButton btn4 = new JButton("����");
				btn4.setBackground(color);

				buttonPanel.add(btn1);
				buttonPanel.add(btn2);
				buttonPanel.add(btn3);
				buttonPanel.add(btn4);

//				revalidate(); 
//				repaint();
				add(buttonPanel);
				

				// ����
				btn1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int index = table.getSelectedRow();
						System.out.println("index : " + index);

						if (index >= 0) {
							index -= 1;
							int num = (int) table.getValueAt(index, 0); // ��� �÷��� ���� ������
							System.out.println("num : " + num);
							formFrame(num);
						}
					}

					private void formFrame(int num) {
						JPanel contentPane;
						JLabel titleLabel, tiLabel, idLabel, nameLabel, gettitle, getId, getName, getUsedate,
								gerContent, getCombo;
						BusinessTripVO vo;

						BusinessTripVO getForm = null;

						setVisible(true);

						for (BusinessTripVO temp : businessList) {

							int listNum = temp.getNum();

							if (listNum == num) {
								getForm = businessList.get(listNum);
								System.out.println(getForm.getNum());
								break;
							}
						}

						if (getForm.equals(null)) {
							System.out.println("num ���� ���� �ʽ��ϴ�.");
							return;
						}

						JFrame frame = new JFrame();
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

						titleLabel = new JLabel("���� ��û��");
						titleLabel.setBounds(176, 25, 188, 100);
						titleLabel.setFont(mainFt);
						panel.add(titleLabel, BorderLayout.CENTER);

						idlabel = new JLabel(String.valueOf(getForm.getEmp_id()));
						idlabel.setBounds(213, 157, 116, 21);
						panel.add(idlabel);

						idLabel = new JLabel("�� ��");
						idLabel.setBounds(144, 160, 57, 15);
						panel.add(idLabel);

						namelabel = new JLabel(getForm.getEmp_name());
						namelabel.setBounds(213, 202, 116, 21);
						panel.add(namelabel);

						nameLabel = new JLabel("�� ��");
						nameLabel.setBounds(144, 205, 57, 15);
						panel.add(nameLabel);

						getCombo = new JLabel(getForm.getPlace());
						getCombo.setBounds(213, 246, 116, 23);
						panel.add(getCombo);

						JLabel comboLabel = new JLabel("������");
						comboLabel.setBounds(145, 246, 57, 15);
						panel.add(comboLabel);

						JLabel useDateLabel = new JLabel("���� ��¥");
						useDateLabel.setBounds(145, 287, 57, 15);
						panel.add(useDateLabel);

						String date = getForm.getWHATDAY();
						String dateText = date.substring(0, Math.min(date.length(), 10));
						getUsedate = new JLabel(dateText);
						getUsedate.setBounds(210, 284, 116, 21);
						panel.add(getUsedate);

						JButton btn1 = new JButton("Ȯ��");
						btn1.setBounds(210, 512, 76, 50);
						btn1.setPreferredSize(new Dimension(90, 30));
						btn1.setFont(ft);
						panel.add(btn1);

						panel.repaint();

						btn1.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								frame.dispose();
							}
						});

						frame.setVisible(true);
					}
				});

				// ���� ����
				btn2.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

				
						int index = table.getSelectedRow();
						int temp= (int)table.getValueAt(index, 0);
						
						System.out.println("��ư : " + index);

						if (temp >= 0) {
							
							int num = (int) table.getValueAt(index, 1);
							String placeCode = (String) table.getValueAt(index, 3);
							int place = 0;

							if ("P1".equals(placeCode)) {
								place = 10000;
							} else if ("P2".equals(placeCode)) {
								place = 20000;
							} else if ("P3".equals(placeCode)) {
								place = 10000;
							} else if ("P4".equals(placeCode)) {
								place = 30000;
							} else if ("P5".equals(placeCode)) {
								place = 50000;
							} else if ("P6".equals(placeCode)) {
								place = 100000;
							} else if ("P7".equals(placeCode)) {
								place = 10000;
							} else if ("P8".equals(placeCode)) {
								place = 50000;
							}

							System.out.println(num);
							System.out.println(temp);
							BusinessTripDAO.approval(temp, num, place);
							refreshTable_Business();
							
						}
						button2.doClick();
						//refreshTable_Business();
					}
				

				});

				// �ݷ�
				btn3.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int index = table.getSelectedRow();
						int temp= (int)table.getValueAt(index, 0);
						System.out.println("��ư : " + index);

						if (index >= 0) {
							
							int num = (int) table.getValueAt(index, 1); // ��� �÷��� ���� ������
							System.out.println(num);
							BusinessTripDAO.reject(temp, num);
						}
						button2.doClick();
						refreshTable_Business();
					}
				});

				// ����
				btn4.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						int index = table.getSelectedRow() ;
						int temp= (int)table.getValueAt(index, 0);
						System.out.println(index);

						if (temp >= 0) {
							int num = (int) table.getValueAt(index + 1, 1); // ��� �÷��� ���� ������

							System.out.println(num);
							System.out.println("temp:" +temp);
							System.out.println("index:"+index);

							// ���̺� �𵨿��� �� ����
							model2.removeRow(temp);
							System.out.println(businessList.size());
							// ��Ͽ��� �ش� ��ü ����
							businessList.remove(index);
							

							// ���̺� ǥ�� ����
							table.revalidate();
							table.repaint();

							// �����ͺ��̽����� �� �׸��� �����ϴ� DAO �޼��� ȣ��
							BusinessTripDAO.delete(temp);

							repaint();

						}

						button2.doClick();
						refreshTable_Business();
					}

				});

				add(buttonPanel);

				setVisible(true);
				revalidate();
				repaint();
			}
		});
		button3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				removeAll();

				buttonPanel.add(button1);
				buttonPanel.add(button2);
				buttonPanel.add(button3);

				add(buttonPanel);

//				appList = AnnualPDAO.adminSelect();
				appList = CommuteDAO.totaLeaveCount();

//				model3.setRowCount(0);

				// ���̺� �� ����
				model3 = new DefaultTableModel();
//				DefaultTableModel model3 = new DefaultTableModel();

//				model3.setRowCount(0); // ���� �� ����

				// �� �̸�
				model3.addColumn("NO");
				model3.addColumn("������");
				model3.addColumn("��û ����");
				model3.addColumn("��û ��¥");
				model3.addColumn("�� ��");
				model3.addColumn("��� �̸�");
				model3.addColumn("�ۼ���");
				model3.addColumn("�������");
				model3.addColumn("����");

//				for (CommuteVO temp2 : leaveList) {
//
//					for (appFormVO temp : appList) {
//
//						if (temp2.getEMP_ID() == temp.getEmp_id()) {
//
//							// �� �྿ �߰��ϱ�
//							model3.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(),
//									temp.getUseDate(), temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(),
//									temp.getProcessing(), temp2.getLeaveCount() });
//
//						}
//					}
//				}

				for (appFormVO temp : appList) {

					// �� �྿ �߰��ϱ�
					model3.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(), temp.getUseDate(),
							temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(), temp.getProcessing(),
							temp.getLeave() });

				}

				// ���̺� ���� �� �� ����
				table3 = new JTable(model3);

				// ��ũ�� ������ ���̺� �г� ����
				JScrollPane scroll = new JScrollPane(table3);
				scroll.setBounds(250, 30, 700, 400);
				add(scroll);

				JPanel buttonPanel = new JPanel();
				buttonPanel.setBounds(250, 450, 700, 100); // ũ�� ���� (���̸� 100���� �ø�)
				buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // ��ư���� �߾� �����ϰ� ���� ����
				buttonPanel.setBackground(Color.white);
				add("south", buttonPanel);

				// ��ư ���� �� �гο� �߰�
				JButton btn1 = new JButton("����");
				JButton btn2 = new JButton("���� ����");
				JButton btn3 = new JButton("�ݷ�");
				JButton btn4 = new JButton("����");
				btn1.setBackground(color);
				btn2.setBackground(color);
				btn3.setBackground(color);
				btn4.setBackground(color);

				buttonPanel.add(btn1);
				buttonPanel.add(btn2);
				buttonPanel.add(btn3);
				buttonPanel.add(btn4);

				add(buttonPanel);

				// ����
				btn1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int index = table3.getSelectedRow();
						System.out.println("index : " + index);

						if (index >= 0) {
							index -= 1;
							int num = (int) table3.getValueAt(index, 0); // ��� �÷��� ���� ������
//							System.out.println("num : " + num);
							formFrame(num);
						}
					}

					private void formFrame(int num) {
						JPanel contentPane;
						JLabel titleLabel, tiLabel, idLabel, nameLabel, gettitle, getId, getName, getUsedate,
								gerContent, getCombo;
						appFormVO vo;

						appFormVO getForm = null;

						setVisible(true);

						for (appFormVO temp : appList) {

							int listNum = temp.getNum();

							if (listNum == num) {
								getForm = appList.get(listNum);
								System.out.println(getForm.getNum());
								break;
							}
						}

						if (getForm.equals(null)) {
							System.out.println("num ���� ���� �ʽ��ϴ�.");
							return;
						}

						JFrame frame = new JFrame();
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						frame.setBounds(100, 100, 1000, 745);
						contentPane = new JPanel();
						contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

						frame.setContentPane(contentPane);
						contentPane.setLayout(null);

						JPanel panel = new JPanel();
						panel.setBounds(223, 10, 516, 686);
						panel.setBorder(new LineBorder(Color.BLACK));
						contentPane.add(panel);
						panel.setLayout(null);

						Font mainFt = new Font("����", Font.BOLD, 30);
						Font ft = new Font("����", Font.BOLD, 14);

						titleLabel = new JLabel("���� ��û��");
						titleLabel.setBounds(115, 25, 292, 44);
						titleLabel.setFont(mainFt);
						panel.add(titleLabel, BorderLayout.CENTER);

						gettitle = new JLabel(getForm.getTitle());
						gettitle.setBounds(181, 93, 224, 21);
						panel.add(gettitle);

						tiLabel = new JLabel("�� ��");
						tiLabel.setBounds(112, 96, 57, 15);
						panel.add(tiLabel);

						idLabel = new JLabel("�� ��");
						idLabel.setBounds(112, 141, 57, 15);
						panel.add(idLabel);

						getId = new JLabel(String.valueOf(getForm.getEmp_id()));
						getId.setBounds(181, 138, 116, 21);
						panel.add(getId);

						nameLabel = new JLabel("�� ��");
						nameLabel.setBounds(113, 183, 57, 15);
						panel.add(nameLabel);

						getName = new JLabel(getForm.getEmp_name());
						getName.setBounds(181, 180, 116, 21);
						panel.add(getName);

						getCombo = new JLabel(getForm.getType());
						getCombo.setBounds(181, 222, 116, 23);
						panel.add(getCombo);

						JLabel comboLabel = new JLabel("��� ����");
						comboLabel.setBounds(113, 226, 57, 15);
						panel.add(comboLabel);

						JLabel useDateLabel = new JLabel("��� ��¥");
						useDateLabel.setBounds(112, 271, 57, 15);
						panel.add(useDateLabel);

						getUsedate = new JLabel(getForm.getType());
						getUsedate.setBounds(181, 268, 116, 21);
						panel.add(getUsedate);

						JLabel contentLabel = new JLabel("��� ����");
						contentLabel.setBounds(113, 314, 57, 15);
						panel.add(contentLabel);

						gerContent = new JLabel(getForm.getContent());
						gerContent.setBounds(181, 314, 116, 21);
						panel.add(gerContent);

						JButton btn1 = new JButton("Ȯ��");
						btn1.setBounds(210, 512, 76, 50);
						btn1.setPreferredSize(new Dimension(90, 30));
						btn1.setFont(ft);
						panel.add(btn1);

						panel.repaint();

						btn1.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								frame.dispose();
							}
						});

						frame.setVisible(true);
					}
				});

				// ���� ����
				btn2.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						int index = table3.getSelectedRow() + 1;

						System.out.println("��ư : " + index);

						if (index >= 0) {
							index -= 1;
							int num = (int) table3.getValueAt(index, 4); // ��� �÷��� ���� ������
							System.out.println(num);
							AnnualPDAO.approval(index + 1, num);
						}
						refreshTable_Annual();
					}
				});

				// �ݷ�
				btn3.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int index = table3.getSelectedRow() + 1;
						System.out.println("��ư : " + index);

						if (index >= 0) {
							index -= 1;
							int num = (int) table3.getValueAt(index, 4); // ��� �÷��� ���� ������
							System.out.println(num);
							AnnualPDAO.reject(index + 1, num);

							button3.doClick();
						}
					}
				});

				// ����
				btn4.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						int index = table3.getSelectedRow() + 1;

						if (index >= 0) {
							int num = (int) table3.getValueAt(index + 1, 4); // ��� �÷��� ���� ������
							// ���̺� �𵨿��� �� ����
							model3.removeRow(index);

							// ��Ͽ��� �ش� ��ü ����
							list.remove(index);

							// ���̺� ǥ�� ����
							table3.revalidate();
							table3.repaint();

							// �����ͺ��̽����� �� �׸��� �����ϴ� DAO �޼��� ȣ��
							AnnualPDAO.delete(num);

							button3.doClick();
							refreshTable_Annual();
						}
					}

				});

				revalidate();
				repaint();

			}
		});
		buttonPanel.add(button1);
		buttonPanel.add(button2);
		buttonPanel.add(button3);

		add(buttonPanel);

	}

	// �����
	private void refreshTable_Comm() {

		
		list = CommuteDAO.selectAdminCommute();

		model2.setRowCount(0);

		for (CommuteVO temp : list) {

			// �� �྿ �߰��ϱ�
			model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getMONTH_DATE(), temp.getComm(),
					temp.getEnd() });

		}
		
	}

	// ����
	private void refreshTable_Business() {
		
		businessList = BusinessTripDAO.selectAdmin();

		model2.setRowCount(0);

		for (BusinessTripVO temp : businessList) {

			// �� �྿ �߰��ϱ�
			model2.addRow(new Object[] { temp.getNum(), temp.getEmp_id(), temp.getEmp_name(), temp.getPlace(),
					temp.getWHATDAY(), temp.getProcessing() });
		}
		//add()
	
	}

	// ����
	private void refreshTable_Annual() {
		
//		// �����͸� ���ΰ�ħ�ϰ� ���̺� �𵨿� ���ο� �����͸� ������.
//		appList = CommuteDAO.totaLeaveCount();
//
//		// ��� ���� ������.
//		model3.setRowCount(0);
//
//		for (appFormVO temp : appList) {
//
//			// �� �྿ �߰��ϱ�
//			model3.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(), temp.getUseDate(),
//					temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(), temp.getProcessing(), temp.getLeave() });
//		}
//	
	}

	
}
