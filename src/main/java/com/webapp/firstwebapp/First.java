package com.webapp.firstwebapp;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import static com.webapp.firstwebapp.MyConnection.getConnection;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class First extends HttpServlet {

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private String message;
    public First(){
        super();
    }

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>See all participants</h1>");
        displayData(out);
        out.println("</body></html>");
    }


    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firsName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String email = request.getParameter("email");
        String password = request.getParameter("new-password");
        String infos = firsName+" "+lastName+" "+email+" "+password;
        try {
            connection = getConnection();
            String sql = "insert into register (firstName, lastName, email, password) values (?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,firsName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,password);

            int rs = preparedStatement.executeUpdate();
            if (rs != 0)
                System.out.println("Success");
            else {
                System.out.println("Fail");
            }
            this.doGet(request, response);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void displayData(PrintWriter out) {
        PreparedStatement statement ;


        String style = "<style>" +
                "body {\n" +
                "    width: 100%;\n" +
                "    height: 100vh;\n" +
                "    margin: 0;\n" +
                "    background-color: #1b1b32;\n" +
                "    color: #f5f6f7;\n" +
                "    font-family: Tahoma;\n" +
                "    font-size: 16px;\n" +
                "}" +
                "h1 {\n" +
                "    margin: 1em auto;\n" +
                "    text-align: center;\n" +
                "}"+
                "table {\n" +
                "  border-collapse: collapse;\n" +
                "  border: 1px solid black;\n" +
                "  text-align: center;\n" +
                "\tvertical-align: middle;\n" +
                "margin-left: auto;\n" +
                "  margin-right: auto;"+
                "}\n" +
                "\n" +
                "th, td {\n" +
                "  border: 1px solid black;\n" +
                "  padding: 8px;\n" +
                "}" +
                "thead th {\n" +
                "  width: 25%;\n" +
                "}" +
                "thead {\n" +
                "\tbackground-color: #333;\n" +
                "\tcolor: white;\n" +
                "\tfont-size: 0.875rem;\n" +
                "\ttext-transform: uppercase;\n" +
                "\tletter-spacing: 2%;\n" +
                "}" +
                "tbody tr:nth-child(odd) {\n" +
                "  background-color: #1b1b32;\n" +
                "}\n" +
                "tbody tr:nth-child(even) {\n" +
                "  background-color: #eee;\n" +
                "}" +
                "</style>";

        out.println(style);
        out.println("<table>");
        out.println("<thead>\n" +
                "    <tr>\n" +
                "      <th>First Name</th>\n" +
                "      <th>Last Name</th>\n" +
                "      <th>Email</th>\n" +
                "    <tr>\n" +
                "  </thead>");

        try {
            connection = getConnection();
            String sql = "select firstName, lastName, email from register";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");

                out.println("<tbody>");
                out.println("<tr>\n" +
                        "      <td>"+firstName+"</td>\n" +
                        "      <td>"+lastName+"</td>\n" +
                        "      <td>"+email+"</td>\n" +
                        "    </tr>"
                );
                out.println("</tbody>");

            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public void destroy() {
    }
}
