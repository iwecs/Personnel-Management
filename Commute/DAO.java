package Commute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import login.DBUtil;
import login.LoginVO;

public class DAO {

	private static Connection conn = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private LocalDateTime now = LocalDateTime.now();

	public void logIn(int id) throws Exception {

		try {

			System.out.println("[ DAO �ȿ� login() �޼��� ���� ]");

			conn = DBUtil.getConnection();

//			String sql = " select emp_id, emp_pw from LOGIN ";
//			sql += " where emp_id = ? and emp_pw = ? ";
//
//			ps = conn.prepareStatement(sql);
//
//			ps.setInt(1, login.getId());
//			ps.setString(2, login.getPw());

//			rs = ps.executeQuery();
//
//			if (rs.next()) {

//				if (login.getPw().equals(rs.getString("emp_pw"))) {
//					System.out.println("�α��� ����");
//			}

					String existsYn = "N"; // �α����� ���̵��� ���� ����̷��� �����ϴ��� Ȯ���ϴ� ����;

					existsYn = " SELECT COUNT(1) AS existsSql FROM COMMUTE c " + " WHERE c.EMP_ID = ? "
							+ " AND TO_CHAR(SYSDATE, 'YYYMMDD') = TO_CHAR(c.STARTTIME, 'YYYMMDD') ";

					ps = conn.prepareStatement(existsYn);

					ps.setInt(1, id);

					rs = ps.executeQuery();

					if (rs.next()) {

						int ex = rs.getInt("existsSql");

						if (ex > 0) {
							existsYn = "Y";
							return;
						}
					}
					if (!existsYn.equals("Y")) {

						int hour = now.getHour();
						int minute = now.getMinute();

						// ��� �ð� ��
						if ((hour == 9 && minute == 0) || (hour == 8 && minute <= 59)) {
							System.out.println("�������");
							System.out.println(now);

							// ��� ��� ����
//							String sql_login = "MERGE INTO COMMUTE USING DUAL ON A.EMP_ID = ? "
//									+ "  AND TO_CHAR(SYSDATE, 'YYYMMDD') = TO_CHAR(A.STARTTIME, 'YYYMMDD') "
//									+ " WHEN NOT MATCHED THEN ";
							String sql_login = " INSERT INTO COMMUTE (EMP_ID, STARTTIME, ENDTIME, BUSINESS_TRIP) VALUES (?, ?, ?,'N') ";
							try (PreparedStatement psLogin = conn.prepareStatement(sql_login)) {
								psLogin.setInt(1, id);
								psLogin.setTimestamp(2, Timestamp.valueOf(now.withNano(0)));
								psLogin.setTimestamp(3, Timestamp.valueOf(now.withNano(0))); // LocalDateTime.now())

								psLogin.executeUpdate();

							}
						} else {
							System.out.println("����");
							System.out.println(now);

							String sql_login = "INSERT INTO COMMUTE (EMP_ID, STARTTIME, ENDTIME, BUSINESS_TRIP) VALUES (?, ?, ?, 'N')";
							try (PreparedStatement psLogin = conn.prepareStatement(sql_login)) {
								psLogin.setInt(1, id);
								psLogin.setTimestamp(2, Timestamp.valueOf(now.withNano(0)));
								psLogin.setTimestamp(3, Timestamp.valueOf(now.withNano(0)));

								psLogin.executeUpdate();
							}
						}
//					}
//				}
//			} else {
//				System.out.println("�α��� ����");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void logOut(int id) {

		try {

			System.out.println("[ DAO �ȿ� logout() �޼��� ���� ]");

			LocalDateTime now = LocalDateTime.now();
			LocalDate today = LocalDate.now();

			// LocalDateTime�� Timestamp���� ��ȯ
			Timestamp timestamp = Timestamp.valueOf(now.withNano(0));

			conn = DBUtil.getConnection();

			System.out.println("�α׾ƿ� �Ǿ����ϴ�.");

			// ��� ��� ����
//			String sql_logout = "INSERT INTO COMMUTE (EMP_ID, STARTTIME, ENDTIME) VALUES (?, ?, ?)";
			String sql_logout = "UPDATE COMMUTE c SET ENDTIME = ? WHERE EMP_ID = ? AND"
					+ " TO_CHAR(SYSDATE, 'YYYMMDD') = TO_CHAR(c.STARTTIME, 'YYYMMDD')";
			try (PreparedStatement psLogout = conn.prepareStatement(sql_logout)) {
				psLogout.setTimestamp(1, Timestamp.valueOf(now.withNano(0))); // ��� �ð� ���
				psLogout.setInt(2, id);

				psLogout.executeUpdate();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}
}
