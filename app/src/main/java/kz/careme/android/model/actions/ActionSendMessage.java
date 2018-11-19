package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionSendMessage extends BaseAction {
    public static final String ACTION = "send_message";

    @SerializedName("kid_id")
    private int kidId;
    @SerializedName("parent_id")
    private int parentId;
    @SerializedName("message")
    private String message;
    @SerializedName("session_id")
    private String sid;
    @SerializedName("type")
    private int type;

    public ActionSendMessage() {
        setAction(ACTION);
    }

    public int getKidId() {
        return kidId;
    }

    public void setKidId(int kidId) {
        this.kidId = kidId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
