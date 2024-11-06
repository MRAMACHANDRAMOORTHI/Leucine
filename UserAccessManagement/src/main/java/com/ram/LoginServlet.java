package com.ram;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Check credentials in database
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_access_management", "postgres", "root")) {
            String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String role = rs.getString("role");
                    // Redirect based on role
                    switch (role) {
                        case "Employee":
                            response.sendRedirect("requestAccess.jsp");
                            break;
                        case "Manager":
                            response.sendRedirect("pendingRequests.jsp");
                            break;
                        case "Admin":
                            response.sendRedirect("createSoftware.jsp");
                            break;
                    }
                } else {
                    response.sendRedirect("login.jsp?error=Invalid credentials");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
