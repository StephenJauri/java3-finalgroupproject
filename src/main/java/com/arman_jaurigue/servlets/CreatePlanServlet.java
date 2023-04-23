package com.arman_jaurigue.servlets;

import com.arman_jaurigue.data_objects.Plan;
import com.arman_jaurigue.data_objects.User;
import com.arman_jaurigue.logic_layer.MasterManager;
import com.arman_jaurigue.models.CreatePlanModel;
import com.arman_jaurigue.models.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CreatePlanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        if (user != null) {
            request.getRequestDispatcher("WEB-INF/plan/create.jsp").forward(request, response);
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
