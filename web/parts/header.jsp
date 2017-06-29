<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><%= title %></title>
    <link rel="stylesheet" href="css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" type="image/png" href="favicon.png">
</head>
<body>
<div class="container">

    <nav class="navbar navbar-inverse">
        <div class="container-fluid">

            <!--       Brand and toggle get grouped for better mobile display-->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.jsp">Saper</a>
            </div>


            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li <%= p.equals("game") ? "class=\"active\"" : "" %>>
                        <a href="game.jsp">Gra <%= p.equals("game") ? "<span class=\"sr-only\">(current)</span>" : "" %>
                        </a>
                    </li>
                    <li <%= p.equals("records") ? "class=\"active\"" : "" %>>
                        <a href="records.jsp">Wyniki <%= p == "records" ? "<span class=\"sr-only\">(current)</span>" : "" %>
                        </a>
                    </li>
                </ul>

                <%
                    if (session.getAttribute("login") != null) {
                %>
                <div class="btn-group navbar-right">
                    <ul class="nav navbar-nav">
                        <li <%= p.equals("records_user") ? "class=\"active\"" : "" %>>
                            <a href="records_user.jsp">Zalogowany
                                jako: <%= session.getAttribute("login") %> <%= p.equals("game") ? "<span class=\"sr-only\">(current)</span>" : "" %>
                                <img src="avatars/<%=session.getAttribute("avatar")%>" alt="" class="avatar">
                            </a>
                        </li>
                    </ul>
                    <div class="btn-group" role="group">
                        <a href="servlet/logout">
                            <button type="button" class="btn btn-default navbar-btn">
                                Wyloguj
                            </button>
                        </a>
                    </div>
                </div>
                <%
                } else {
                %>
                <div class="btn-group navbar-right">
                    <div class="btn-group" role="group">
                        <a href="login_form.jsp">
                            <button type="button" class="btn btn-default navbar-btn">
                                Zaloguj
                            </button>
                        </a>
                    </div>
                    <div class="btn-group" role="group">
                        <a href="register_form.jsp">
                            <button type="button" class="btn btn-default navbar-btn">
                                Zarejestruj
                            </button>
                        </a>
                    </div>
                </div>
                <%
                    }
                %>

            </div>
        </div>
    </nav>


    <div class="alert alert-danger alert-dismissible <%= (session.getAttribute("error") == null) ? "hidden" : "" %>" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                aria-hidden="true">&times;</span>
        </button>
        <strong>Ostrzeżenie!</strong>
        <%
            out.print((session.getAttribute("error") != null) ? session.getAttribute("error") : "");
            session.removeAttribute("error");
        %>
    </div>

    <div class="alert alert-success alert-dismissible <%= (session.getAttribute("success") == null) ? "hidden" : "" %>" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                aria-hidden="true">&times;</span>
        </button>
        <strong>Sukces!</strong>
        <%
            out.print((session.getAttribute("success") != null) ? session.getAttribute("success") : "");
            session.removeAttribute("success");
        %>
    </div>
