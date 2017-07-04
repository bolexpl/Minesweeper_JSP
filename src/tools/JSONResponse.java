package tools;

import java.util.ArrayList;

public class JSONResponse {
    private String error;
    private String empty;
    private boolean success;
    private ArrayList<Record> data;
    private User user;

    public JSONResponse() {
        this.error = null;
        this.empty = null;
        this.success = false;
        this.data = null;
        this.user=null;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public void setData(ArrayList<Record> data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
