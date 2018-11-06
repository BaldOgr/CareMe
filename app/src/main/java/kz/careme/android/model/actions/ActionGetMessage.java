package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionGetMessage extends BaseAction{
    public static final String ACTION = "get_message";

    @SerializedName("session_id")
    private String sessionId;
    @SerializedName("kid_session_id")
    private String kidSessionId;

    public ActionGetMessage() {
        setAction(ACTION);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getKidSessionId() {
        return kidSessionId;
    }

    public void setKidSessionId(String kidSessionId) {
        this.kidSessionId = kidSessionId;
    }
}
