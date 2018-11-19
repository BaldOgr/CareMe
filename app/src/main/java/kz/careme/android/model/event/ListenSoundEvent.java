package kz.careme.android.model.event;

import com.google.gson.annotations.SerializedName;

public class ListenSoundEvent {

    @SerializedName("kid_id")
    private String kidId;

    public String getKidId() {
        return kidId;
    }

    public void setKidId(String kidId) {
        this.kidId = kidId;
    }
}
