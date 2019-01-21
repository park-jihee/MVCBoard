<%@page import="board.bbs"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="board.bbsDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.min.css">
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
			writer.println("location.href='index.jsp'");
			writer.println("</script>");
		}
		
		int bID = 0;
		if(request.getParameter("bID") != null){
			bID = Integer.parseInt(request.getParameter("bID"));
		}
		if(bID == 0){
			PrintWriter writer = response.getWriter();
			writer.println("<script>");
			writer.println("alert('유효하지 읺은 글 입니다.')");
			writer.println("location.href='index.jsp'");
			writer.println("</script>");
		}
		bbs bbs = new bbsDAO().getbbs(bID);
		if(!userID.equals(bbs.getbID())){
			PrintWriter writer = response.getWriter();
			writer.println("<script>");
			writer.println("alert('권한이 없습니다.')");
			writer.println("location.href='board.jsp'");
			writer.println("</script>");
		} else {
			bbsDAO bbsDAO = new bbsDAO();
			int result = bbsDAO.delete(bID);
			
			if(result == -1){
				PrintWriter writer = response.getWriter();
				writer.println("<script>");
				writer.println("alert('글 삭제에 실패했습니다.')");
				writer.println("history.back()");
				writer.println("</script>");
			} else {
				PrintWriter writer = response.getWriter();
				writer.println("<script>");
				writer.println("location.href='board.jsp'");
				writer.println("</script>");
			}
		}
		
	%> 
</body>
</html>