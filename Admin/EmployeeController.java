package Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Admin.AdminPage;
import login.LoginVO;


public class EmployeeController {

	private ArrayList<EmployeeVO> list, searchNamelist , searchFirstlist , searchDeptlist;
	private DefaultTableModel newModel;
	private JTable table;
	Scanner sc = new Scanner(System.in);

	
	//자세히 보기
	public void detail(JTable table, ArrayList<EmployeeVO> list) {
		int selectRow = table.getSelectedRow();
		System.out.println("컨트롤러 detail 실행");
		System.out.println(list.get(selectRow).getEMP_NAME());
		if(selectRow != -1) {


	        // 테이블 데이터 설정
	        String[] columnNames = {"항목", "내용"};
	        Object[][] data = {
	            {"사번", list.get(selectRow).getEMP_ID()},
	            {"이름", list.get(selectRow).getEMP_NAME()},
	            {"생일", list.get(selectRow).getEMP_BIRTH()},
	            {"이메일", list.get(selectRow).getEMAIL()},
	            {"전화번호", list.get(selectRow).getPHONE()},
	            {"부서코드",list.get(selectRow).getDEPT_CODE()},
	            {"부서명", list.get(selectRow).getDept_name()},
	            {"직급코드", list.get(selectRow).getJOB_CODE()},
	            {"직급명", list.get(selectRow).getJOB_NAME()},
	            {"고용일", list.get(selectRow).getHIRE_DATE()}
	        };
	       
	        newModel = new DefaultTableModel(data, columnNames) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // 모든 셀에 대해 편집 불가
	            }
	        };
			
			// 새로운 테이블 생성 및 설정
			JTable newTable = new JTable(newModel);
			
			JTableHeader header = newTable.getTableHeader();
			header.setReorderingAllowed(false);
			header.setBackground(Color.LIGHT_GRAY);
			
			newTable.setPreferredSize(EmployeeManage.tablePanel.getSize());
	        newTable.setRowHeight(30);
	        newTable.setFont(AdminPage.ft);
	        newTable.getColumnModel().getColumn(0).setPreferredWidth(100); // 항목 열 너비 조정
	        newTable.getColumnModel().getColumn(1).setPreferredWidth(200); // 내용 열 너비 조정
			JScrollPane newSc = new JScrollPane(newTable);
			newSc.setPreferredSize(new Dimension(500,340));
			newSc.setFont(AdminPage.ft);
			
			JButton back = new JButton("메인화면");
			back.setFont(AdminPage.ft);
			back.setBackground(AdminPage.color);
			back.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
		
					
					AdminPage.mPagePanel.removeAll();
					AdminPage.mPagePanel.add(new EmployeeManage(AdminPage.mPagePanel));
					AdminPage.mPagePanel.revalidate(); 
					AdminPage.mPagePanel.repaint();
					
				}
			});
			// 추가
			EmployeeManage.tablePanel.removeAll();
			EmployeeManage.tablePanel.add(newSc,BorderLayout.CENTER);
			EmployeeManage.tablePanel.add(back,BorderLayout.SOUTH);
			EmployeeManage.tablePanel.revalidate(); 
			EmployeeManage.tablePanel.repaint();
		
		}else {
			//선택한 행이 없으면
			JOptionPane.showMessageDialog(null,"사원을 선택해 주세요.");
		}
	}
	
	//사원 등록
	public void insert(DefaultTableModel model) {
		
		JDialog dialog = new JDialog((Frame) null, "사원 등록", true);
		dialog.setFont(AdminPage.ft);
	    dialog.setLayout(new GridBagLayout());
	    
	    
	    dialog.setFont(AdminPage.ft);
	    dialog.getContentPane().setBackground(AdminPage.Backgroundcolor);
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5, 5, 5, 5);
	    gbc.anchor = GridBagConstraints.WEST;
	    
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    dialog.add(new JLabel("이름: "), gbc);
	    gbc.gridx = 1;
	    JTextField NameField = new JTextField(15);
	    dialog.add(NameField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy = 1;
	    dialog.add(new JLabel("생일: "), gbc);
	    gbc.gridx = 1;
	    JTextField BirthField = new JTextField(15);
	    dialog.add(BirthField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    dialog.add(new JLabel("이메일: "), gbc);
	    gbc.gridx = 1;
	    JTextField EmailField = new JTextField(15);
	    dialog.add(EmailField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy = 3;
	    dialog.add(new JLabel("전화번호: "), gbc);
	    gbc.gridx = 1;
	    JTextField PhoneField = new JTextField(15);
	    dialog.add(PhoneField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy = 4;
	    dialog.add(new JLabel("부서명: "), gbc);
	    gbc.gridx = 1;
	    JLabel DeptLabel = new JLabel("부서명: ");
		String[] deptitems = {"D1(인사부)","D2(총무부)",
				"D3(기획부)","D4(회계부)",
				"D5(품질관리부)","D6(전산부)","D7(영업부)","D8(마케팅부)",
				"D9(생산관리부)"};
		JComboBox<String> DeptBox = new JComboBox<>(deptitems);
		DeptBox.setBackground(AdminPage.Backgroundcolor);
	    dialog.add(DeptBox, gbc);
	    
	    gbc.gridx = 0;
	    gbc.gridy = 5;
	    dialog.add(new JLabel("직급명: "), gbc);
	    gbc.gridx = 1;
	    JLabel JobLabel = new JLabel("직급명: ");
		String[] Jobitems = {"J1(이사)","J2(부장)",
				"J3(차장)","J4(과장)",
				"J5(대리)","J6(주임)","J7(사원)"};
		JComboBox<String> JobBox = new JComboBox<>(Jobitems);
		JobBox.setBackground(AdminPage.Backgroundcolor);
	    dialog.add(JobBox, gbc);

	    
	    gbc.gridx = 0;
	    gbc.gridy = 6;
	    dialog.add(new JLabel("고용일: "), gbc);
	    gbc.gridx = 1;
	    JTextField HireField = new JTextField(15);
	    dialog.add(HireField, gbc);
	    
	    gbc.gridx = 0;
	    gbc.gridy = 7;
	    dialog.add(new JLabel("연봉: "), gbc);
	    gbc.gridx = 1;
	    JTextField SalaryField = new JTextField(15);
	    dialog.add(SalaryField, gbc);
	    
	    gbc.gridx = 0;
	    gbc.gridy = 8;
	    dialog.add(new JLabel("보너스: "), gbc);
	    gbc.gridx = 1;
	    JTextField BonusField = new JTextField(15);
	    dialog.add(BonusField, gbc);
	    gbc.gridx = 2;
	    String[] Bonusitems = {"%","정액제"};
		JComboBox<String> BonusBox = new JComboBox<>(Bonusitems);
		BonusBox.setBackground(AdminPage.Backgroundcolor);
	    dialog.add(BonusBox, gbc);
		
	    
		JButton submitButton = new JButton("등록하기");
		gbc.gridx = 0;
	    gbc.gridy = 9;
	    gbc.gridwidth = 3;
	    gbc.anchor = GridBagConstraints.CENTER;
	    submitButton.setBackground(AdminPage.color);
	    submitButton.setFont(AdminPage.ft);
	    //칸을 가득채우는 함수
	    //gbc.fill = GridBagConstraints.HORIZONTAL;
	    dialog.add(submitButton, gbc);
	    
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(NameField.getText().isEmpty() || BirthField.getText().isEmpty() 
						|| EmailField.getText().isEmpty() || PhoneField.getText().isEmpty()
						|| SalaryField.getText().isEmpty() || HireField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸이 있습니다.! 다시입력해주세요");
				}else if(HireField.getText().length()!=8) {
					JOptionPane.showMessageDialog(null, "고용일의 날짜 형식이 올바르지 않습니다.\nYYYYMMDD 형식으로 입력해주세요");
				}else if(Integer.parseInt(SalaryField.getText().toString()) <= 26400000) {
					JOptionPane.showMessageDialog(null, "연봉이 너무 적습니다.\n26400000 이상으로 입력해주세요");
				}
				else {
				String name = NameField.getText(); 
				String birth = BirthField.getText(); 
				String email = EmailField.getText(); 
				String phone = PhoneField.getText(); 
				String dept = ((String)DeptBox.getSelectedItem()).substring(0,2); 
				String job = ((String)JobBox.getSelectedItem()).substring(0,2);
				int salary = Integer.parseInt(SalaryField.getText().toString());
				int monthly_pay = salary/12;
				String hire_date = HireField.getText(); 
				//보너스 : %, 정액제 
				//%는 0~100 사이 정액제는 300000~monthly_pay 사이
				double bonus  = Double.parseDouble(BonusField.getText().toString());
				
				EmployeeVO vo = new EmployeeVO(name,birth,email,
						phone, dept, job, hire_date);
				EmployeeDao.insert(vo);
				System.out.println("직원정보 추가 성공");

				// 추가 후 새로고침
				list = EmployeeDao.select();
				//%일경우
				if(BonusBox.getSelectedItem().equals("%")) {
					if(bonus <0 || bonus > 100) {
						JOptionPane.showMessageDialog(null, 
								"보너스(%)는 (0~100)사이의 숫자로 입력해주세요");
						return;
					}else {
						bonus = bonus/100;
						int lastIndex = list.size() - 1;
						int emp_id = list.get(lastIndex).getEMP_ID();
						System.out.println("방금 추가된 직원의 emp_id는 "+emp_id+"입니다.");
						SalaryVO salaryvo = new SalaryVO(emp_id, salary, monthly_pay, bonus);
						EmployeeDao.insertSalary(salaryvo);
						System.out.println("급여정보 보너스(%)로 추가 성공");
					}
				}//정액제일경우
				else {
					if(bonus < 0 || bonus > monthly_pay) {
						System.out.println(monthly_pay);
						JOptionPane.showMessageDialog(null, 
								"보너스(정액제)는 (300000~"+monthly_pay+"(월급))사이의 숫자로 입력해주세요");
						return;
					}else {
						int lastIndex = list.size() - 1;
						int emp_id = list.get(lastIndex).getEMP_ID();
						System.out.println("방금 추가된 직원의 emp_id는 "+emp_id+"입니다.");
						SalaryVO salaryvo = new SalaryVO(emp_id, salary, monthly_pay, bonus);
						EmployeeDao.insertSalary(salaryvo);
						System.out.println("급여정보 추가 성공");
					}
				}
					
				// 모든 행이 삭제된다.
				model.setRowCount(0);
					
				for (EmployeeVO temp : list) {
					// 한 행씩 추가하기
					model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getDept_name(),
					});
				}
			}
			}
	
		
		});
		dialog.setBackground(AdminPage.Backgroundcolor);
	    dialog.pack();
	    dialog.setLocationRelativeTo(null);
	    dialog.setVisible(true);
		
	}
	//이름 전체로 검색하는 함수
	public void searchName(JPanel tablePabel, String name) {
		
		//찾는 이름이 있는지 검색하는 함수
		boolean check = EmployeeDao.checkName(name);
		if(!check) {
			//찾는 이름이 없으면
			JOptionPane.showMessageDialog(null, "찾는 사원의 정보가 없습니다.!");
			AdminPage.mPagePanel.removeAll();
			AdminPage.mPagePanel.add(new EmployeeManage(AdminPage.mPagePanel));
			AdminPage.mPagePanel.revalidate(); 
			AdminPage.mPagePanel.repaint();
		}else {
			//이름 찾음
			
			ArrayList<EmployeeVO> searchlist = EmployeeDao.search(name);
			DefaultTableModel viewModel = new DefaultTableModel()
			{
	      
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // 모든 셀에 대해 편집 불가
	            }
	        };

			// 열이름
	        viewModel.addColumn("사번");
	        viewModel.addColumn("이름");
	        viewModel.addColumn("부서명");

			for (EmployeeVO temp : searchlist) {
				// 한 행씩 추가하기
				viewModel.addRow(new Object[] { temp.getEMP_ID(), 
						temp.getEMP_NAME(),temp.getDept_name()
				});
				System.out.println(temp.getEMP_NAME());
			}
			
			// 새로운 테이블 생성 및 설정
			JTable viewTable = new JTable(viewModel);
			
			JTableHeader header = viewTable.getTableHeader();
			header.setReorderingAllowed(false);
			header.setBackground(Color.LIGHT_GRAY);
			viewTable.setFont(AdminPage.ft);
			viewTable.setRowHeight(30);
			
			JScrollPane viewSc = new JScrollPane(viewTable);
			
			viewTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            public void valueChanged(ListSelectionEvent event) {
	            	//이벤트 발생한경우
	                if (!event.getValueIsAdjusting()) {
	                	
	                	searchNamelist = EmployeeDao.search(name);
	                	detail(viewTable,searchNamelist);
	                }
	            }
	        });

			// 추가
			tablePabel.add(viewSc);
			
			
		}
		
	}
	//성으로 직원 검색하는 함수
	public void searchFirstName(JPanel tablePabel, String name) {
		
		String Firstname = name;
		//찾는 이름이 있는지 검색하는 함수
		System.out.println("성씨: "+Firstname);
		
		boolean check = EmployeeDao.checkFirstName(Firstname);
		if(!check) {
			//찾는 이름이 없으면
			JOptionPane.showMessageDialog(null, "찾는 사원의 정보가 없습니다.!");
			AdminPage.mPagePanel.removeAll();
			AdminPage.mPagePanel.add(new EmployeeManage(AdminPage.mPagePanel));
			AdminPage.mPagePanel.revalidate(); 
			AdminPage.mPagePanel.repaint();
		}
		else {
			
			ArrayList<EmployeeVO> searchlist = EmployeeDao.searchFirstname(Firstname);
			//실제 화면에 띄울 table
			DefaultTableModel viewModel = new DefaultTableModel()
			{
	      
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // 모든 셀에 대해 편집 불가
	            }
	        };

			// 열이름
	        viewModel.addColumn("사번");
	        viewModel.addColumn("이름");
	        viewModel.addColumn("부서명");

			for (EmployeeVO temp : searchlist) {
				// 한 행씩 추가하기
				viewModel.addRow(new Object[] { temp.getEMP_ID(), 
						temp.getEMP_NAME(),temp.getDept_name()
				});
				System.out.println(temp.getEMP_NAME());
			}
			
			// 새로운 테이블 생성 및 설정
			JTable viewTable = new JTable(viewModel);
			viewTable.setFont(AdminPage.ft);
			viewTable.setRowHeight(30);
			JTableHeader header = viewTable.getTableHeader();
			header.setReorderingAllowed(false);
			header.setBackground(Color.LIGHT_GRAY);
			
			JScrollPane viewSc = new JScrollPane(viewTable);
			
			
			viewTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            public void valueChanged(ListSelectionEvent event) {
	            	//이벤트 발생한경우
	                if (!event.getValueIsAdjusting()) {
	                	searchFirstlist = EmployeeDao.searchFirstname(Firstname);
	                	detail(viewTable,searchFirstlist);
	                }
	            }
	        });

			// 추가
			tablePabel.add(viewSc);
			
		}
		
	}
	//부서 검색 함수
	public void searchDept(JPanel tablePanel, String dept) {
		String Dept = dept;
		//찾는 이름이 있는지 검색하는 함수
		System.out.println("부서코드: "+dept);
		
		ArrayList<EmployeeVO> searchdeptlist = EmployeeDao.searchDept(dept);
		//실제 화면에 띄울 table
		DefaultTableModel viewModel = new DefaultTableModel()
		{
	      
	           @Override
	           public boolean isCellEditable(int row, int column) {
	               return false; // 모든 셀에 대해 편집 불가
	           }
	    };

			// 열이름
	    	viewModel.addColumn("부서명");
	        viewModel.addColumn("사번");
	        viewModel.addColumn("이름");

			for (EmployeeVO temp : searchdeptlist) {
				// 한 행씩 추가하기
				viewModel.addRow(new Object[] { temp.getDept_name(),temp.getEMP_ID(), 
						temp.getEMP_NAME()
				});
				System.out.println(temp.getEMP_NAME());
			}
			
			// 새로운 테이블 생성 및 설정
			JTable viewTable = new JTable(viewModel);
			viewTable.setFont(AdminPage.ft);
			viewTable.setRowHeight(30);
			JTableHeader header = viewTable.getTableHeader();
			header.setReorderingAllowed(false);
			header.setBackground(Color.LIGHT_GRAY);
			
			JScrollPane viewSc = new JScrollPane(viewTable);

			
			viewTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            public void valueChanged(ListSelectionEvent event) {
	            	//이벤트 발생한경우
	                if (!event.getValueIsAdjusting()) {
	                	searchDeptlist = EmployeeDao.searchDept(dept);
	                	detail(viewTable,searchDeptlist);
	                }
	            }
	        });

			// 추가

			tablePanel.add(viewSc);

		
	}
	

	//부서 이동
	public void deptUpdate(DefaultTableModel model) {

		//전체 패널
		JPanel UpdateDeptPanel = new JPanel();
		
		//테두리
		LineBorder border = new LineBorder(AdminPage.color, 5);
		UpdateDeptPanel.setBorder(border);
		
		//이름 패널
		JPanel NamePanel = new JPanel();
		JLabel NameLabel = new JLabel("이름: ");
		JTextField NameField = new JTextField(10);
		NamePanel.add(NameLabel);
		NamePanel.add(NameField);
		
		
		//부서 패널
		JPanel DeptPanel = new JPanel();
		JLabel DeptLabel = new JLabel("부서명: ");
		String[] deptitems = {"D1(인사부)","D2(총무부)",
				"D3(기획부)","D4(회계부)",
				"D5(품질관리부)","D6(전산부)","D7(영업부)","D8(마케팅부)",
				"D9(생산관리부)"};
		JComboBox<String> DeptBox = new JComboBox<>(deptitems);
		DeptBox.setBackground(AdminPage.Backgroundcolor);
		DeptPanel.add(DeptLabel);
		DeptPanel.add(DeptBox);

		JPanel btnPanel = new JPanel();
		JButton submitButton = new JButton("부서 이동 하기");
		submitButton.setBackground(AdminPage.color);
		
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("컨트롤러 deptupdate 제출버튼 클릭");
				String name = NameField.getText();
				String dept = ((String)DeptBox.getSelectedItem()).substring(0,2); 
				boolean check = EmployeeDao.checkName(name);
				
				if(!check) {
					//찾는 이름이 없으면
					JOptionPane.showMessageDialog(submitButton, 
							"부서 이동할 사원이 존재하지 않습니다.!");
					
				}else {
					//업데이트 실행
					EmployeeDao.update(name,dept);
					
					// 업데이트 후 새로 고침
					list = EmployeeDao.select();
					// 모든 행을 삭제
					model.setRowCount(0);
					

					for (EmployeeVO temp : list) {
						// 한 행씩 추가하기
						model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getDept_name(),
								});
					}
					
				}	
			}
		});
		btnPanel.add(submitButton);
		
		NamePanel.setBackground(AdminPage.Backgroundcolor);
		DeptPanel.setBackground(AdminPage.Backgroundcolor);
		btnPanel.setBackground(AdminPage.Backgroundcolor);
		
		UpdateDeptPanel.add(NamePanel);
		UpdateDeptPanel.add(DeptPanel);
		UpdateDeptPanel.add(btnPanel);
		
		UpdateDeptPanel.setBounds(30, 80, 200, 180);
		UpdateDeptPanel.setBackground(AdminPage.Backgroundcolor);
		EmployeeManage.buttonPanel.removeAll();
		EmployeeManage.buttonPanel.add(UpdateDeptPanel);
		EmployeeManage.buttonPanel.revalidate(); 
		EmployeeManage.buttonPanel.repaint();
		
	}

	//직금 이동
	public void jobUpdate(DefaultTableModel model) {

		JPanel UpdateJobPanel = new JPanel();
		
		LineBorder border = new LineBorder(AdminPage.color, 5);
		UpdateJobPanel.setBorder(border);
		
		//이름 패널
		JPanel NamePanel = new JPanel();
		JLabel NameLabel = new JLabel("이름: ");
		JTextField NameField = new JTextField(10);
		NamePanel.add(NameLabel);
		NamePanel.add(NameField);
		
		//부서 패널
		JPanel JobPanel = new JPanel();
		JLabel JobLabel = new JLabel("직급명: ");
		String[] Jobitems = {"J1(이사)","J2(부장)",
				"J3(차장)","J4(과장)",
				"J5(대리)","J6(주임)","J7(사원)"};
		JComboBox<String> JobBox = new JComboBox<>(Jobitems);
		JobBox.setBackground(AdminPage.Backgroundcolor);
		JobPanel.add(JobLabel);
		JobPanel.add(JobBox);

		JPanel btnPanel = new JPanel();
		JButton submitButton = new JButton("직급 이동 하기");
		submitButton.setBackground(AdminPage.color);
		
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("컨트롤러 deptupdate 제출버튼 클릭");
				String name = NameField.getText();
				String job = ((String)JobBox.getSelectedItem()).substring(0,2); 
				boolean check = EmployeeDao.checkName(name);
				
				if(!check) {
					//찾는 이름이 없으면
					JOptionPane.showMessageDialog(submitButton, 
							"직급 이동할 사원이 존재하지 않습니다.!");
					
				}else {
					//업데이트 실행
					EmployeeDao.updateJob(name,job);
					
					// 업데이트 후 새로 고침
					list = EmployeeDao.select();
					// 모든 행을 삭제
					model.setRowCount(0);
					

					for (EmployeeVO temp : list) {
						// 한 행씩 추가하기
						model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), temp.getDept_name(),
								});
					}
					
				}	
			}
		});
		btnPanel.add(submitButton);
		NamePanel.setBackground(AdminPage.Backgroundcolor);
		JobPanel.setBackground(AdminPage.Backgroundcolor);
		btnPanel.setBackground(AdminPage.Backgroundcolor);
		
		UpdateJobPanel.add(NamePanel);
		UpdateJobPanel.add(JobPanel);
		UpdateJobPanel.add(btnPanel);
		
		UpdateJobPanel.setBounds(50, 80, 170, 200);
		UpdateJobPanel.setBackground(AdminPage.Backgroundcolor);
		EmployeeManage.buttonPanel.removeAll();
		EmployeeManage.buttonPanel.add(UpdateJobPanel);
		EmployeeManage.buttonPanel.revalidate(); 
		EmployeeManage.buttonPanel.repaint();

		
	}

	public void delete() {
		
		System.out.println("controller delete");
		JPanel deletePanel = new JPanel();
		
		LineBorder border = new LineBorder(AdminPage.color, 5);
		deletePanel.setBorder(border);
		
		
		//id입력
		JPanel idPanel = new JPanel();
		JLabel idLabel = new JLabel("사원번호: ");
		JTextField idField = new JTextField(10);
		idPanel.add(idLabel);
		idPanel.add(idField);
		
		JPanel btnPanel = new JPanel();
		JButton deletebtn = new JButton("삭제하기");
		deletebtn.setBackground(AdminPage.color);
		
		deletebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					//id필드가 비어있으면
					if(idField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "id를 입력해주세요!");	
					}else {
					
						EmployeeVO empvo = EmployeeDao.searchID(Integer.parseInt(idField.getText().toString()));
						if(empvo!=null ) {
							
							JFrame pwFrame = new JFrame("관리자 확인");
							pwFrame.setFont(AdminPage.ft);
							
							JPanel MessagePanel = new JPanel();
							MessagePanel.setBackground(AdminPage.Backgroundcolor);
							JLabel nameLabel = new JLabel("선택한 사람은 " +empvo.getDept_name()+ "의 "
												+empvo.getEMP_NAME()+"입니다.");
							JLabel MessageLabel = new JLabel("정말 삭제하실려면 \n관리자 비밀번호를 입력해주세요");
							nameLabel.setFont(AdminPage.ft);
							MessageLabel.setFont(AdminPage.ft);
							
							JPasswordField pwField = new JPasswordField(14);
							JButton submitbtn = new JButton("입력하기");
							submitbtn.setBackground(AdminPage.color);
							
							MessagePanel.add(nameLabel);
							MessagePanel.add(MessageLabel);
							MessagePanel.add(pwField);
							MessagePanel.add(submitbtn);
							
							pwFrame.add(MessagePanel,BorderLayout.CENTER);
							pwFrame.setBounds(500,300,400,200);
							pwFrame.setVisible(true);
							
							submitbtn.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									String inputPw = pwField.getText();
									//패스워드를 가져오는 함수
									LoginVO loginvo = EmployeeDao.passwordFind(223);
									if(inputPw.equals(loginvo.getPw())) {
										//삭제 실행
										
										EmployeeDao.delete(empvo);
										System.out.println("삭제 성공");
										JOptionPane.showMessageDialog(null, "삭제되었습니다.");
										refreshEMP();
											
									}else {
										JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력해주세요");
									}	
								}
							
							});
						}else {
							JOptionPane.showMessageDialog(null, "해당 ID가 없습니다.");
						}
					}
				}catch(NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "id는 숫자입니다.");
				}
			}
		});
			
		btnPanel.add(deletebtn);
		idPanel.setBackground(AdminPage.Backgroundcolor);
		btnPanel.setBackground(AdminPage.Backgroundcolor);
		deletePanel.add(idPanel);
		deletePanel.add(btnPanel);
		
		deletePanel.setBounds(30, 80, 200, 170);
		deletePanel.setBackground(AdminPage.Backgroundcolor);
		EmployeeManage.buttonPanel.removeAll();
		EmployeeManage.buttonPanel.add(deletePanel);
		EmployeeManage.buttonPanel.revalidate(); 
		EmployeeManage.buttonPanel.repaint();

		
	}

	public void refreshEMP() {
		AdminPage.mPagePanel.removeAll();
		AdminPage.mPagePanel.add(new EmployeeManage(AdminPage.mPagePanel));
		AdminPage.mPagePanel.revalidate(); 
		AdminPage.mPagePanel.repaint();
	}



}

