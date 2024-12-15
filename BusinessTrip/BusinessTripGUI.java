package BusinessTrip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import AnnualPaidLeave.AnnualPDAO;
import AnnualPaidLeave.AnnualPGUI;
import AnnualPaidLeave.appFormVO;
import Commute.CommuteDAO;
import Commute.CommuteVO;

public class BusinessTripGUI extends JFrame {

	private JTable table;
	private DefaultTableModel model;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idField, nameField, contentField, useDateField;
	private JLabel tiLabel, idLabel, nameLabel;
	private ArrayList<BusinessTripVO> list = new ArrayList<BusinessTripVO>();
	private JButton btn, btn1, btn2, btn3;

	public BusinessTripGUI() {

		setBounds(100, 0, 1000, 750);

		init();

		// ������ �ִ� ������ ��������
		//list = BusinessTripDAO.select();

		drawList();

		setTitle("Table Select");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void drawList() {
		System.out.println("drawList()");

		// ���̺� ������ �����.
		model.getDataVector().removeAllElements();

		for (BusinessTripVO temp : list) {

			// �� �྿ �߰��ϱ�
			model.addRow(new Object[] { temp.getNum(), temp.getEmp_id(), temp.getEmp_name(), temp.getPlace(),
					temp.getWHATDAY(), temp.getProcessing() });
		}

		model.fireTableDataChanged();
	}

	private void init() {
		// ���̺� �� ����
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

		// ��ũ�� �� ���� ���̺� �г� ����
		JScrollPane scroll = new JScrollPane(table);

		add(scroll);

		JPanel panel = new JPanel();
		add("South", panel);

		btn = new JButton("���ΰ�ħ");
		btn.setSize(50, 50);

		btn1 = new JButton("��û�ϱ�");
		btn1.setSize(100, 50);

		btn2 = new JButton("�����ϱ�");
		btn2.setSize(150, 50);

		panel.add(btn);
		panel.add(btn1);
		panel.add(btn2);

		// ���ΰ�ħ ��ư �׼�
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();

			}

		});

		btn1.addActionListener(new ActionListener() {

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

				idField = new JTextField();
				idField.setBounds(213, 157, 116, 21);
				panel.add(idField);
				idField.setColumns(10);

				idLabel = new JLabel("�� ��");
				idLabel.setBounds(144, 160, 57, 15);
				panel.add(idLabel);

				nameField = new JTextField();
				nameField.setBounds(213, 202, 116, 21);
				panel.add(nameField);
				nameField.setColumns(10);

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

				// Ȯ��
				btn1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						// int emp_id = Integer.parseInt(textField_1.getText());
						if (!idField.getText().isEmpty()) {
							int emp_id = Integer.parseInt(idField.getText());
							String name = nameField.getText();
							String type = (String) comboBox.getSelectedItem();
							String usedate = useDateField.getText();
//							if (BusinessTripDAO.SearchName(emp_id, name)) {
//								JOptionPane.showMessageDialog(null, "����� �̸��� ��ġ���� �ʽ��ϴ�.","error",JOptionPane.ERROR_MESSAGE);
					//	} else {
								BusinessTripVO vo = new BusinessTripVO(emp_id, name, type, usedate);
								BusinessTripDAO.insert(vo);
							//}
						}

						else {
							JOptionPane.showMessageDialog(btn1, "��ĭ�� �ֽ��ϴ�");
							return;
						}

						// �Է� ��ȿ�� �˻� �� ���� ���
						if (idField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(btn2, "����� �Է��ϼ���.");
						} else if (nameField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(btn2, "�̸��� �Է��ϼ���.");
						} else if (useDateField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(btn2, "��¥�� �Է��ϼ���.");
						} else {
							JOptionPane.showMessageDialog(btn2, "����Ǿ����ϴ�.");
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
						//refreshTable();

						dispose();
						new BusinessTripGUI();

					}

				});

				// ���
				btn2.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						setVisible(false);

						new BusinessTripGUI();

					}

				});
			}

		});

		btn2.addActionListener(new ActionListener() {

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

					//refreshTable();

				} else {
					// ���� ���õ��� ���� ��� ����ڿ��� �޽��� ǥ��
					JOptionPane.showMessageDialog(null, "������ ���� �����ϼ���.", "���õ� �� ����", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}
//
//	private void refreshTable() {
//		// �����͸� ���ΰ�ħ�ϰ� ���̺� �𵨿� ���ο� �����͸� ������.
//
//		list = BusinessTripDAO.select();
//
//		// ��� ���� ������.
//		model.setRowCount(0);
//
//		// �ٽ� ������
//		for (BusinessTripVO vo : list) {
//			// �� �྿ �߰��ϱ�
//			model.addRow(new Object[] { vo.getType(), vo.getEmp_id(), vo.getEmp_name() });
//		}
//
//	}

	public static void main(String[] args) {
		// new BusinessTripGUI();
	}
}
