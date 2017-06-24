package servlets;

import tools.BCrypt;
import tools.Tools;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "Register")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,    // 10 MB
        maxFileSize = 1024 * 1024 * 50,                    // 50 MB
        maxRequestSize = 1024 * 1024 * 100)            // 100 MB
public class Register extends HttpServlet {

    private static final long serialVersionUID = 205242440643911308L;

    /**
     * Directory where uploaded files will be saved, its relative to
     * the web application directory.
     */
    private static final String UPLOAD_DIR = "avatars";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute("error") != null) {
            session.removeAttribute("error");
        }
        if (session.getAttribute("success") != null) {
            session.removeAttribute("success");
        }

        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        Connection connection;
        PreparedStatement ps;
        String sql;
        try {
            Class.forName(Tools.DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(Tools.DB_URL, Tools.DB_USER, Tools.DB_PASS);

            sql = "select * from users where login=? or email=?;";
            ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, email);

            if (!ps.executeQuery().next()) {
                String fileName = saveFile(request);

                sql = "INSERT INTO users (login, email, pass, avatar) VALUE (?, ?, ?, ?);";
                ps = connection.prepareStatement(sql);
                ps.setString(1, login);
                ps.setString(2, email);
                ps.setString(3, BCrypt.hashpw(pass, BCrypt.gensalt()));
                ps.setString(4, fileName);

                ps.execute();

                session.setAttribute("success", "Zarejestrowano. Można się zalogować");
            } else {
                session.setAttribute("error", "Email lub login zajęty.");
            }
            ps.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException |
                IllegalAccessException | InstantiationException e) {
            session.setAttribute("error", "Błąd logowania");
        }

        if(session.getAttribute("error") != null){
            response.sendRedirect("../register_form.jsp");
        }else{
            response.sendRedirect("../index.jsp");
        }
    }

    private String saveFile(HttpServletRequest request) throws IOException, ServletException {

        Part filePart = request.getPart("avatar");

        if(getFileName(filePart).equals("")){
            return "no_avatar.jpg";
        }

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + UPLOAD_DIR;

        String s[] = getFileName(filePart).split("\\.");
        boolean jpg = s[s.length - 1].equals("jpg");

        String fileName;
        File f;
        do {
            fileName = Tools.generateRandomString() + (jpg ? ".jpg" : ".png");
            f = new File(uploadFilePath + fileName);
        } while (f.exists());

        filePart.write(uploadFilePath + "/" + fileName);
        return fileName;
    }

    /**
     * Utility method to get file name from HTTP header content-disposition
     */
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
