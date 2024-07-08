package com.example.pushnotification;

import android.os.Parcel;
import android.os.Parcelable;

public class Camera implements Parcelable {
    private String id;
    private String macAddress;
    private String username;
    private String password;
    private String location;
    private String type;

    public Camera() {
        // Default constructor required for Firestore
    }

    public Camera(String macAddress, String username, String password, String location, String type) {
        this.macAddress = macAddress;
        this.username = username;
        this.password = password;
        this.location = location;
        this.type = type;
    }

    protected Camera(Parcel in) {
        macAddress = in.readString();
        username = in.readString();
        password = in.readString();
        location = in.readString();
        type = in.readString();
    }

    public static final Creator<Camera> CREATOR = new Creator<Camera>() {
        @Override
        public Camera createFromParcel(Parcel in) {
            return new Camera(in);
        }

        @Override
        public Camera[] newArray(int size) {
            return new Camera[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(macAddress);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(location);
        dest.writeString(type);
    }
}
