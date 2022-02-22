package com.tamazian.servlet;

import com.tamazian.service.UserService;
import com.tamazian.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //  Integer id = Integer.valueOf(req.getParameter("id"));
        req.setAttribute("users", userService.findAll());

        req.getRequestDispatcher(JspHelper.getPath("users")).forward(req, resp);
    }
}
