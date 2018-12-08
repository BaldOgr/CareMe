package kz.careme.android.modules.more;

import com.google.gson.annotations.SerializedName;

import kz.careme.android.model.actions.BaseAction;

public class ActionPullABell extends BaseAction {
    public static final String ACTION = "send_signal";

    @SerializedName("session_id")
    private String sessionId;

    @SerializedName("kid_session_id")
    private String kidSessionId;

    @SerializedName("kid_id")
    private int kidId;

    public ActionPullABell() {
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

    public int getKidId() {
        return kidId;
    }

    public void setKidId(int kidId) {
        this.kidId = kidId;
    }
}
