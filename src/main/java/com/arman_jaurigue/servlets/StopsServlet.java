package com.arman_jaurigue.servlets;

import com.arman_jaurigue.data_objects.Plan;
import com.arman_jaurigue.data_objects.Stop;
import com.arman_jaurigue.data_objects.User;
import com.arman_jaurigue.logic_layer.MasterManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

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
                    request.setAttribute("user", user);
                    request.setAttribute("plan", plan);
                    request.setAttribute("model", stops);
                    request.setAttribute("owner", owner);
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

    }
}
