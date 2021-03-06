package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionEditKid extends BaseAction{
    public static final String ACTION = "edit_kid";

    @SerializedName("kid")
    private int kidId;

    @SerializedName("name")
    private String name;

    @SerializedName("lastname")
    private String lastname;

    @SerializedName("session_id")
    private String sid;

    public ActionEditKid() {
        setAction(ACTION);
    }

    public ActionEditKid(int kidId, String name, String lastname) {
        setAction(ACTION);
        this.kidId = kidId;
        this.name = name;
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getKidId() {
        return kidId;
    }

    public void setKidId(int kidId) {
        this.kidId = kidId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
