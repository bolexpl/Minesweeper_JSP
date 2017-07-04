package tools;

public class Record {
    private int id;
    private int user_id;
    private int czas;
    private String board;
    private String login;

    public Record(int id, int user_id, int czas, String board, String login) {
        this.id = id;
        this.user_id = user_id;
        this.czas = czas;
        this.board = board;
        this.login = login;
    }

    public Record(int id, int czas, String board) {
        this.id = id;
        this.czas = czas;
        this.board = board;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCzas() {
        return czas;
    }

    public void setCzas(int czas) {
        this.czas = czas;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
