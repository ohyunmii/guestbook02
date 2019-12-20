package com.bigdata2019.guestbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bigdata2019.guestbook.vo.GuestbookVo;

public class GuestbookDao {
	
	@SuppressWarnings("finally")
	public Boolean insert(GuestbookVo vo) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			
			String sql = "insert into guestbook values(null, ?, ?, ?, now())";
			pstmt = conn.prepareStatement(sql);

			// Execute SQL Statment
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());
			

			int count = pstmt.executeUpdate();
			result = (count == 1);

			
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load driver: " + e);
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		} finally {
			// remove resources
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

	}
	
	public Boolean delete(Long no, String password) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
	
			String sql = " delete from guestbook where no = ? and password = ?";
					
			pstmt = conn.prepareStatement(sql);			
		
			pstmt.setLong(1, no);
			pstmt.setString(2, password);
			
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load Driver: " + e);
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<GuestbookVo> findAll() {
		List<GuestbookVo> result = new ArrayList<GuestbookVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "select no, name, contents, date_format(reg_date, '%Y-%c-%d %h:%i:%s') from guestbook order by no desc";

			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				GuestbookVo vo = new GuestbookVo();
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setRegDate(rs.getString(4));

				result.add(vo);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load driver: " + e);
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");

		String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=utf8";
		Connection conn = DriverManager.getConnection(url, "webdb", "webdb");

		return conn;
	}

}
