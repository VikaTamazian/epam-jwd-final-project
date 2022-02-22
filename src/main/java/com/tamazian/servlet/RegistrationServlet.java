package com.tamazian.servlet;

import com.tamazian.dto.CreateUserDto;
import com.tamazian.entity.Title;
import com.tamazian.exception.ValidationException;
import com.tamazian.service.UserService;
import com.tamazian.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", Title.values());
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateUserDto userDto = CreateUserDto.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .image(req.getPart("image"))
                .title(req.getParameter("title"))
                .birthday(req.getParameter("birthday"))
                .build();

        try {
            userService.create(userDto);
            resp.sendRedirect("/login");
        }catch (ValidationException e){
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
