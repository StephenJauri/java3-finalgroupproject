package com.arman_jaurigue.servlets;

import com.arman_jaurigue.data_access.websocket.MessageEndpoint;
import com.arman_jaurigue.data_objects.Plan;
import com.arman_jaurigue.data_objects.Stop;
import com.arman_jaurigue.data_objects.User;
import com.arman_jaurigue.logic_layer.MasterManager;
import com.arman_jaurigue.logic_layer.PlanManager;
import com.arman_jaurigue.models.InviteUserModel;
import com.arman_jaurigue.models.Model;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet(name = "StopsServlet", value = "/stops")
public class StopsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        if (user != null)
        {
            Integer planId = Integer.parseInt(request.getParameter("planId"));
            if(planId != null) {
                try {
                    Plan plan = MasterManager.getMasterManager().getPlanManager().getPlanByPlanId(planId);
                    List<Stop> stops = MasterManager.getMasterManager().getStopManager().getAllStopsByPlanId(planId);
                    User owner = MasterManager.getMasterManager().getUserManager().getUserById(plan.getUserId());
                    List<User> attendees = MasterManager.getMasterManager().getUserManager().getUsersByPlanId(planId);
                    Set<Session> viewingSessions = MessageEndpoint.getPlanViewers(plan.getPlanId());
                    List<User> currentlyViewingUsers = viewingSessions == null ? new ArrayList<>() : viewingSessions.stream().map(sess -> MessageEndpoint.getRelatedUser(sess)).toList();
                    request.setAttribute("user", user);
                    request.setAttribute("plan", plan);
                    request.setAttribute("model", stops);
                    request.setAttribute("owner", owner);
                    request.setAttribute("currentlyViewingUsers", currentlyViewingUsers);
                    request.setAttribute("isAssistant",attendees.stream().anyMatch(attendee -> attendee.getRole().toString().equals("Assistant") && attendee.getId() == user.getId()));
                    request.setAttribute("attendees", attendees);
                    request.getRequestDispatcher("WEB-INF/stop/stops.jsp").forward(request, response);
                } catch (Exception ex) {
                    System.out.println("ERROR:" + ex.getMessage());
//                    response.sendError(500);
                    throw new ServletException(ex);
                }
            }
            else {
                response.sendError(404);
            }
        } else {
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        if (user != null) {
            InviteUserModel model = new InviteUserModel();
            request.setAttribute("inviteModel", model);
            if (Model.buildModel(model, request)) {
                try {
                    User planUser = MasterManager.getMasterManager().getUserManager().getUserByEmail(model.getUserEmail());
                    Plan plan = MasterManager.getMasterManager().getPlanManager().getPlanByPlanId(model.getPlanId());
                    List<User> planUsers = MasterManager.getMasterManager().getUserManager().getUsersByPlanId(plan.getPlanId());
                    if (planUser.getId() == user.getId()) {
                        model.setUserEmailError("You cannot invite yourself");
                    } else if (planUsers.stream().anyMatch(usr -> usr.getId() == planUser.getId())) {
                        model.setUserEmailError("You already invite this person");
                    } else {
                        System.out.println("adding");
                        MasterManager.getMasterManager().getPlanManager().addUserPlan(planUser, plan, model.getRole());
                        System.out.println("Added");
                        request.removeAttribute("inviteModel");
                    }
                } catch (Exception e) {
                    model.setUserEmailError("No user with that email found");
                }
            }
            doGet(request,response);
        } else {
            response.sendRedirect("login");
        }
    }
}
