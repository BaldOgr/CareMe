package kz.careme.android.model;

import com.google.gson.annotations.SerializedName;

public class ErrorMessage {
    @SerializedName("error")
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
