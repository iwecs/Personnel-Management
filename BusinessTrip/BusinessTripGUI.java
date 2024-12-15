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

		// 기존에 있는 데이터 가져오기
		//list = BusinessTripDAO.select();

		drawList();

		setTitle("Table Select");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void drawList() {
		System.out.println("drawList()");

		// 테이블 데이터 지우기.
		model.getDataVector().removeAllElements();

		for (BusinessTripVO temp : list) {

			// 한 행씩 추가하기
			model.addRow(new Object[] { temp.getNum(), temp.getEmp_id(), temp.getEmp_name(), temp.getPlace(),
					temp.getWHATDAY(), temp.getProcessing() });
		}

		model.fireTableDataChanged();
	}

	private void init() {
		// 테이블 모델 생성
		model = new DefaultTableModel();

		// 열 이름
		model.addColumn("no");
		model.addColumn("사 번");
		model.addColumn("사원 이름");
		model.addColumn("신청 지역");
		model.addColumn("신청 날짜");
		model.addColumn("진행 상태");

		// 테이블 생성 및 모델 설정
		table = new JTable(model);
		JTableHeader header = table.getTableHeader();
		 header.setReorderingAllowed(false);
		 header.setBackground(Color.LIGHT_GRAY);

		// 스크롤 가 능한 테이블 패널 생성
		JScrollPane scroll = new JScrollPane(table);

		add(scroll);

		JPanel panel = new JPanel();
		add("South", panel);

		btn = new JButton("새로고침");
		btn.setSize(50, 50);

		btn1 = new JButton("신청하기");
		btn1.setSize(100, 50);

		btn2 = new JButton("삭제하기");
		btn2.setSize(150, 50);

		panel.add(btn);
		panel.add(btn1);
		panel.add(btn2);

		// 새로고침 버튼 액션
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

				Font mainFt = new Font("굴림", Font.BOLD, 30);
				Font ft = new Font("굴림", Font.BOLD, 14);

				JLabel titleLabel = new JLabel("출장 신청서");
				titleLabel.setBounds(176, 25, 188, 100);
				titleLabel.setFont(mainFt);
				panel.add(titleLabel, BorderLayout.CENTER);

				idField = new JTextField();
				idField.setBounds(213, 157, 116, 21);
				panel.add(idField);
				idField.setColumns(10);

				idLabel = new JLabel("사 번");
				idLabel.setBounds(144, 160, 57, 15);
				panel.add(idLabel);

				nameField = new JTextField();
				nameField.setBounds(213, 202, 116, 21);
				panel.add(nameField);
				nameField.setColumns(10);

				nameLabel = new JLabel("이 름");
				nameLabel.setBounds(144, 205, 57, 15);
				panel.add(nameLabel);

				String[] box = { "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8" };
				JComboBox comboBox = new JComboBox(box);
				comboBox.setBounds(213, 242, 116, 23);
				panel.add(comboBox);

				JLabel comboLabel = new JLabel("출장지");
				comboLabel.setBounds(145, 246, 57, 15);
				panel.add(comboLabel);

				JLabel useDateLabel = new JLabel("출장 날짜");
				useDateLabel.setBounds(145, 287, 57, 15);
				panel.add(useDateLabel);

				useDateField = new JTextField(4);
				useDateField.setBounds(214, 284, 116, 21);
				panel.add(useDateField);
				useDateField.setColumns(4);

				JButton btn1 = new JButton("제출");
				btn1.setBounds(147, 512, 76, 50);
				btn1.setPreferredSize(new Dimension(90, 30));
				btn1.setFont(ft);
				panel.add(btn1);

				JButton btn2 = new JButton("취소");
				btn2.setBounds(275, 512, 76, 50);
				btn2.setPreferredSize(new Dimension(90, 30));
				btn2.setFont(ft);
				panel.add(btn2);

				frame.add(panel);

				frame.setVisible(true);
				panel.repaint();

				// 확인
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
//								JOptionPane.showMessageDialog(null, "사번과 이름이 일치하지 않습니다.","error",JOptionPane.ERROR_MESSAGE);
					//	} else {
								BusinessTripVO vo = new BusinessTripVO(emp_id, name, type, usedate);
								BusinessTripDAO.insert(vo);
							//}
						}

						else {
							JOptionPane.showMessageDialog(btn1, "빈칸이 있습니다");
							return;
						}

						// 입력 유효성 검사 및 정보 출력
						if (idField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(btn2, "사번을 입력하세요.");
						} else if (nameField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(btn2, "이름을 입력하세요.");
						} else if (useDateField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(btn2, "날짜를 입력하세요.");
						} else {
							JOptionPane.showMessageDialog(btn2, "제출되었습니다.");
						}
						// 날짜
						System.out.println("날짜 : " + useDateField.getText());

						// 사번
						System.out.println("사번 : " + idField.getText());

						// 이름
						System.out.println("이름 : " + nameField.getText());

						// 신청유형 : 콤보박스 내용 String형으로 가져오기
						System.out.println("츌장지 : " + comboBox.getSelectedItem().toString());

						// 추가 후 새로고침
						//refreshTable();

						dispose();
						new BusinessTripGUI();

					}

				});

				// 취소
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
				// 선택한 행의 인덱스 가져오기
				int index = table.getSelectedRow();

				// 행이 선택된 경우
				if (index >= 0) {
					// 테이블 모델에서 행 제거
					model.removeRow(index);

					// 목록에서 해당 객체 제거
					list.remove(index);

					// 테이블 표시 갱신
					table.revalidate();
					table.repaint();

					// 데이터베이스에서 이 항목을 삭제하는 DAO 메서드 호출
					BusinessTripDAO.delete(index);

					//refreshTable();

				} else {
					// 행이 선택되지 않은 경우 사용자에게 메시지 표시
					JOptionPane.showMessageDialog(null, "삭제할 행을 선택하세요.", "선택된 행 없음", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}
//
//	private void refreshTable() {
//		// 데이터를 새로고침하고 테이블 모델에 새로운 데이터를 설정함.
//
//		list = BusinessTripDAO.select();
//
//		// 모든 행이 삭제됨.
//		model.setRowCount(0);
//
//		// 다시 가져옴
//		for (BusinessTripVO vo : list) {
//			// 한 행씩 추가하기
//			model.addRow(new Object[] { vo.getType(), vo.getEmp_id(), vo.getEmp_name() });
//		}
//
//	}

	public static void main(String[] args) {
		// new BusinessTripGUI();
	}
}
