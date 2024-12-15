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

		// 기존에 있는 데이터 가져오기
		list = AnnualPDAO.adminSelect();
//		leaveList = CommuteDAO.totaLeaveCount();
		drawList();

		setTitle("[Admin] Table Select");
		setVisible(true);

	}

	private void drawList() {

		// 테이블 데이터 지우기.
		model.getDataVector().removeAllElements();

		for (CommuteVO temp2 : leaveList) {

			for (appFormVO temp : list) {

				if (temp2.getEMP_ID() == temp.getEmp_id()) { 

					// 한 행씩 추가하기
					model.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(), temp.getUseDate(),
							temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(), temp.getProcessing(),
							temp2.getLeaveCount()});
					
				}
			}
		}

		model.fireTableDataChanged();

	}

	private void init() {
		// 테이블 모델 생성
		model = new DefaultTableModel();

		// 열 이름
		model.addColumn("NO");
		model.addColumn("문서명");
		model.addColumn("신청 유형");
		model.addColumn("신청 날짜");
		model.addColumn("사 번");
		model.addColumn("사원 이름");
		model.addColumn("작성일");
		model.addColumn("진행상태");
		model.addColumn("연차");

		// 테이블 생성 및 모델 설정
		table = new JTable(model);

		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setBackground(Color.LIGHT_GRAY);
		// 스크롤 가능한 테이블 패널 생성
		JScrollPane scroll = new JScrollPane(table);

		add(scroll);

		JPanel panel = new JPanel();
		add("South", panel);

		btn1 = new JButton("보기");
		btn1.setSize(50, 50);

		btn2 = new JButton("결재 승인");
		btn2.setSize(100, 50);

		btn3 = new JButton("반려");
		btn3.setSize(150, 50);

		btn4 = new JButton("삭제");
		btn4.setSize(200, 50);

		panel.add(btn1);
		panel.add(btn2);
		panel.add(btn3);
		panel.add(btn4);

		// 보기
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				System.out.println("index : " + index);

				if (index >= 0) {
					index -= 1;
					int num = (int) table.getValueAt(index, 0); // 사번 컬럼의 값을 가져옴
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
					System.out.println("num 값이 같지 않습니다.");
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

				Font mainFt = new Font("굴림", Font.BOLD, 30);
				Font ft = new Font("굴림", Font.BOLD, 14);

				titleLabel = new JLabel("연차/휴가 신청서");
				titleLabel.setBounds(115, 25, 292, 44);
				titleLabel.setFont(mainFt);
				panel.add(titleLabel, BorderLayout.CENTER);

				gettitle = new JLabel(getForm.getTitle());
				gettitle.setBounds(181, 93, 224, 21);
				panel.add(gettitle);

				tiLabel = new JLabel("제 목");
				tiLabel.setBounds(112, 96, 57, 15);
				panel.add(tiLabel);

				idLabel = new JLabel("사 번");
				idLabel.setBounds(112, 141, 57, 15);
				panel.add(idLabel);

				getId = new JLabel(String.valueOf(getForm.getEmp_id()));
				getId.setBounds(181, 138, 116, 21);
				panel.add(getId);

				nameLabel = new JLabel("이 름");
				nameLabel.setBounds(113, 183, 57, 15);
				panel.add(nameLabel);

				getName = new JLabel(getForm.getEmp_name());
				getName.setBounds(181, 180, 116, 21);
				panel.add(getName);

				getCombo = new JLabel(getForm.getType());
				getCombo.setBounds(181, 222, 116, 23);
				panel.add(getCombo);

				JLabel comboLabel = new JLabel("사용 유형");
				comboLabel.setBounds(113, 226, 57, 15);
				panel.add(comboLabel);

				JLabel useDateLabel = new JLabel("사용 날짜");
				useDateLabel.setBounds(112, 271, 57, 15);
				panel.add(useDateLabel);

				getUsedate = new JLabel(getForm.getType());
				getUsedate.setBounds(181, 268, 116, 21);
				panel.add(getUsedate);

				JLabel contentLabel = new JLabel("사용 사유");
				contentLabel.setBounds(113, 314, 57, 15);
				panel.add(contentLabel);

				gerContent = new JLabel(getForm.getContent());
				gerContent.setBounds(181, 314, 116, 21);
				panel.add(gerContent);

				JButton btn1 = new JButton("확인");
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

		// 결재 승인
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int index = table.getSelectedRow() + 1;

				System.out.println("버튼 : " + index);

				if (index >= 0) {
					index -= 1;
					int num = (int) table.getValueAt(index, 4); // 사번 컬럼의 값을 가져옴
					System.out.println(num);
					AnnualPDAO.approval(index + 1, num);
				}
			}
		});

		// 반려
		btn3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow() + 1;
				System.out.println("버튼 : " + index);

				if (index >= 0) {
					index -= 1;
					int num = (int) table.getValueAt(index, 4); // 사번 컬럼의 값을 가져옴
					System.out.println(num);
					AnnualPDAO.reject(index + 1, num);
				}
			}
		});

		// 삭제
		btn4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int index = table.getSelectedRow();

				if (index >= 0) {
					int num = (int) table.getValueAt(index, 4); // 사번 컬럼의 값을 가져옴
					// 테이블 모델에서 행 제거
					model.removeRow(index);

					// 목록에서 해당 객체 제거
					list.remove(index);

					// 테이블 표시 갱신
					table.revalidate();
					table.repaint();

					// 데이터베이스에서 이 항목을 삭제하는 DAO 메서드 호출
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

