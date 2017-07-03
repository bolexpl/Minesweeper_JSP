package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tools.JSONResponse;
import tools.Record;
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
import java.util.ArrayList;

@WebServlet(name = "RecordsUserServlet")
public class RecordsUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().serializeNulls().create();
        JSONResponse obj = new JSONResponse();
        ArrayList<Record> data = new ArrayList<>();



        if (session.getAttribute("id") == null) {
            obj.setError("Musisz być zalogowany");
            out.print(gson.toJson(obj));
            return;
        }

        int id = Integer.parseInt((String) session.getAttribute("id"));


        Connection connection;
        PreparedStatement ps;
        ResultSet resultSet;
        try {
            String sql = "SELECT records.id, czas, board FROM records INNER JOIN users ON records.user_id = users.id WHERE user_id=? ORDER BY records.czas";
            Class.forName(Tools.DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(Tools.DB_URL, Tools.DB_USER, Tools.DB_PASS);

            if(request.getParameter("page") != null && request.getParameter("limit") != null){
                sql+=" LIMIT ?, ?";
            }

            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            if(request.getParameter("page") != null && request.getParameter("limit") != null){
                int offset = Integer.parseInt(request.getParameter("page"))*Integer.parseInt(request.getParameter("limit"));
                ps.setInt(2, offset);
                ps.setInt(3, Integer.parseInt(request.getParameter("limit")));
            }

            resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                obj.setEmpty("Brak wyników");
            } else {
                do {
                    Record r = new Record(
                            resultSet.getInt("id"),
                            resultSet.getInt("czas"),
                            resultSet.getString("board")
                    );

                    data.add(r);
                } while (resultSet.next());

                obj.setData(data);
            }
        } catch (SQLException | ClassNotFoundException
                | InstantiationException | IllegalAccessException e) {
            obj.setEmpty("Błąd bazy danych");
        }

        out.print(gson.toJson(obj));
    }
}
