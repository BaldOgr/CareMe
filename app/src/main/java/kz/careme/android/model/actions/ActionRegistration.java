package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionRegistration extends BaseAction {
    @SerializedName("user_id")
    private String userId;

    public ActionRegistration() {
        action = "reg";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
