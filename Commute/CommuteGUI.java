package Commute;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import AnnualPaidLeave.AnnualPDAO;
import AnnualPaidLeave.appFormVO;
import login.LoginVO;

// ��� �� �ް� ��� ��Ȳ
public class CommuteGUI extends JFrame {

	private JTable table;
	private DefaultTableModel model;
	private ArrayList<CommuteVO> list = new ArrayList<CommuteVO>();
	private JButton btn1;
	private LoginVO logvo = new LoginVO();

	public CommuteGUI() {

		System.out.println("CommuteGUI ������ ȣ��");

		setTitle("Table Select");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 400, 1000, 750);
		setLocationRelativeTo(null);

		init();

		// ������ �ִ� ������ ��������
		//list = CommuteDAO.selectCommute();
		System.out.println(list);
		drawList();

		setVisible(true);

	}

//	private int leaveCount() {
//		
//		int value = 0;
//		int availableLeave;
//		int total = 0;
//		
//		// ���� ��¥
//		LocalDate currentDate = LocalDate.now();
//
//		// ������ �Ի��� ����Ʈ
//		ArrayList<LocalDate> hireDates = CommuteDAO.sreachHiredate(logvo.getId());
//
//
//		for (LocalDate hireDate : hireDates) {
//
//			int daysWorked = (int) ChronoUnit.MONTHS.between(hireDate, currentDate) / 12; // �� �������� Ȯ��
////			int workMonth = (daysWorked - 1) / 2;
//			System.out.println("daysWorked : " + daysWorked);
//			System.out.println("hireDate : " + hireDate);
//			System.out.println("currentDate : " + currentDate);
////			System.out.println("workDate : " + workMonth);
//
//			int use = CommuteDAO.useLeave();
//			System.out.println("use : " + use);
//					
//			if (daysWorked >= 3) {
//				// 3���� ������ ���� ��������
//				value = CommuteDAO.hireDate3();
//				availableLeave = value - use;
//				System.out.println("��� ���� : "  + availableLeave);
//				total = availableLeave - use;
//				System.out.println("Total : " + total);
//			} else if (daysWorked >= 2) {
//				// 2���� ������ ���� ��������
//				value = CommuteDAO.hireDate2();
//				availableLeave = value - use;
//				System.out.println("��� ���� : "  + availableLeave);
//				total = availableLeave - use;
//				System.out.println("Total : " + total);
//			} else if (daysWorked >= 0) {
//				// 2���� �̸��� ���� ���� ��������
//				value = CommuteDAO.hireDate();
//				availableLeave = value - use;
//				System.out.println("��� ���� : "  + availableLeave);
//				total = availableLeave - use;
//				System.out.println("Total : " + total);
//			}
//			
//
//		}
//		return total;
//	}

	public void drawList() {

		// ���̺� ������ �����.
		model.getDataVector().removeAllElements();
		
		

		// CommuteVO Ŭ������ �� �྿ �߰��ϱ�
		for (CommuteVO temp : list) {

			// ���� ���� ��������
//			int leaveCount = leaveCount(); // ������ ���� ������ �������� �޼��� ȣ��

			// �� �྿ �߰��ϱ�
			model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getComm(),
					temp.getEnd()});

		}

		model.fireTableDataChanged();

	}

	public void init() {
		// ���̺� �� ����
		model = new DefaultTableModel();

		// �� �̸�
		model.addColumn("���");
		model.addColumn("�����");
		model.addColumn("��¥");
		model.addColumn("����");
		model.addColumn("���");

		// ���̺� ���� �� �� ����
		table = new JTable(model);
		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setBackground(Color.LIGHT_GRAY);
		// ��ũ�� ������ ���̺� �г� ����
		JScrollPane scroll = new JScrollPane(table);

		add(scroll);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		btn1 = new JButton("Ȯ��");
		btn1.setSize(50, 50);

		panel.add(btn1);

		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				drawList();
			}
		});
	}

	public static void main(String[] args) {
		System.out.println("CommuteGUI ����");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CommuteGUI();
			}
		});

	}

}

