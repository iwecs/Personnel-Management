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

		// 기존에 있는 데이터 가져오기
		list = BusinessTripDAO.selectAdmin();

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
			model.addRow(new Object[] { temp.getNum(), temp.getEmp_id(), temp.getEmp_name(), temp.getPlace(), temp.getWHATDAY(),temp.getProcessing() });
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
							System.out.println("num 값이 같지 않습니다.");
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

						Font mainFt = new Font("굴림", Font.BOLD, 30);
						Font ft = new Font("굴림", Font.BOLD, 14);

						titleLabel = new JLabel("출장 신청서");
						titleLabel.setBounds(176, 25, 188, 100);
						titleLabel.setFont(mainFt);
						panel.add(titleLabel, BorderLayout.CENTER);

						idField = new JTextField(getForm.getEmp_id());
						idField.setBounds(213, 157, 116, 21);
						panel.add(idField);
						idField.setColumns(10);

						idLabel = new JLabel("사 번");
						idLabel.setBounds(144, 160, 57, 15);
						panel.add(idLabel);

						nameField = new JTextField(getForm.getEmp_name());
						nameField.setBounds(213, 202, 116, 21);
						panel.add(nameField);
						nameField.setColumns(10);

						nameLabel = new JLabel("이 름");
						nameLabel.setBounds(144, 205, 57, 15);
						panel.add(nameLabel);
						
						getCombo = new JLabel(getForm.getType());
						getCombo.setBounds(181, 222, 116, 23);
						panel.add(getCombo);
						
//						String[] box = { "P1","P2","P3","P4","P5","P6","P7","P8" };
//						JComboBox comboBox = new JComboBox(box);
//						comboBox.setBounds(213, 242, 116, 23);
//						panel.add(comboBox);

						JLabel comboLabel = new JLabel("출장지");
						comboLabel.setBounds(145, 246, 57, 15);
						panel.add(comboLabel);

						JLabel useDateLabel = new JLabel("출장 날짜");
						useDateLabel.setBounds(145, 287, 57, 15);
						panel.add(useDateLabel);

						getUsedate = new JLabel(getForm.getType());
						getUsedate.setBounds(214, 284, 116, 21);
						panel.add(getUsedate);

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

				// 반려
				btn3.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int index = table.getSelectedRow() + 1;
						System.out.println("버튼 : " + index);

						if (index >= 0) {
							index -= 1;
							int num = (int) table.getValueAt(index, 1); // 사번 컬럼의 값을 가져옴
							System.out.println(num);
							BusinessTripDAO.reject(index + 1, num);
						}
					}
				});

				// 삭제
				btn4.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						int index = table.getSelectedRow();

						if (index >= 0) {
							int num = (int) table.getValueAt(index, 1); // 사번 컬럼의 값을 가져옴
							// 테이블 모델에서 행 제거
							model.removeRow(index);

							// 목록에서 해당 객체 제거
							list.remove(index);

							// 테이블 표시 갱신
							table.revalidate();
							table.repaint();

							// 데이터베이스에서 이 항목을 삭제하는 DAO 메서드 호출
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
