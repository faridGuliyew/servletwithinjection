package com.new2.servletapp.actions;

import jakarta.servlet.annotation.WebServlet;
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

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String information = req.getParameter("information");

        Connection conn;
        Statement stmt;
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        Object roleObject = session.getAttribute("role");
        String role = null;

        if (roleObject instanceof String) {
            role = (String) roleObject;
        }

        if (role != "administrator") {
            resp.getWriter().write("You are unauthorized!");
            return;
        }

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
            String sql = "INSERT INTO information(value)  VALUES ('" + information + "');";
            Boolean isFail = statement.execute(sql);

            if (!isFail) {
                resp.getWriter().println("Added successfully");
            } else {
                resp.getWriter().println("Something went wrong");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Database error: " + e.getMessage());
        }
    }
}