package User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Admin.AdminPage;

public class UserController extends JFrame {

	static JPanel findPanel;
	public void info(int inputId) {
		
		
		UserVO vo1 = UserDao.searchMyEMP(inputId);
		UserVO vo2 = UserDao.serchMyDEPT(inputId);
		UserVO vo3 = UserDao.searchMyPosition(inputId);
		ArrayList<UserVO> list = UserDao.searchMyDeptPerson(inputId);
		findPanel = new JPanel();
		

		DefaultTableModel newModel2 = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // 모든 셀에 대해 편집 불가
			}
		};

		
		String[] columnNames = {"항목", "내용"};

		Object[][] data = {
	            {"성명", vo1.getEMP_NAME()},
	            {"사번", vo1.getEMP_ID()},
	            {"직급", vo3.getPOSITION()},
	            {"소속", vo2.getDept_name()},
	            {"생년월일", vo1.getEMP_BIRTH()},
	            {"전화번호", vo1.getEMP_PHONE()},
	            {"이메일", vo1.getEMP_EMAIL()}
	        };
		
		DefaultTableModel newModel = new DefaultTableModel(data, columnNames) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // 모든 셀에 대해 편집 불가
			}
		};
		
		
			newModel2.addColumn("성명");
			newModel2.addColumn("사번");
			newModel2.addColumn("직급");
			newModel2.addColumn("소속");
			newModel2.addColumn("생년월일");
			newModel2.addColumn("전화번호");
			newModel2.addColumn("이메일");
			
			for(UserVO vo : list) {	
			newModel2.addRow(new Object[] {
					vo.getEMP_NAME(),
					vo.getEMP_ID(),
					vo.getPOSITION(),
					vo.getDept_name(),
					vo.getEMP_BIRTH(),
					vo.getEMP_PHONE(),
					vo.getEMP_EMAIL()
			});
			}
			
			JTable newTable = new JTable(newModel);
			JTable newTable2 = new JTable(newModel2);

			JScrollPane newSc = new JScrollPane(newTable);
			JScrollPane newSc2 = new JScrollPane(newTable2);
			
			newSc.setPreferredSize(new Dimension(400, 400));
			
			newSc2.setPreferredSize(new Dimension(500, 400));
			newTable2.getColumnModel().getColumn(5).setPreferredWidth(130);
			newTable2.getColumnModel().getColumn(6).setPreferredWidth(150);
			
			
			newTable.setRowHeight(53);
	        
	        
	        // 중앙 정렬
	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	        newTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

	        // 우측 정렬
	        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
	        newTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

			
			findPanel.add(newSc);
			findPanel.add(newSc2);
			
			findPanel.setBounds(0, 0, 1000, 400);
			findPanel.setBackground(UserPage.Backgroundcolor);
			findPanel.setVisible(true);

	}

}
