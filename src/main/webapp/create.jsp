<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script type="text/javascript" src="myscript.js"></script>
    <title>Create User Page</title>
</head>
<body>
<h2>Add user</h2>
<form action="CreatorServ" method="post" onsubmit="return myFunction() && validateEmail()" >
    <table >
        <tr>
            <td>Login</td>
            <td><input type="text" name="login" ></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="text" name="password" id="password"></td>
        </tr>
        <tr>
            <td>Confirm password</td>
            <td><input type="text" name="password2" id="password2" ></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input type="text" name="email" id="email"></td>
        </tr>
        <tr>
            <td>First Name</td>
            <td><input type="text" name="firstName" ></td>
        </tr>
        <tr>
            <td>Last Name</td>
            <td><input type="text" name="lastName" ></td>
        </tr>
        <tr>
            <td>Date of birth</td>
            <td><input type="date" name="birthday" ></td>
        </tr>
        <tr>
            <td>Role</td>
            <td>Regular<input type="radio" name="role" value="regular" checked>
                Admin<input type="radio" name="role" value="admin" ></td>
        </tr>

    </table>
    <br>
    <input type="submit" value="Add">  <a href="LoginServ">Cancel</a>

</form>
</body>
</html>