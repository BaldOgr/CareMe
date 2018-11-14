package kz.careme.android.model.event;

import com.google.gson.annotations.SerializedName;

public class KidEditedEvent {
    @SerializedName("msg")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
