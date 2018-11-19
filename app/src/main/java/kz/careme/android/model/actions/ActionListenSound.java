package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionListenSound extends BaseAction {
    public static final String ACTION = "listen_sound";

    @SerializedName("session_id")
    private String sid;

    @SerializedName("kid_id")
    private int kidId;

    public ActionListenSound() {
        setAction(ACTION);
    }

    public ActionListenSound(String sid, int kidId) {
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

    public int getKidId() {
        return kidId;
    }

    public void setKidId(int kidId) {
        this.kidId = kidId;
    }
}
