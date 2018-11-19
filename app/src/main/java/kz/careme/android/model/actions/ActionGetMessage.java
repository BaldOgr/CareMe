package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionGetMessage extends BaseAction{
    public static final String ACTION = "get_message";

    @SerializedName("kid_id")
    private int kidId;
    @SerializedName("parent_id")
    private int parentId;

    public ActionGetMessage() {
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
}
