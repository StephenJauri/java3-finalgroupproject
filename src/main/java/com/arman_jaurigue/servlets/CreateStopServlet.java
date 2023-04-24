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

        User user = (User)request.getSession().getAttribute("user");
        if (user != null) {
            CreateStopModel model = new CreateStopModel();
            Model.buildAndSetModel(model, request);

            if (model.getPlanIdError().length() > 0) {
                response.sendRedirect("plans");
                return;
            }
            try {
                Plan plan = MasterManager.getMasterManager().getPlanManager().getPlanByPlanId(model.getPlanId());
                List<User> authorizedUsers = MasterManager.getMasterManager().getUserManager().
            } catch (Exception ex){
                response.sendError(401);
            }
            request.getRequestDispatcher("WEB-INF/stop/create.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        if (user != null)
        {
            CreatePlanModel model = new CreatePlanModel();
            if (Model.buildAndSetModel(model, request)) {
                Plan plan = new Plan();
                Model.buildModel(plan, request);
                try {
                    MasterManager.getMasterManager().getPlanManager().addPlan(user, plan);
                    response.sendRedirect("plans");
                } catch (Exception e)
                {
                    response.sendError(500);
                }
            } else {
                request.getRequestDispatcher("WEB-INF/plan/create.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("login");
        }
    }
}
