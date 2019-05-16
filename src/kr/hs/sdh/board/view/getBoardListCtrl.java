package kr.hs.sdh.board.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.hs.sdh.board.vo.BoardVO;

public class getBoardListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		if (id == null) {
			response.sendRedirect("login.jsp");
		}

		// 검색을 위한 키 받기
		request.setCharacterEncoding("UTF-8");
		String searchCondition = request.getParameter("searchCondition");
		String searchKeyword = request.getParameter("searchKeyword");

		// 처음 조회시에 기본값을 넣기
		if (searchCondition == null) {
			searchCondition = "TITLE";
		}
		if (searchKeyword == null) {
			searchKeyword = "";
		}

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
			// 처음 조회시에 기본값을 넣기
			String sql = "select * from board where title like '%'||?||'%' order by seq desc";
			// 4. 쿼리 세팅
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, searchKeyword);
			// 5. 쿼리 실행
			rs = stmt.executeQuery();

			ArrayList<BoardVO> boardList = new ArrayList<>();
			while (rs.next()) {
				BoardVO vo = new BoardVO();
				int seq = rs.getInt("seq");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String nickname = rs.getString("nickname");
				Date regdate = rs.getDate("regdate");
				int cnt = rs.getInt("cnt");
				
				vo.setSeq(seq);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setNickname(nickname);
				vo.setRegdate(regdate);
				vo.setCnt(cnt);
				
				boardList.add(vo);
			} 
			request.setAttribute("boardList", boardList);
			RequestDispatcher view = request.getRequestDispatcher("getBoardList.jsp");
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
