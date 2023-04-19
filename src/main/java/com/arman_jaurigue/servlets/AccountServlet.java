package com.arman_jaurigue.servlets;

import com.arman_jaurigue.data_objects.User;
import com.arman_jaurigue.models.Model;
import com.arman_jaurigue.models.RegisterModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            request.getRequestDispatcher("/").forward(request,response);
        }
    }
}
