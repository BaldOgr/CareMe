package kz.careme.android.model;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("sid")
    private String sid;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
