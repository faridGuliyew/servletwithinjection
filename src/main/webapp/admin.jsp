<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession s = request.getSession();
    String role = (String) s.getAttribute("role");
%>

<!DOCTYPE html>
<html>
<head>
    <title>ADMIN PANEL</title>
</head>
<body>
<h2>ADD INFORMATION</h2>

<% if ("administrator".equals(role)) { %>
<form action="admin" method="get">
    <input type="text" name="information" placeholder="Enter information to add">
    <input type="submit" value="ADD">
</form>
<% } else { %>
<p>You do not have permission to access this page.</p>
<% } %>
</body>
</html>