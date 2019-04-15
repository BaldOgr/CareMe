package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionStartListenSound extends BaseAction {
    public static final String ACTION = "start_sound";

    @SerializedName("kid_id")
    private long kidId;
    @SerializedName("session_id")
    private String sessionId;
    @SerializedName("id")
    private long id;

    public ActionStartListenSound() {
        setAction(ACTION);
    }

    public long getKidId() {
        return kidId;
    }

    public void setKidId(long kidId) {
        this.kidId = kidId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
