package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionRegister extends BaseAction{
    public static final String ACTION = "reg";
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("sid")
    private String sid;
    @SerializedName("user_id")
    private int userId;

    public ActionRegister() {
        action = ACTION;
    }

    public String getAction() {
        return action;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
