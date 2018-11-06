package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionSendMessage extends BaseAction{
    public static final String ACTION = "send_message";

    @SerializedName("session_id")
    private String sessionId;
    @SerializedName("kid_session_id")
    private String kidSessionId;
    @SerializedName("message")
    private String message;

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

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
