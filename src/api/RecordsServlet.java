package api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tools.JSONResponse;
import tools.Record;
import tools.Tools;

@WebServlet(name = "RecordsServlet")
public class RecordsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        JSONResponse obj = new JSONResponse();
        ArrayList<Record> data = new ArrayList<>();

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

            if(request.getParameter("page") != null && request.getParameter("limit") != null){
                sql+=" LIMIT ?, ?";
            }

            ps = connection.prepareStatement(sql);

            if(request.getParameter("page") != null && request.getParameter("limit") != null){
                int offset = Integer.parseInt(request.getParameter("page"))*Integer.parseInt(request.getParameter("limit"));
                ps.setInt(1, offset);
                ps.setInt(2, Integer.parseInt(request.getParameter("limit")));
            }

            resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                obj.setEmpty("Brak wyników");
            } else {
                do {
                    Record r = new Record(
                            resultSet.getInt("id"),
                            Integer.parseInt(resultSet.getString("user_id")),
                            resultSet.getInt("czas"),
                            resultSet.getString("board"),
                            resultSet.getString("login")
                    );
                    data.add(r);

                } while (resultSet.next());

                obj.setData(data);
            }
            ps.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException |
                IllegalAccessException | InstantiationException e) {
            obj.setError("Błąd bazy danych");
        }

        Gson gson = new GsonBuilder().serializeNulls().create();
        out.print(gson.toJson(obj));
    }
}
