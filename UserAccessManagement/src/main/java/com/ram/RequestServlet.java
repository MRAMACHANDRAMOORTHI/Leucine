package com.ram;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/RequestServlet")
public class RequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String softwareId = request.getParameter("software_id");
        String accessType = request.getParameter("access_type");
        String reason = request.getParameter("reason");
        int userId = Integer.parseInt(request.getSession().getAttribute("user_id").toString());

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/yourdb", "username", "password");
            String query = "INSERT INTO requests (user_id, software_id, access_type, reason, status) VALUES (?, ?, ?, ?, 'Pending')";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, Integer.parseInt(softwareId));
            stmt.setString(3, accessType);
            stmt.setString(4, reason);
            stmt.executeUpdate();
            response.sendRedirect("requestAccess.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
