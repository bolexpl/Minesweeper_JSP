package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        if (session.getAttribute("error") != null) {
            session.removeAttribute("error");
        }
        if (session.getAttribute("success") != null) {
            session.removeAttribute("success");
        }

        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        Connection connection;
        Statement statement;
        String query;
        try {
            query = "select * from users where login=" + login + ";";

            Class.forName(Tools.DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(Tools.DB_URL, Tools.DB_USER, Tools.DB_PASS);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                out.print(resultSet.getInt("id") + " ");
                out.print(resultSet.getString("login") + " ");
                out.print(resultSet.getString("pass") + " ");
                out.println(resultSet.getString("email"));
            }

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException |
                IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }


    }
}
