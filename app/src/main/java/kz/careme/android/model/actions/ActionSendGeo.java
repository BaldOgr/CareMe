package kz.careme.android.model.actions;

import com.google.gson.annotations.SerializedName;

public class ActionSendGeo extends BaseAction{
    public static final String ACTION = "send_geo";

    @SerializedName("session_id")
    private String sessionId;

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("batteryLevel")
    private String batteryLevel;

    @SerializedName("batterState")
    private String batterState;

    @SerializedName("course")
    private String course;

    @SerializedName("speed")
    private float speed;

    @SerializedName("time")
    private long time;

    @SerializedName("accuracy")
    private float accuracy;

    public ActionSendGeo() {
        setAction(ACTION);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public String getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getBatterState() {
        return batterState;
    }

    public void setBatterState(String batterState) {
        this.batterState = batterState;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
}
