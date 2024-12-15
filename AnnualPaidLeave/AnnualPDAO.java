package AnnualPaidLeave;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import login.DBUtil;
import login.LoginVO;

public class AnnualPDAO {

	static Connection conn = null;
	static PreparedStatement ps = null;
	static ResultSet rs = null;
	static LoginVO logvo = new LoginVO();
	private static ArrayList<appFormVO> list = new ArrayList<appFormVO>();

	public static ArrayList<appFormVO> select(int id) {

		System.out.println("[ AnnualPDAO �ȿ� Select()�޼��� ���� ]");

		try {
			conn = DBUtil.getConnection();

			String sql = "SELECT * FROM TypeOfLeave WHERE emp_id = ? Order by num";

			ps = conn.prepareStatement(sql);

//			ps.setInt(1, logvo.getId());
			ps.setInt(1, id);

			rs = ps.executeQuery();

			while (rs.next()) {
				appFormVO temp = new appFormVO();

				temp.setTitle(rs.getString("title"));
				temp.setType(rs.getString("leave_type"));
				temp.setUseDate(rs.getString("leave_date"));
				temp.setEmp_id(rs.getInt("emp_id"));
				temp.setEmp_name(rs.getString("emp_name"));
				temp.setContext(rs.getString("content"));
				temp.setWriteDate(rs.getString("writeDate"));
				temp.setProcessing(rs.getString("processing"));

				list.add(temp);

			}

			rs.close();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public static ArrayList<appFormVO> adminSelect() {
		System.out.println("[ AnnualPDAO �ȿ� Select()�޼��� ���� ]");

		try {
			conn = DBUtil.getConnection();

			String sql = " SELECT * FROM TypeOfLeave order by num ";

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
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

				list.add(temp);

			}

			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));

			}

			rs.close();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static boolean insert(appFormVO vo) {
		System.out.println("[ DAO �ȿ� Insert() �޼��� ���� ]");

		boolean result = false;

		try {

			conn = DBUtil.getConnection();

			String sql = "INSERT INTO TypeOfLeave (title, leave_type, leave_date, emp_id, emp_name, content ) "
					+ "VALUES ( ? , ? , ? , ? , ? , ? ) ";

			ps = conn.prepareStatement(sql);

			ps.setString(1, vo.getTitle());
			ps.setString(2, vo.getType());
			ps.setString(3, vo.getUseDate().toString());
			ps.setInt(4, vo.getEmp_id());
			ps.setString(5, vo.getEmp_name());
			ps.setString(6, vo.getContent());

			int res = ps.executeUpdate();

			if (res > 0) {
				System.out.println("SQL ���̺� �߰��Ǿ����ϴ�.");
				System.out.println("���� : " + res);
				result = true;
			} else {
				result = false;
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
		return result;

	}

	public static void search() {

		System.out.println("[ AnnualPDAO �ȿ� Search()�޼��� ���� ]");

		try {
			conn = DBUtil.getConnection();

			String sql = "SELECT * FROM TypeOfLeave where num = ? order by num ";

			ps = conn.prepareStatement(sql);

			ps.setString(1, rs.getString("num"));

			rs = ps.executeQuery();

			while (rs.next()) {
				appFormVO temp = new appFormVO();

				temp.setTitle(rs.getString("title"));
				temp.setType(rs.getString("leave_type"));
				temp.setUseDate(rs.getString("leave_date"));
				temp.setEmp_id(rs.getInt("emp_id"));
				temp.setEmp_name(rs.getString("emp_name"));
				temp.setContext(rs.getString("content"));
				temp.setWriteDate(rs.getString("writeDate"));
				temp.setProcessing(rs.getString("processing"));

				list.add(temp);

			}

			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));

			}

			rs.close();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void delete(int num) {
		System.out.println("[ DAO �ȿ� Delete() �޼��� ���� ]");

		try {

			conn = DBUtil.getConnection();

			String sql = "DELETE FROM TypeOfLeave WHERE num = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);

			ps.executeUpdate();

			System.out.println("�����Ϸ�");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static String getPreparedStatementQuery(PreparedStatement pstmt, String query) throws SQLException {
		String sql = query;
		for (int i = 1; i <= pstmt.getParameterMetaData().getParameterCount(); i++) {
			sql = sql.replaceFirst("\\?", pstmt.getParameterMetaData().getParameterTypeName(i));
		}
		return sql;
	}

	public static void approval(int num, int id) {
		// ���� ���� ��ư ���� �� �����ϴ� �ڵ�
		System.out.println("[ DAO �ȿ� Approval() �޼��� ���� ]");

		try {

			conn = DBUtil.getConnection();

			String updateSQL = "UPDATE TypeOfLeave " + "SET processing = ? " + "WHERE processing = ? AND num = ? AND emp_id = ? ";
			
			ps = conn.prepareStatement(updateSQL);
			
			ps.setString(1, "����Ϸ�");
			ps.setString(2, "��û");
			ps.setInt(3, num); // num
			ps.setInt(4, id); // emp_id
			

			System.out.println(getPreparedStatementQuery(ps, updateSQL));

			int rowsUpdated = ps.executeUpdate();
			System.out.println("rowsUpdated : " + rowsUpdated);

			int res = ps.executeUpdate();

			if (rowsUpdated > 0) {
				System.out.println("SQL ���̺� �����Ǿ����ϴ�.");
				select(id);
			} else {
				System.out.println(res);
				System.out.println("�ٽ� �������ּ���");
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

	}

	public static void reject(int num, int id) {
		// �ݷ� ��ư ���� �� �����ϴ� �ڵ�
		System.out.println("[ DAO �ȿ� Reject() �޼��� ���� ]");

		try {

			conn = DBUtil.getConnection();

			String updateSQL = "UPDATE TypeOfLeave SET PROCESSING = ? WHERE (PROCESSING = ? OR PROCESSING = ? ) AND num = ? AND emp_id = ? ";
		
			ps = conn.prepareStatement(updateSQL);

			ps.setString(1, "�ݷ�");
			ps.setString(2, "��û");
			ps.setString(3, "����Ϸ�");
			ps.setInt(4, num); // num
			ps.setInt(5, id); // emp_id
			
			int res = ps.executeUpdate();
			
			System.out.println(getPreparedStatementQuery(ps, updateSQL));
			
			int rowsUpdated = ps.executeUpdate();
			System.out.println("rowsUpdated : " + rowsUpdated);
			
			if (res > 0) {
				System.out.println("�ݷ� ó���� �����Ǿ����ϴ�.");
//				select();
			} else {
				System.out.println("�ٽ� �������ּ���");
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

	}

	public static ArrayList<appFormVO> annForm(int num) {
		System.out.println("[ DAO �ȿ� Annform() �޼��� ���� ]");

		try {

			conn = DBUtil.getConnection();

			String sql = " SELECT * FROM  TypeOfLeave WHERE num = ? ";
			ps = conn.prepareStatement(sql);

			ps.setInt(1, num);

			int res = ps.executeUpdate();

			rs = ps.executeQuery();

			while (rs.next()) {
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

}
