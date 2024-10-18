package com.new2.servletapp.actions;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");


        Connection conn;
        Statement stmt;
        PrintWriter out = resp.getWriter();

        try {
            // Database connection details
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/login_schema",
                    "root",
                    ""
            );
            Statement statement = connection.createStatement();

            // Vulnerable query (no prepared statement, direct user input)
            String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
//                resp.getWriter().println("Login successful. Welcome, " + username);
                // Get or create a session object
                HttpSession session = req.getSession();

                // Store the username in the session
                session.setAttribute("username", username);

                // Redirect to a welcome page
                resp.sendRedirect("index.jsp");
            } else {
                resp.getWriter().println("Invalid username or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Database error: " + e.getMessage());
        }
    }

}
