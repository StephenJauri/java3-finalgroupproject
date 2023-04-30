<%@ page import="com.arman_jaurigue.data_objects.Plan" %>
<%@ page import="com.arman_jaurigue.logic_layer.MasterManager" %>
<%@ page import="java.util.List" %>
<%@ page import="com.arman_jaurigue.data_objects.*" %>
<%@ page import="java.util.ArrayList" %>
<%
    Plan plan = (Plan) request.getAttribute("plan");
    List<Stop> model = (List<Stop>) request.getAttribute("model");
    User owner = (User) request.getAttribute("owner");
    List<User> attendees = (List<User>) request.getAttribute("attendees");
    User proposer;
    List<Stop> acceptedStops = new ArrayList<Stop>();
    List<Stop> pendingStops = new ArrayList<Stop>();
    List<Stop> deniedStops = new ArrayList<Stop>();

    for (Stop stop : model) {
        if (stop.getStatus() == null) {
            pendingStops.add(stop);
        } else if (stop.getStatus()) {
            acceptedStops.add(stop);
        } else {
            deniedStops.add(stop);
        }
    }
%>
<div class="container">
    <div class="row">
        <div class="col-xs-6">
            <h2>Plan: <%=plan.getName()%>
            </h2>
        </div>
        <div class="col-xs-6">
            <h2><%=DateTimeFormatter.ofPattern("MM/dd/yy", Locale.ENGLISH).format(plan.getStartDate())%> to <%= DateTimeFormatter.ofPattern("MM/dd/yy", Locale.ENGLISH).format(plan.getEndDate())%></   h2>
            <h2><%=plan.getStartDate()%> to <%=plan.getStartDate()%>
            </h2>
        </div>
    </div>
    <div class="row">
        <img src=""/>
    </div>
    <div class="row">
        <h2><%= owner.getFirstName()%> <%= owner.getLastName()%> <b>Owner</b></h2>
    </div>
        <% for (User user : attendees) { %>
            <div class="row">
                <div class="col-xs-4">
                    <h5><%=user.getFirstName()%> <%=user.getLastName() %>
                        <b><%= user.getRole().equals("Assistant") ? "Assistant" : ""%>
                        </b></h5>
                </div>
                <div class="col-xs-4">
                    <button class="button">Remove</button>
                </div>
                <div class="col-xs-4">
                    <button class="button">Edit</button>
                </div>
            </div>
    <% } %>
    <h5>Accepted</h5>
    <hr/>
    <div class="row">
        <% if (acceptedStops.size() > 0) { %>
        <% for (Stop stop : acceptedStops) { %>
        <div class="stop-control">
            <% proposer = MasterManager.getMasterManager().getUserManager().getUserById(plan.getUserId()); %>
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
                Accepted
            </div>
            <div class="stop-location">
                Location: <%= stop.getLocation() %>
            </div>
            <div class="stop-desc">
                Description: <%= stop.getDescription() %>
            </div>
        </div>
        <% } %>
        <% } else { %>
        <h7>No accepted stops</h7>
        <% } %>
    </div>
    <h5>Pending</h5>
    <hr/>
    <div class="row">
        <% if (pendingStops.size() > 0) { %>
        <% for (Stop stop : pendingStops) { %>
            <div class="stop-control">
                <% proposer = MasterManager.getMasterManager().getUserManager().getUserById(plan.getUserId()); %>
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
                <div class="stop-accept">
                    <button>Accept</button>
                </div>
                <div class="stop-deny">
                    <button>Deny</button>
                </div>
            </div>
        <% } %>
        <% } else { %>
        <h7>No pending stops</h7>
        <% } %>
    </div>
    <h5>Denied</h5>
    <hr/>
    <div class="row">
        <% if (deniedStops.size() > 0) { %>
        <% for (Stop stop : deniedStops) { %>
        <div class="stop-control">
            <% proposer = MasterManager.getMasterManager().getUserManager().getUserById(plan.getUserId()); %>
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
                Denied
            </div>
            <div class="stop-location">
                Location: <%= stop.getLocation() %>
            </div>
            <div class="stop-desc">
                Description: <%= stop.getDescription() %>
            </div>
        </div>
        <% } %>
        <% } else { %>
        <h7>No denied stops</h7>
        <% } %>
    </div>
</div>