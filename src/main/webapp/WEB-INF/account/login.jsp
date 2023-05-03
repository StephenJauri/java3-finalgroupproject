<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Log In</jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-xs-12 my-2 mx-2">
                <a href="register" class="btn button">Register</a>
            </div>
        </div>
        <form action="login" method="post" class="basic-input-groups">

            <div class="basic-input-group">
                <h4>Email</h4>
                <input type="text" value="${model.email}" name="email"/>
                <p class="error">${model.emailError}</p>
            </div>

            <div class="basic-input-group">
                <h4>Password</h4>
                <input type="password" name="password"/>
                <p class="error">${model.passwordError}</p>
                <p class="error">${model.otherError}</p>
            </div>
            <button class="btn" type="submit">Log In</button>
        </form>
    </jsp:body>
</t:layout>