package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Logout")
public class Logout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.sendRedirect("../index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute("error") != null) {
            session.removeAttribute("error");
        }
        if (session.getAttribute("success") != null) {
            session.removeAttribute("success");
        }

        if (session.getAttribute("login") != null) {
            session.removeAttribute("id");
            session.removeAttribute("login");
            session.removeAttribute("avatar");
            session.setAttribute("success", "Wylogowano");
        }

        response.sendRedirect("../index.jsp");
    }
}
