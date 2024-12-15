package Admin;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeManage extends JPanel {

	private JTable table;
	private DefaultTableModel model;
	private ArrayList<EmployeeVO> list;
	static JPanel tablePanel, buttonPanel;
	EmployeeVO Employee = new EmployeeVO();
	Scanner sc = new Scanner(System.in);
	EmployeeController controller = new EmployeeController();

	
    public EmployeeManage(JPanel panel) {
      
    	setPreferredSize(panel.getSize());
    	setLayout(null);
    	setBackground(AdminPage.Backgroundcolor);
    	
    	//���̺� �г�
    	tablePanel = new JPanel();
    	tablePanel.setBounds(300, 50, 500, 450);
    	tablePanel.setBackground(AdminPage.Backgroundcolor);
    	tablePanel.setPreferredSize(tablePanel.getSize());
    	
    	//�׵θ�
//    	LineBorder border = new LineBorder(AdminPage.color, 3);
//    	tablePanel.setBorder(border);
    	
		// ������ �ִ� �����͸� ��������
		list = EmployeeDao.select();

		model = new DefaultTableModel()
		{
      
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // ��� ���� ���� ���� �Ұ�
            }
        };

		// ���̸�
		model.addColumn("���");
		model.addColumn("�̸�");
		model.addColumn("�μ���");


		for (EmployeeVO temp : list) {
			// �� �྿ �߰��ϱ�
			model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), 
					temp.getDept_name()
					});
		}


		// ���̺� ���� �� �� ����
		table = new JTable(model);
		// ��ũ�� ������ ���̺� �г��� ����
		JScrollPane s = new JScrollPane(table);
		
		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setBackground(Color.LIGHT_GRAY);
		s.setFont(AdminPage.ft);
		header.setBackground(Color.LIGHT_GRAY);
		table.setFont(AdminPage.ft);

    	LineBorder border = new LineBorder(AdminPage.color, 8);
    	//s.setBorder(border);
    	
		
		//���̺� ���� ����
		table.setRowHeight(30);
		
		tablePanel.add(s);
		add(tablePanel);
		tablePanel.setVisible(true);
    	
		JPanel SearchPanel = new JPanel();
		SearchPanel.setBounds(300, 0, 400, 50);
		String[] searchitems = { "�̸�", "��","�μ�"};
		JComboBox<String> SearchBox = new JComboBox<>(searchitems);
		
		SearchBox.setFont(AdminPage.ft);
		SearchBox.setBackground(AdminPage.Backgroundcolor);
		
		JTextField SearchField = new JTextField(20);
		JButton SearchBtn = new JButton("�˻��ϱ�");
		
		//��ư���� ��Ʈ ����
		SearchBtn.setFont(AdminPage.ft);
		SearchBtn.setBackground(AdminPage.color);
		
		SearchPanel.add(SearchBox);
		SearchPanel.add(SearchField);
		SearchPanel.add(SearchBtn);
		SearchPanel.setBackground(AdminPage.Backgroundcolor);
		add(SearchPanel);
		
    	String[] deptitems = {"D1(�λ��)","D2(�ѹ���)",
				"D3(��ȹ��)","D4(ȸ���)",
				"D5(ǰ��������)","D6(�����)","D7(������)","D8(�����ú�)",
				"D9(���������)"};
		JComboBox<String> DeptBox = new JComboBox<>(deptitems);
		DeptBox.setFont(AdminPage.ft);
		DeptBox.setBackground(AdminPage.Backgroundcolor);
		
		SearchBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) SearchBox.getSelectedItem();
				if ("�μ�".equals(selectedItem)) {
					System.out.println(selectedItem);
                	SearchPanel.removeAll();
                	SearchPanel.setBounds(300, 0, 400, 50);
                	SearchBox.setSelectedItem("�μ�");
                	
            		SearchPanel.add(SearchBox);
            		SearchPanel.add(DeptBox);
            		SearchPanel.add(SearchBtn);
            		SearchPanel.revalidate(); 
            		SearchPanel.repaint();
            		SearchPanel.setBackground(AdminPage.Backgroundcolor);
            		add(SearchPanel);
                }else {
                	SearchPanel.removeAll();
                	SearchPanel.setBounds(300, 0, 400, 50);
                	SearchPanel.add(SearchBox);
                	SearchPanel.add(SearchField);
                	SearchPanel.add(SearchBtn);
                	SearchPanel.revalidate(); 
            		SearchPanel.repaint();
            		SearchPanel.setBackground(AdminPage.Backgroundcolor);
            		add(SearchPanel);
                }
				
			}
		});
		
		
		SearchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String selectedItem = (String) SearchBox.getSelectedItem();
				//�̸����� �˻�
                if ("�̸�".equals(selectedItem)) {
    				tablePanel.removeAll();
    				System.out.println(SearchField.getText());
    				controller.searchName(tablePanel, SearchField.getText());
    				tablePanel.revalidate(); 
    				tablePanel.repaint();
    				tablePanel.setBackground(AdminPage.Backgroundcolor);
    			//������ �˻�
                } else if ("��".equals(selectedItem)) {
                	tablePanel.removeAll();
       				System.out.println(SearchField.getText());
       				if(SearchField.getText().length()>1) {
       					JOptionPane.showMessageDialog(null, "���� 1���ڷ� �Է����ּ���");
       					return;
       				}else {
       					tablePanel.removeAll();
       					controller.searchFirstName(tablePanel, SearchField.getText());
        				tablePanel.revalidate(); 
        				tablePanel.repaint();
        				tablePanel.setBackground(AdminPage.Backgroundcolor);
       				}
 
                }else if ("�μ�".equals(selectedItem)) {
                
       				tablePanel.removeAll();
       				controller.searchDept(tablePanel, ((String)DeptBox.getSelectedItem()).substring(0,2));
        			tablePanel.revalidate(); 
        			tablePanel.repaint();
        			tablePanel.setBackground(AdminPage.Backgroundcolor);
       				
                }
			}
		});
		
    	
        buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBackground(AdminPage.Backgroundcolor);
        buttonPanel.setBounds(50,70,230,400);
        
        JButton button1 = new JButton("��� ���");    
        JButton button2 = new JButton("�μ� �̵�");
        JButton button3 = new JButton("���� ����");
        JButton button4 = new JButton("��� ����");
        
        button1.setFont(AdminPage.ft);
        button2.setFont(AdminPage.ft);
        button3.setFont(AdminPage.ft);
        button4.setFont(AdminPage.ft);
        
        button1.setBackground(AdminPage.color);
        button2.setBackground(AdminPage.color);
        button3.setBackground(AdminPage.color);
        button4.setBackground(AdminPage.color);
        
        //�ڼ��� ����
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
            	//�̺�Ʈ �߻��Ѱ��
                if (!event.getValueIsAdjusting()) {
                	list = EmployeeDao.select();
                	controller.detail(table,list);
                }
            }
        });
        
		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.insert(model);
			}
		});
		
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.deptUpdate(model);
			}
			
		});
		
		button3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.jobUpdate(model);
			}
			
			
		});
		button4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.delete();
				
			}
		});

		//50,70,170,400  
        button1.setBounds(50, 80, 170, 50);
        button2.setBounds(50, 160, 170, 50);
        button3.setBounds(50, 240, 170, 50);
        button4.setBounds(50, 320, 170, 50);
        
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        add(buttonPanel);
    }
}