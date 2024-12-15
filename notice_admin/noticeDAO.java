package notice_admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import login.DBUtil;

public class noticeDAO {
	static Connection conn;
	static PreparedStatement pt;
	static ResultSet rs;

	public static ArrayList<noticeVO> select() {
		System.out.println("noticeDAO클래스 select()");

		ArrayList<noticeVO> list = new ArrayList<noticeVO>();

		try {
			conn = DBUtil.getConnection();
			String sql = "select * from board";
			pt = conn.prepareStatement(sql);
			rs = pt.executeQuery();

			while (rs.next()) {
				noticeVO temp = new noticeVO(sql, sql, 0, null, sql);
				temp.setTitle(rs.getString("title"));
				temp.setContent(rs.getString("content"));
				temp.setWriter_id(rs.getInt("writer_id"));

				Timestamp date = rs.getTimestamp("write_date");
				temp.setWrite_Date(date.toLocalDateTime());

				list.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void insert() {

		System.out.println("Dao 클래스 insert()");
		conn = DBUtil.getConnection();

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void insert(noticeVO temp) {
		try {
			// 1. db연결
			conn = DBUtil.getConnection();
			// 2. sql 명령문
			String sql = "insert into board(title, content, writer_id, write_date) ";
			sql += " values(board_idx1.nextval, ? , ? , ? ,sysdate)";

			// 3. 임시 전송
			pt = conn.prepareStatement(sql);
			pt.setString(1, temp.getTitle());
			pt.setString(2, temp.getContent());
			pt.setInt(3, temp.getWriter_id());

			int result = pt.executeUpdate();
			System.out.println("전송 :" + result);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pt != null)
					pt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static noticeVO search(String title) {

		noticeVO vo = null;

		try {

			conn = DBUtil.getConnection();

			String sql = "select * from board ";
			sql += " where title = ?";

			pt = conn.prepareStatement(sql);
			pt.setString(1, title);

			rs = pt.executeQuery();

			if (rs.next()) {

				vo = new noticeVO(rs.getString("title"), rs.getString("content"), rs.getInt("writer_id"),
						rs.getTimestamp("write_Date").toLocalDateTime(), rs.getString("noticeType"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vo;
	}

	public static int update(noticeVO vo) {

		conn = DBUtil.getConnection();
		int result = 0;

		try {

			String sql = "update board ";
			sql += " set title =  ?, content = ?, writer_id =? ";
			sql += " where title = ?";

			pt = conn.prepareStatement(sql);

			pt.setString(1, vo.getTitle());
			pt.setString(2, vo.getContent());
			pt.setInt(3, vo.getWriter_id());
			pt.setString(4, vo.getTitle());

			result = pt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int delete(noticeVO vo) {
		conn = DBUtil.getConnection();
		int result = 0;

		try {

			String sql = "delete from board ";
			sql += " where title = ?";

			pt = conn.prepareStatement(sql);

			pt.setString(1, vo.getTitle());

			result = pt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}