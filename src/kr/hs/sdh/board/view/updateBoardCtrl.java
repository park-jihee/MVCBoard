package kr.hs.sdh.board.view;

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

public class updateBoardCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		if (id == null) {
			response.sendRedirect("login.jsp");
		}
		
		int urlSeq = Integer.parseInt(request.getParameter("seq"));
		
		// DB 작업
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String reqTitle = request.getParameter("title");
		String reqContent = request.getParameter("content");
		try {
			// 1. 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. 연결 설정
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "hr", "hr");
			// 3. 쿼리 준비
			String sql = "update board set title=?, content=? where seq=?";
			// 4. 쿼리 세팅
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, reqTitle);
			stmt.setString(2, reqContent);
			stmt.setInt(3, urlSeq);
			// 5. 쿼리 실행
			rs = stmt.executeQuery();

			response.sendRedirect("getBoardListCtrl");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 닫기
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
