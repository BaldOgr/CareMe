package kz.careme.android.model.actions;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public abstract class BaseAction {
    @SerializedName("action")
    protected String action;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
