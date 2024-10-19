package com.new2.servletapp.actions;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/loginSecure")
public class LoginSecureServlet extends HttpServlet {

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

            // Vulnerable query (no prepared statement, direct user input)
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("role");
//                resp.getWriter().println("Login successful. Welcome, " + username);
                // Get or create a session object
                HttpSession session = req.getSession();

                // Store the username in the session
                session.setAttribute("username", username);
                session.setAttribute("role", resultSet.getString("role"));

                if (role.equals("administrator")) {
                    resp.sendRedirect("admin.jsp");
                } else {
                    resp.sendRedirect("index.jsp");
                }
                // Redirect to a welcome page
            } else {
                resp.getWriter().println("Invalid username or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Database error: " + e.getMessage());
        }
    }

}
