package com.mbds.vamp_mobileapp.models;

public class Profile {

    // Car equipment
    int retroPosition;
    int preferedTemperature;

    // Music Manager
    int soundVolume;
    Playlist [] playlists;


    public Profile() {}


    public int getRetroPosition() {
        return retroPosition;
    }

    public void setRetroPosition(int retroPosition) {
        this.retroPosition = retroPosition;
    }

    public int getPreferedTemperature() {
        return preferedTemperature;
    }

    public void setPreferedTemperature(int preferedTemperature) {
        this.preferedTemperature = preferedTemperature;
    }

    public int getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(int soundVolume) {
        this.soundVolume = soundVolume;
    }

    public Playlist[] getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Playlist[] playlists) {
        this.playlists = playlists;
    }
}
