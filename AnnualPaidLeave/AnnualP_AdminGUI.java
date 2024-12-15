package AnnualPaidLeave;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Commute.CommuteDAO;
import Commute.CommuteVO;

public class AnnualP_AdminGUI extends JFrame {

	private JTable table;
	private DefaultTableModel model;
	private ArrayList<appFormVO> list = new ArrayList<appFormVO>();
	private ArrayList<CommuteVO> leaveList = new ArrayList<CommuteVO>();
	private JButton btn1, btn2, btn3, btn4;

	public AnnualP_AdminGUI() {

		setBounds(500, 400, 1000, 750);

		init();

		// ������ �ִ� ������ ��������
		list = AnnualPDAO.adminSelect();
//		leaveList = CommuteDAO.totaLeaveCount();
		drawList();

		setTitle("[Admin] Table Select");
		setVisible(true);

	}

	private void drawList() {

		// ���̺� ������ �����.
		model.getDataVector().removeAllElements();

		for (CommuteVO temp2 : leaveList) {

			for (appFormVO temp : list) {

				if (temp2.getEMP_ID() == temp.getEmp_id()) { 

					// �� �྿ �߰��ϱ�
					model.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(), temp.getUseDate(),
							temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(), temp.getProcessing(),
							temp2.getLeaveCount()});
					
				}
			}
		}

		model.fireTableDataChanged();

	}

	private void init() {
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
		// ��ũ�� ������ ���̺� �г� ����
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
//					System.out.println("num : " + num);
					formFrame(num);
				}
			}

			private void formFrame(int num) {
				JPanel contentPane;
				JLabel titleLabel, tiLabel, idLabel, nameLabel, gettitle, getId, getName, getUsedate, gerContent,
						getCombo;
				appFormVO vo;

				appFormVO getForm = null;

				setVisible(true);

				for (appFormVO temp : list) {

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

				titleLabel = new JLabel("����/�ް� ��û��");
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
					int num = (int) table.getValueAt(index, 4); // ��� �÷��� ���� ������
					System.out.println(num);
					AnnualPDAO.approval(index + 1, num);
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
					int num = (int) table.getValueAt(index, 4); // ��� �÷��� ���� ������
					System.out.println(num);
					AnnualPDAO.reject(index + 1, num);
				}
			}
		});

		// ����
		btn4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int index = table.getSelectedRow();

				if (index >= 0) {
					int num = (int) table.getValueAt(index, 4); // ��� �÷��� ���� ������
					// ���̺� �𵨿��� �� ����
					model.removeRow(index);

					// ��Ͽ��� �ش� ��ü ����
					list.remove(index);

					// ���̺� ǥ�� ����
					table.revalidate();
					table.repaint();

					// �����ͺ��̽����� �� �׸��� �����ϴ� DAO �޼��� ȣ��
					AnnualPDAO.delete(num);

					repaint();

				}
			}

		});
	}

	public static void main(String[] args) {
		new AnnualP_AdminGUI();
	}

}

