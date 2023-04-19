<%@ page import="com.arman_jaurigue.data_objects.Plan" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    List<Plan> model = (List<Plan>) request.getAttribute("model");
%>
<t:layout>
    <jsp:attribute name="title">Plans</jsp:attribute>
    <jsp:body>
        ${model.size()}
    </jsp:body>
</t:layout>