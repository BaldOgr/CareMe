package kz.careme.android.model.event;

import com.google.gson.annotations.SerializedName;

public class PullABellEvent {
    @SerializedName("kid_id")
    private int kidId;

    public int getKidId() {
        return kidId;
    }

    public void setKidId(int kidId) {
        this.kidId = kidId;
    }
}
