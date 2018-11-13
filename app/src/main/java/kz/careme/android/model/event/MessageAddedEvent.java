package kz.careme.android.model.event;

import com.google.gson.annotations.SerializedName;

public class MessageAddedEvent {
    @SerializedName("msg")
    private String message;

    @SerializedName("action")
    private String action;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
