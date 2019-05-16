package kr.hs.sdh.user.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// DB 작업
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
			
		try {
			// 1. 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. 연결 설정
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "hr", "hr");
			// 3. 쿼리 준비
			String sql = "select * from users where id=? and password=?";
			stmt = conn.prepareStatement(sql);
			// 4. 쿼리 세팅
			stmt.setString(1, id);
			stmt.setString(2, password);
			// 5. 쿼리 실행
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				HttpSession session = request.getSession();
				session.setAttribute("id", rs.getString("id"));
				session.setAttribute("name", rs.getString("name"));
				
				response.sendRedirect("getBoardListCtrl");
			} else {
				response.sendRedirect("login.jsp");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 닫기
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
