package com.ram;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/ApprovalServlet")
public class ApprovalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestId = request.getParameter("request_id");
        String action = request.getParameter("action");

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/yourdb", "username", "password");
            String query = "UPDATE requests SET status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, action);
            stmt.setInt(2, Integer.parseInt(requestId));
            stmt.executeUpdate();
            response.sendRedirect("pendingRequests.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
