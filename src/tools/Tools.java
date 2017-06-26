package tools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Tools {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/Minesweeper_JSP";
    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_USER = "root";
    public static final String DB_PASS = "";

    /**
     * Directory where uploaded files will be saved, its relative to
     * the web application directory.
     */
    public static final String UPLOAD_DIR = "avatars";

    public static String generateRandomString() {
        return generateRandomString(10);
    }

    public static String generateRandomString(int length) {

        StringBuilder builder = new StringBuilder();

        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            builder.append(characters.charAt(rand.nextInt(characters.length())));
        }

        return builder.toString();
    }

    /**
     * Method to saving file
     * @return file name
     * */
    public static String saveFile(HttpServletRequest request) throws IOException, ServletException {

        Part filePart = request.getPart("avatar");

        if(Tools.getFileName(filePart).equals("")){
            return "no_avatar.jpg";
        }

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + UPLOAD_DIR;

        String s[] = Tools.getFileName(filePart).split("\\.");
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
    public static String getFileName(Part part) {
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
