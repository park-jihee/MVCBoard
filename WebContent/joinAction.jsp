<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>

<!-- 한명의 회원정보를 담는 user클래스를 자바 빈즈로 사용 / scope:페이지 현재의 페이지에서만 사용-->
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPW" /> 
<jsp:setProperty name="user" property="userNAME" />
<jsp:setProperty name="user" property="userGENDER" />
<jsp:setProperty name="user" property="userEMAIL" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>JSP 게시판</title>
</head>
<body>
	<%
		//회원들은 페이지에 접속 할 수 없도록
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String)session.getAttribute("userID");
		}
		if(userID != null){
			PrintWriter writer = response.getWriter();
			writer.println("<script>");
			writer.println("alert('이미 로그인 되어있습니다')");
			writer.println("location.href = 'main.jsp'");
			writer.println("</script>");
		}
		if (user.getUserID() == null || user.getUserPW() == null || user.getUserNAME() == null
			|| user.getUserGENDER() == null || user.getUserEMAIL() == null){
				PrintWriter writer = response.getWriter();
				writer.println("<script>");
				writer.println("alert('입력이 안 된 사항이 있습니다.')");
				writer.println("history.back()");
				writer.println("</script>");
			} else{
				UserDAO userDAO = new UserDAO(); //인스턴스생성
				int result = userDAO.join(user);
				
				if(result == -1){ // 아이디가 기본키기. 중복되면 오류.
					PrintWriter writer = response.getWriter();
					writer.println("<script>");
					writer.println("alert('이미 존재하는 아이디 입니다.')");
					writer.println("history.back()");
					writer.println("</script>");
				}
				//가입성공
				else {
					PrintWriter writer = response.getWriter();
					writer.println("<script>");
					writer.println("location.href = 'main.jsp'");
					writer.println("</script>");
				}
			}
	 %>

</body>
</html>










