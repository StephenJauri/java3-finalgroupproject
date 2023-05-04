package com.arman_jaurigue.servlets;

import com.arman_jaurigue.data_objects.User;
import com.arman_jaurigue.logic_layer.MasterManager;
import com.arman_jaurigue.models.Model;
import com.arman_jaurigue.models.RegisterModel;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/account/register.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RegisterModel model = new RegisterModel();
        if (!Model.buildAndSetModel(model, request))
        {
            request.getRequestDispatcher("WEB-INF/account/register.jsp").forward(request, response);
        }
        else
        {
            User newUser = new User();
            Model.buildModel(newUser, request);
            try {
                newUser = MasterManager.getMasterManager().getUserManager().registerUser(newUser, model.getPassword());
                request.getSession().setAttribute("user", newUser);
            } catch (RuntimeException e) {
                model.setOtherError(e.getMessage());
                request.getRequestDispatcher("WEB-INF/account/register.jsp").forward(request,response);
                return;
            }
            response.sendRedirect(ServletHelper.getBaseUrl(request));
        }
    }
}
