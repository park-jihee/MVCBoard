<%@page import="kr.hs.sdh.board.vo.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	BoardVO vo = (BoardVO) request.getAttribute("boardVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.min.css">
<title>글 상세</title>
</head>

<body>
	<div align="center" style="margin: auto auto">
		<h3>글 상세</h3>
		<h3>
			<%= session.getAttribute("name") %>님 환영합니다 <a href="logout">logout</a>
		</h3>
		<hr>
		<form action="updateBoardCtrl" method="post">
			<input name="seq" type="hidden" value="<%= vo.getSeq() %>"/>
			<% if (vo != null) { %>
			<table class="table" style="width: 800px">
				<tr>
					<td>제목</td>
					<td align="left">
						<input name="title" type="text" value="<%= vo.getTitle() %>" class="form-control"/>
					</td>
				</tr>
				<tr>
					<td>작성자</td>
					<td align="left"><%= vo.getNickname() %></td>
				</tr>
				<tr>
					<td>내용</td>
					<td align="left">
						<textarea name="content" cols="40" rows="10" class="form-control"><%= vo.getContent() %></textarea>
					</td>
				</tr>
				<tr>
					<td>등록일</td>
					<td align="left"><%= vo.getRegdate() %></td>
				</tr>
				<tr>
					<td>조회수</td>
					<td align="left"><%= vo.getCnt() %></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="글 수정" class="btn btn-primary"/>
					</td>
				</tr>
			</table>
			<% } else { %>
			<p>존재하지 않는 게시글 입니다.</p>
			<% } %>
		</form>
		<hr>
		<a href="addBoard.jsp">글등록</a>&nbsp;&nbsp;&nbsp; 
		<a href="deleteBoardCtrl?seq=<%= vo.getSeq() %>">글삭제</a>&nbsp;&nbsp;&nbsp; 
		<a href="getBoardListCtrl">글목록</a>
	</div>
</body>
</html>










