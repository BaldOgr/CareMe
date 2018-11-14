package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionGetMessage extends BaseAction{
    public static final String ACTION = "get_message";

    @SerializedName("sender_id")
    private int senderId;
    @SerializedName("receiver_id")
    private int receiverId;

    public ActionGetMessage() {
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
}
