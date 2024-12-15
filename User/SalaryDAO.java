package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import login.DBUtil;

public class SalaryDAO {
	static Connection conn;
	static PreparedStatement pt;
	static ResultSet rs;

	public static SalaryVO Select(int emp_id) {
		SalaryVO salary = new SalaryVO();
		
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT e.*,s.*,d.dept_name, p.position " + "FROM employee e "
					+ "INNER JOIN salary        s ON e.emp_id = s.emp_id "
					+ "INNER JOIN department    d ON e.dept_code = d.dept_code "
					+ "INNER JOIN position_rank p ON e.job_code = p.job_code " + "WHERE e.emp_id = ?";
			pt = conn.prepareStatement(sql);
			pt.setInt(1, emp_id);
			rs = pt.executeQuery();
			while (rs.next()) {
				salary.setEMP_id(rs.getInt("EMP_ID"));
				salary.setEMP_NAME(rs.getString("EMP_NAME"));
				salary.setEMP_BIRTH(rs.getString("EMP_BIRTH"));
				salary.setEMAIL(rs.getString("EMP_EMAIL"));
				salary.setPHONE(rs.getString("EMP_PHONE"));
				salary.setDEPT_CODE(rs.getString("DEPT_NAME"));
				salary.setJOB_CODE(rs.getString("POSITION"));
				salary.setHIRE_DATE(rs.getString("HIRE_DATE"));
				salary.setSalary(rs.getInt("SALARY"));
				salary.setMonthly_pay(rs.getInt("MONTHLY_PAY"));
				salary.setBonus(rs.getDouble("BONUS"));
				salary.setTransport_allowance(rs.getInt("TRANSPORT_ALLOWANCE"));
				salary.setTravel_allowance(rs.getInt("TRAVEL_ALLOWANCE"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pt != null)
					pt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return salary;
	}

	public static List<Object[]> getTravelDetails(int emp_id) {
		List<Object[]> travelDetails = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("\u00A4 ###,###");

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT BT.WHATDAY, BTD.PLACE, " + "CASE BTD.PLACE_CODE " + "WHEN 'P1' THEN 10000 "
					+ "WHEN 'P2' THEN 20000 " + "WHEN 'P3' THEN 10000 " + "WHEN 'P4' THEN 30000 "
					+ "WHEN 'P5' THEN 50000 " + "WHEN 'P6' THEN 100000 " + "WHEN 'P7' THEN 10000 "
					+ "WHEN 'P8' THEN 50000 " + "END AS ALLOWANCE " + "FROM BUSINESS_TRIP BT "
					+ "JOIN BUSINESS_TRIP_DESTINATION BTD ON BT.PLACE_CODE = BTD.PLACE_CODE " + "WHERE BT.EMP_ID = ?"
							+ "ORDER BY BT.WHATDAY";
			pt = conn.prepareStatement(sql);
			pt.setInt(1, emp_id);
			ResultSet rs = pt.executeQuery();

			while (rs.next()) {
				travelDetails.add(new Object[] { rs.getDate("WHATDAY"), rs.getString("PLACE"),
						df.format(rs.getInt("ALLOWANCE")) });
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pt != null)
					pt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return travelDetails;
	}
}
