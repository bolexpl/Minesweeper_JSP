package tools;

public class User {

    private int id;
    private String login;
    private String avatar;

    public User(int id, String login, String avatar) {
        this.id = id;
        this.login = login;
        this.avatar = avatar;
    }

    public User(String avatar) {
        this.avatar = avatar;
    }
}
