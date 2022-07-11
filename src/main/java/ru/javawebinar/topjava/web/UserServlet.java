package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");

        String user = request.getParameter("selectedUser");
        if (user != null && !user.isEmpty()) {
            SecurityUtil.setAuthUserId(user.equals("admin") ? 2 : 1);
            request.setAttribute("selectedUser", user);
            request.getRequestDispatcher("/").forward(request, response);
        }
        else {
            request.getRequestDispatcher("/users.jsp").forward(request, response);
        }

    }
}
