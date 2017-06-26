<%@ page import="tools.Tools" %>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String title = "Moje rekordy";
    String p = "records_user";

    if (session.getAttribute("login") == null) {
        response.sendRedirect("index.jsp");
    }
%>
<%@include file="parts/header.jsp" %>

<section class="records">

    <form method="post" action="servlet/set_avatar" enctype="multipart/form-data">

        <div class="form-group" style="width: 50%;">
            <label for="avatar">Zmiana awatara</label>
            <input type="file" name="avatar" class="form-control" title="Aby usunąć awatar nie podawaj obrazka"
                   id="avatar" placeholder="Awatar">
        </div>

        <button class="btn btn-default" type="submit" style="display: inline-block;">Zmień</button>
    </form>

    <hr/>

    <%
        int id = Integer.parseInt((String) session.getAttribute("id"));
        Connection connection;
        PreparedStatement ps;
        String sql;
        ResultSet resultSet;
        try {
            sql = "SELECT records.id, user_id, czas, board, login FROM records INNER JOIN users ON records.user_id = users.id WHERE user_id=? ORDER BY records.czas";
            Class.forName(Tools.DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(Tools.DB_URL, Tools.DB_USER, Tools.DB_PASS);

            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                out.print("Brak wyników.");
            } else {
    %>

    <h3>
        Moje wyniki
    </h3>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <td>#</td>
            <td>Czas</td>
            <td>Plansza</td>
            <td></td>
        </tr>
        </thead>
        <tbody>

        <%
            int i=1;
            do{
        %>
        <tr>
            <td><%= i++ %>.</td>
            <td><%= resultSet.getInt("czas")%>s</td>
            <td><%= resultSet.getString("board")%></td>
            <td>
                <a href="servlet/delete_record?id=<%= resultSet.getInt("id") %>&page=records_user">
                    <button type="button" class="btn btn-danger">Usuń</button>
                </a>
            </td>
        </tr>
        <%
            }while(resultSet.next());
        %>

        </tbody>
    </table>

    <%
            }
        } catch (SQLException | ClassNotFoundException
                | InstantiationException | IllegalAccessException e) {

        }
    %>
</section>


<%@include file="parts/footer.jsp" %>
