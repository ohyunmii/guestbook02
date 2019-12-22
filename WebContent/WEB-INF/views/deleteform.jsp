<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String no = request.getParameter("no");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Guestbook</title>
</head>
<body>
	<form method="post" action="/guestbook01/delete.jsp">
	<input type='hidden' name="no" value="<%=no %>">
	<table>
		<tr>
			<td>password</td>
			<td><input type="password" name="password"></td>
			<td><input type="submit" value="confirm"></td>
			<td><a href="/guestbook01">Go Back to Main</a></td>
		</tr>
	</table>
	</form>
</body>
</html>