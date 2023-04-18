package com.arman_jaurigue.servlets;

import com.armanJaurigue.dataObjects.User;
import com.arman_jaurigue.models.Model;
import com.arman_jaurigue.models.RegisterModel;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RegisterModel model = new RegisterModel();
        request.setAttribute("model", model);
        request.getRequestDispatcher("WEB-INF/account/register.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RegisterModel model = new RegisterModel();
        if (!Model.BuildModel(model, request))
        {
            request.getRequestDispatcher("WEB-INF/account/register.jsp").forward(request, response);
        }
        else
        {
            User newUser = new User();
            Model.BuildModel(newUser, request);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
