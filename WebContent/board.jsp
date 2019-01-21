<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="board.bbs"%>
<%@page import="java.util.ArrayList"%>
<%@page import="board.bbsDAO"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.min.css">
<title>JSP 게시판</title>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String)session.getAttribute("userID");
		}
	%>
	
	<nav class="navbar navbar-default">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="main.jsp" class="navbar-brand">JSP 게시판</a>
        </div>
        <div class="collapse navbar-collapse" id="#bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="main.jsp">메인</a></li>
                <li><a href="board.jsp">게시판</a></li>
            </ul>
            
            <%
            	//로그인 안된 경우
            	if(userID == null){
            %>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">접속하기<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="login.jsp">로그인</a></li>
                        <li><a href="join.jsp">회원가입</a></li>
                    </ul>
                </li>            
            </ul>
            <%
            	//로그인 안된 경우
            	} else {
            %>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">회원관리<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="logoutAction.jsp">로그아웃</a></li>
                    </ul>
                </li>            
            </ul>
            <%
            	}
            %>
            </div>
        </nav>
        <!--게시판 -->
    <div class="container">
		<div class = "row">
			<table class="table table-striped" style="text-align:center; border:1px solid #dddddd"> 
				<thead>
					<tr>
						<th style="background-color: #eeeeee; text-align: center;">번호</th>
						<th style="background-color: #eeeeee; text-align: center;">제목</th>
						<th style="background-color: #eeeeee; text-align: center;">작성자</th>
						<th style="background-color: #eeeeee; text-align: center;">작성일</th>
					</tr>
				</thead>
				<tbody>
				<%
					bbsDAO bbsDAO = new bbsDAO();
					ArrayList<bbs> list = bbsDAO.getList(pageNumber);
					for(int i=0; i<list.size(); i++){
				%>
					<tr>
						<td><%=list.get(i).getbID()%></td>
						<td><a href="view.jsp?bID=<%=list.get(i).getbID()%>"><%=list.get(i).getbTITLE()%></a></td>
						<td><%=list.get(i).getUserID()%></td>
						<td><%=list.get(i).getbDATE().substring(0,11) + list.get(i).getbDATE().substring(11,13)+"시" + list.get(i).getbDATE().substring(14,16)+"분"%></td>
					</tr>
				<%
					}
				%>
				</tbody>
			</table>	
			<!-- 페이지 넘기기 -->
			
			<%
				if(pageNumber != 1){
			%>
			
			<a href="board.jsp?pageNumber=<%=pageNumber-1%>" clss="btn btn-success btn-arrow-left">이전</a>
			
			<%
				} 
				if(bbsDAO.nextPage(pageNumber)){
			%>
			
			<a href="board.jsp?pageNumber=<%=pageNumber+1%>" clss="btn btn-success btn-arrow-left">다음</a>
			
			<%
				}
			%>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>

</body>
</html>