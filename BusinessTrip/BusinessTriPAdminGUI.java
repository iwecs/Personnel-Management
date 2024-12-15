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
import AnnualPaidLeave.AnnualP_AdminGUI;
import AnnualPaidLeave.appFormVO;

public class BusinessTriPAdminGUI extends JFrame {

	private JTable table;
	private DefaultTableModel model;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idField, nameField, contentField, useDateField;
	private JLabel tiLabel, idLabel, nameLabel;
	private JButton btn, btn1, btn2, btn3, btn4;
	private ArrayList<BusinessTripVO> list = new ArrayList<BusinessTripVO>();

	public BusinessTriPAdminGUI() {

		setBounds(100, 0, 1000, 750);

		init();

		// ������ �ִ� ������ ��������
		list = BusinessTripDAO.selectAdmin();

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
			model.addRow(new Object[] { temp.getNum(), temp.getEmp_id(), temp.getEmp_name(), temp.getPlace(), temp.getWHATDAY(),temp.getProcessing() });
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

		btn1 = new JButton("����");
		btn1.setSize(50, 50);

		btn2 = new JButton("���� ����");
		btn2.setSize(100, 50);

		btn3 = new JButton("�ݷ�");
		btn3.setSize(150, 50);

		btn4 = new JButton("����");
		btn4.setSize(200, 50);

		panel.add(btn1);
		panel.add(btn2);
		panel.add(btn3);
		panel.add(btn4);

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
						JLabel titleLabel, tiLabel, idLabel, nameLabel, gettitle, getId, getName, getUsedate, gerContent,
								getCombo;
						BusinessTripVO vo;

						BusinessTripVO getForm = null;

						setVisible(true);

						for (BusinessTripVO temp : list) {

							int listNum = temp.getNum();

							if (listNum == num) {
								getForm = list.get(listNum);
								System.out.println(getForm.getNum());
								break;
							}
						}

						if (getForm.equals(null)) {
							System.out.println("num ���� ���� �ʽ��ϴ�.");
							return;
						}

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

						titleLabel = new JLabel("���� ��û��");
						titleLabel.setBounds(176, 25, 188, 100);
						titleLabel.setFont(mainFt);
						panel.add(titleLabel, BorderLayout.CENTER);

						idField = new JTextField(getForm.getEmp_id());
						idField.setBounds(213, 157, 116, 21);
						panel.add(idField);
						idField.setColumns(10);

						idLabel = new JLabel("�� ��");
						idLabel.setBounds(144, 160, 57, 15);
						panel.add(idLabel);

						nameField = new JTextField(getForm.getEmp_name());
						nameField.setBounds(213, 202, 116, 21);
						panel.add(nameField);
						nameField.setColumns(10);

						nameLabel = new JLabel("�� ��");
						nameLabel.setBounds(144, 205, 57, 15);
						panel.add(nameLabel);
						
						getCombo = new JLabel(getForm.getType());
						getCombo.setBounds(181, 222, 116, 23);
						panel.add(getCombo);
						
//						String[] box = { "P1","P2","P3","P4","P5","P6","P7","P8" };
//						JComboBox comboBox = new JComboBox(box);
//						comboBox.setBounds(213, 242, 116, 23);
//						panel.add(comboBox);

						JLabel comboLabel = new JLabel("������");
						comboLabel.setBounds(145, 246, 57, 15);
						panel.add(comboLabel);

						JLabel useDateLabel = new JLabel("���� ��¥");
						useDateLabel.setBounds(145, 287, 57, 15);
						panel.add(useDateLabel);

						getUsedate = new JLabel(getForm.getType());
						getUsedate.setBounds(214, 284, 116, 21);
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
								drawList();
							}
						});

						frame.setVisible(true);
					}
				});

				// ���� ����
				btn2.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						int index = table.getSelectedRow() + 1;

						System.out.println("��ư : " + index);

						if (index >= 0) {
							index -= 1;
							int num = (int) table.getValueAt(index, 1); 
							String placeCode = (String) table.getValueAt(index, 3);
							int place = 0;
							
							if("P1".equals(placeCode)) {
								place = 10000;
							} else if("P2".equals(placeCode)){
								place = 20000;
							} else if("P3".equals(placeCode)) {
								place = 10000;
							} else if("P4".equals(placeCode)) {
								place = 30000;
							} else if("P5".equals(placeCode)) {
								place = 50000;
							} else if("P6".equals(placeCode)) {
								place = 100000;
							} else if("P7".equals(placeCode)) {
								place = 10000;
							} else if("P8".equals(placeCode)) {
								place = 50000;
							}

							System.out.println(num);
							BusinessTripDAO.approval(index + 1, num, place);
						}
					}
				});

				// �ݷ�
				btn3.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int index = table.getSelectedRow() + 1;
						System.out.println("��ư : " + index);

						if (index >= 0) {
							index -= 1;
							int num = (int) table.getValueAt(index, 1); // ��� �÷��� ���� ������
							System.out.println(num);
							BusinessTripDAO.reject(index + 1, num);
						}
					}
				});

				// ����
				btn4.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						int index = table.getSelectedRow();

						if (index >= 0) {
							int num = (int) table.getValueAt(index, 1); // ��� �÷��� ���� ������
							// ���̺� �𵨿��� �� ����
							model.removeRow(index);

							// ��Ͽ��� �ش� ��ü ����
							list.remove(index);

							// ���̺� ǥ�� ����
							table.revalidate();
							table.repaint();

							// �����ͺ��̽����� �� �׸��� �����ϴ� DAO �޼��� ȣ��
							BusinessTripDAO.delete(num);

							repaint();

						}
					}

				});

	}

	public static void main(String[] args) {
		new BusinessTriPAdminGUI();
	}

}
