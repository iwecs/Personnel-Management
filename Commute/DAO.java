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

			System.out.println("[ DAO 안에 login() 메서드 실행 ]");

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
//					System.out.println("로그인 성공");
//			}

					String existsYn = "N"; // 로그인한 아이디의 오늘 출근이력이 존재하는지 확인하는 변수;

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

						// 출근 시간 비교
						if ((hour == 9 && minute == 0) || (hour == 8 && minute <= 59)) {
							System.out.println("정상출근");
							System.out.println(now);

							// 출근 기록 저장
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
							System.out.println("지각");
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
//				System.out.println("로그인 실패");
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

			System.out.println("[ DAO 안에 logout() 메서드 실행 ]");

			LocalDateTime now = LocalDateTime.now();
			LocalDate today = LocalDate.now();

			// LocalDateTime을 Timestamp으로 변환
			Timestamp timestamp = Timestamp.valueOf(now.withNano(0));

			conn = DBUtil.getConnection();

			System.out.println("로그아웃 되었습니다.");

			// 퇴근 기록 저장
//			String sql_logout = "INSERT INTO COMMUTE (EMP_ID, STARTTIME, ENDTIME) VALUES (?, ?, ?)";
			String sql_logout = "UPDATE COMMUTE c SET ENDTIME = ? WHERE EMP_ID = ? AND"
					+ " TO_CHAR(SYSDATE, 'YYYMMDD') = TO_CHAR(c.STARTTIME, 'YYYMMDD')";
			try (PreparedStatement psLogout = conn.prepareStatement(sql_logout)) {
				psLogout.setTimestamp(1, Timestamp.valueOf(now.withNano(0))); // 퇴근 시간 기록
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
