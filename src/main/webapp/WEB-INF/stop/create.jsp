<%@ page import="com.arman_jaurigue.data_objects.Plan" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:attribute name="title">Create Stop</jsp:attribute>
    <jsp:body>
        <form action="createstop" method="post" class="basic-input-groups">
        <input type="hidden" name="planId" value="${model.planId}"/>
            <div class="basic-input-group">
                <h4>Stop Name</h4>
                <input type="text" value="${model.name}" name="name"/>
                <p class="error">${model.nameError}</p>
            </div>

            <div class="basic-input-group">
                <h4>Location</h4>
                <input type="text" value="${model.location}" name="location"/>
                <p class="error">${model.locationError}</p>
            </div>

            <div class="basic-input-group">
                <h4>Date and Time</h4>
                <input type="datetime-local" name="time" value="${model.time}"/>
                <p class="error">${model.timeError}</p>
            </div>

            <div class="basic-input-group">
                <h4>Description</h4>
                <input type="text" name="description" value="${model.description}"/>
                <p class="error">${model.descriptionError}</p>
            </div>
        <button class="btn" type="submit">Create Plan</button>
    </form>
    </jsp:body>
</t:layout>