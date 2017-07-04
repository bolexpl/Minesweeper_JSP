package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tools.BCrypt;
import tools.JSONResponse;
import tools.Tools;
import tools.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        Gson gson = new GsonBuilder().serializeNulls().create();
        JSONResponse obj = new JSONResponse();

        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        Connection connection;
        PreparedStatement ps;
        String sql;
        try {
            sql = "SELECT * FROM users WHERE login=? OR email=?;";

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

                    User user = new User(
                            Integer.parseInt((String) resultSet.getString("id")),
                            resultSet.getString("login"),
                            resultSet.getString("avatar"));

                    obj.setUser(user);
                    obj.setSuccess(true);

                    session.setAttribute("id", resultSet.getString("id"));
                    session.setAttribute("login", resultSet.getString("login"));
                    session.setAttribute("avatar", resultSet.getString("avatar"));
                } else {
                    obj.setError("Zły login lub hasło");
                }

            } else {
                obj.setError("Zły login lub hasło");
            }

            ps.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException |
                IllegalAccessException | InstantiationException e) {
            obj.setError("Błąd bazy danych");
        }

        out.print(gson.toJson(obj));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
