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

	
	//�ڼ��� ����
	public void detail(JTable table, ArrayList<EmployeeVO> list) {
		int selectRow = table.getSelectedRow();
		System.out.println("��Ʈ�ѷ� detail ����");
		System.out.println(list.get(selectRow).getEMP_NAME());
		if(selectRow != -1) {


	        // ���̺� ������ ����
	        String[] columnNames = {"�׸�", "����"};
	        Object[][] data = {
	            {"���", list.get(selectRow).getEMP_ID()},
	            {"�̸�", list.get(selectRow).getEMP_NAME()},
	            {"����", list.get(selectRow).getEMP_BIRTH()},
	            {"�̸���", list.get(selectRow).getEMAIL()},
	            {"��ȭ��ȣ", list.get(selectRow).getPHONE()},
	            {"�μ��ڵ�",list.get(selectRow).getDEPT_CODE()},
	            {"�μ���", list.get(selectRow).getDept_name()},
	            {"�����ڵ�", list.get(selectRow).getJOB_CODE()},
	            {"���޸�", list.get(selectRow).getJOB_NAME()},
	            {"�����", list.get(selectRow).getHIRE_DATE()}
	        };
	       
	        newModel = new DefaultTableModel(data, columnNames) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // ��� ���� ���� ���� �Ұ�
	            }
	        };
			
			// ���ο� ���̺� ���� �� ����
			JTable newTable = new JTable(newModel);
			
			JTableHeader header = newTable.getTableHeader();
			header.setReorderingAllowed(false);
			header.setBackground(Color.LIGHT_GRAY);
			
			newTable.setPreferredSize(EmployeeManage.tablePanel.getSize());
	        newTable.setRowHeight(30);
	        newTable.setFont(AdminPage.ft);
	        newTable.getColumnModel().getColumn(0).setPreferredWidth(100); // �׸� �� �ʺ� ����
	        newTable.getColumnModel().getColumn(1).setPreferredWidth(200); // ���� �� �ʺ� ����
			JScrollPane newSc = new JScrollPane(newTable);
			newSc.setPreferredSize(new Dimension(500,340));
			newSc.setFont(AdminPage.ft);
			
			JButton back = new JButton("����ȭ��");
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
			// �߰�
			EmployeeManage.tablePanel.removeAll();
			EmployeeManage.tablePanel.add(newSc,BorderLayout.CENTER);
			EmployeeManage.tablePanel.add(back,BorderLayout.SOUTH);
			EmployeeManage.tablePanel.revalidate(); 
			EmployeeManage.tablePanel.repaint();
		
		}else {
			//������ ���� ������
			JOptionPane.showMessageDialog(null,"����� ������ �ּ���.");
		}
	}
	
	//��� ���
	public void insert(DefaultTableModel model) {
		
		JDialog dialog = new JDialog((Frame) null, "��� ���", true);
		dialog.setFont(AdminPage.ft);
	    dialog.setLayout(new GridBagLayout());
	    
	    
	    dialog.setFont(AdminPage.ft);
	    dialog.getContentPane().setBackground(AdminPage.Backgroundcolor);
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5, 5, 5, 5);
	    gbc.anchor = GridBagConstraints.WEST;
	    
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    dialog.add(new JLabel("�̸�: "), gbc);
	    gbc.gridx = 1;
	    JTextField NameField = new JTextField(15);
	    dialog.add(NameField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy = 1;
	    dialog.add(new JLabel("����: "), gbc);
	    gbc.gridx = 1;
	    JTextField BirthField = new JTextField(15);
	    dialog.add(BirthField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    dialog.add(new JLabel("�̸���: "), gbc);
	    gbc.gridx = 1;
	    JTextField EmailField = new JTextField(15);
	    dialog.add(EmailField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy = 3;
	    dialog.add(new JLabel("��ȭ��ȣ: "), gbc);
	    gbc.gridx = 1;
	    JTextField PhoneField = new JTextField(15);
	    dialog.add(PhoneField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy = 4;
	    dialog.add(new JLabel("�μ���: "), gbc);
	    gbc.gridx = 1;
	    JLabel DeptLabel = new JLabel("�μ���: ");
		String[] deptitems = {"D1(�λ��)","D2(�ѹ���)",
				"D3(��ȹ��)","D4(ȸ���)",
				"D5(ǰ��������)","D6(�����)","D7(������)","D8(�����ú�)",
				"D9(���������)"};
		JComboBox<String> DeptBox = new JComboBox<>(deptitems);
		DeptBox.setBackground(AdminPage.Backgroundcolor);
	    dialog.add(DeptBox, gbc);
	    
	    gbc.gridx = 0;
	    gbc.gridy = 5;
	    dialog.add(new JLabel("���޸�: "), gbc);
	    gbc.gridx = 1;
	    JLabel JobLabel = new JLabel("���޸�: ");
		String[] Jobitems = {"J1(�̻�)","J2(����)",
				"J3(����)","J4(����)",
				"J5(�븮)","J6(����)","J7(���)"};
		JComboBox<String> JobBox = new JComboBox<>(Jobitems);
		JobBox.setBackground(AdminPage.Backgroundcolor);
	    dialog.add(JobBox, gbc);

	    
	    gbc.gridx = 0;
	    gbc.gridy = 6;
	    dialog.add(new JLabel("�����: "), gbc);
	    gbc.gridx = 1;
	    JTextField HireField = new JTextField(15);
	    dialog.add(HireField, gbc);
	    
	    gbc.gridx = 0;
	    gbc.gridy = 7;
	    dialog.add(new JLabel("����: "), gbc);
	    gbc.gridx = 1;
	    JTextField SalaryField = new JTextField(15);
	    dialog.add(SalaryField, gbc);
	    
	    gbc.gridx = 0;
	    gbc.gridy = 8;
	    dialog.add(new JLabel("���ʽ�: "), gbc);
	    gbc.gridx = 1;
	    JTextField BonusField = new JTextField(15);
	    dialog.add(BonusField, gbc);
	    gbc.gridx = 2;
	    String[] Bonusitems = {"%","������"};
		JComboBox<String> BonusBox = new JComboBox<>(Bonusitems);
		BonusBox.setBackground(AdminPage.Backgroundcolor);
	    dialog.add(BonusBox, gbc);
		
	    
		JButton submitButton = new JButton("����ϱ�");
		gbc.gridx = 0;
	    gbc.gridy = 9;
	    gbc.gridwidth = 3;
	    gbc.anchor = GridBagConstraints.CENTER;
	    submitButton.setBackground(AdminPage.color);
	    submitButton.setFont(AdminPage.ft);
	    //ĭ�� ����ä��� �Լ�
	    //gbc.fill = GridBagConstraints.HORIZONTAL;
	    dialog.add(submitButton, gbc);
	    
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(NameField.getText().isEmpty() || BirthField.getText().isEmpty() 
						|| EmailField.getText().isEmpty() || PhoneField.getText().isEmpty()
						|| SalaryField.getText().isEmpty() || HireField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "��ĭ�� �ֽ��ϴ�.! �ٽ��Է����ּ���");
				}else if(HireField.getText().length()!=8) {
					JOptionPane.showMessageDialog(null, "������� ��¥ ������ �ùٸ��� �ʽ��ϴ�.\nYYYYMMDD �������� �Է����ּ���");
				}else if(Integer.parseInt(SalaryField.getText().toString()) <= 26400000) {
					JOptionPane.showMessageDialog(null, "������ �ʹ� �����ϴ�.\n26400000 �̻����� �Է����ּ���");
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
				//���ʽ� : %, ������ 
				//%�� 0~100 ���� �������� 300000~monthly_pay ����
				double bonus  = Double.parseDouble(BonusField.getText().toString());
				
				EmployeeVO vo = new EmployeeVO(name,birth,email,
						phone, dept, job, hire_date);
				EmployeeDao.insert(vo);
				System.out.println("�������� �߰� ����");

				// �߰� �� ���ΰ�ħ
				list = EmployeeDao.select();
				//%�ϰ��
				if(BonusBox.getSelectedItem().equals("%")) {
					if(bonus <0 || bonus > 100) {
						JOptionPane.showMessageDialog(null, 
								"���ʽ�(%)�� (0~100)������ ���ڷ� �Է����ּ���");
						return;
					}else {
						bonus = bonus/100;
						int lastIndex = list.size() - 1;
						int emp_id = list.get(lastIndex).getEMP_ID();
						System.out.println("��� �߰��� ������ emp_id�� "+emp_id+"�Դϴ�.");
						SalaryVO salaryvo = new SalaryVO(emp_id, salary, monthly_pay, bonus);
						EmployeeDao.insertSalary(salaryvo);
						System.out.println("�޿����� ���ʽ�(%)�� �߰� ����");
					}
				}//�������ϰ��
				else {
					if(bonus < 0 || bonus > monthly_pay) {
						System.out.println(monthly_pay);
						JOptionPane.showMessageDialog(null, 
								"���ʽ�(������)�� (300000~"+monthly_pay+"(����))������ ���ڷ� �Է����ּ���");
						return;
					}else {
						int lastIndex = list.size() - 1;
						int emp_id = list.get(lastIndex).getEMP_ID();
						System.out.println("��� �߰��� ������ emp_id�� "+emp_id+"�Դϴ�.");
						SalaryVO salaryvo = new SalaryVO(emp_id, salary, monthly_pay, bonus);
						EmployeeDao.insertSalary(salaryvo);
						System.out.println("�޿����� �߰� ����");
					}
				}
					
				// ��� ���� �����ȴ�.
				model.setRowCount(0);
					
				for (EmployeeVO temp : list) {
					// �� �྿ �߰��ϱ�
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
	//�̸� ��ü�� �˻��ϴ� �Լ�
	public void searchName(JPanel tablePabel, String name) {
		
		//ã�� �̸��� �ִ��� �˻��ϴ� �Լ�
		boolean check = EmployeeDao.checkName(name);
		if(!check) {
			//ã�� �̸��� ������
			JOptionPane.showMessageDialog(null, "ã�� ����� ������ �����ϴ�.!");
			AdminPage.mPagePanel.removeAll();
			AdminPage.mPagePanel.add(new EmployeeManage(AdminPage.mPagePanel));
			AdminPage.mPagePanel.revalidate(); 
			AdminPage.mPagePanel.repaint();
		}else {
			//�̸� ã��
			
			ArrayList<EmployeeVO> searchlist = EmployeeDao.search(name);
			DefaultTableModel viewModel = new DefaultTableModel()
			{
	      
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // ��� ���� ���� ���� �Ұ�
	            }
	        };

			// ���̸�
	        viewModel.addColumn("���");
	        viewModel.addColumn("�̸�");
	        viewModel.addColumn("�μ���");

			for (EmployeeVO temp : searchlist) {
				// �� �྿ �߰��ϱ�
				viewModel.addRow(new Object[] { temp.getEMP_ID(), 
						temp.getEMP_NAME(),temp.getDept_name()
				});
				System.out.println(temp.getEMP_NAME());
			}
			
			// ���ο� ���̺� ���� �� ����
			JTable viewTable = new JTable(viewModel);
			
			JTableHeader header = viewTable.getTableHeader();
			header.setReorderingAllowed(false);
			header.setBackground(Color.LIGHT_GRAY);
			viewTable.setFont(AdminPage.ft);
			viewTable.setRowHeight(30);
			
			JScrollPane viewSc = new JScrollPane(viewTable);
			
			viewTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            public void valueChanged(ListSelectionEvent event) {
	            	//�̺�Ʈ �߻��Ѱ��
	                if (!event.getValueIsAdjusting()) {
	                	
	                	searchNamelist = EmployeeDao.search(name);
	                	detail(viewTable,searchNamelist);
	                }
	            }
	        });

			// �߰�
			tablePabel.add(viewSc);
			
			
		}
		
	}
	//������ ���� �˻��ϴ� �Լ�
	public void searchFirstName(JPanel tablePabel, String name) {
		
		String Firstname = name;
		//ã�� �̸��� �ִ��� �˻��ϴ� �Լ�
		System.out.println("����: "+Firstname);
		
		boolean check = EmployeeDao.checkFirstName(Firstname);
		if(!check) {
			//ã�� �̸��� ������
			JOptionPane.showMessageDialog(null, "ã�� ����� ������ �����ϴ�.!");
			AdminPage.mPagePanel.removeAll();
			AdminPage.mPagePanel.add(new EmployeeManage(AdminPage.mPagePanel));
			AdminPage.mPagePanel.revalidate(); 
			AdminPage.mPagePanel.repaint();
		}
		else {
			
			ArrayList<EmployeeVO> searchlist = EmployeeDao.searchFirstname(Firstname);
			//���� ȭ�鿡 ��� table
			DefaultTableModel viewModel = new DefaultTableModel()
			{
	      
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // ��� ���� ���� ���� �Ұ�
	            }
	        };

			// ���̸�
	        viewModel.addColumn("���");
	        viewModel.addColumn("�̸�");
	        viewModel.addColumn("�μ���");

			for (EmployeeVO temp : searchlist) {
				// �� �྿ �߰��ϱ�
				viewModel.addRow(new Object[] { temp.getEMP_ID(), 
						temp.getEMP_NAME(),temp.getDept_name()
				});
				System.out.println(temp.getEMP_NAME());
			}
			
			// ���ο� ���̺� ���� �� ����
			JTable viewTable = new JTable(viewModel);
			viewTable.setFont(AdminPage.ft);
			viewTable.setRowHeight(30);
			JTableHeader header = viewTable.getTableHeader();
			header.setReorderingAllowed(false);
			header.setBackground(Color.LIGHT_GRAY);
			
			JScrollPane viewSc = new JScrollPane(viewTable);
			
			
			viewTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            public void valueChanged(ListSelectionEvent event) {
	            	//�̺�Ʈ �߻��Ѱ��
	                if (!event.getValueIsAdjusting()) {
	                	searchFirstlist = EmployeeDao.searchFirstname(Firstname);
	                	detail(viewTable,searchFirstlist);
	                }
	            }
	        });

			// �߰�
			tablePabel.add(viewSc);
			
		}
		
	}
	//�μ� �˻� �Լ�
	public void searchDept(JPanel tablePanel, String dept) {
		String Dept = dept;
		//ã�� �̸��� �ִ��� �˻��ϴ� �Լ�
		System.out.println("�μ��ڵ�: "+dept);
		
		ArrayList<EmployeeVO> searchdeptlist = EmployeeDao.searchDept(dept);
		//���� ȭ�鿡 ��� table
		DefaultTableModel viewModel = new DefaultTableModel()
		{
	      
	           @Override
	           public boolean isCellEditable(int row, int column) {
	               return false; // ��� ���� ���� ���� �Ұ�
	           }
	    };

			// ���̸�
	    	viewModel.addColumn("�μ���");
	        viewModel.addColumn("���");
	        viewModel.addColumn("�̸�");

			for (EmployeeVO temp : searchdeptlist) {
				// �� �྿ �߰��ϱ�
				viewModel.addRow(new Object[] { temp.getDept_name(),temp.getEMP_ID(), 
						temp.getEMP_NAME()
				});
				System.out.println(temp.getEMP_NAME());
			}
			
			// ���ο� ���̺� ���� �� ����
			JTable viewTable = new JTable(viewModel);
			viewTable.setFont(AdminPage.ft);
			viewTable.setRowHeight(30);
			JTableHeader header = viewTable.getTableHeader();
			header.setReorderingAllowed(false);
			header.setBackground(Color.LIGHT_GRAY);
			
			JScrollPane viewSc = new JScrollPane(viewTable);

			
			viewTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            public void valueChanged(ListSelectionEvent event) {
	            	//�̺�Ʈ �߻��Ѱ��
	                if (!event.getValueIsAdjusting()) {
	                	searchDeptlist = EmployeeDao.searchDept(dept);
	                	detail(viewTable,searchDeptlist);
	                }
	            }
	        });

			// �߰�

			tablePanel.add(viewSc);

		
	}
	

	//�μ� �̵�
	public void deptUpdate(DefaultTableModel model) {

		//��ü �г�
		JPanel UpdateDeptPanel = new JPanel();
		
		//�׵θ�
		LineBorder border = new LineBorder(AdminPage.color, 5);
		UpdateDeptPanel.setBorder(border);
		
		//�̸� �г�
		JPanel NamePanel = new JPanel();
		JLabel NameLabel = new JLabel("�̸�: ");
		JTextField NameField = new JTextField(10);
		NamePanel.add(NameLabel);
		NamePanel.add(NameField);
		
		
		//�μ� �г�
		JPanel DeptPanel = new JPanel();
		JLabel DeptLabel = new JLabel("�μ���: ");
		String[] deptitems = {"D1(�λ��)","D2(�ѹ���)",
				"D3(��ȹ��)","D4(ȸ���)",
				"D5(ǰ��������)","D6(�����)","D7(������)","D8(�����ú�)",
				"D9(���������)"};
		JComboBox<String> DeptBox = new JComboBox<>(deptitems);
		DeptBox.setBackground(AdminPage.Backgroundcolor);
		DeptPanel.add(DeptLabel);
		DeptPanel.add(DeptBox);

		JPanel btnPanel = new JPanel();
		JButton submitButton = new JButton("�μ� �̵� �ϱ�");
		submitButton.setBackground(AdminPage.color);
		
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("��Ʈ�ѷ� deptupdate �����ư Ŭ��");
				String name = NameField.getText();
				String dept = ((String)DeptBox.getSelectedItem()).substring(0,2); 
				boolean check = EmployeeDao.checkName(name);
				
				if(!check) {
					//ã�� �̸��� ������
					JOptionPane.showMessageDialog(submitButton, 
							"�μ� �̵��� ����� �������� �ʽ��ϴ�.!");
					
				}else {
					//������Ʈ ����
					EmployeeDao.update(name,dept);
					
					// ������Ʈ �� ���� ��ħ
					list = EmployeeDao.select();
					// ��� ���� ����
					model.setRowCount(0);
					

					for (EmployeeVO temp : list) {
						// �� �྿ �߰��ϱ�
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

	//���� �̵�
	public void jobUpdate(DefaultTableModel model) {

		JPanel UpdateJobPanel = new JPanel();
		
		LineBorder border = new LineBorder(AdminPage.color, 5);
		UpdateJobPanel.setBorder(border);
		
		//�̸� �г�
		JPanel NamePanel = new JPanel();
		JLabel NameLabel = new JLabel("�̸�: ");
		JTextField NameField = new JTextField(10);
		NamePanel.add(NameLabel);
		NamePanel.add(NameField);
		
		//�μ� �г�
		JPanel JobPanel = new JPanel();
		JLabel JobLabel = new JLabel("���޸�: ");
		String[] Jobitems = {"J1(�̻�)","J2(����)",
				"J3(����)","J4(����)",
				"J5(�븮)","J6(����)","J7(���)"};
		JComboBox<String> JobBox = new JComboBox<>(Jobitems);
		JobBox.setBackground(AdminPage.Backgroundcolor);
		JobPanel.add(JobLabel);
		JobPanel.add(JobBox);

		JPanel btnPanel = new JPanel();
		JButton submitButton = new JButton("���� �̵� �ϱ�");
		submitButton.setBackground(AdminPage.color);
		
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("��Ʈ�ѷ� deptupdate �����ư Ŭ��");
				String name = NameField.getText();
				String job = ((String)JobBox.getSelectedItem()).substring(0,2); 
				boolean check = EmployeeDao.checkName(name);
				
				if(!check) {
					//ã�� �̸��� ������
					JOptionPane.showMessageDialog(submitButton, 
							"���� �̵��� ����� �������� �ʽ��ϴ�.!");
					
				}else {
					//������Ʈ ����
					EmployeeDao.updateJob(name,job);
					
					// ������Ʈ �� ���� ��ħ
					list = EmployeeDao.select();
					// ��� ���� ����
					model.setRowCount(0);
					

					for (EmployeeVO temp : list) {
						// �� �྿ �߰��ϱ�
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
		
		
		//id�Է�
		JPanel idPanel = new JPanel();
		JLabel idLabel = new JLabel("�����ȣ: ");
		JTextField idField = new JTextField(10);
		idPanel.add(idLabel);
		idPanel.add(idField);
		
		JPanel btnPanel = new JPanel();
		JButton deletebtn = new JButton("�����ϱ�");
		deletebtn.setBackground(AdminPage.color);
		
		deletebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					//id�ʵ尡 ���������
					if(idField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "id�� �Է����ּ���!");	
					}else {
					
						EmployeeVO empvo = EmployeeDao.searchID(Integer.parseInt(idField.getText().toString()));
						if(empvo!=null ) {
							
							JFrame pwFrame = new JFrame("������ Ȯ��");
							pwFrame.setFont(AdminPage.ft);
							
							JPanel MessagePanel = new JPanel();
							MessagePanel.setBackground(AdminPage.Backgroundcolor);
							JLabel nameLabel = new JLabel("������ ����� " +empvo.getDept_name()+ "�� "
												+empvo.getEMP_NAME()+"�Դϴ�.");
							JLabel MessageLabel = new JLabel("���� �����ϽǷ��� \n������ ��й�ȣ�� �Է����ּ���");
							nameLabel.setFont(AdminPage.ft);
							MessageLabel.setFont(AdminPage.ft);
							
							JPasswordField pwField = new JPasswordField(14);
							JButton submitbtn = new JButton("�Է��ϱ�");
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
									//�н����带 �������� �Լ�
									LoginVO loginvo = EmployeeDao.passwordFind(223);
									if(inputPw.equals(loginvo.getPw())) {
										//���� ����
										
										EmployeeDao.delete(empvo);
										System.out.println("���� ����");
										JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
										refreshEMP();
											
									}else {
										JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٽ� �Է����ּ���");
									}	
								}
							
							});
						}else {
							JOptionPane.showMessageDialog(null, "�ش� ID�� �����ϴ�.");
						}
					}
				}catch(NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "id�� �����Դϴ�.");
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

