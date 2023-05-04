package com.arman_jaurigue.servlets;

import com.arman_jaurigue.data_objects.User;
import com.arman_jaurigue.logic_layer.MasterManager;
import com.arman_jaurigue.models.LoginModel;
import com.arman_jaurigue.models.Model;
import com.arman_jaurigue.models.RegisterModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect("account");
        } else {
            request.getRequestDispatcher("WEB-INF/account/login.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginModel model = new LoginModel();
        if (!Model.buildAndSetModel(model, request))
        {
            request.getRequestDispatcher("WEB-INF/account/login.jsp").forward(request, response);
        }
        else
        {
            User user = null;
            try {
                user = MasterManager.getMasterManager().getUserManager().loginUser(model.getEmail(), model.getPassword());
                request.getSession().setAttribute("user", user);
            } catch (RuntimeException e) {
                model.setOtherError(e.getMessage());
                request.getRequestDispatcher("WEB-INF/account/login.jsp").forward(request,response);
                return;
            }
            response.sendRedirect(ServletHelper.getBaseUrl(request));
        }
    }
}
