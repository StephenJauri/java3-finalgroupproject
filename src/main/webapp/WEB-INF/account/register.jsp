<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Register</jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="/scripts/account/register.js"></script>
    </jsp:attribute>
    <jsp:body>
        <form action="register" method="post" class="basic-input-groups">
            <div class="basic-input-group">
                <h4>First Name</h4>
                <input type="text" value="${model.firstName}"  name="firstName"/>
                <p class="error">${model.firstNameError}</p>
            </div>

            <div class="basic-input-group">
                <h4>Last Name</h4>
                <input type="text" value="${model.lastName}" name="lastName"/>
                <p class="error">${model.lastNameError}</p>
            </div>

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

            <div class="basic-input-group">
                <h4>Confirm Password</h4>
                <input type="password" name="confirmPassword"/>
                <p class="error">${model.confirmPasswordError}</p>
                <p class="error">${model.otherError}</p>
            </div>
            <button class="btn"  type="submit" id="registerSubmit">Sign Up</button>
        </form>
    </jsp:body>
</t:layout>