package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionRegister extends BaseAction{
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public ActionRegister() {
        action = "reg";
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
}
