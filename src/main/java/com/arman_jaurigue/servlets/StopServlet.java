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

@WebServlet(name = "StopServlet", value = "/stop")
public class StopServlet extends HttpServlet {
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
                    request.setAttribute("plan", plan);
                    request.setAttribute("model", stops);
                    request.setAttribute("owner", owner);
                    request.setAttribute("attendees", attendees);
                    request.getRequestDispatcher("WEB-INF/stop/stops.jsp").forward(request, response);
                } catch (Exception ex) {
                    // Error Page Here
                    return;
                }
            } else {
                // Error Page Here
                return;
            }

        } else {
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
