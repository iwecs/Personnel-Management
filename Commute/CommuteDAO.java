package Commute;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import AnnualPaidLeave.appFormVO;
import login.DBUtil;
import login.LoginVO;

public class CommuteDAO {

	static Connection conn = null;
	static PreparedStatement ps = null;
	static ResultSet rs = null;
	static LoginVO logvo = new LoginVO();
	private static ArrayList<CommuteVO> list = new ArrayList<CommuteVO>();
	

	public static ArrayList<CommuteVO> selectAdminCommute() {
		System.out.println("[ CommuteDAO 안에 selectAdminCommute() 메서드 실행 ]");

		try {

			conn = DBUtil.getConnection();

			String sql = "SELECT EMP.EMP_ID, EMP.EMP_NAME , D.MONTH_DATE, " + " CASE "
					+ "    WHEN COM.EMP_ID IS NOT NULL THEN '출근' "
					+ "    WHEN COM.EMP_ID IS NOT NULL AND TO_NUMBER(TO_CHAR(COM.STARTTIME, 'HH24')) > 9 THEN '지각' "
					+ "    WHEN COM.EMP_ID IS NOT NULL AND TO_NUMBER(TO_CHAR(COM.STARTTIME, 'HH24')) <= 9 THEN '출근' "
					+ "    ELSE '출근' " + " END AS 근태, " + " CASE "
					+ "    WHEN COM.EMP_ID IS NOT NULL AND TO_NUMBER(TO_CHAR(COM.ENDTIME, 'HH24')) BETWEEN 18 AND 19 THEN '퇴근' "
					+ "    WHEN COM.EMP_ID IS NOT NULL AND TO_NUMBER(TO_CHAR(COM.ENDTIME, 'HH24')) >= 19 THEN '야근' "
					+ "    ELSE '퇴근' " + " END AS 퇴근, " + " CASE " + "    WHEN TYL.LEAVE_TYPE = '오전반차' THEN "
					+ "        CASE " + "            WHEN COM.EMP_ID IS NOT NULL THEN '출근' "
					+ "            WHEN TO_CHAR(SYSDATE, 'AM', 'NLS_DATE_LANGUAGE=ENGLISH') = 'AM' THEN '오전반차' "
					+ "            WHEN TO_CHAR(SYSDATE, 'AM', 'NLS_DATE_LANGUAGE=ENGLISH') = 'PM' AND COM.EMP_ID IS NULL THEN '결근' "
					+ "        END " + "    WHEN TYL.LEAVE_TYPE = '오후반차' THEN " + "        CASE "
					+ "            WHEN TO_CHAR(SYSDATE, 'AM', 'NLS_DATE_LANGUAGE=ENGLISH') = 'AM' AND COM.EMP_ID IS NULL THEN '결근' "
					+ "            WHEN TO_CHAR(SYSDATE, 'AM', 'NLS_DATE_LANGUAGE=ENGLISH') = 'AM' AND COM.EMP_ID IS NOT NULL THEN '출근' "
					+ "            WHEN TO_CHAR(SYSDATE, 'AM', 'NLS_DATE_LANGUAGE=ENGLISH') = 'PM' THEN '오후반차' "
					+ "        END " + "    WHEN TYL.LEAVE_TYPE = '연차' THEN '연차' " + "    ELSE '출근' " + " END AS 연차 "
					+ "FROM EMPLOYEE EMP " + "INNER JOIN  ( "
					+ "    SELECT TO_DATE(TO_CHAR(SYSDATE, 'YYYYMM') || LPAD(LEVEL, 2, '0')) AS MONTH_DATE "
					+ "    FROM DUAL " + "    CONNECT BY LEVEL <= TO_CHAR(LAST_DAY(SYSDATE), 'DD')) D  " + "ON 1=1 "
					+ "LEFT JOIN COMMUTE COM ON COM.EMP_ID = EMP.EMP_ID AND TO_CHAR(COM.STARTTIME,'YYYY-MM-DD') = TO_CHAR(D.MONTH_DATE,'YYYY-MM-DD') "
					+ "LEFT JOIN TYPEOFLEAVE TYL ON TYL.EMP_ID = EMP.EMP_ID AND TO_CHAR(TYL.LEAVE_DATE,'YYYY-MM-DD') = TO_CHAR(D.MONTH_DATE,'YYYY-MM-DD') "
					+ "WHERE MONTH_DATE <= SYSDATE ORDER BY D.MONTH_DATE, emp.emp_id ";

			ps = conn.prepareStatement(sql);
			System.out.println("selectcommute 실행끝");
			rs = ps.executeQuery();

			while (rs.next()) {
				CommuteVO temp = new CommuteVO();

				temp.setEMP_ID(rs.getInt("EMP_ID"));
				temp.setEMP_NAME(rs.getString("EMP_NAME"));
				temp.setMONTH_DATE(rs.getDate("MONTH_DATE"));
				temp.setComm(rs.getString("근태"));
				temp.setEnd(rs.getString("퇴근"));
				temp.setLeave(rs.getString("연차"));

				list.add(temp);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return list;
	}

	public static ArrayList<CommuteVO> selectCommute(int id) {
		System.out.println("[ CommuteDAO 안에 selectCommute() 메서드 실행 ]");

		try {

			conn = DBUtil.getConnection();

			String sql = "SELECT EMP.EMP_ID, EMP.EMP_NAME , D.MONTH_DATE, " + " CASE "
					+ "    WHEN COM.EMP_ID IS NOT NULL THEN '출근'"
					+ "    WHEN COM.EMP_ID IS NOT NULL AND TO_NUMBER(TO_CHAR(COM.STARTTIME, 'HH24')) > 9 THEN '지각' "
					+ "    WHEN COM.EMP_ID IS NOT NULL AND TO_NUMBER(TO_CHAR(COM.STARTTIME, 'HH24')) <= 9 THEN '출근' "
					+ "    ELSE '결근' " + " END AS 근태, " + " CASE "
					+ "    WHEN COM.EMP_ID IS NOT NULL AND TO_NUMBER(TO_CHAR(COM.ENDTIME, 'HH24')) BETWEEN 18 AND 19 THEN '퇴근' "
					+ "    WHEN COM.EMP_ID IS NOT NULL AND TO_NUMBER(TO_CHAR(COM.ENDTIME, 'HH24')) >= 19 THEN '야근' "
					+ "    ELSE '결근' " + " END AS 퇴근, " + " CASE " + "    WHEN TYL.LEAVE_TYPE = '오전반차' THEN "
					+ "        CASE " + "            WHEN COM.EMP_ID IS NOT NULL THEN '출근' "
					+ "            WHEN TO_CHAR(SYSDATE, 'AM', 'NLS_DATE_LANGUAGE=ENGLISH') = 'AM' THEN '오전반차' "
					+ "            WHEN TO_CHAR(SYSDATE, 'AM', 'NLS_DATE_LANGUAGE=ENGLISH') = 'PM' AND COM.EMP_ID IS NULL THEN '결근' "
					+ "        END " + "    WHEN TYL.LEAVE_TYPE = '오후반차' THEN " + "        CASE "
					+ "            WHEN TO_CHAR(SYSDATE, 'AM', 'NLS_DATE_LANGUAGE=ENGLISH') = 'AM' AND COM.EMP_ID IS NULL THEN '결근' "
					+ "            WHEN TO_CHAR(SYSDATE, 'AM', 'NLS_DATE_LANGUAGE=ENGLISH') = 'AM' AND COM.EMP_ID IS NOT NULL THEN '출근' "
					+ "            WHEN TO_CHAR(SYSDATE, 'AM', 'NLS_DATE_LANGUAGE=ENGLISH') = 'PM' THEN '오후반차' \r\n"
					+ "        END " + "    WHEN TYL.LEAVE_TYPE = '연차' THEN '연차' " + "    ELSE '결근' " + " END AS 연차 "
					+ "FROM EMPLOYEE EMP " + "INNER JOIN  ( "
					+ "    SELECT TO_DATE(TO_CHAR(SYSDATE, 'YYYYMM') || LPAD(LEVEL, 2, '0')) AS MONTH_DATE "
					+ "    FROM DUAL " + "    CONNECT BY LEVEL <= TO_CHAR(LAST_DAY(SYSDATE), 'DD')) D  " + "ON 1=1 "
					+ "LEFT JOIN COMMUTE COM ON COM.EMP_ID = EMP.EMP_ID AND TO_CHAR(COM.STARTTIME,'YYYY-MM-DD') = TO_CHAR(D.MONTH_DATE,'YYYY-MM-DD') "
					+ "LEFT JOIN TYPEOFLEAVE TYL ON TYL.EMP_ID = EMP.EMP_ID AND TO_CHAR(TYL.LEAVE_DATE,'YYYY-MM-DD') = TO_CHAR(D.MONTH_DATE,'YYYY-MM-DD') "
					+ "WHERE EMP.EMP_ID = ? "
					+ "AND MONTH_DATE <= SYSDATE " + "ORDER BY D.MONTH_DATE, emp.emp_id ";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, id);
//			ps.setInt(1, 224);

			rs = ps.executeQuery();

			while (rs.next()) {
				CommuteVO temp = new CommuteVO();

				temp.setEMP_ID(rs.getInt("EMP_ID"));
				temp.setEMP_NAME(rs.getString("EMP_NAME"));
				temp.setMONTH_DATE(rs.getDate("MONTH_DATE"));
				temp.setComm(rs.getString("근태"));
				temp.setEnd(rs.getString("퇴근"));
				temp.setLeave(rs.getString("연차"));

				list.add(temp);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return list;
	}

	public static ArrayList<CommuteVO> leaveCount_year() {
		System.out.println("[ CommuteDAO 안에 leaveCount() 메서드 실행 ]");

		try {

			conn = DBUtil.getConnection();

			String sql = "select emp_id, hire_date, TRUNC(MONTHS_BETWEEN(SYSDATE, hire_date)/12)*15 as \"workMonth\" from employee "
					+ "where MONTHS_BETWEEN(SYSDATE, hire_date) >=13 and emp_id = ? " + "order by EMP_ID ";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, logvo.getId());
//			ps.setInt(1, 223);

			rs = ps.executeQuery();

			while (rs.next()) {
				CommuteVO temp = new CommuteVO();

				temp.setEMP_ID(rs.getInt("EMP_ID"));
				temp.setEMP_NAME(rs.getString("EMP_NAME"));
				temp.setSTARTTIME(rs.getTimestamp("STARTTIME"));
				temp.setENDTIME(rs.getTimestamp("ENDTIME"));
				temp.setLEAVE_CODE(rs.getString("LEAVE_CODE"));
				temp.setLEAVE_TYPE(rs.getString("leave_type"));
				temp.setBUSINESS_TRIP(rs.getString("business_trip"));

				list.add(temp);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static ArrayList<LocalDate> sreachHiredate(int id) {
		System.out.println("[ CommuteDAO 안에 sreachHiredate() 메서드 실행 ]");

		ArrayList<LocalDate> hireDates = new ArrayList<>();

		try {

			conn = DBUtil.getConnection();

			String sql = "SELECT hire_date FROM employee WHERE emp_id = ? ORDER BY emp_id ";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, id);
//			ps.setInt(1, 223);

			rs = ps.executeQuery();

			while (rs.next()) {
				LocalDate hireDate = rs.getDate("hire_date").toLocalDate();
				hireDates.add(hireDate);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return hireDates;
	}

	public static int hireDate3() {

		System.out.println("[ CommuteDAO 안에 hireDate3() 메서드 실행 ]");

		CommuteVO com = null;
		int workMonth = 0;
		try {

			conn = DBUtil.getConnection();

			String sql = "select emp_id, hire_date, (TRUNC(MONTHS_BETWEEN(SYSDATE, hire_date)/12)*15 )+1 as \"workMonth\" from employee ";
			sql += " where MONTHS_BETWEEN(SYSDATE, hire_date) >= (12*3) AND emp_id = ? order by EMP_ID ";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, logvo.getId());
//			ps.setInt(1, 223);

			rs = ps.executeQuery();

			while (rs.next()) {
				workMonth = rs.getInt("workMonth");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return workMonth;

	}

	public static int hireDate2() {
		System.out.println("[ CommuteDAO 안에 hireDate2() 메서드 실행 ]");

//		CommuteVO com = null;
		int workMonth = 0;
		try {

			conn = DBUtil.getConnection();

			String sql = "select emp_id, hire_date, (TRUNC(MONTHS_BETWEEN(SYSDATE, hire_date)/12)*15 )+1 as \"workMonth\" from employee ";
			sql += " where MONTHS_BETWEEN(SYSDATE, hire_date) >= 12 AND emp_id = ? " + " order by EMP_ID ";

			ps = conn.prepareStatement(sql);

//			ps.setInt(1, logvo.getId());
			ps.setInt(1, 223);

			rs = ps.executeQuery();

			while (rs.next()) {

				workMonth = rs.getInt("workMonth");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return workMonth;

	}

	public static int hireDate() {
		System.out.println("[ CommuteDAO 안에 hireDate() 메서드 실행 ]");

//		CommuteVO com = null;
		int workMonth = 0;
		try {

			conn = DBUtil.getConnection();

			String sql = "select emp_id, hire_date, TRUNC(MONTHS_BETWEEN(SYSDATE, hire_date)/12) as \"workMonth\" from employee ";
			sql += " where (MONTHS_BETWEEN(SYSDATE, hire_date) < 13 and MONTHS_BETWEEN(SYSDATE, hire_date) >= 0) AND emp_id = ?  "
					+ " order by EMP_ID ";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, logvo.getId());
//			ps.setInt(1, 223);

			rs = ps.executeQuery();

			while (rs.next()) {

				workMonth = rs.getInt("workMonth");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return workMonth;

	}

	public static ArrayList<CommuteVO> hireDateTotal() {
		System.out.println("[ CommuteDAO 안에 hireDateTotal() 메서드 실행 ]");

		int workMonth = 0;

		try {

			conn = DBUtil.getConnection();

			String sql = "select emp_id, hire_date, TRUNC(TRUNC( MONTHS_BETWEEN(SYSDATE, hire_date))/12) as \"WorkYear\", "
					+ "TRUNC(MONTHS_BETWEEN(SYSDATE, hire_date))- 12*TRUNC(TRUNC( MONTHS_BETWEEN(SYSDATE, hire_date))/12) AS \"WorkMonth\" ";
			sql += " from employee where MONTHS_BETWEEN(SYSDATE, hire_date) >= 0 order by EMP_ID ";

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {

				CommuteVO temp = new CommuteVO();

				temp.setEMP_ID(rs.getInt("emp_id"));
				temp.setWorkYear(rs.getInt("WorkYear"));
				temp.setWorkMonth(rs.getInt("WorkMonth"));

				list.add(temp);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static int useLeave() {
		System.out.println("[ CommuteDAO 안에 useLeave() 메서드 실행 ]");

//		CommuteVO com = null;
		int useLeave = 0;

		try {

			conn = DBUtil.getConnection();

			String sql = "select t.EMP_ID, count(l.LEAVE_CODE) as \"사용 연차 갯수\" from TypeOfLeave t  ";
			sql += " join COMMUTE c on c.EMP_ID = t.EMP_ID   ";
			sql += " join LEAVE l on l.leave = t.leave_type where t.emp_id = ? ";
			sql += " group by t.EMP_ID  order by EMP_ID ";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, logvo.getId());
//			ps.setInt(1, 223);

			rs = ps.executeQuery();

			while (rs.next()) {

				useLeave = rs.getInt("사용 연차 갯수");
				System.out.println("useLeave : " + useLeave);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return useLeave;

	}

	public static int useLeaveTotal() {
		System.out.println("[ CommuteDAO 안에 useLeaveTotal() 메서드 실행 ]");

//		CommuteVO com = null;
		int useLeave = 0;

		try {

			conn = DBUtil.getConnection();

			String sql = "select t.EMP_ID, count(l.LEAVE_CODE) as \"사용 연차 갯수\" from TypeOfLeave t  ";
			sql += " join COMMUTE c on c.EMP_ID = t.EMP_ID   ";
			sql += " join LEAVE l on l.leave = t.leave_type ";
			sql += " group by t.EMP_ID  order by EMP_ID ";

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {

				useLeave = rs.getInt("사용 연차 갯수");
				System.out.println("useLeave : " + useLeave);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return useLeave;

	}

	public static ArrayList<CommuteVO> leaveCount(int id) {
		System.out.println("[ CommuteDAO 안에 leaveCount() 메서드 실행 ]");

		try {

			conn = DBUtil.getConnection();

			String sql = "SELECT TYP.* "
					+ "     , LEAVE.LEAVE "
					+ "  FROM TYPEOFLEAVE TYP "
					+ "  LEFT OUTER JOIN (SELECT EMP.EMP_ID AS EMP_ID "
					+ "                     , (CASE WHEN TO_CHAR(SYSDATE, 'YYYY') = TO_CHAR(EMP.HIRE_DATE, 'YYYY') "
					+ "                             THEN TO_CHAR(SYSDATE, 'MM') - TO_CHAR(EMP.HIRE_DATE, 'MM') - 1 "
					+ "                             ELSE (15 + CASE WHEN TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(EMP.HIRE_DATE, 'YYYY') - 1 = 0 THEN 0 ELSE "
					+ "                                                                                                                                CASE WHEN (FLOOR((TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(EMP.HIRE_DATE, 'YYYY') - 1)/2)) > 10 THEN 10 "
					+ "                                                                                                                                     ELSE FLOOR((TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(EMP.HIRE_DATE, 'YYYY') - 1)/2) "
					+ "                                                                                                                                     END "
					+ "                                             END) "
					+ "                             END) AS leave "
					+ "                  FROM EMPLOYEE EMP "
					+ "                 where EMP.HIRE_DATE <= SYSDATE "
					+ "                             order by emp.emp_id) LEAVE "
					+ "     ON LEAVE.EMP_ID = TYP.EMP_ID "
					+ "	WHERE TYP.EMP_ID = ? "
					+ " ORDER BY NUM ";

			ps = conn.prepareStatement(sql);

//			ps.setInt(1, logvo.getId());
			ps.setInt(1, id);

			rs = ps.executeQuery();

			while (rs.next()) {

				CommuteVO temp = new CommuteVO();

				temp.setEMP_ID(rs.getInt("emp_id"));
				temp.setLeaveCount(rs.getInt("leave"));

				list.add(temp);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static ArrayList<appFormVO> totaLeaveCount() {
		System.out.println("[ CommuteDAO 안에 totaleaveCount() 메서드 실행 ]");
		
		ArrayList<appFormVO> appFormList = new ArrayList<appFormVO>();

		try {

			conn = DBUtil.getConnection();

//			String sql = "SELECT EMP.EMP_ID, (CASE WHEN TO_CHAR(SYSDATE, 'YYYY') = TO_CHAR(EMP.HIRE_DATE, 'YYYY') THEN TO_CHAR(SYSDATE, 'MM') - TO_CHAR(EMP.HIRE_DATE, 'MM') - 1 "
//					+ "ELSE "
//					+ "(15 + CASE WHEN TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(EMP.HIRE_DATE, 'YYYY') - 1 = 0 THEN 0 "
//					+ "ELSE "
//					+ " CASE WHEN (FLOOR((TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(EMP.HIRE_DATE, 'YYYY') - 1)/2)) > 10 THEN 10 "
//					+ " ELSE FLOOR((TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(EMP.HIRE_DATE, 'YYYY') - 1)/2) "
//					+ " END  END) "
//					+ "END) AS leave  FROM EMPLOYEE EMP where (CASE WHEN TO_CHAR(SYSDATE, 'YYYY') = TO_CHAR(EMP.HIRE_DATE, 'YYYY') THEN TO_CHAR(SYSDATE, 'MM') - TO_CHAR(EMP.HIRE_DATE, 'MM') - 1 "
//					+ "ELSE "
//					+ "(15 + CASE WHEN TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(EMP.HIRE_DATE, 'YYYY') - 1 = 0 THEN 0 "
//					+ "ELSE "
//					+ " CASE WHEN (FLOOR((TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(EMP.HIRE_DATE, 'YYYY') - 1)/2)) > 10 THEN 10 "
//					+ " ELSE FLOOR((TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(EMP.HIRE_DATE, 'YYYY') - 1)/2) "
//					+ " END  END)"
//					+ "END) >= 0 order by emp.emp_id ";
			
			String sql = "SELECT TYP.* "
					+ "     , LEAVE.LEAVE "
					+ "  FROM TYPEOFLEAVE TYP "
					+ "  LEFT OUTER JOIN (SELECT EMP.EMP_ID AS EMP_ID "
					+ "                     , (CASE WHEN TO_CHAR(SYSDATE, 'YYYY') = TO_CHAR(EMP.HIRE_DATE, 'YYYY') "
					+ "                             THEN TO_CHAR(SYSDATE, 'MM') - TO_CHAR(EMP.HIRE_DATE, 'MM') - 1 "
					+ "                             ELSE (15 + CASE WHEN TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(EMP.HIRE_DATE, 'YYYY') - 1 = 0 THEN 0 ELSE "
					+ "                                                                                                                                CASE WHEN (FLOOR((TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(EMP.HIRE_DATE, 'YYYY') - 1)/2)) > 10 THEN 10 "
					+ "                                                                                                                                     ELSE FLOOR((TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(EMP.HIRE_DATE, 'YYYY') - 1)/2) "
					+ "                                                                                                                                     END "
					+ "                                             END) "
					+ "                             END) AS leave "
					+ "                  FROM EMPLOYEE EMP "
					+ "                 where EMP.HIRE_DATE <= SYSDATE "
					+ "                             order by emp.emp_id) LEAVE "
					+ "     ON LEAVE.EMP_ID = TYP.EMP_ID "
					+ " ORDER BY NUM ";

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {

//				CommuteVO temp = new CommuteVO();
				
				appFormVO temp = new appFormVO();

				temp.setNum(rs.getInt("num"));
				temp.setTitle(rs.getString("title"));
				temp.setType(rs.getString("leave_type"));
				temp.setUseDate(rs.getString("leave_date"));
				temp.setEmp_id(rs.getInt("emp_id"));
				temp.setEmp_name(rs.getString("emp_name"));
				temp.setContext(rs.getString("content"));
				temp.setWriteDate(rs.getString("writeDate"));
				temp.setProcessing(rs.getString("processing"));
				temp.setLeave(rs.getInt("leave"));

//				temp.setEMP_ID(rs.getInt("emp_id"));
//				temp.setLeave(rs.getString("leave"));
				

				appFormList.add(temp);

			}
			System.out.println("[ CommuteDAO 안에 totaleaveCount() 메서드 끝 ]");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return appFormList;
	}

}
