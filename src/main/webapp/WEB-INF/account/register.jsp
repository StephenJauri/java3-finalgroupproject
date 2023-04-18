<%--
  Created by IntelliJ IDEA.
  User: geeks
  Date: 4/17/2023
  Time: 10:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="styles/register.css"/>
</head>
<body>
<form action="register" method="post">
    <div class="input-group">
        <h2>First Name</h2>
        <input type="text" value="${model.firstName}"  name="firstName"/>
        <p class="error">${model.firstNameError}</p>
    </div>

    <div class="input-group">
        <h2>Last Name</h2>
        <input type="text" value="${model.lastName}" name="lastName"/>
        <p class="error">${model.lastNameValidMessage}</p>
    </div>

    <div class="input-group">
        <h2>Email</h2>
        <input type="text" value="${model.email}" name="email"/>
        <p class="error">${model.emailError}</p>
    </div>

    <div class="input-group">
        <h2>Password</h2>
        <input type="password" name="password"/>
        <p class="error">${model.passwordError}</p>
    </div>
    <button type="submit">Sign Up</button>
</form>
</body>
</html>
