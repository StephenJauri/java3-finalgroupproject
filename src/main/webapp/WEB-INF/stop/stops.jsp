<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="styles"><link href="styles/stop.css" rel="stylesheet"></link></jsp:attribute>
    <jsp:attribute name="title">Stops</jsp:attribute>
    <jsp:attribute name="scripts"><script src="scripts/stops/stops.js"></script></jsp:attribute>
    <jsp:body>
        <jsp:include page="stops-partial.jsp"/>
    </jsp:body>
</t:layout>