package com.banking.controller;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.getWriter().println(gson.toJson(Map.of("error", "No active session")));
            return;
        }

        String email = (String) session.getAttribute("email");
        String role = (String) session.getAttribute("role");
        response.getWriter().println(gson.toJson(Map.of("email", email, "role", role)));
    }
}