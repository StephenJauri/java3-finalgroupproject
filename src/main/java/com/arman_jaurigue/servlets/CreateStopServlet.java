package com.arman_jaurigue.servlets;

import com.arman_jaurigue.data_objects.Plan;
import com.arman_jaurigue.data_objects.User;
import com.arman_jaurigue.logic_layer.MasterManager;
import com.arman_jaurigue.models.CreatePlanModel;
import com.arman_jaurigue.models.CreateStopModel;
import com.arman_jaurigue.models.Model;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class CreateStopServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User fake = new User();
        fake.setId(2);
        request.getSession().setAttribute("user", fake);
        User user = (User)request.getSession().getAttribute("user");
        if (user != null) {
            CreateStopModel model = new CreateStopModel();
            Model.buildAndSetModel(model, request);
            System.out.println("This far");

            if (model.getPlanIdError().length() > 0) {
                System.out.println("Oops");
                response.sendRedirect("plans");
                return;
            }
            try {
                Plan plan = MasterManager.getMasterManager().getPlanManager().getPlanByPlanId(model.getPlanId());
                System.out.println("Loaded plan");
                List<User> authorizedUsers = MasterManager.getMasterManager().getUserManager().getUsersByPlanId(plan.getPlanId());
                System.out.println("Loaded lsit");
                if (!(user.getId() == plan.getUserId() || authorizedUsers.stream().anyMatch(u -> u.getId() == user.getId() && u.getInviteStatus()))) {
                    response.sendError(401);
                    return;
                }
            } catch (Exception ex){
                response.sendError(401);
                return;
            }
            request.getRequestDispatcher("WEB-INF/stop/create.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}