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
		setLayout(null); // 레이아웃 매니저를 null로 설정
		setBackground(Color.white);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1, 10, 40));
		buttonPanel.setBackground(Color.white);
		buttonPanel.setBounds(50, 50, 170, 300);

		button1 = new JButton("출퇴근 인식");
		button2 = new JButton("출장");
		button3 = new JButton("연차");
		
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

				// 테이블 모델 생성
				model = new DefaultTableModel();

				model.setRowCount(0); // 기존 행 삭제

				// 열 이름
				model.addColumn("사번");
				model.addColumn("사원명");
				model.addColumn("날짜");
				model.addColumn("근태");
				model.addColumn("퇴근");
				model.addColumn("연차");

				// CommuteVO 클래스를 한 행씩 추가하기
				for (CommuteVO temp : list) {
					model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getMONTH_DATE(),
							temp.getComm(), temp.getEnd(), temp.getLeave() });
				}

				System.out.println("CommuteGUI 실행");

				JFrame frame = new JFrame();
				frame.setBounds(400, 400, 1000, 750);
				frame.setLocationRelativeTo(null);

				// 테이블 생성 및 모델 설정
				table = new JTable(model);
				JTableHeader header = table.getTableHeader();
				header.setReorderingAllowed(false);
				header.setBackground(Color.LIGHT_GRAY);

				// 스크롤 가능한 테이블 패널 생성
				JScrollPane scroll = new JScrollPane(table);

				// 메인 패널에 추가
				scroll.setBounds(250, 30, 700, 400);
				add(scroll);

				JPanel panel = new JPanel();
				frame.add(panel, BorderLayout.SOUTH);

				btn1 = new JButton("확인");
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

				// 테이블 모델 생성
				model2 = new DefaultTableModel();

				model2.setRowCount(0); // 기존 행 삭제

				// 열 이름
				model2.addColumn("no");
				model2.addColumn("사 번");
				model2.addColumn("사원 이름");
				model2.addColumn("신청 지역");
				model2.addColumn("신청 날짜");
				model2.addColumn("진행 상태");

			
				// BusinessTripVO 클래스를 한 행씩 추가하기
				for (BusinessTripVO temp : businessList) {

					// 한 행씩 추가하기
					model2.addRow(new Object[] { temp.getNum(), temp.getEmp_id(), temp.getEmp_name(), temp.getPlace(),
							temp.getWHATDAY(), temp.getProcessing() });
				}

				System.out.println("CommuteGUI 실행");

				JFrame frame = new JFrame();
				frame.setBounds(400, 400, 1000, 750);
				frame.setLocationRelativeTo(null);

				// 테이블 생성 및 모델 설정
				table = new JTable(model2);

				// 스크롤 가능한 테이블 패널 생성
				JScrollPane scroll = new JScrollPane(table);

				// 메인 패널에 추가
				scroll.setBounds(250, 30, 700, 400);
				add(scroll);

				// 메인 패널에 추가할 패널 생성
				JPanel buttonPanel = new JPanel();
				buttonPanel.setBounds(250, 450, 700, 100); // 크기 조절 (높이를 100으로 늘림)
				buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 버튼들을 중앙 정렬하고 간격 조절
				buttonPanel.setBackground(Color.white);
				add("south", buttonPanel);

				// 버튼 생성 및 패널에 추가
				JButton btn1 = new JButton("보기");
				btn1.setBackground(color);
				JButton btn2 = new JButton("결재 승인");
				btn2.setBackground(color);
				JButton btn3 = new JButton("반려");
				btn3.setBackground(color);
				JButton btn4 = new JButton("삭제");
				btn4.setBackground(color);

				buttonPanel.add(btn1);
				buttonPanel.add(btn2);
				buttonPanel.add(btn3);
				buttonPanel.add(btn4);

//				revalidate(); 
//				repaint();
				add(buttonPanel);
				

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
							System.out.println("num 값이 같지 않습니다.");
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

						Font mainFt = new Font("굴림", Font.BOLD, 30);
						Font ft = new Font("굴림", Font.BOLD, 14);

						titleLabel = new JLabel("출장 신청서");
						titleLabel.setBounds(176, 25, 188, 100);
						titleLabel.setFont(mainFt);
						panel.add(titleLabel, BorderLayout.CENTER);

						idlabel = new JLabel(String.valueOf(getForm.getEmp_id()));
						idlabel.setBounds(213, 157, 116, 21);
						panel.add(idlabel);

						idLabel = new JLabel("사 번");
						idLabel.setBounds(144, 160, 57, 15);
						panel.add(idLabel);

						namelabel = new JLabel(getForm.getEmp_name());
						namelabel.setBounds(213, 202, 116, 21);
						panel.add(namelabel);

						nameLabel = new JLabel("이 름");
						nameLabel.setBounds(144, 205, 57, 15);
						panel.add(nameLabel);

						getCombo = new JLabel(getForm.getPlace());
						getCombo.setBounds(213, 246, 116, 23);
						panel.add(getCombo);

						JLabel comboLabel = new JLabel("출장지");
						comboLabel.setBounds(145, 246, 57, 15);
						panel.add(comboLabel);

						JLabel useDateLabel = new JLabel("출장 날짜");
						useDateLabel.setBounds(145, 287, 57, 15);
						panel.add(useDateLabel);

						String date = getForm.getWHATDAY();
						String dateText = date.substring(0, Math.min(date.length(), 10));
						getUsedate = new JLabel(dateText);
						getUsedate.setBounds(210, 284, 116, 21);
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
							}
						});

						frame.setVisible(true);
					}
				});

				// 결재 승인
				btn2.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

				
						int index = table.getSelectedRow();
						int temp= (int)table.getValueAt(index, 0);
						
						System.out.println("버튼 : " + index);

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

				// 반려
				btn3.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int index = table.getSelectedRow();
						int temp= (int)table.getValueAt(index, 0);
						System.out.println("버튼 : " + index);

						if (index >= 0) {
							
							int num = (int) table.getValueAt(index, 1); // 사번 컬럼의 값을 가져옴
							System.out.println(num);
							BusinessTripDAO.reject(temp, num);
						}
						button2.doClick();
						refreshTable_Business();
					}
				});

				// 삭제
				btn4.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						int index = table.getSelectedRow() ;
						int temp= (int)table.getValueAt(index, 0);
						System.out.println(index);

						if (temp >= 0) {
							int num = (int) table.getValueAt(index + 1, 1); // 사번 컬럼의 값을 가져옴

							System.out.println(num);
							System.out.println("temp:" +temp);
							System.out.println("index:"+index);

							// 테이블 모델에서 행 제거
							model2.removeRow(temp);
							System.out.println(businessList.size());
							// 목록에서 해당 객체 제거
							businessList.remove(index);
							

							// 테이블 표시 갱신
							table.revalidate();
							table.repaint();

							// 데이터베이스에서 이 항목을 삭제하는 DAO 메서드 호출
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

				// 테이블 모델 생성
				model3 = new DefaultTableModel();
//				DefaultTableModel model3 = new DefaultTableModel();

//				model3.setRowCount(0); // 기존 행 삭제

				// 열 이름
				model3.addColumn("NO");
				model3.addColumn("문서명");
				model3.addColumn("신청 유형");
				model3.addColumn("신청 날짜");
				model3.addColumn("사 번");
				model3.addColumn("사원 이름");
				model3.addColumn("작성일");
				model3.addColumn("진행상태");
				model3.addColumn("연차");

//				for (CommuteVO temp2 : leaveList) {
//
//					for (appFormVO temp : appList) {
//
//						if (temp2.getEMP_ID() == temp.getEmp_id()) {
//
//							// 한 행씩 추가하기
//							model3.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(),
//									temp.getUseDate(), temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(),
//									temp.getProcessing(), temp2.getLeaveCount() });
//
//						}
//					}
//				}

				for (appFormVO temp : appList) {

					// 한 행씩 추가하기
					model3.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(), temp.getUseDate(),
							temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(), temp.getProcessing(),
							temp.getLeave() });

				}

				// 테이블 생성 및 모델 설정
				table3 = new JTable(model3);

				// 스크롤 가능한 테이블 패널 생성
				JScrollPane scroll = new JScrollPane(table3);
				scroll.setBounds(250, 30, 700, 400);
				add(scroll);

				JPanel buttonPanel = new JPanel();
				buttonPanel.setBounds(250, 450, 700, 100); // 크기 조절 (높이를 100으로 늘림)
				buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 버튼들을 중앙 정렬하고 간격 조절
				buttonPanel.setBackground(Color.white);
				add("south", buttonPanel);

				// 버튼 생성 및 패널에 추가
				JButton btn1 = new JButton("보기");
				JButton btn2 = new JButton("결재 승인");
				JButton btn3 = new JButton("반려");
				JButton btn4 = new JButton("삭제");
				btn1.setBackground(color);
				btn2.setBackground(color);
				btn3.setBackground(color);
				btn4.setBackground(color);

				buttonPanel.add(btn1);
				buttonPanel.add(btn2);
				buttonPanel.add(btn3);
				buttonPanel.add(btn4);

				add(buttonPanel);

				// 보기
				btn1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int index = table3.getSelectedRow();
						System.out.println("index : " + index);

						if (index >= 0) {
							index -= 1;
							int num = (int) table3.getValueAt(index, 0); // 사번 컬럼의 값을 가져옴
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

						titleLabel = new JLabel("연차 신청서");
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
							}
						});

						frame.setVisible(true);
					}
				});

				// 결재 승인
				btn2.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						int index = table3.getSelectedRow() + 1;

						System.out.println("버튼 : " + index);

						if (index >= 0) {
							index -= 1;
							int num = (int) table3.getValueAt(index, 4); // 사번 컬럼의 값을 가져옴
							System.out.println(num);
							AnnualPDAO.approval(index + 1, num);
						}
						refreshTable_Annual();
					}
				});

				// 반려
				btn3.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int index = table3.getSelectedRow() + 1;
						System.out.println("버튼 : " + index);

						if (index >= 0) {
							index -= 1;
							int num = (int) table3.getValueAt(index, 4); // 사번 컬럼의 값을 가져옴
							System.out.println(num);
							AnnualPDAO.reject(index + 1, num);

							button3.doClick();
						}
					}
				});

				// 삭제
				btn4.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						int index = table3.getSelectedRow() + 1;

						if (index >= 0) {
							int num = (int) table3.getValueAt(index + 1, 4); // 사번 컬럼의 값을 가져옴
							// 테이블 모델에서 행 제거
							model3.removeRow(index);

							// 목록에서 해당 객체 제거
							list.remove(index);

							// 테이블 표시 갱신
							table3.revalidate();
							table3.repaint();

							// 데이터베이스에서 이 항목을 삭제하는 DAO 메서드 호출
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

	// 출퇴근
	private void refreshTable_Comm() {

		
		list = CommuteDAO.selectAdminCommute();

		model2.setRowCount(0);

		for (CommuteVO temp : list) {

			// 한 행씩 추가하기
			model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getMONTH_DATE(), temp.getComm(),
					temp.getEnd() });

		}
		
	}

	// 출장
	private void refreshTable_Business() {
		
		businessList = BusinessTripDAO.selectAdmin();

		model2.setRowCount(0);

		for (BusinessTripVO temp : businessList) {

			// 한 행씩 추가하기
			model2.addRow(new Object[] { temp.getNum(), temp.getEmp_id(), temp.getEmp_name(), temp.getPlace(),
					temp.getWHATDAY(), temp.getProcessing() });
		}
		//add()
	
	}

	// 연차
	private void refreshTable_Annual() {
		
//		// 데이터를 새로고침하고 테이블 모델에 새로운 데이터를 설정함.
//		appList = CommuteDAO.totaLeaveCount();
//
//		// 모든 행이 삭제됨.
//		model3.setRowCount(0);
//
//		for (appFormVO temp : appList) {
//
//			// 한 행씩 추가하기
//			model3.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(), temp.getUseDate(),
//					temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(), temp.getProcessing(), temp.getLeave() });
//		}
//	
	}

	
}
