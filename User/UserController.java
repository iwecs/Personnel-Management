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
				return false; // ��� ���� ���� ���� �Ұ�
			}
		};

		
		String[] columnNames = {"�׸�", "����"};

		Object[][] data = {
	            {"����", vo1.getEMP_NAME()},
	            {"���", vo1.getEMP_ID()},
	            {"����", vo3.getPOSITION()},
	            {"�Ҽ�", vo2.getDept_name()},
	            {"�������", vo1.getEMP_BIRTH()},
	            {"��ȭ��ȣ", vo1.getEMP_PHONE()},
	            {"�̸���", vo1.getEMP_EMAIL()}
	        };
		
		DefaultTableModel newModel = new DefaultTableModel(data, columnNames) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // ��� ���� ���� ���� �Ұ�
			}
		};
		
		
			newModel2.addColumn("����");
			newModel2.addColumn("���");
			newModel2.addColumn("����");
			newModel2.addColumn("�Ҽ�");
			newModel2.addColumn("�������");
			newModel2.addColumn("��ȭ��ȣ");
			newModel2.addColumn("�̸���");
			
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
	        
	        
	        // �߾� ����
	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	        newTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

	        // ���� ����
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
