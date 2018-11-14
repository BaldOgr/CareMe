package kz.careme.android.model.event;

import com.google.gson.annotations.SerializedName;

public class GetParentIdEvent {
    @SerializedName("parent_id")
    private int parentId;

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
