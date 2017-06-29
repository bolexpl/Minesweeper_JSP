package api;

import org.json.simple.JSONObject;
import tools.Tools;

import javax.servlet.ServletException;
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

@WebServlet(name = "DeleteRecordServlet")
public class DeleteRecordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        if(session.getAttribute("id") == null){
            out.print("{\"error\": \"nie zalogowany\"}");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));

        JSONObject obj = new JSONObject();
        obj.put("error", null);

        Connection connection;
        PreparedStatement ps;
        String sql;
        try{
            sql = "DELETE FROM records WHERE id=?";
            Class.forName(Tools.DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(Tools.DB_URL, Tools.DB_USER, Tools.DB_PASS);

            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

            obj.put("success", true);

            ps.close();
            connection.close();

        }catch (ClassNotFoundException | IllegalAccessException
                | InstantiationException | SQLException e){
            obj.put("error", "Błąd bazy danych");
        }

        out.print(obj);
    }
}
