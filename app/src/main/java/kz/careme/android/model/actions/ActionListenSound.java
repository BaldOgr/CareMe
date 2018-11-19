package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionListenSound extends BaseAction {
    public static final String ACTION = "listen_sound";

    @SerializedName("session_id")
    private String sid;

    @SerializedName("kid_id")
    private String kidId;

    public ActionListenSound() {
        setAction(ACTION);
    }

    public ActionListenSound(String sid, String kidId) {
        this();
        this.sid = sid;
        this.kidId = kidId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getKidId() {
        return kidId;
    }

    public void setKidId(String kidId) {
        this.kidId = kidId;
    }
}
