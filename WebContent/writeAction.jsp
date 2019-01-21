<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.bbsDAO" %>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html; charset=UTF-8");
%>
<jsp:useBean id="board" class="board.bbs" scope="page" />
<jsp:setProperty name="board" property="bTITLE" />
<jsp:setProperty name="board" property="bCONTENT" />
<%
	System.out.println(board);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="UTF-8">
<title>JSP 게시판</title>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String)session.getAttribute("userID");
		}
		if(userID == null){
			PrintWriter writer = response.getWriter();
			writer.println("<script>");
			writer.println("alert('로그인을 하세요.')");
			writer.println("location.href='login.jsp'");
			writer.println("</script>");
		} else {
			if(board.getbTITLE()==null || board.getbCONTENT()==null){
				PrintWriter writer = response.getWriter();
				writer.println("<script>");
				writer.println("alert('입력이 안된 사항이 있습니다.')");
				writer.println("history.back()");
				writer.println("</script>");	
			} else {
				bbsDAO bbsDAO = new bbsDAO();
				int result = bbsDAO.write(board.getbTITLE(), userID, board.getbCONTENT());
				if(result == -1){
					PrintWriter writer = response.getWriter();
					writer.println("<script>");
					writer.println("alert('글쓰기를 실패했습니다.')");
					writer.println("history.back()");
					writer.println("</script>");
				} else {
					PrintWriter writer = response.getWriter();
					writer.println("<script>");
					writer.println("location.href='board.jsp'");
					writer.println("history.back()");
					writer.println("</script>");
				}
 			}
		}
	%>
</body>
</html>