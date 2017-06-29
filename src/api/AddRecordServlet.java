package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tools.JSONResponse;
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

@WebServlet(name = "AddRecordServlet")
public class AddRecordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        JSONResponse obj = new JSONResponse();
        Gson gson = new GsonBuilder().serializeNulls().create();

        if(session.getAttribute("id") == null){
            obj.setError("Musisz się zalogować");
            out.print(gson.toJson(obj));
            return;
        }

        int userId = Integer.parseInt((String) session.getAttribute("id"));
        int time = Integer.parseInt(request.getParameter("time"));
        String board = request.getParameter("board");

        Connection connection;
        PreparedStatement ps;
        String sql;
        try {
            sql = "INSERT INTO records (user_id, czas, board) VALUE (?, ?, ?)";

            Class.forName(Tools.DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(Tools.DB_URL, Tools.DB_USER, Tools.DB_PASS);

            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, time);
            ps.setString(3, board);
            ps.execute();

            obj.setSuccess(true);

            ps.close();
            connection.close();

        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | SQLException e) {
            obj.setError(e.getMessage());
        }
        out.print(gson.toJson(obj));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
