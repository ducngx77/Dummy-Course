<%@ page import="entity.user.User" %>
<%@ page import="entity.user.Role" %>

<!DOCTYPE html>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->
<%
    String message = "You are an admin";
    String isAdmin = (String) request.getAttribute("isAdmin");
    String isTeacher = (String) request.getAttribute("isTeacher");
    String message1 = "You are a teacher";
%>
<html>
<head>
    <title>TODO supply a title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<%if (request.getSession().getAttribute("role") != null) {%>
<p>
    Hello, you are an/a <%=((Role) request.getSession().getAttribute("role")).getUserType()%>
</p>
<%}%>
<%if (request.getSession().getAttribute("user") != null) {%>
<h1>Hi <%=((User) request.getSession().getAttribute("user")).getUsername()%>
</h1>
<a href="login?go=logout">Log out</a>
<%}%>

</body>
</html>
