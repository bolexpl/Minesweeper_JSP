package servlets;

import tools.Tools;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "DeleteRecord")
public class DeleteRecord extends HttpServlet {
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

        int id = Integer.parseInt(request.getParameter("id"));

        Connection connection;
        PreparedStatement ps;
        String sql;
        try{
            sql = "DELETE FROM records WHERE id=?";
            Class.forName(Tools.DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(Tools.DB_URL, Tools.DB_USER, Tools.DB_PASS);

            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

            ps.close();
            connection.close();

        }catch (ClassNotFoundException | IllegalAccessException
                | InstantiationException | SQLException e){
            session.setAttribute("error","Błąd bazy danych");
        }

        if(request.getParameter("board") != null){
            response.sendRedirect("../"+request.getParameter("page")+".jsp?board="+request.getParameter("board"));
        }else{
            response.sendRedirect("../"+request.getParameter("page")+".jsp");
        }
    }
}
