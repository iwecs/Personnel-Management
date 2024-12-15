package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Admin.EmployeeVO;
import login.DBUtil;
import login.loginGUI;

public class UserDao {

	static Connection conn;
	static PreparedStatement pt1, pt2;
	static ResultSet rs1, rs2;

	// �� EMPLOYEE ã��
	public static UserVO searchMyEMP(int inputId) {

		UserVO vo = null;

		try {

			System.out.println("UserDao�� searchMyEMP �Լ� ����");
			conn = DBUtil.getConnection();

			String sql = "select * from employee ";
			sql += " where EMP_ID = ?";

			pt1 = conn.prepareStatement(sql);
			pt1.setInt(1, inputId);
			rs1 = pt1.executeQuery();

			if (rs1.next()) {

				vo = new UserVO(rs1.getInt("EMP_ID"), rs1.getString("EMP_NAME"), rs1.getString("EMP_BIRTH"),
						rs1.getString("EMP_EMAIL"), rs1.getString("EMP_PHONE"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vo;
	}

	// �� �μ��� ã��
	public static UserVO serchMyDEPT(int inputId) {
		
		UserVO vo = null;

		try {

			System.out.println("UserDao�� serchMyDEPT �Լ� ����");
			conn = DBUtil.getConnection();

			String sql = "select * from employee ";
			sql += " where EMP_ID = ?";

			pt1 = conn.prepareStatement(sql);
			pt1.setInt(1, inputId);
			rs1 = pt1.executeQuery();
			
			if (rs1.next()) {

				vo = new UserVO(rs1.getString("DEPT_CODE"), rs1.getInt("EMP_ID"));
			}
			
			String sql1 = "select DEPT_NAME from DEPARTMENT where DEPT_CODE = ?";
			
			pt2 = conn.prepareStatement(sql1);
			pt2.setString(1, vo.getDEPT_CODE());
			rs2 = pt2.executeQuery();

			if (rs2.next()) {
				vo.setDept_name(rs2.getString("DEPT_NAME"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vo;
	}
	
	// �� ���� ã��
	public static UserVO searchMyPosition(int inputId) {

		UserVO vo = null;
		
		try {

			System.out.println("UserDao�� searchMyPosition �Լ� ����");
			conn = DBUtil.getConnection();

			String sql = "select * from employee ";
			sql += " where EMP_ID = ?";

			pt1 = conn.prepareStatement(sql);
			pt1.setInt(1, inputId);
			rs1 = pt1.executeQuery();

			if (rs1.next()) {

				vo = new UserVO(rs1.getInt("EMP_ID"), rs1.getString("JOB_CODE"));
			}
			
			
			String sql1 = "select POSITION from POSITION_RANK where JOB_CODE = ?";
			
			pt2 = conn.prepareStatement(sql1);
			pt2.setString(1, vo.getJOB_CODE());
			rs2 = pt2.executeQuery();

			if (rs2.next()) {
				vo = new UserVO(rs2.getString("POSITION"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return vo;
	}
	
	
		// �� �μ� ����� ���� ���
		public static ArrayList<UserVO> searchMyDeptPerson(int inputId) {

			UserVO vo = null;
			ArrayList<UserVO> list = new ArrayList<UserVO>();
			
			try {

				System.out.println("UserDao�� searchMyDeptPerson �Լ� ����");
				conn = DBUtil.getConnection();

				String sql = "select * from employee ";
				sql += " where EMP_ID = ?";

				pt1 = conn.prepareStatement(sql);
				pt1.setInt(1, inputId);
				rs1 = pt1.executeQuery();

				if (rs1.next()) {
					vo = new UserVO(rs1.getString("DEPT_CODE"), rs1.getInt("EMP_ID"));
				}
				
				String sqlForDept = "SELECT E.EMP_NAME, E.EMP_ID, E.EMP_BIRTH, E.EMP_PHONE, E.EMP_EMAIL, " +
						"D.DEPT_NAME, P.POSITION " +
						"FROM EMPLOYEE E " +
						"JOIN DEPARTMENT D ON E.DEPT_CODE = D.DEPT_CODE " +
						"JOIN POSITION_RANK P ON E.JOB_CODE = P.JOB_CODE " +
						"WHERE E.DEPT_CODE = ?";
				
				pt2 = conn.prepareStatement(sqlForDept);
				pt2.setString(1, vo.getDEPT_CODE());
				rs2 = pt2.executeQuery();
				
				while(rs2.next()) {
					UserVO PersonVO = new UserVO(
						rs2.getInt("EMP_ID"),
						rs2.getString("EMP_NAME"),
						rs2.getString("POSITION"),
						rs2.getString("DEPT_NAME"),
						rs2.getString("EMP_BIRTH"),
						rs2.getString("EMP_PHONE"),
						rs2.getString("EMP_EMAIL")
							);
					list.add(PersonVO);
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return list;
		}

	
}