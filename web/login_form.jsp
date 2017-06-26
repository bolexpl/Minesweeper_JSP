<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String title = "Logowanie";
    String p = "index";
%>

<%@include file="parts/header.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <form method="post"
              action="servlet/login<%= request.getParameter("back") != null ? "?back=" + request.getParameter("back") : "" %>">

            <div class="form-group">
                <label for="login">Login lub email</label>
                <input type="text" name="login" class="form-control" title="Login lub email" id="login"
                       placeholder="Login lub email" required autofocus>
            </div>

            <div class="form-group">
                <label for="pass">Hasło</label>
                <input type="password" name="pass" class="form-control" title="Hasło" id="pass" placeholder="Hasło"
                       required>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-default">Zaloguj</button>
            </div>

        </form>
    </div>
</div>

<%@include file="parts/footer.jsp" %>