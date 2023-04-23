<%@ page import="com.arman_jaurigue.data_objects.Plan" %>
<%@ page import="java.util.List" %>
<%@ page import="com.arman_jaurigue.logic_layer.MasterManager" %>
<%@ page import="com.arman_jaurigue.data_objects.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="styles"><link href="styles/plan.css" rel="stylesheet"></link></jsp:attribute>
    <jsp:attribute name="title">Plans</jsp:attribute>
    <jsp:body>
        <jsp:include page="plan-partial.jsp"/>
    </jsp:body>
</t:layout>