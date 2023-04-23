<%@ page import="com.arman_jaurigue.data_objects.Plan" %>
<%@ page import="com.arman_jaurigue.logic_layer.MasterManager" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.arman_jaurigue.data_objects.Stop" %>
<%
    Plan plan = (Plan) request.getAttribute("plan");
    List<Stop> model = (List<Stop>) request.getAttribute("model");
%>
<div class="container">
    <div class="row">
        <div class="col-xs-6">
            <h2>Plan: <%=plan.getName()%>
            </h2>
        </div>
        <div class="col-xs-6">
            <h2><%=plan.getStartDate()%> to <%=plan.getStartDate()%>
            </h2>
        </div>
    </div>
    <div class="row">
        <img src=""/>
    </div>
    <% for (Stop stop : model) { %>
    <div class="plan-control container">
        <div class="plan-owner">
            Owner: <%= owner.getFirstName()%> <%= owner.getLastName()%>
        </div>
        <div class="plan-name">
            Plan Name: <%= plan.getName()%>
        </div>
        <div class="plan-start-date">
            <%=DateTimeFormatter.ofPattern("MM/dd/yy", Locale.ENGLISH).format(plan.getStartDate())%>
            to <%= DateTimeFormatter.ofPattern("MM/dd/yy", Locale.ENGLISH).format(plan.getEndDate())%>
        </div>
        <div class="plan-view">
            <button class="btn" type="submit" id="planView">View</button>
        </div>
    </div>
    <% } %>
</div>