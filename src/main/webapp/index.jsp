<%--<html>--%>
<%--<body>--%>
<%--<h2>Hello World!</h2>--%>
<%--</body>--%>
<%--</html>--%>


<%@ page session="true" %>
<html>
<head><title>Welcome</title></head>
<body>

<%
    // Access the session object
    String username = (String) session.getAttribute("username");

    if (username != null) {
%>
<h1>Welcome, <%= username %>!</h1>
<%
} else {
%>
<h1>Please log in first.</h1>
<%
    }
%>

</body>
</html>