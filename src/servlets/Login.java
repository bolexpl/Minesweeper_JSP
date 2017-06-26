package servlets;

import tools.BCrypt;
import tools.Tools;

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
        PreparedStatement ps;
        String sql;
        try {
            sql = "select * from users where login=? or email=?;";

            Class.forName(Tools.DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(Tools.DB_URL, Tools.DB_USER, Tools.DB_PASS);

            ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, login);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {

                String hash = resultSet.getString("pass");
                hash = hash.substring(0, 2) + "a" + hash.substring(3);
                if (BCrypt.checkpw(pass, hash)) {

                    session.setAttribute("success","Zalogowano");
                    session.setAttribute("id",resultSet.getString("id"));
                    session.setAttribute("login",resultSet.getString("login"));
                    session.setAttribute("avatar",resultSet.getString("avatar"));
                }else{
                    session.setAttribute("error","Zły login lub hasło");
                }

            } else {
                session.setAttribute("error","Zły login lub hasło");
            }

            ps.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException |
                IllegalAccessException | InstantiationException e) {
            session.setAttribute("error","Błąd bazy danych");
        }

        if(session.getAttribute("error") != null){
            response.sendRedirect("../login_form.jsp");
        }else{
            if(request.getParameter("back") != null && request.getParameter("back").equals("game")){
                response.sendRedirect("../game.jsp");
            }else{
                response.sendRedirect("../index.jsp");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.sendRedirect("../index.jsp");
    }
}
