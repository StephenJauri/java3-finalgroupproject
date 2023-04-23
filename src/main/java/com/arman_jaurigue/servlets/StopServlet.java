package com.arman_jaurigue.servlets;

import com.arman_jaurigue.data_objects.Plan;
import com.arman_jaurigue.data_objects.User;
import com.arman_jaurigue.logic_layer.MasterManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StopServlet", value = "/StopServlet")
public class StopServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        if (user != null)
        {
            try {
//                List<Stop> stops = MasterManager.getMasterManager().get
//                List<Plan> plans = MasterManager.getMasterManager().getPlanManager().getAllPlansByUserId(user.getId());
//                request.setAttribute("model", plans);
//                request.getRequestDispatcher("WEB-INF/plan/plans.jsp").forward(request, response);
            } catch (RuntimeException ex) {
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
