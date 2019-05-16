<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.min.css">
<title>로그인</title>
</head>
<body>
	<div align="center" style="margin: auto auto">
		<h1>로그인</h1>
		<hr>
		<form action="login" method="post">
			<table class="table" style="width:400px">
				<tr>
					<td>아이디</td>
					<td><input type="text" name="id" /></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="로그인" class="btn btn-primary"/>
					</td>
				</tr>
			</table>
		</form>
		<hr>
	</div>
</body>
</html>