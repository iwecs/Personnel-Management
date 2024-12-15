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

// 출근 및 휴가 사용 현황
public class CommuteAdminGUI extends JFrame {

	private JTable table;
	private DefaultTableModel model;
	private ArrayList<CommuteVO> list = new ArrayList<CommuteVO>();
	private JButton btn1;
	private LoginVO logvo = new LoginVO();

	public CommuteAdminGUI() {

		System.out.println("CommuteGUI 생성자 호출");

		setTitle("Table Select");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 400, 1000, 750);
		setLocationRelativeTo(null);

		init();

		// 기존에 있는 데이터 가져오기
		list = CommuteDAO.selectAdminCommute();
		System.out.println(list);
		drawList();

		setVisible(true);

	}



	private void drawList() {

		// 테이블 데이터 지우기.
		model.getDataVector().removeAllElements();

//		int useTotal = CommuteDAO.useLeaveTotal();
//		ArrayList<CommuteVO> hireDatesList = leaveCount();

		// CommuteVO 클래스를 한 행씩 추가하기
		for (CommuteVO temp : list) {

			// 한 행씩 추가하기
			model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getComm(), temp.getEnd(),
					temp.getLeave() });
//		leaveCount(); // 직원의 연차 갯수를 가져오는 메서드 호출

			model.fireTableDataChanged();

		}
	}

	private void init() {
		// 테이블 모델 생성
		model = new DefaultTableModel();

		// 열 이름
		model.addColumn("사번");
		model.addColumn("사원명");
		model.addColumn("근태");
		model.addColumn("퇴근");
		model.addColumn("연차");
		model.addColumn("연차갯수");
		model.addColumn("사용한 연차 갯수");

		// 테이블 생성 및 모델 설정
		table = new JTable(model);

		// 스크롤 가능한 테이블 패널 생성
		JScrollPane scroll = new JScrollPane(table);

		add(scroll);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		btn1 = new JButton("확인");
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
		System.out.println("CommuteGUI 실행");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CommuteAdminGUI();
			}
		});

	}

}
