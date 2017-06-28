package api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tools.Tools;

@WebServlet(name = "RecordsServlet")
public class RecordsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        JSONObject obj = new JSONObject();
        JSONArray data = new JSONArray();
        obj.put("error", null);
        obj.put("empty", null);
        obj.put("data", data);

        Connection connection;
        PreparedStatement ps;
        String sql;
        ResultSet resultSet;
        try {
            sql = "SELECT records.id, user_id, czas, board, login, avatar FROM records INNER JOIN users ON records.user_id = users.id ORDER BY records.czas";
            Class.forName(Tools.DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(Tools.DB_URL, Tools.DB_USER, Tools.DB_PASS);

            if (request.getParameter("board") != null) {
                switch (request.getParameter("board")) {
                    case "8x8":
                        sql = "SELECT records.id, user_id, czas, board, login, avatar FROM records INNER JOIN users ON records.user_id = users.id WHERE board='8x8' ORDER BY records.czas";
                        break;
                    case "16x16":
                        sql = "SELECT records.id, user_id, czas, board, login, avatar FROM records INNER JOIN users ON records.user_id = users.id WHERE board='16x16' ORDER BY records.czas";
                        break;
                    case "16x30":
                    case "30x16":
                        sql = "SELECT records.id, user_id, czas, board, login, avatar FROM records INNER JOIN users ON records.user_id = users.id WHERE board='30x16' OR board='16x30' ORDER BY records.czas";
                        break;
                    case "custom":
                        sql = "SELECT records.id, user_id, czas, board, login, avatar FROM records INNER JOIN users ON records.user_id = users.id WHERE board NOT IN('8x8','16x16','30x16','16x30') ORDER BY records.czas";
                        break;
                }
            }

            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                obj.put("empty", "Brak wyników");
            } else {
                do {
                    JSONObject row = new JSONObject();

                    row.put("id", resultSet.getInt("id"));
                    row.put("czas", resultSet.getInt("czas"));
                    row.put("board", resultSet.getString("board"));
                    row.put("avatar", resultSet.getString("avatar"));
                    row.put("login", resultSet.getString("login"));
                    row.put("user_id", resultSet.getString("user_id"));

                    data.add(row);
                } while (resultSet.next());

            }
            ps.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException |
                IllegalAccessException | InstantiationException e) {
            obj.put("error", e.getMessage());
        }

        out.print(obj);
    }
}
