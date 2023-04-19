<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Register</jsp:attribute>
    <jsp:body>
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
            </div>
            <button type="submit">Sign Up</button>
        </form>
    </jsp:body>
</t:layout>