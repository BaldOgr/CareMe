package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionGetParentId extends BaseAction {
    public static final String ACTION = "get_parent_id";

    @SerializedName("kid_session_id")
    private String kidSessionId;

    public ActionGetParentId() {
        setAction(ACTION);
    }

    public String getKidSessionId() {
        return kidSessionId;
    }

    public void setKidSessionId(String kidSessionId) {
        this.kidSessionId = kidSessionId;
    }
}
