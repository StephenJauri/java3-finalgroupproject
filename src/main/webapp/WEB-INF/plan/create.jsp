<%@ page import="com.arman_jaurigue.data_objects.Plan" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:attribute name="title">Create Plan</jsp:attribute>
    <jsp:body>
        <form action="createplan" method="post" class="basic-input-groups">

        <div class="basic-input-group">
            <h4>Plan Name</h4>
            <input type="text" value="${model.name}" name="name"/>
            <p class="error">${model.nameError}</p>
        </div>

            <div class="basic-input-group">
                <h4>Start Date and Time</h4>
                <input type="datetime-local" name="startDate" value="${model.startDate}"/>
                <p class="error">${model.startDateError}</p>
            </div>
            <div class="basic-input-group">
                <h4>End Date and Time</h4>
                <input type="datetime-local" name="endDate" value="${model.endDate}"/>
                <p class="error">${model.endDateError}</p>
            </div>
        <button class="btn" type="submit">Create Plan</button>
    </form>
    </jsp:body>
</t:layout>