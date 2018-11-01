package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionGenerateCode extends BaseAction {
    public static final String ACTION = "generate_code";

    @SerializedName("session_id")
    private String sid;

    @SerializedName("kid_id")
    private int kidId;

    @SerializedName("code")
    private int code;

    public ActionGenerateCode() {
        setAction(ACTION);
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getKidId() {
        return kidId;
    }

    public void setKidId(int kidId) {
        this.kidId = kidId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
