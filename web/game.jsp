<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String title = "Gra";
    String p = "game";

    if (session.getAttribute("login") == null) {
        response.sendRedirect("login_form.jsp?back=game");
    }
%>

<%@include file="parts/header.jsp" %>

<section class="board" id="container">

    <%@include file="form.jsp" %>

</section>

<%@include file="parts/footer.jsp" %>
