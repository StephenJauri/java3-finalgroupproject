package com.arman_jaurigue.servlets;

import com.arman_jaurigue.data_objects.Plan;
import com.arman_jaurigue.data_objects.Stop;
import com.arman_jaurigue.data_objects.User;
import com.arman_jaurigue.logic_layer.MasterManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StopServlet", value = "/stop")
public class StopServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        if (user != null)
        {
            Integer stopId = Integer.parseInt(request.getParameter("stopId"));
            if(stopId != null) {
                try {
                    Stop stop = MasterManager.getMasterManager().getStopManager().getStopByStopId(stopId);
                    int planOwner = MasterManager.getMasterManager().getPlanManager().getPlanByPlanId(stop.getPlanId()).getUserId();
                    List<User> attendees = MasterManager.getMasterManager().getUserManager().getUsersByPlanId(stopId);
                    request.setAttribute("user", user);
                    request.setAttribute("planOwner", planOwner);
                    request.setAttribute("stop", stop);
                    request.getRequestDispatcher("WEB-INF/stop/stop-partial.jsp").forward(request, response);
                } catch (Exception ex) {
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
}
