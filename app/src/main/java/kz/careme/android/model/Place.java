package kz.careme.android.model;

public class Place {
    private int id;
    private float posX;
    private float posY;
    private int safeRadius;
    private String name;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public int getSafeRadius() {
        return safeRadius;
    }

    public void setSafeRadius(int safeRadius) {
        this.safeRadius = safeRadius;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
