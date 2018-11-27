package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionSavePlace extends BaseAction{
    public static final String ACTION = "save_place";

    @SerializedName("session_id")
    private String sessionId;

    @SerializedName("kid_id")
    private int kidId;

    @SerializedName("title")
    private String title;

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("radius")
    private int radius;

    @SerializedName("type")
    private int type;

    public ActionSavePlace() {
        setAction(ACTION);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getKidId() {
        return kidId;
    }

    public void setKidId(int kidId) {
        this.kidId = kidId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
