<%@ page import="com.arman_jaurigue.data_objects.Plan" %>
<%@ page import="com.arman_jaurigue.logic_layer.MasterManager" %>
<%@ page import="com.arman_jaurigue.data_objects.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %>
<%
  List<Plan> model = (List<Plan>) request.getAttribute("model");
  User owner = null;
%>


<div class="row">
    <div class="col-xs-12 my-2 mx-2">
        <a href="createplan" class="btn button">Add Plan</a>
    </div>
</div>

<% for(Plan plan: model) { %>
  <% owner = MasterManager.getMasterManager().getUserManager().getUserById(plan.getUserId()); %>
  <div class="plan-control container">
      <div class="plan-owner">
        Owner: <%= owner.getFirstName()%> <%= owner.getLastName()%>
      </div>
      <div class="plan-name">
        Plan Name: <%= plan.getName()%>
      </div>
      <div class="plan-start-date">
      <%=DateTimeFormatter.ofPattern("MM/dd/yy", Locale.ENGLISH).format(plan.getStartDate())%> to <%= DateTimeFormatter.ofPattern("MM/dd/yy", Locale.ENGLISH).format(plan.getEndDate())%>
      </div>
      <div class="plan-view">
        <a href="stops?planId=<%=plan.getPlanId()%>" class="btn button" type="submit" id="planView">View</a>
      </div>
  </div>
<% } %>