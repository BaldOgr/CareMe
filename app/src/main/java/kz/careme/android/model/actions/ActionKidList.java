package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kz.careme.android.model.Kid;

public class ActionKidList extends BaseAction {
    public static final String ACTION = "kids_list";

    @SerializedName("session_id")
    private String sessionId;

    private List<Kid> kids;

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


    public void setKids(List<Kid> kids) {
        this.kids = kids;
    }

    public List<Kid> getKids() {
        return kids;
    }
}
