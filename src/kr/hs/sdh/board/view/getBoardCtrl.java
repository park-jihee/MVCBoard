package kr.hs.sdh.board.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.hs.sdh.board.vo.BoardVO;

public class getBoardCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		try {
			// 1. 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. 연결 설정
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "hr", "hr");
			
			// 조회 수 증사
			// 3. 쿼리 준비
			String sql = "";
			sql = "update board set cnt=cnt+1 where seq=?";
			// 4. 쿼리 세팅
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, urlSeq);
			// 5. 쿼리 실행
			stmt.executeUpdate();
			
			// 게시글 가져오기
			// 3. 쿼리 준비
			sql = "select * from board where seq=?";
			// 4. 쿼리 세팅
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, urlSeq);
			// 5. 쿼리 실행
			rs = stmt.executeQuery();

			BoardVO boardVO = new BoardVO();
			if (rs.next()) {
				int seq = rs.getInt("seq");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String nickname = rs.getString("nickname");
				Date regdate = rs.getDate("regdate");
				int cnt = rs.getInt("cnt");
				
				boardVO.setSeq(seq);
				boardVO.setTitle(title);
				boardVO.setContent(content);
				boardVO.setNickname(nickname);
				boardVO.setRegdate(regdate);
				boardVO.setCnt(cnt);
			}
			request.setAttribute("boardVO", boardVO);
			RequestDispatcher view = request.getRequestDispatcher("getBoard.jsp");
			view.forward(request, response);
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
		doGet(request, response);
	}

}
