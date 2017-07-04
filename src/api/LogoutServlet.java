package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tools.JSONResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        Gson gson = new GsonBuilder().serializeNulls().create();
        JSONResponse obj = new JSONResponse();

        if (session.getAttribute("id") == null) {
            obj.setError("Nie jesteś zalogowany");
            out.print(gson.toJson(obj));
            return;
        }

        if (session.getAttribute("login") != null) {
            session.removeAttribute("id");
            session.removeAttribute("login");
            session.removeAttribute("avatar");
            obj.setSuccess(true);
        }

        out.print(gson.toJson(obj));
    }
}
