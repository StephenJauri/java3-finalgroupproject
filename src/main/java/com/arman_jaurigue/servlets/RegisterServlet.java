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
                MasterManager.getMasterManager().getUserManager().registerUser(newUser, model.getPassword());
            } catch (RuntimeException e) {
                request.getRequestDispatcher("WEB-INF/account/register.jsp").forward(request,response);
                return;
            }
            model.setOtherError("Success");
            request.getRequestDispatcher("WEB-INF/account/register.jsp").forward(request,response);
        }
    }
}
