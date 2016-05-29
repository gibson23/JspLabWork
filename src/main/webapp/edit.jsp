<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script type="text/javascript" src="myscript.js"></script>
    <title>Edit Page</title>
</head>
<body>
<jsp:useBean id="editingUser" class="ua.hypson.jdbclab.entity.User" scope="session" />
<h2>Edit user</h2>
<form action="EditorServ" method="post" onsubmit="return myFunction() && validateEmail()">
    Login <input type="text" name="login" value="${editingUser.login}" disabled /><br>
    Password<input type="text" name="password" value="${editingUser.password}" id="password"/><br>
    Password<input type="text" name="password2" value="${editingUser.password}" id="password2"/><br>
    Email<input type="text" name="email" value="${editingUser.email}" id="email"/><br>
    First Name<input type="text" name="firstName" value="${editingUser.firstName}" /><br>
    Last Name<input type="text" name="lastName" value="${editingUser.lastName}" /><br>
    Date of birth<input type="date" name="birthday" value="${editingUser.birthday}" /><br>
    Regular<input type="radio" name="role" value="regular" ${editingUser.role.name == 'regular' ? 'checked' : ''} />
    Admin<input type="radio" name="role" value="admin" ${editingUser.role.name == 'admin' ? 'checked' : ''} /><br>
    <input type="submit" value="Edit"> <a href="LoginServ">Cancel</a>
</form>
</body>
</html>