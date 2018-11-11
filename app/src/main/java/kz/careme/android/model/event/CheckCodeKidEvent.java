package kz.careme.android.model.event;

import com.google.gson.annotations.SerializedName;

public class CheckCodeKidEvent {
    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("child_id")
    private int childId;
    @SerializedName("parent_Id")
    private int parentId;
    @SerializedName("msg")
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
