package kz.careme.android.model.actions;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class BaseAction {
    @SerializedName("action")
    protected String action;

    @SerializedName("error")
    private String error;

    @SerializedName("code")
    private int code;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
