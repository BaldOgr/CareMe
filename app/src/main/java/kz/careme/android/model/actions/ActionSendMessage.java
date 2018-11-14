package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionSendMessage extends BaseAction {
    public static final String ACTION = "send_message";

    @SerializedName("sender_id")
    private int senderId;
    @SerializedName("receiver_id")
    private int receiverId;
    @SerializedName("message")
    private String message;

    public ActionSendMessage() {
        setAction(ACTION);
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
