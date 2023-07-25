package com.example;

// EmployeeServlet.java

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;

public class UsersServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://192.168.0.126:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "postgres";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");

            out.println("<html><body><h2>Users List</h2><ul>");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                double surname = resultSet.getDouble("surname");
                out.println("<li>Employee ID: " + id + ", Name: " + name + ", Age: " + age + ", Surname: " + surname + "</li>");
            }

            out.println("</ul></body></html>");

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}
