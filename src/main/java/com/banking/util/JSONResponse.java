package com.banking.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JSONResponse {

    public static void send(HttpServletResponse response, String status, String message) throws IOException {
        response.setContentType("application/json");
        response.getWriter().println("{\"status\":\"" + status + "\",\"message\":\"" + message + "\"}");
    }
}