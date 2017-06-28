package api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tools.Tools;

import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;

@WebServlet(name = "RecordsUserServlet")
public class RecordsUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        if(session.getAttribute("id") == null){
            out.print("{\"error\": \"nie zalogowany\"}");
            return;
        }

        int id = Integer.parseInt((String) session.getAttribute("id"));

        JSONObject obj = new JSONObject();
        JSONArray data = new JSONArray();
        obj.put("error", null);
        obj.put("empty", null);
        obj.put("data", data);

        Connection connection;
        PreparedStatement ps;
        ResultSet resultSet;
        try {
            String sql = "SELECT records.id, czas, board FROM records INNER JOIN users ON records.user_id = users.id WHERE user_id=? ORDER BY records.czas";
            Class.forName(Tools.DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(Tools.DB_URL, Tools.DB_USER, Tools.DB_PASS);

            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                obj.put("empty", "Brak wyników");
            } else {
                do {
                    JSONObject row = new JSONObject();
                    row.put("czas", resultSet.getInt("czas"));
                    row.put("board", resultSet.getInt("board"));
                    row.put("id", resultSet.getInt("id"));

                    data.add(row);
                } while (resultSet.next());
            }
        } catch (SQLException | ClassNotFoundException
                | InstantiationException | IllegalAccessException e) {
            obj.put("error", "Błąd bazy danych");
        }
        out.print(obj);
    }
}
