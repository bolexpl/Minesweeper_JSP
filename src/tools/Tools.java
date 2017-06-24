package tools;

import java.util.Random;

public class Tools {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/Minesweeper_JSP";
    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_USER = "root";
    public static final String DB_PASS = "";

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
}
