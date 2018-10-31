package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionActivateCode extends BaseAction{
    public static final String ACTION = "activate_code";

    @SerializedName("code")
    private int code;

    @SerializedName("child_id")
    private int childId;

    @SerializedName("parent_id")
    private int parentId;

    public ActionActivateCode() {
        setAction(ACTION);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
