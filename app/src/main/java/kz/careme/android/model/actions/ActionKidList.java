package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionKidList extends BaseAction {
    public static final String ACTION = "kids_list";

    @SerializedName("session_id")
    private String sessionId;

    public ActionKidList() {
        setAction(ACTION);
    }

    public ActionKidList(String sessionId) {
        this();
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
