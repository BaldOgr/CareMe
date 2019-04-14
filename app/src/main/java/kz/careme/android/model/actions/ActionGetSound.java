package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionGetSound extends BaseAction {
    public static final String ACTION = "get_sound";

    @SerializedName("id")
    private long id;

    @SerializedName("date")
    private String date;

    @SerializedName("parent_id")
    private long parentId;

    @SerializedName("file")
    private String file;

    @SerializedName("session_id")
    private String sid;

    @SerializedName("kid_id")
    private long kidId;

    public ActionGetSound() {
    }

    public ActionGetSound(String sid, long kidId) {
        setAction(ACTION);
        this.sid = sid;
        this.kidId = kidId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public long getKidId() {
        return kidId;
    }

    public void setKidId(long kidId) {
        this.kidId = kidId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
