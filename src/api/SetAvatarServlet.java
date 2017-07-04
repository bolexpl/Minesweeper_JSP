package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tools.JSONResponse;
import tools.Tools;
import tools.User;

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

@WebServlet(name = "SetAvatarServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,    // 10 MB
        maxFileSize = 1024 * 1024 * 50,                    // 50 MB
        maxRequestSize = 1024 * 1024 * 100)            // 100 MB
public class SetAvatarServlet extends HttpServlet {

    private static final long serialVersionUID = 205242440643911308L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        Gson gson = new GsonBuilder().serializeNulls().create();
        JSONResponse obj = new JSONResponse();

        if (session.getAttribute("id") == null) {
            obj.setError("Musisz się zalogować");
            out.print(gson.toJson(obj));
            return;
        }

        Connection connection;
        PreparedStatement ps;
        String sql;
        try {
            Class.forName(Tools.DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(Tools.DB_URL, Tools.DB_USER, Tools.DB_PASS);

            String fileName = Tools.saveFile(request);

            sql = "UPDATE users SET `avatar`=? WHERE id=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, fileName);
            ps.setInt(2, Integer.parseInt((String) session.getAttribute("id")));

            ps.execute();

            obj.setSuccess(true);
            session.setAttribute("avatar", fileName);
            obj.setUser(new User(fileName));

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
