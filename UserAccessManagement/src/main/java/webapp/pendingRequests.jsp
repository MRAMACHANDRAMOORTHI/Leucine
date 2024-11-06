<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pending Requests</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        button {
            background-color: #28a745;
            color: #fff;
            padding: 8px 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #218838;
        }
        .reject-button {
            background-color: #dc3545;
        }
        .reject-button:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
<h2>Pending Requests</h2>
<table>
    <tr>
        <th>Request ID</th>
        <th>Username</th>
        <th>Action</th>
    </tr>
    <%
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/yourdb", "username", "password");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM requests WHERE status = 'Pending'");
        while (rs.next()) {
    %>
    <tr>
        <td><%= rs.getInt("id") %></td>
        <td><%= rs.getString("username") %></td>
        <td>
            <form action="ApprovalServlet" method="post">
                <input type="hidden" name="request_id" value="<%= rs.getInt("id") %>">
                <button type="submit" name="action" value="Approve">Approve</button>
                <button type="submit" name="action" value="Reject" class="reject-button">Reject</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>
