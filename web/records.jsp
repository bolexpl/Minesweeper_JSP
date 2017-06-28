<%@ page import="tools.Tools" %>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String title = "Wyniki";
    String p = "records";
%>
<%@include file="parts/header.jsp" %>

<section class="records">

    <form method="get">
        <div class="form-group" style="width: 50%;">
            <label for="plansza">Rozmiar planszy:</label>
            <select class="form-control" id="plansza" name="board" style="width: 50%;display: inline-block;">
                <option value="all" <%= (request.getParameter("board") == null || request.getParameter("board").equals("all")) ? "selected" : ""  %>>
                    Wszystkie
                </option>
                <option value="8x8" <%= (session.getAttribute("board") != null && session.getAttribute("board").equals("8x8")) ? "selected" : "" %>>
                    8x8
                </option>
                <option value="16x16" <%= (session.getAttribute("board") != null && session.getAttribute("board").equals("16x16")) ? "selected" : "" %>>
                    16x16
                </option>
                <option value="30x16" <%= (session.getAttribute("board") != null && session.getAttribute("board").equals("30x16")) ? "selected" : "" %>>
                    30x16 lub16x30
                </option>
                <option value="custom" <%= (session.getAttribute("board") != null && session.getAttribute("board").equals("custom")) ? "selected" : "" %>>
                    Własne ustawienia
                </option>
            </select>
            <button class="btn btn-default" type="submit" style="display: inline-block;">Filtruj</button>
        </div>
    </form>

    <%
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
                out.print("Brak wyników.");
            } else {
    %>

    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <td>#</td>
            <td>Czas</td>
            <td>Plansza</td>
            <td>Gracz</td>
        </tr>
        </thead>
        <tbody>
        <%
            int i = 1;
            do {
        %>
        <tr>
            <td><%= i++ %>.</td>
            <td><%= resultSet.getInt("czas") %>s</td>
            <td><%= resultSet.getString("board") %>
            </td>
            <td>
                <img src="avatars/<%=resultSet.getString("avatar")%>" alt="" class="avatar">
                <%=resultSet.getString("login")%>
            </td>
        </tr>
        <%
            } while (resultSet.next());
        %>
        </tbody>
    </table>
    <%
            }
            ps.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException |
                IllegalAccessException | InstantiationException e) {
            session.setAttribute("error", e.getMessage());
        }
    %>
</section>

<%@include file="parts/footer.jsp" %>
