<%@ page import="com.arman_jaurigue.logic_layer.MasterManager" %>
<%@ page import="com.arman_jaurigue.data_objects.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.arman_jaurigue.data_objects.Stop" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int planOwner = (Integer)request.getAttribute("planOwner");
    User loggedInUser = (User) request.getAttribute("user");
    Stop stop = (Stop)request.getAttribute("stop");
    User proposer = MasterManager.getMasterManager().getUserManager().getUserById(stop.getUserId());;
%>
<div class="stop-control" id="stop-control-<%=stop.getStopId()%>">
    <div class="stop-name">
        Stop Name: <%= stop.getName() %>
    </div>
    <div class="stop-proposer">
        <%= proposer.getFirstName() %> <%= proposer.getLastName() %>
    </div>
    <div class="stop-time">
        Time: <%= stop.getTime() %>
    </div>
    <div class="stop-status">
        Pending
    </div>
    <div class="stop-location">
        Location: <%= stop.getLocation() %>
    </div>
    <div class="stop-desc">
        Description: <%= stop.getDescription() %>
    </div>
    <% if (planOwner == loggedInUser.getId()) { %>
    <div class="stop-accept">
        <button class="approve-stop" type="submit" value="<%=stop.getStopId()%>">Accept</button>
    </div>
    <div class="stop-deny">
        <button class="deny-stop" type="submit" value="<%=stop.getStopId()%>">Deny</button>
    </div>
    <% } %>
</div>