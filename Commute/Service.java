package Commute;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

import login.LoginVO;

public class Service {

	private static Scanner scan = new Scanner(System.in);

	private static Controller con = new Controller();

	// �α���
	public void service_login(int id) throws Exception {

		con.logIn(id);

	}

	// �α׾ƿ�
	public void service_logout(int id) throws Exception {

		con.logOut(id);

	}

}
