package com.ram;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/SoftwareServlet")
public class SoftwareServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String accessLevels = String.join(",", request.getParameterValues("access_levels"));

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/yourdb", "username", "password");
            String query = "INSERT INTO software (name, description, access_levels) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, accessLevels);
            stmt.executeUpdate();
            response.sendRedirect("createSoftware.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
