package api;

import org.json.simple.JSONObject;
import tools.BCrypt;
import tools.Tools;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "RegisterServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,    // 10 MB
        maxFileSize = 1024 * 1024 * 50,                    // 50 MB
        maxRequestSize = 1024 * 1024 * 100)            // 100 MB
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 205242440643911308L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        JSONObject obj = new JSONObject();
        obj.put("error", null);

        Connection connection;
        PreparedStatement ps;
        String sql;
        try {
            Class.forName(Tools.DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(Tools.DB_URL, Tools.DB_USER, Tools.DB_PASS);

            sql = "SELECT * FROM users WHERE login=? OR email=?;";
            ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, email);

            if (!ps.executeQuery().next()) {
                String fileName = Tools.saveFile(request);

                sql = "INSERT INTO users (login, email, pass, avatar) VALUE (?, ?, ?, ?);";
                ps = connection.prepareStatement(sql);
                ps.setString(1, login);
                ps.setString(2, email);
                ps.setString(3, BCrypt.hashpw(pass, BCrypt.gensalt()));
                ps.setString(4, fileName);

                ps.execute();

                obj.put("success", "Zarejestrowano. Można się zalogować");
            } else {
                obj.put("error", "Email lub login zajęty");
            }
            ps.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException |
                IllegalAccessException | InstantiationException e) {
            session.setAttribute("error", "Błąd logowania");
            obj.put("error", "Błąd logowania");
        }

        out.print(obj);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
