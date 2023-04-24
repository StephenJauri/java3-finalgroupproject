<%@ page import="com.arman_jaurigue.data_objects.Plan" %>
<%@ page import="com.arman_jaurigue.logic_layer.MasterManager" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.arman_jaurigue.data_objects.*" %>
<%
    Plan plan = (Plan) request.getAttribute("plan");
    List<Stop> model = (List<Stop>) request.getAttribute("model");
    User owner = (User) request.getAttribute("owner");
    List<User> attendees = (List<User>) request.getAttribute("attendees");
%>
<div class="container">
    <div class="row">
        <div class="col-xs-6">
            <h2>Plan: <%=plan.getName()%></h2>
        </div>
        <div class="col-xs-6">
            <h2><%=plan.getStartDate()%> to <%=plan.getStartDate()%></h2>
        </div>
    </div>
    <div class="row">
        <img src=""/>
    </div>
    <div class="row">
        <h2><%= owner.getFirstName()%> <%= owner.getLastName()%> <b>Owner</b></h2>
    </div>
    <div class="row">
        <div class="col-xs-4">
            <% for (User user : attendees) { %>
            <h2><%=user.getFirstName()%> <%=user.getLastName() %> <b><%= user.getRole().equals("Assistant") ?  "Assistant" : ""%></b></h2>
            <% } %>
        </div>
        <div class="col-xs-4">
            <button>Remove</button>
        </div>
        <div class="col-xs-4">
            <button>Edit</button>
        </div>
    </div>
    <% for (Stop stop : model) { %>
        <div class="row stop-control">
            <% if (stop.getStatus()) %>
                <div class="stop-name">
                    Stop Name: <%= stop.getName() %>
                </div>
            <div class="stop-proposer">
                <% %>
            </div>
        </div>
    <% } %>
</div>