package BusinessTrip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import AnnualPaidLeave.appFormVO;
import login.DBUtil;
import login.LoginVO;

public class BusinessTripDAO {

	static Connection conn = null;
	static PreparedStatement ps, ps2 = null;
	static ResultSet rs = null;
	static LoginVO logvo = new LoginVO();
	private static ArrayList<BusinessTripVO> list = new ArrayList<BusinessTripVO>();

	public static ArrayList<BusinessTripVO> select(int id) {
		System.out.println("[ BusinessTripDAO 안에 Select()메서드 실행 ]");

		try {
			conn = DBUtil.getConnection();

			String sql = "SELECT BT.NUM, BT.EMP_ID, EMP.EMP_NAME, BTD.PLACE, BT.WHATDAY, BT.PROCESSING FROM BUSINESS_TRIP BT "
					+ "    INNER JOIN BUSINESS_TRIP_DESTINATION BTD ON BTD.PLACE_CODE = BT.PLACE_CODE "
					+ "    INNER JOIN EMPLOYEE EMP ON EMP.EMP_ID = BT.EMP_ID " + "    WHERE EMP.EMP_ID = ? "
					+ "    Order by BT.NUM ";

			ps = conn.prepareStatement(sql);

//			ps.setInt(1, logvo.getId());
			ps.setInt(1, id);

			rs = ps.executeQuery();

			while (rs.next()) {
				BusinessTripVO temp = new BusinessTripVO();

				temp.setNum(rs.getInt("num"));
				temp.setEmp_id(rs.getInt("emp_id"));
				temp.setEmp_name(rs.getString("emp_name"));
				temp.setPlace(rs.getString("PLACE"));
				temp.setWHATDAY(rs.getString("WHATDAY"));
				temp.setProcessing(rs.getString("PROCESSING"));

				list.add(temp);

			}

//			for (int i = 0; i < list.size(); i++) {
//				System.out.println(list.get(i));
//
//			}

			rs.close();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void delete(int num) {
		System.out.println("[ DAO 안에 Delete() 메서드 실행 ]");

		try {

			conn = DBUtil.getConnection();

			String sql = "DELETE FROM BUSINESS_TRIP WHERE num = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);

			ps.executeUpdate();

			System.out.println("삭제완료");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static boolean insert(BusinessTripVO vo) {
		System.out.println("[ BusinessTripDAO 안에 Insert() 메서드 실행 ]");

		boolean result = false;

		try {

			conn = DBUtil.getConnection();

			String sql = "INSERT INTO BUSINESS_TRIP (WHATDAY, PLACE_CODE, EMP_ID, WHETHER) "
					+ "VALUES ( ? , ? , ? , 'Y' ) ";

			ps = conn.prepareStatement(sql);

			ps.setString(1, vo.getWHATDAY());
			ps.setString(2, vo.getType());
			ps.setInt(3, vo.getEmp_id());

			System.out.println("res : " + vo.getType());

			int res = ps.executeUpdate();

			if (res > 0) {
				System.out.println("SQL 테이블에 추가되었습니다.");
				System.out.println("전송 : " + res);
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

	public static ArrayList<BusinessTripVO> selectAdmin() {
		System.out.println("[ BusinessTripDAO 안에 Select()메서드 실행 ]");

		try {
			conn = DBUtil.getConnection();

			String sql = "    SELECT BT.NUM, BT.EMP_ID, EMP.EMP_NAME, BTD.PLACE, BT.WHATDAY, BT.PROCESSING FROM BUSINESS_TRIP BT "
					+ "    INNER JOIN BUSINESS_TRIP_DESTINATION BTD ON BTD.PLACE_CODE = BT.PLACE_CODE "
					+ "    INNER JOIN EMPLOYEE EMP ON EMP.EMP_ID = BT.EMP_ID " + "    Order by BT.NUM ";

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				BusinessTripVO temp = new BusinessTripVO();

				temp.setNum(rs.getInt("NUM"));
				temp.setEmp_id(rs.getInt("emp_id"));
				temp.setEmp_name(rs.getString("emp_name"));
				temp.setPlace(rs.getString("PLACE"));
				temp.setWHATDAY(rs.getString("WHATDAY"));
				temp.setProcessing(rs.getString("PROCESSING"));

				list.add(temp);

			}

//			for (int i = 0; i < list.size(); i++) {
//				System.out.println(list.get(i));
//			}

			rs.close();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void approval(int num, int id, int place) {
		System.out.println("[ DAO 안에 approval() 메서드 실행 ]");

		try {

			conn = DBUtil.getConnection();

			String updateSQL = "UPDATE BUSINESS_TRIP SET processing = ? "
					+ "WHERE processing = ? AND num = ? AND emp_id = ? ";

			ps = conn.prepareStatement(updateSQL);

			ps.setString(1, "결재완료");
			ps.setString(2, "신청");
			ps.setInt(3, num); // num
			ps.setInt(4, id); // emp_id
			System.out.println("num : " + num + ", id : " + id);

			int rowsUpdated = ps.executeUpdate();

			// 출장 승인 시, 출장비 업데이트

			String businessSQL = "UPDATE SALARY S " + "SET travel_allowance = ?  WHERE EMP_ID = ? ";

			ps2 = conn.prepareStatement(businessSQL);

			ps2.setInt(1, place); // emp_id
			ps2.setInt(2, id);

			int res = ps2.executeUpdate();
			System.out.println("res : " + res);
			System.out.println("rowsUpdated : " + rowsUpdated);

			if (res > 0) {
				System.out.println("SQL 테이블에 수정되었습니다.");
				select(id);
			} else {
				System.out.println(res);
				System.out.println("다시 선택해주세요");
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
		// 반려 버튼 실행 시 동작하는 코드
		System.out.println("[ DAO 안에 Reject() 메서드 실행 ]");

		try {

			conn = DBUtil.getConnection();

			String updateSQL = "UPDATE BUSINESS_TRIP SET PROCESSING = ? "
					+ " WHERE (PROCESSING = ? OR PROCESSING = ? ) AND num = ? AND emp_id = ? ";

			ps = conn.prepareStatement(updateSQL);

			ps.setString(1, "반려");
			ps.setString(2, "신청");
			ps.setString(3, "결재완료");
			ps.setInt(4, num); // num
			ps.setInt(5, id); // emp_id

			int res = ps.executeUpdate();

			int rowsUpdated = ps.executeUpdate();
			System.out.println("rowsUpdated : " + rowsUpdated);

			if (res > 0) {
				System.out.println("반려 처리로 수정되었습니다.");
//						select();
			} else {
				System.out.println("다시 선택해주세요");
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

	public static String sreach(int inputId) {
		System.out.println("[ DAO 안에 Reject() 메서드 실행 ]");

		String name = null;

		try {

			conn = DBUtil.getConnection();

			String updateSQL = "select EMP_NAME from employee where EMP_ID = ?";

			ps = conn.prepareStatement(updateSQL);

			ps.setInt(1, inputId);

			rs = ps.executeQuery();

			if (rs.next()) {

				name = rs.getString("emp_name");
			}

			int rowsUpdated = ps.executeUpdate();
			System.out.println("rowsUpdated : " + rowsUpdated);

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
		return name;
	}

}
