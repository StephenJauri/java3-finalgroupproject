package com.arman_jaurigue.servlets;

import com.arman_jaurigue.data_objects.Plan;
import com.arman_jaurigue.data_objects.Stop;
import com.arman_jaurigue.data_objects.User;
import com.arman_jaurigue.logic_layer.MasterManager;
import com.arman_jaurigue.models.CreatePlanModel;
import com.arman_jaurigue.models.CreateStopModel;
import com.arman_jaurigue.models.Model;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class CreateStopServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {User user = (User)request.getSession().getAttribute("user");
        int planId;
        if (user != null) {
            try {
                planId = Integer.parseInt(request.getParameter("planId"));
                CreateStopModel model = new CreateStopModel();
                model.setPlanId(planId);
                request.setAttribute("model", model);
            } catch (NumberFormatException ex) {
                response.sendRedirect("plans");
                return;
            }

            try {
                Plan plan = MasterManager.getMasterManager().getPlanManager().getPlanByPlanId(planId);
                List<User> authorizedUsers = MasterManager.getMasterManager().getUserManager().getUsersByPlanId(plan.getPlanId());
                if (!(user.getId() == plan.getUserId() || authorizedUsers.stream().anyMatch(u -> u.getId() == user.getId() && u.getInviteStatus() == true))) {
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
        User user = (User)request.getSession().getAttribute("user");
        if (user != null)
        {
            CreateStopModel model = new CreateStopModel();
            if (Model.buildAndSetModel(model, request)) {
                Stop stop = new Stop();
                Model.buildModel(stop, request);
                try {
                    Plan plan = MasterManager.getMasterManager().getPlanManager().getPlanByPlanId(model.getPlanId());
                    List<User> authorizedUsers = MasterManager.getMasterManager().getUserManager().getUsersByPlanId(plan.getPlanId());
                    if (!(user.getId() == plan.getUserId() || authorizedUsers.stream().anyMatch(u -> u.getId() == user.getId() && u.getInviteStatus() == true))) {
                        response.sendError(401);
                        return;
                    }

                    System.out.println(plan.getEndDate().toString());
                    System.out.println(plan.getStartDate().toString());
                    System.out.println(stop.getTime().toString());
                    if (stop.getTime().before(Timestamp.valueOf(plan.getStartDate())) || stop.getTime().after(Timestamp.valueOf(plan.getEndDate()))) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);
                        model.setTimeError("Time must be between the start and end of the plan: " + plan.getStartDate().format(formatter) + " - " + plan.getEndDate().format(formatter));
                        request.getRequestDispatcher("WEB-INF/stop/create.jsp").forward(request, response);
                        return;
                    }

                    MasterManager.getMasterManager().getStopManager().addStop(user, stop);
                    response.sendRedirect("plans");
                } catch (Exception e)
                {
                    response.sendError(500);
                }
            } else {
                request.getRequestDispatcher("WEB-INF/stop/create.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("login");
        }
    }
}
