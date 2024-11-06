package com.ram;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // PostgreSQL Connection
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_access_management", "postgres", "root")) {
            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'Employee')";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("login.jsp");
    }
}
