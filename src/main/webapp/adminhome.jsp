<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="mytag" uri="mytag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Admin Home</title>
</head>
<body>
<jsp:useBean id="user" class="ua.hypson.jdbclab.entity.User" scope="session" />
<jsp:useBean id="users" class="java.util.ArrayList" scope="request" />

<p>Admin  <jsp:getProperty property="firstName" name="user"/> (<a href="LogOutServ">log out</a>)</p>
<a href="create.jsp">Add user</a>
<mytag:my list="${users}" />

</body>
</html>
