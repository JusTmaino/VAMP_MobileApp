package com.mbds.vamp.vamp_mobileapp.models;

public class Location {

    int longitude;
    int latitude;

    public Location(int longitude, int latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }
}
