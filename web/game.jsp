<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String title = "Gra";
    String p = "game";

    if (session.getAttribute("login") == null) {
        response.sendRedirect("login_form.php?back=game");
    }
%>

<%@include file="parts/header.jsp" %>

<section class="board" id="container">

    <%@include file="form.php" %>

</section>

<%@include file="parts/footer.jsp" %>
