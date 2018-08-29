<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>HTTP <%=response.getStatus()%> Message</title>
</head>
<body>
<% if(response.getStatus() == 500 || response.getStatus() == 404){ %>
	<font color="red">
	<% if(exception != null){ %>
	Message code: <%=exception.getMessage() %>
	<% } else if(response.getStatus() == 404) { %>
	Requested page doesn't exist!
	<% } else if(response.getStatus() == 500) { %>
	Internal server error occurred!
	<% } %>
	</font><br>
<% } %>
	Error code is <%=response.getStatus() %><br>
	Please go to <a href="/index.jsp">Service Manager Home</a>
</body>
</html>