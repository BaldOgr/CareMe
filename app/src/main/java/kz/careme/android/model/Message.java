package kz.careme.android.model;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("id")
    private Long id;
    @SerializedName("date")
    private String date;
    @SerializedName("parent_id")
    private int parentId;

    @SerializedName("kid_id")
    private int kidId;

    @SerializedName("message")
    private String message;

    @SerializedName("type")
    private int type;
    private boolean loading;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getKidId() {
        return kidId;
    }

    public void setKidId(int kidId) {
        this.kidId = kidId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
