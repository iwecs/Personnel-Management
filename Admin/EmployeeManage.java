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
    	
    	//테이블 패널
    	tablePanel = new JPanel();
    	tablePanel.setBounds(300, 50, 500, 450);
    	tablePanel.setBackground(AdminPage.Backgroundcolor);
    	tablePanel.setPreferredSize(tablePanel.getSize());
    	
    	//테두리
//    	LineBorder border = new LineBorder(AdminPage.color, 3);
//    	tablePanel.setBorder(border);
    	
		// 기존에 있는 데이터를 가져오기
		list = EmployeeDao.select();

		model = new DefaultTableModel()
		{
      
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 모든 셀에 대해 편집 불가
            }
        };

		// 열이름
		model.addColumn("사번");
		model.addColumn("이름");
		model.addColumn("부서명");


		for (EmployeeVO temp : list) {
			// 한 행씩 추가하기
			model.addRow(new Object[] { temp.getEMP_ID(), temp.getEMP_NAME(), 
					temp.getDept_name()
					});
		}


		// 테이블 생성 및 모델 설정
		table = new JTable(model);
		// 스크롤 가능한 테이블 패널을 생성
		JScrollPane s = new JScrollPane(table);
		
		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setBackground(Color.LIGHT_GRAY);
		s.setFont(AdminPage.ft);
		header.setBackground(Color.LIGHT_GRAY);
		table.setFont(AdminPage.ft);

    	LineBorder border = new LineBorder(AdminPage.color, 8);
    	//s.setBorder(border);
    	
		
		//테이블 사이 간격
		table.setRowHeight(30);
		
		tablePanel.add(s);
		add(tablePanel);
		tablePanel.setVisible(true);
    	
		JPanel SearchPanel = new JPanel();
		SearchPanel.setBounds(300, 0, 400, 50);
		String[] searchitems = { "이름", "성","부서"};
		JComboBox<String> SearchBox = new JComboBox<>(searchitems);
		
		SearchBox.setFont(AdminPage.ft);
		SearchBox.setBackground(AdminPage.Backgroundcolor);
		
		JTextField SearchField = new JTextField(20);
		JButton SearchBtn = new JButton("검색하기");
		
		//버튼색깔 폰트 지정
		SearchBtn.setFont(AdminPage.ft);
		SearchBtn.setBackground(AdminPage.color);
		
		SearchPanel.add(SearchBox);
		SearchPanel.add(SearchField);
		SearchPanel.add(SearchBtn);
		SearchPanel.setBackground(AdminPage.Backgroundcolor);
		add(SearchPanel);
		
    	String[] deptitems = {"D1(인사부)","D2(총무부)",
				"D3(기획부)","D4(회계부)",
				"D5(품질관리부)","D6(전산부)","D7(영업부)","D8(마케팅부)",
				"D9(생산관리부)"};
		JComboBox<String> DeptBox = new JComboBox<>(deptitems);
		DeptBox.setFont(AdminPage.ft);
		DeptBox.setBackground(AdminPage.Backgroundcolor);
		
		SearchBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) SearchBox.getSelectedItem();
				if ("부서".equals(selectedItem)) {
					System.out.println(selectedItem);
                	SearchPanel.removeAll();
                	SearchPanel.setBounds(300, 0, 400, 50);
                	SearchBox.setSelectedItem("부서");
                	
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
				//이름으로 검색
                if ("이름".equals(selectedItem)) {
    				tablePanel.removeAll();
    				System.out.println(SearchField.getText());
    				controller.searchName(tablePanel, SearchField.getText());
    				tablePanel.revalidate(); 
    				tablePanel.repaint();
    				tablePanel.setBackground(AdminPage.Backgroundcolor);
    			//성으로 검색
                } else if ("성".equals(selectedItem)) {
                	tablePanel.removeAll();
       				System.out.println(SearchField.getText());
       				if(SearchField.getText().length()>1) {
       					JOptionPane.showMessageDialog(null, "성은 1글자로 입력해주세요");
       					return;
       				}else {
       					tablePanel.removeAll();
       					controller.searchFirstName(tablePanel, SearchField.getText());
        				tablePanel.revalidate(); 
        				tablePanel.repaint();
        				tablePanel.setBackground(AdminPage.Backgroundcolor);
       				}
 
                }else if ("부서".equals(selectedItem)) {
                
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
        
        JButton button1 = new JButton("사원 등록");    
        JButton button2 = new JButton("부서 이동");
        JButton button3 = new JButton("직급 승인");
        JButton button4 = new JButton("사원 삭제");
        
        button1.setFont(AdminPage.ft);
        button2.setFont(AdminPage.ft);
        button3.setFont(AdminPage.ft);
        button4.setFont(AdminPage.ft);
        
        button1.setBackground(AdminPage.color);
        button2.setBackground(AdminPage.color);
        button3.setBackground(AdminPage.color);
        button4.setBackground(AdminPage.color);
        
        //자세히 보기
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
            	//이벤트 발생한경우
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