package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class CheckCodeKidAction extends BaseAction{
    public static final String ACTION = "check_code_kid";
    @SerializedName("kid_session_id")
    String sid;

    public CheckCodeKidAction() {
        setAction(ACTION);
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
