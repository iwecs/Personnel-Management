package report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import login.DBUtil;

public class ReportDAO {
	static private Connection connection;
	static private PreparedStatement pstmt;
	static private ResultSet rs;
	

	public void addReport(Report report) throws SQLException {
		System.out.println("addReport");
		String sql = "INSERT INTO reports (reportTitle, reportContent, fromId, toId, reportDate) VALUES (?, ?, ?, ?, ?)";
		try { 
			
			connection=DBUtil.getConnection();
			pstmt = connection.prepareStatement(sql); 
			
			pstmt.setString(1, report.getReportTitle());
			pstmt.setString(2, report.getReportContent());
			pstmt.setString(3, report.getFromId());
			pstmt.setString(4, report.getToId());
			pstmt.setTimestamp(5, Timestamp.valueOf(report.getReportDate()));
			int rs =pstmt.executeUpdate();
			System.out.println(rs);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

//	Statement stmt = connection.createStatement(); 
//	ResultSet rs = stmt.executeQuery(sql))

	public List<Report> getAllReports() throws SQLException {
		List<Report> reports = new ArrayList<>();
		
		try {
			connection = DBUtil.getConnection();
			String sql = "SELECT * FROM reports";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Report report = new Report(rs.getString("reportTitle"), rs.getString("reportContent"),
						rs.getString("fromId"), rs.getString("toId"),
						rs.getTimestamp("reportDate").toLocalDateTime());
				reports.add(report);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return reports;
	}

	public Report getReportById(int reportId) throws SQLException {
		String sql = "SELECT * FROM reports WHERE reportId = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, reportId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Report(rs.getString("reportTitle"), rs.getString("reportContent"),
							rs.getString("fromId"), rs.getString("toId"),
							rs.getTimestamp("reportDate").toLocalDateTime());
				}
			}
		}
		return null;
	}
}
