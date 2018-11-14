package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionAuth extends BaseAction{
    public static final String ACTION = "auth";

    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("sid")
    private String sid;

    public ActionAuth() {
        action = ACTION;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
