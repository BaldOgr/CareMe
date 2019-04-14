package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionListenSound extends BaseAction {
    public static final String ACTION = "listen_sound";

    @SerializedName("parent_id")
    private int parentId;

    @SerializedName("kid_session_id")
    private String kidSessionId;

    @SerializedName("kid_id")
    private long kidId;

    @SerializedName("file")
    private String file;

    public ActionListenSound() {
        setAction(ACTION);
    }

    public ActionListenSound(int parentId, String kidSessionId, String file) {
        this();
        this.parentId = parentId;
        this.kidSessionId = kidSessionId;
        this.file = file;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getKidSessionId() {
        return kidSessionId;
    }

    public void setKidSessionId(String kidSessionId) {
        this.kidSessionId = kidSessionId;
    }

    public long getKidId() {
        return kidId;
    }

    public void setKidId(long kidId) {
        this.kidId = kidId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
