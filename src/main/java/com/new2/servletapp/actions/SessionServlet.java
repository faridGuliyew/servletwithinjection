package com.new2.servletapp.actions;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/SessionServlet")
public class SessionServlet extends HttpServlet {

    // Inside the servlet (e.g., LoginServlet.java)

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        // Get or create a session object
        HttpSession session = request.getSession();

        // Store the username in the session
        session.setAttribute("username", username);

        // Redirect to a welcome page
        response.sendRedirect("index.jsp");
    }

}
