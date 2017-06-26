<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer>
    JS na NaN%
</footer>
</div>

<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<%
    if (p.equals("game")) {
%>
<script src="js/<%= p %>.js"></script>
<%
    }
%>


</body>
</html>