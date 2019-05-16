package kr.hs.sdh.board.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class deleteBoardCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");

		if (id == null) {
			response.sendRedirect("login.jsp");
		}

		int seq = Integer.parseInt(request.getParameter("seq"));

		// DB 작업
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// 1. 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. 연결 설정
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "hr", "hr");

			// 게시글 가져오기
			// 3. 쿼리 준비
			String sql = "delete from board where seq=?";
			// 4. 쿼리 세팅
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, seq);
			// 5. 쿼리 실행
			stmt.executeUpdate();
			
			response.sendRedirect("getBoardListCtrl");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 닫기
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
