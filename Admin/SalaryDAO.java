package Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import login.DBUtil;

public class SalaryDAO {
	static Connection conn;
	static PreparedStatement pt;
	static ResultSet rs;

	public static ArrayList<SalaryVO> select() {

		System.out.println("봉급Dao클래스 select()");

		ArrayList<SalaryVO> list = new ArrayList<SalaryVO>();

		try {
			conn = DBUtil.getConnection();
			System.out.println(conn);
			String sql = "SELECT e.EMP_ID,e.EMP_NAME, s.SALARY, round(s.monthLY_pay) as MONTHLY_PAY, s.BONUS ,s.TRANSPORT_ALLOWANCE, s.TRAVEL_ALLOWANCE "
					+ "FROM employee e " + "INNER JOIN salary s ON e.EMP_ID = s.EMP_ID order by EMP_ID ";
			pt = conn.prepareStatement(sql);
			rs = pt.executeQuery();
			int i = 1;
			while (rs.next()) {
				SalaryVO temp = new SalaryVO();
				temp.setEmp_id(rs.getInt("EMP_ID"));
				temp.setEmp_name(rs.getString("EMP_NAME"));
				temp.setSalary(rs.getInt("SALARY"));
				temp.setMonthly_pay(rs.getInt("MONTHLY_PAY"));
				temp.setBonus(rs.getDouble("BONUS"));
				temp.setTravel_allowance(rs.getInt("TRAVEL_ALLOWANCE")); // 출장비
				temp.setTransport_allowance(rs.getInt("TRANSPORT_ALLOWANCE")); // 교통비
				i++;
				list.add(temp);
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

		return list;
	}

	public static ArrayList<SalaryVO> search(String criteria, String keyword, String salaryCond, String bonusTypeCond) {
		ArrayList<SalaryVO> list = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			StringBuilder sql = new StringBuilder(
					"SELECT e.EMP_ID, e.EMP_NAME, s.SALARY, round(s.MONTHLY_PAY) as MONTHLY_PAY, s.BONUS, s.TRANSPORT_ALLOWANCE, s.TRAVEL_ALLOWANCE ");
			sql.append("FROM employee e INNER JOIN salary s ON e.EMP_ID = s.EMP_ID WHERE ");

			if ("EMP_NAME".equals(criteria)) {
				sql.append(criteria).append(" LIKE ?");
				keyword = "%" + keyword + "%";
			} else if ("EMP_ID".equals(criteria)) {
				sql.append("e.").append(criteria).append("= ?");

			} else if ("MONTHLY_PAY".equals(criteria) && salaryCond != null) {
				sql.append(criteria).append(" ").append(getConditionOperator(salaryCond)).append(" ?");
			} else if ("BONUS".equals(criteria) && bonusTypeCond != null) {
				if ("%".equals(bonusTypeCond)) {
					sql.append(criteria).append(" < 1 AND ").append(criteria).append(" ")
							.append(getConditionOperator(salaryCond)).append(" ?");
					keyword = String.valueOf(Double.parseDouble(keyword) / 100);
				} else if ("정액제".equals(bonusTypeCond)) {
					sql.append(criteria).append(" >= 10000 AND ").append(criteria).append(" ")
							.append(getConditionOperator(salaryCond)).append(" ?");
				}
			}

			pt = conn.prepareStatement(sql.toString());
			pt.setString(1, keyword);
			rs = pt.executeQuery();

			while (rs.next()) {
				SalaryVO temp = new SalaryVO();
				temp.setEmp_id(rs.getInt("EMP_ID"));
				temp.setEmp_name(rs.getString("EMP_NAME"));
				temp.setSalary(rs.getInt("SALARY"));
				temp.setMonthly_pay(rs.getInt("MONTHLY_PAY"));
				temp.setBonus(rs.getDouble("BONUS"));
				temp.setTransport_allowance(rs.getInt("TRANSPORT_ALLOWANCE"));
				temp.setTravel_allowance(rs.getInt("TRAVEL_ALLOWANCE"));
				list.add(temp);
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
		return list;
	}

	private static String getConditionOperator(String condition) {
		switch (condition) {
		case "이상":
			return ">=";
		case "이하":
			return "<=";
		default:
			return "=";
		}
	}

	public static DetailVO detailSelect(int EMP_ID) {
		DetailVO detail = new DetailVO();
		try {
			conn = DBUtil.getConnection();
			System.out.println(conn);
			String sql = "SELECT * " + "FROM employee e INNER JOIN salary s ON e.emp_id = s.emp_id "
					+ "WHERE e.emp_id = ?";
			pt = conn.prepareStatement(sql);
			pt.setInt(1, EMP_ID);
			rs = pt.executeQuery();
			while (rs.next()) {
				detail.setEMP_id(rs.getInt("EMP_ID"));
				detail.setEMP_NAME(rs.getString("EMP_NAME"));
				detail.setEMP_BIRTH(rs.getString("EMP_BIRTH"));
				detail.setEMAIL(rs.getString("EMP_EMAIL"));
				detail.setPHONE(rs.getString("EMP_PHONE"));
				detail.setDEPT_CODE(rs.getString("DEPT_CODE"));
				detail.setJOB_CODE(rs.getString("JOB_CODE"));
				detail.setHIRE_DATE(rs.getString("HIRE_DATE"));
				detail.setSalary(rs.getInt("SALARY"));
				detail.setMonthly_pay(rs.getInt("MONTHLY_PAY"));
				detail.setBonus(rs.getDouble("BONUS"));
				detail.setTransport_allowance(rs.getInt("TRANSPORT_ALLOWANCE"));
				detail.setTravel_allowance(rs.getInt("TRAVEL_ALLOWANCE"));

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
		return detail;
	}

	public static void updateSalary(int empId, int salary, int monthlyPay, double bonus,int transportAllowance) {
	      Connection conn = null;
	      PreparedStatement pt = null;

	      try {
	         conn = DBUtil.getConnection();
	         String sql = "UPDATE salary SET SALARY = ?, monthLY_pay = ?, BONUS = ?, TRANSPORT_ALLOWANCE = ? WHERE EMP_ID = ?";
	         pt = conn.prepareStatement(sql);
	         pt.setInt(1, salary);
	         pt.setInt(2, monthlyPay);
	         pt.setDouble(3, bonus);
	         pt.setInt(4, transportAllowance);
	         pt.setInt(5, empId);

	         pt.executeUpdate();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (pt != null)
	               pt.close();
	            if (conn != null)
	               conn.close();
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	      }
	   }
}
