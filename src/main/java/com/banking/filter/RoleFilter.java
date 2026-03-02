package com.banking.filter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RoleFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.getWriter().println("Access Denied");
            return;
        }

        chain.doFilter(request, response);
    }
}