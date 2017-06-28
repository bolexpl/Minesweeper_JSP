package api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
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

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        JSONObject obj = new JSONObject();
        obj.put("error", null);

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
                    obj.put("success", "Zalogowano");
                    obj.put("id", resultSet.getString("id"));
                    obj.put("login", resultSet.getString("login"));
                    obj.put("avatar", resultSet.getString("avatar"));

                    session.setAttribute("id", resultSet.getString("id"));
                    session.setAttribute("login", resultSet.getString("login"));
                    session.setAttribute("avatar", resultSet.getString("avatar"));
                } else {
                    obj.put("error", "Zły login lub hasło");
                }

            } else {
                obj.put("error", "Zły login lub hasło");
            }

            ps.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException |
                IllegalAccessException | InstantiationException e) {
            obj.put("error", "Błąd bazy danych");
        }

        out.print(obj);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
