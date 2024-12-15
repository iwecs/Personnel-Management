package User;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Admin.AdminPage;
import AnnualPaidLeave.AnnualPDAO;
import AnnualPaidLeave.AnnualPGUI;
import AnnualPaidLeave.appFormVO;
import BusinessTrip.BusinessTripDAO;
import BusinessTrip.BusinessTripGUI;
import BusinessTrip.BusinessTripVO;
import Commute.CommuteDAO;
import Commute.CommuteVO;
import Commute.Service;
import login.loginGUI;
import notice_user.NoticeBoardGUI;
import report.ReportBoard;

public class UserPage extends JFrame {

	static JPanel mMenuPanel, mPagePanel, mMenubarPanel;
	UserController controller = new UserController();
	private ArrayList<CommuteVO> commList = new ArrayList<CommuteVO>();
	private ArrayList<appFormVO> appList = new ArrayList<appFormVO>();
	private ArrayList<BusinessTripVO> list = new ArrayList<BusinessTripVO>();
	private JTable table;
	private DefaultTableModel model;
	private JPanel contentPane;
	private JTextField titleField, idField, nameField, contentField, useDateField;
	private JLabel tiLabel, idLabel, idlabel, namelabel, nameLabel;
	private JButton btn, btn1, btn2, btn3;
	private int inputId;
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	static Font ft = new Font("맑은 고딕", Font.BOLD, 15);
	static Color color = new Color(150, 195, 222);
	static Color Backgroundcolor = Color.white;

	public UserPage(int inputId, String title, int x, int y, int width, int height) {

		this.inputId = inputId;
		initContainer(title, x, y, width, height);
		initMenu();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	private void initContainer(String title, int x, int y, int width, int height) {

		setLayout(null);
		setTitle(title);
		setBounds(x, y, width, height);
		mPagePanel = new JPanel();
		mPagePanel.setLayout(null);
		mPagePanel.setBounds(0, 150, width, 450);
		mPagePanel.setBackground(Backgroundcolor);
		add(mPagePanel);

		information();
		line();
		logo();
		logout();
	}

	private void logo() {

		JPanel logoPanel = new JPanel();
		logoPanel.setBackground(Color.white);
		logoPanel.setBounds(30, 30, 140, 100);

		// 원본 이미지 로드
		ImageIcon logoIcon = new ImageIcon("./image/logo.png");

		// 원하는 크기로 이미지 크기 조정
		Image originalImage = logoIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(140, 100, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel logoLabel = new JLabel(scaledIcon);
		logoPanel.add(logoLabel);
		add(logoPanel);

	}

	private void logout() {
		JPanel logoutPanel = new JPanel();
		logoutPanel.setLayout(null);
		logoutPanel.setBounds(800, 30, 100, 30);
		logoutPanel.setBackground(Color.white);
		ImageIcon logoutIcon = new ImageIcon("./image/logout.png");

		// 원하는 크기로 이미지 크기 조정
		Image originalImage = logoutIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(100, 30, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		// logoutbtn = new JButton(scaledIcon);
		JButton logoutbtn = new JButton("로그아웃");
		logoutbtn.setFont(ft);
		logoutbtn.setBackground(new Color(150, 195, 222));
		// logoutbtn.setBorderPainted(false);
		logoutbtn.setSize(100, 30);
		logoutPanel.add(logoutbtn);
		add(logoutPanel);

		logoutbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Service ser = new Service();
				try {
					ser.service_logout(inputId);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
	}

	private void information() {
		JPanel inforPanel = new JPanel();
		inforPanel.setBounds(0, 660, 820, 100);
		inforPanel.setBackground(Color.white);

		Font ft = new Font("맑은 고딕", Font.PLAIN, 12);
		JLabel inforLabel1 = new JLabel(
				"Anchae                                  대표이사 : 김만수 	               	 사업자 등록번호 : 123-45-67891 			                             이메일 : anchae@an.net");
		inforLabel1.setFont(ft);
		inforLabel1.setForeground(Color.LIGHT_GRAY);
		inforPanel.add(inforLabel1);

		JLabel inforLabel2 = new JLabel(
				"주소 : (06626) 서울 서초구 서초대로길74길 45, 3층(서초동, 엔데버타워)	                           연락처 : 02-1234-5678                      Fax : 02-1234-5679");
		inforLabel2.setFont(ft);
		inforLabel2.setForeground(Color.LIGHT_GRAY);
		inforPanel.add(inforLabel2);

		add(inforPanel);
	}

	private void line() {
		JPanel linePanel = new JPanel();
		linePanel.setBounds(0, 590, 1000, 110);
		linePanel.setBackground(Color.pink);

		// 원본 이미지 로드
		ImageIcon lineIcon = new ImageIcon("./image/Line.png");

		// 원하는 크기로 이미지 크기 조정
		Image originalImage = lineIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(1000, 110, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel llineLabel = new JLabel(scaledIcon);

		linePanel.add(llineLabel);
		add(linePanel);
	}

	private void initMenu() {

		JPanel barpanel = new JPanel(new BorderLayout());
		getContentPane().setBackground(Color.white);
		JMenuBar menubar = new JMenuBar();
		menubar.setBackground(new Color(150, 195, 222));

		Font font = new Font("맑은 고딕", Font.BOLD, 28);

		logo();

		// 1. 사원관리
		JMenu menu01 = new JMenu("사원 관리");
		menu01.setFont(font);

		// 사원정보 클릭 이벤트
		menu01.addMouseListener((MouseListener) new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				mPagePanel.removeAll();
				controller.info(inputId);
				mPagePanel.add(UserController.findPanel);
				mPagePanel.revalidate();
				mPagePanel.repaint();
			}
		});

		// 2. 급여 관리
		JMenu menu02 = new JMenu("급여 관리");
		menu02.setFont(font);
		menu02.addMouseListener((MouseListener) new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mPagePanel.removeAll();
				SalaryPage salaryPage = new SalaryPage(inputId);
				mPagePanel.add(salaryPage.getTablePanel());
				mPagePanel.add(salaryPage.getTravelDetailsPanel());

				mPagePanel.revalidate();
				mPagePanel.repaint();
			}
		});

		// 3. 근태관리
		JMenu menu03 = new JMenu("근태 관리");

		JMenuItem item05 = new JMenuItem("출퇴근 조회");
		JMenuItem item06 = new JMenuItem("출장 신청/조회");
		JMenuItem item07 = new JMenuItem("연차 신청/조회");

		menu03.setFont(font);
		// 출퇴근 조회 클릭 이벤트
		item05.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				mPagePanel.removeAll();

				commList = CommuteDAO.selectCommute(inputId);

				model = new DefaultTableModel();

				// 열 이름
				model.addColumn("사번");
				model.addColumn("사원명");
				model.addColumn("날짜");
				model.addColumn("근태");
				model.addColumn("퇴근");

				// 테이블 생성 및 모델 설정
				table = new JTable(model);

				for (CommuteVO temp : commList) {

					// 연차 갯수 가져오기
//					int leaveCount = leaveCount(); // 직원의 연차 갯수를 가져오는 메서드 호출

					// 한 행씩 추가하기
					model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getMONTH_DATE(),
							temp.getComm(), temp.getEnd() });

				}

				// 스크롤 가능한 테이블 패널 생성
				JScrollPane scroll = new JScrollPane(table);
				scroll.setBounds(20, 0, 940, 350); // 테이블 위치 및 크기 조정
				add(scroll);

				JPanel panel = new JPanel();
//				add(panel, BorderLayout.SOUTH);

				btn1 = new JButton("확인");
				btn1.setBounds(400, 420, 100, 30); // 버튼 위치 및 크기 조정
				btn1.setBackground(color);

				panel.add(btn1);

				mPagePanel.add(btn1);
				mPagePanel.add(scroll);

				btn1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});

				refreshTable_Comm(inputId);

				mPagePanel.revalidate();
				mPagePanel.repaint();

			}
		});

		// 출장 클릭 이벤트
		item06.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				mPagePanel.removeAll();

				list = BusinessTripDAO.select(inputId);

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
				for (BusinessTripVO temp : list) {

					// 한 행씩 추가하기
					model.addRow(new Object[] { temp.getNum(), temp.getEmp_id(), temp.getEmp_name(), temp.getPlace(),
							temp.getWHATDAY(), temp.getProcessing() });
				}

				// 스크롤 가 능한 테이블 패널 생성
				JScrollPane scroll = new JScrollPane(table);
				scroll.setBounds(20, 0, 940, 400); // 테이블 위치 및 크기 조정
				mPagePanel.add(scroll);

				// 버튼 생성 및 추가
				JButton btnRefresh = new JButton("새로고침");
				btnRefresh.setBounds(350, 420, 100, 30); // 버튼 위치 및 크기 조정
				btnRefresh.setBackground(new Color(150, 195, 222));
				mPagePanel.add(btnRefresh); // 패널에 버튼 추가

				JButton btnApply = new JButton("신청하기");
				btnApply.setBounds(450, 420, 100, 30); // 버튼 위치 및 크기 조정
				btnApply.setBackground(color);
				mPagePanel.add(btnApply); // 패널에 버튼 추가

				JButton btnDelete = new JButton("삭제하기");
				btnDelete.setBounds(550, 420, 100, 30); // 버튼 위치 및 크기 조정
				btnDelete.setBackground(color);
				mPagePanel.add(btnDelete);

				// 새로고침 버튼 액션
				btnRefresh.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						repaint();

					}

				});

				btnApply.addActionListener(new ActionListener() {

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

						String id = String.valueOf(inputId);
						idlabel = new JLabel(id);
						idlabel.setBounds(213, 157, 116, 21);
						panel.add(idlabel);

						idLabel = new JLabel("사 번");
						idLabel.setBounds(144, 160, 57, 15);
						panel.add(idLabel);

						String name = BusinessTripDAO.sreach(inputId);
						namelabel = new JLabel(name);
						namelabel.setBounds(213, 202, 116, 21);
						panel.add(namelabel);
						
//						idField = new JTextField();
//						idField.setBounds(213, 157, 116, 21);
//						panel.add(idField);
//						idField.setColumns(10);
//
//						idLabel = new JLabel("사 번");
//						idLabel.setBounds(144, 160, 57, 15);
//						panel.add(idLabel);
//
//						nameField = new JTextField();
//						nameField.setBounds(213, 202, 116, 21);
//						panel.add(nameField);
//						nameField.setColumns(10);
//
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


						btn1.addActionListener(new ActionListener() {

							 @Override
							public void actionPerformed(ActionEvent e) {

							if (idlabel != null) {
							int emp_id = inputId;
							String name = BusinessTripDAO.sreach(inputId);
							String type = (String) comboBox.getSelectedItem();
									 
							String usedate = useDateField.getText();
									 
							 if (isValidDateFormat(usedate)) {
								 if (isAfterCurrentDate(usedate)) {
								 JOptionPane.showMessageDialog(null, "저장되었습니다.");
								 } else {
								 JOptionPane.showMessageDialog(null, "현재 날짜 이후의 날짜만 입력 가능합니다.");
								 }
								 } else {
								 JOptionPane.showMessageDialog(null, "올바른 날짜 형식이 아닙니다. yyyy-MM-dd 형식으로 입력해주세요.");
								 }
							 // 위의 조건문에서 리턴되지 않았다면 유효한 날짜 형식이므로 이하의 코드 실행
							 BusinessTripVO vo = new BusinessTripVO(emp_id, name, type, usedate);
							 BusinessTripDAO.insert(vo);
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
								refreshTable_Business(inputId);

								dispose();
								initMenu();

							}

						});

						// 취소
						btn2.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								initMenu();

							}

						});
					}

				});

				btnDelete.addActionListener(new ActionListener() {

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

							refreshTable_Business(inputId);

						} else {
							// 행이 선택되지 않은 경우 사용자에게 메시지 표시
							JOptionPane.showMessageDialog(null, "삭제할 행을 선택하세요.", "선택된 행 없음", JOptionPane.ERROR_MESSAGE);
						}
					}

				});

				mPagePanel.revalidate();
				mPagePanel.repaint();

			}
		});

		// 연차 클릭 이벤트
		item07.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				mPagePanel.removeAll();

				commList = CommuteDAO.leaveCount(inputId);

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
//				for (CommuteVO temp2 : commList) {
//
//					for (appFormVO temp : appList) {
//
//						if (temp2.getEMP_ID() == temp.getEmp_id()) {
//
//							// 한 행씩 추가하기
//							model.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(),
//									temp.getUseDate(), temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(),
//									temp.getProcessing(), temp2.getLeaveCount() });
//						}
//					}
//				}

				for (appFormVO temp : appList) {

					// 한 행씩 추가하기
					model.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(), temp.getUseDate(),
							temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(), temp.getProcessing(),
							temp.getLeave() });

				}

				// 스크롤 가 능한 테이블 패널 생성
				JScrollPane scroll = new JScrollPane(table);
				scroll.setBounds(30, 0, 940, 400); // 테이블 위치 및 크기 조정
				mPagePanel.add(scroll);

				// 버튼 생성 및 추가
				JButton btnRefresh = new JButton("새로고침");
				btnRefresh.setBounds(350, 420, 100, 30); // 버튼 위치 및 크기 조정
				btnRefresh.setBackground(color);
				mPagePanel.add(btnRefresh); // 패널에 버튼 추가

				JButton btnApply = new JButton("신청하기");
				btnApply.setBounds(450, 420, 100, 30); // 버튼 위치 및 크기 조정
				btnApply.setBackground(color);
				mPagePanel.add(btnApply); // 패널에 버튼 추가

				JButton btnDelete = new JButton("삭제하기");
				btnDelete.setBounds(550, 420, 100, 30); // 버튼 위치 및 크기 조정
				btnDelete.setBackground(color);
				mPagePanel.add(btnDelete);

				// 새로고침 버튼 액션
				btnRefresh.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						repaint();

					}

				});

				btnApply.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						setVisible(true);

						setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						setBounds(100, 100, 1000, 745);
						setBackground(Color.WHITE);
						contentPane = new JPanel();
						contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

						setContentPane(contentPane);
						contentPane.setLayout(null);

						JPanel panel = new JPanel();
						panel.setBounds(223, 10, 516, 686);
						panel.setBorder(new LineBorder(Color.BLACK));
						panel.setBackground(Color.WHITE);
						contentPane.add(panel);
						panel.setLayout(null);

						Font mainFt = new Font("굴림", Font.BOLD, 28);
						Font ft = new Font("굴림", Font.BOLD, 14);

						JLabel titleLabel = new JLabel("연차 신청서");
						titleLabel.setBounds(176, 25, 188, 100);
						titleLabel.setFont(mainFt);
						panel.add(titleLabel, BorderLayout.CENTER);

						titleField = new JTextField();
						titleField.setBounds(181, 93, 224, 21);
						panel.add(titleField);
						titleField.setColumns(10);

						tiLabel = new JLabel("제 목");
						tiLabel.setBounds(112, 96, 57, 15);
						panel.add(tiLabel);

						
						idlabel = new JLabel(String.valueOf(inputId));
						idlabel.setBounds(181, 138, 116, 21);
						panel.add(idlabel);

						nameLabel = new JLabel("사원명");
						nameLabel.setBounds(113, 183, 57, 15);
						panel.add(nameLabel);

						String name = BusinessTripDAO.sreach(inputId);
						namelabel = new JLabel(name);
						namelabel.setBounds(181, 180, 116, 21);
						panel.add(namelabel);
						
						
						idLabel = new JLabel("사 번");
						idLabel.setBounds(112, 141, 57, 15);
						panel.add(idLabel);

//						idField = new JTextField();
//						idField.setBounds(181, 138, 116, 21);
//						panel.add(idField);
//						idField.setColumns(10);

						nameLabel = new JLabel("이 름");
						nameLabel.setBounds(113, 183, 57, 15);
						panel.add(nameLabel);
//
//						nameField = new JTextField();
//						nameField.setBounds(181, 180, 116, 21);
//						panel.add(nameField);
//						nameField.setColumns(10);

						String[] box = { "연차", "오전반차", "오후반차" };
						JComboBox comboBox = new JComboBox(box);
						comboBox.setBounds(181, 222, 116, 23);
						panel.add(comboBox);

						JLabel comboLabel = new JLabel("사용 유형");
						comboLabel.setBounds(113, 226, 57, 15);
						panel.add(comboLabel);

						JLabel useDateLabel = new JLabel("사용 날짜");
						useDateLabel.setBounds(112, 271, 57, 15);
						panel.add(useDateLabel);

						useDateField = new JTextField(4);
						useDateField.setBounds(181, 268, 116, 21);
						panel.add(useDateField);
						useDateField.setColumns(4);

						JLabel contentLabel = new JLabel("사용 사유");
						contentLabel.setBounds(113, 314, 57, 15);
						panel.add(contentLabel);

						contentField = new JTextField();
						contentField.setBounds(113, 339, 292, 59);
						panel.add(contentField);
						contentField.setColumns(10);

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

						panel.repaint();

						// 확인
						btn1.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								
								if (idlabel != null) {
									int emp_id = inputId;
								    String name = BusinessTripDAO.sreach(inputId);

									String title = titleField.getText();
									String useDate = useDateField.getText();
									String type = (String) comboBox.getSelectedItem();
									String content = contentField.getText();

									appFormVO vo = new appFormVO(title, type, useDate, emp_id, name, content);
									AnnualPDAO.insert(vo);
								
								} else {
									JOptionPane.showMessageDialog(btn1, "빈칸이 있습니다");
									return;
								}

								// 입력 유효성 검사 및 정보 출력
								if (titleField.getText().isEmpty()) {
									JOptionPane.showMessageDialog(btn2, "제목을 입력하세요.");
								} else if (idField.getText().isEmpty()) {
									JOptionPane.showMessageDialog(btn2, "사번을 입력하세요.");
								} else if (nameField.getText().isEmpty()) {
									JOptionPane.showMessageDialog(btn2, "이름을 입력하세요.");
								} else if (contentField.getText().isEmpty()) {
									JOptionPane.showMessageDialog(btn2, "신청 사유를 입력하세요.");
								} else {
									JOptionPane.showMessageDialog(btn2, "제출되었습니다.");
								}

								// 제목
								System.out.println("제목 : " + titleField.getText());

								// 날짜
								System.out.println("날짜 : " + useDateField.getText());

								// 사번
								System.out.println("사번 : " + idField.getText());

								// 이름
								System.out.println("이름 : " + nameField.getText());

								// 신청유형 : 콤보박스 내용 String형으로 가져오기
								System.out.println("신청 유형 : " + comboBox.getSelectedItem().toString());

								// 신청 사유
								System.out.println("신청 사유 : " + contentField.getText());

								// 추가 후 새로고침
								refreshTable_Annual(inputId);

								JOptionPane.showMessageDialog(btn, "신청이 완료 되었습니다.");

								dispose();
								initMenu();

							}

						});

						// 취소
						btn2.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								initMenu();

							}

						});

//						newFrame.setVisible(true);
					}

				});

				btnDelete.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// 선택한 행의 인덱스 가져오기
						int index = table.getSelectedRow();

						// 행이 선택된 경우
						if (index >= 0) {
							// 테이블 모델에서 행 제거
							model.removeRow(index);

							// 목록에서 해당 객체 제거
							appList.remove(index);

							// 테이블 표시 갱신
							table.revalidate();
							table.repaint();

							// 데이터베이스에서 이 항목을 삭제하는 DAO 메서드 호출
							AnnualPDAO.delete(index);

							refreshTable_Annual(inputId);

						} else {
							// 행이 선택되지 않은 경우 사용자에게 메시지 표시
							JOptionPane.showMessageDialog(null, "삭제할 행을 선택하세요.", "선택된 행 없음", JOptionPane.ERROR_MESSAGE);
						}

					}

				});

				mPagePanel.revalidate();
				mPagePanel.repaint();
			}
		});

		menu03.add(item05);
		menu03.add(item06);
		menu03.add(item07);


		// 4. 보고 관리
		JMenu menu04 = new JMenu("보고 관리");
		JMenuItem item08 = new JMenuItem("업무 전달");
		JMenuItem item09 = new JMenuItem("공지사항(게시글) 조회");

				menu04.setFont(font);
				// 업무 전달 클릭 이벤트
				item08.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						SwingUtilities.invokeLater(() -> new ReportBoard().setVisible(true)); /// 수정
					}
				});///


		// 공지사항 조회 클릭 이벤트
		item09.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new NoticeBoardGUI().setVisible(true));

			}
		});
		menu04.add(item08);
		menu04.add(item09);

		// 메뉴들을 메뉴 바에 추가
		menubar.add(menu01);
		menubar.add(menu02);
		menubar.add(menu03);
		menubar.add(menu04);

		mMenubarPanel = new JPanel();
		mMenubarPanel.setBounds(200, 80, 550, 50);
		mMenubarPanel.setBackground(Color.white);
		mMenubarPanel.add(menubar);

		add(mMenubarPanel);

	}

//	private void refreshTable() {
//		// 데이터를 새로고침하고 테이블 모델에 새로운 데이터를 설정함.
//
//		appList = AnnualPDAO.select();
//
//		// 모든 행이 삭제됨.
//		model.setRowCount(0);
//
//		// 다시 가져옴
//		for (appFormVO vo : appList) {
//			// 한 행씩 추가하기
//			model.addRow(new Object[] { vo.getTitle(), vo.getType(), vo.getUseDate(), vo.getEmp_id(), vo.getEmp_name(),
//					vo.getContent() });
//		}
//
//	}
	// 출퇴근
	private void refreshTable_Comm(int inputId) {
		commList = CommuteDAO.selectCommute(inputId);

		for (CommuteVO temp : commList) {

			// 한 행씩 추가하기
			model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getMONTH_DATE(), temp.getComm(),
					temp.getEnd() });

		}
	}

	// 출장
	private void refreshTable_Business(int inputId) {

		list = BusinessTripDAO.select(inputId);

		model.setRowCount(0);

		for (BusinessTripVO temp : list) {

			// 한 행씩 추가하기
			model.addRow(new Object[] { temp.getNum(), temp.getEmp_id(), temp.getEmp_name(), temp.getPlace(),
					temp.getWHATDAY(), temp.getProcessing() });
		}

	}

	// 연차
	private void refreshTable_Annual(int inputId) {

		// 데이터를 새로고침하고 테이블 모델에 새로운 데이터를 설정함.
		commList = CommuteDAO.leaveCount(inputId);

		// 모든 행이 삭제됨.
		model.setRowCount(0);

		for (appFormVO temp : appList) {

			// 한 행씩 추가하기
			model.addRow(new Object[] { temp.getNum(), temp.getTitle(), temp.getType(), temp.getUseDate(),
					temp.getEmp_id(), temp.getEmp_name(), temp.getWriteDate(), temp.getProcessing(), temp.getLeave() });
		}

	}
	public static boolean isValidDateFormat(String dateStr) {
		 // 날짜 형식을 나타내는 정규 표현식
		 String regex = "\\d{4}-\\d{2}-\\d{2}";

		 // 정규 표현식을 사용하여 형식을 확인
		 return dateStr.matches(regex);
		 }

		 public static boolean isAfterCurrentDate(String dateStr) {
		 try {
		 LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
		 LocalDate today = LocalDate.now();

		 // 현재 날짜 이후이면 true 반환
		 return !date.isBefore(today);
		 } catch (DateTimeParseException e) {
		 return false;
		 }
		 }

	public static void main(String[] args) {
		// UserPage up = new UserPage("사원 모드", 0,0,1000,750);

	}
}