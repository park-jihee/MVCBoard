<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>

<jsp:useBean id="user" class="user.User" />
<jsp:setProperty name="user" property="userID"/>
<jsp:setProperty name="user" property="userPW"/>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="UTF-8">
<title>JSP 게시판</title>
</head>
<body>
	<% 
		UserDAO userDAO = new UserDAO();
		int result = userDAO.login(user.getUserID(), user.getUserPW());
		
		//로그인 성공
		if(result == 1){
			PrintWriter writer = response.getWriter();
			writer.println("<script>");
			writer.println("location.href='main.jsp'");
			writer.println("</script>");
		}
		
		//로그인 실패
		else if(result == 0){
			PrintWriter writer = response.getWriter();
			writer.println("<script>");
			writer.println("alert('비밀번호가 틀립니다.')");
			writer.println("history.back()");
			writer.println("</script>");
		}
		
		//아이디 없음
		else if(result == -1){
			PrintWriter writer = response.getWriter();
			writer.println("<script>");
			writer.println("alert('존재하지 않는 아이디 입니다.')");
			writer.println("history.back()");
			writer.println("</script>");
		}
		
		//아이디 없음
		else if(result == -1){
			PrintWriter writer = response.getWriter();
			writer.println("<script>");
			writer.println("alert('DB오류기 발생했습니다.')");
			writer.println("history.back()");
			writer.println("</script>");
		}
		
		%>
</body>
</html>