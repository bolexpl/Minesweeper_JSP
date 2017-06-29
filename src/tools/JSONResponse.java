package tools;

import java.util.ArrayList;

public class JSONResponse {
    private String error;
    private String empty;
    private ArrayList<Record> data;

    public JSONResponse() {
        this.error = null;
        this.empty = null;
        this.data = null;
    }

    public String getError() {
        return error;
    }

    public String getEmpty() {
        return empty;
    }

    public ArrayList<Record> getData() {
        return data;
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
}
