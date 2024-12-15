package Admin;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import login.loginGUI;


public class AdminPage extends JFrame{

	static JPanel  mMenuPanel, mPagePanel, logoutPanel, logoPanel , ChangePanel;
	static JButton logoutbtn, changeColorbtn;
	
	static Font ft = new Font("맑은 고딕",Font.BOLD,15);
	static Color color = new Color(150,195,222);
	static Color Backgroundcolor = Color.white;
	public AdminPage(String title, int x, int y, int width, int height) {

		initContainer(title, x, y, width, height);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public AdminPage() {
		// TODO Auto-generated constructor stub
	}

	private void initContainer(String title, int x, int y, int width, int height) {

		setTitle(title);
		setBounds(x, y, width, height);
		setLayout(null);
		
		mMenuPanel = new JPanel();
		mMenuPanel.setLayout(null);
		mMenuPanel.setBounds(140, 60, width, 100);
		mMenuPanel.setBackground(Backgroundcolor);
		menuBtn();
		
		logoutPanel = new JPanel();
		logoutPanel.setLayout(null);
		logoutPanel.setBounds(800, 30, 100, 30);
		logoutPanel.setBackground(Backgroundcolor);
		logout();
		
		logoPanel = new JPanel();
		logoPanel.setBounds(20, 30, 140, 100);
		logoPanel.setBackground(Backgroundcolor);
		logo();

		mPagePanel = new JPanel();
		mPagePanel.setBounds(0, 200, width, height);
		mPagePanel.setBackground(Backgroundcolor);
		add(mPagePanel);
		
		
		getContentPane().setBackground(Backgroundcolor);

	}
	private void logo() {
		
		//원본 이미지 로드
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
		ImageIcon logoutIcon = new ImageIcon("./image/logout.png"); 
		
		// 원하는 크기로 이미지 크기 조정
        Image originalImage = logoutIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

		
        logoutbtn = new JButton("로그아웃");
        logoutbtn.setFont(ft);
        logoutbtn.setBackground(color);
		
		logoutbtn.setSize(100, 30);
		logoutPanel.add(logoutbtn);
		add(logoutPanel);
		
		logoutbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new loginGUI();
				
			}
		});
	}
	public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "오류", JOptionPane.ERROR_MESSAGE);
    }
	private void menuBtn() {
		
		JButton btn1 = new JButton("사원 관리");
		
		btn1.setBounds(30, 50, 150, 40);
		btn1.setFont(ft);
		btn1.setBackground(color);
		//테두리 없앰
		//btn1.setBorderPainted(false);
		//btn1.setFocusPainted(false); 
		//btn1.add(new JSeparator());
		//btn1.setContentAreaFilled(false);
		//텍스트 색상
		//btn1.setForeground(new Color(000,051,204));
		//테두리 색
		//btn1.setBorder(BorderFactory.createLineBorder(new Color(000,204,255)));
		//투명화
		//btn1.setOpaque(false);
		
		//btn1.setBackground(new Color(153,204,255));
		mMenuPanel.add(btn1);
		//mMenuPanel.setBackground(Backgroundcolor);
		
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 
				mPagePanel.removeAll();
				mPagePanel.add(new EmployeeManage(mPagePanel));
				mPagePanel.revalidate(); 
				mPagePanel.repaint();
			
			}
		});
		
		JButton btn2 = new JButton("급여 관리");
		
		btn2.setBounds(210, 50, 150, 40);
		btn2.setFont(ft);
		btn2.setBackground(color);
		mMenuPanel.add(btn2);
		
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mPagePanel.removeAll();
				mPagePanel.add(new SalaryInfoManage(mPagePanel));
				mPagePanel.revalidate(); 
				mPagePanel.repaint();
				
				
			}
		});
		
		JButton btn3 = new JButton("근태 관리");
		btn3.setBounds(390, 50, 150, 40);
		btn3.setFont(ft);
		btn3.setBackground(color);
		mMenuPanel.add(btn3);
		
		btn3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mPagePanel.removeAll();
				mPagePanel.add(new AttendanceManage(mPagePanel));
				mPagePanel.revalidate(); 
				mPagePanel.repaint();
				
			}
		});

		
		
		
		JButton btn4 = new JButton("보고 관리");
		btn4.setBounds(570, 50, 150, 40);
		btn4.setFont(ft);
		btn4.setBackground(color);
		mMenuPanel.add(btn4);
		
		btn4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mPagePanel.removeAll();
				mPagePanel.add(new ReportManage(mPagePanel));
				mPagePanel.revalidate(); 
				mPagePanel.repaint();
				
			}
		});

		
		add(mMenuPanel);
	}

	public static void main(String[] args) {
		
		//AdminPage ap = new AdminPage("관리자 모드", 0,0,1000,750);

	}
}

