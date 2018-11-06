package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionSoundAroundPhone extends BaseAction{
    public static final String ACTION = "listen_sound";

    @SerializedName("session_id")
    private String sessionId;

    @SerializedName("kid_id")
    private Long kidId;

    public ActionSoundAroundPhone() {
        setAction(ACTION);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getKidId() {
        return kidId;
    }

    public void setKidId(Long kidId) {
        this.kidId = kidId;
    }
}
