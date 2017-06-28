package api;

import org.json.simple.JSONObject;

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

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        if(session.getAttribute("id") == null){
            out.print("{\"error\": \"nie zalogowany\"}");
            return;
        }

        JSONObject obj = new JSONObject();
        if (session.getAttribute("login") != null) {
            session.removeAttribute("id");
            session.removeAttribute("login");
            session.removeAttribute("avatar");
            obj.put("success", "Wylogowano");
        }

        out.print(obj);
    }
}
