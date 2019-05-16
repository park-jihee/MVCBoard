<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.min.css">
<title>Index Page</title>
</head>
<body>
	<div align="center" style="margin: auto auto; height: 100%">
		<h1>게시판 제작 예제</h1>
		<br>
		<br>
		<div style="width: 800px;">
			<%
				String id = (String) session.getAttribute("id");
				if (id == null) {
			%>
			<a href="login.jsp">로그인</a> |
			<a href="getBoardListCtrl">글 목록</a> | 
			<a href="login.jsp">글 쓰기</a> 
			<%
				} else {
			%>
			<a href="logout">로그아웃</a> |
			<a href="getBoardListCtrl">글 목록</a> | 
			<a href="addBoard.jsp">글 쓰기</a>
			<% 	} %>

		</div>
	</div>
</body>
</html>