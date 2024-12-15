package Commute;

import java.awt.BorderLayout;
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

import AnnualPaidLeave.AnnualPDAO;
import AnnualPaidLeave.appFormVO;
import login.LoginVO;

// ��� �� �ް� ��� ��Ȳ
public class CommuteAdminGUI extends JFrame {

	private JTable table;
	private DefaultTableModel model;
	private ArrayList<CommuteVO> list = new ArrayList<CommuteVO>();
	private JButton btn1;
	private LoginVO logvo = new LoginVO();

	public CommuteAdminGUI() {

		System.out.println("CommuteGUI ������ ȣ��");

		setTitle("Table Select");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 400, 1000, 750);
		setLocationRelativeTo(null);

		init();

		// ������ �ִ� ������ ��������
		list = CommuteDAO.selectAdminCommute();
		System.out.println(list);
		drawList();

		setVisible(true);

	}



	private void drawList() {

		// ���̺� ������ �����.
		model.getDataVector().removeAllElements();

//		int useTotal = CommuteDAO.useLeaveTotal();
//		ArrayList<CommuteVO> hireDatesList = leaveCount();

		// CommuteVO Ŭ������ �� �྿ �߰��ϱ�
		for (CommuteVO temp : list) {

			// �� �྿ �߰��ϱ�
			model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getComm(), temp.getEnd(),
					temp.getLeave() });
//		leaveCount(); // ������ ���� ������ �������� �޼��� ȣ��

			model.fireTableDataChanged();

		}
	}

	private void init() {
		// ���̺� �� ����
		model = new DefaultTableModel();

		// �� �̸�
		model.addColumn("���");
		model.addColumn("�����");
		model.addColumn("����");
		model.addColumn("���");
		model.addColumn("����");
		model.addColumn("��������");
		model.addColumn("����� ���� ����");

		// ���̺� ���� �� �� ����
		table = new JTable(model);

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
				new CommuteAdminGUI();
			}
		});

	}

}
