<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="styles"><link href="styles/plan.css" rel="stylesheet"></link></jsp:attribute>
    <jsp:attribute name="title">Plans</jsp:attribute>
    <jsp:body>
        <jsp:include page="plan-partial.jsp"/>
    </jsp:body>
</t:layout>