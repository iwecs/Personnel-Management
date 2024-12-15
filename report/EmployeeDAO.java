//package report;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//public class EmployeeDAO {
//	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
//	private static final String DB_USER = "c##system";
//	private static final String DB_PASSWORD = "1234";
//
//	public List<String> getEmployeeNames() {
//		List<String> employeeNames = new ArrayList<>();
//		String sql = "SELECT EMP_NAME FROM EMPLOYEE";
//
//		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//				PreparedStatement statement = connection.prepareStatement(sql);
//				ResultSet resultSet = statement.executeQuery()) {
//
//			while (resultSet.next()) {
//				employeeNames.add(resultSet.getString("EMP_NAME"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return employeeNames;
//	}
//}