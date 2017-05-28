package servlets;

import java.util.Random;

public class Tools {

    public static String DB_HOST = "localhost";
    public static String DB_NAME = "Minesweeper_Web";
    public static String DB_USER = "root";
    public static String DB_PASS = "";

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
