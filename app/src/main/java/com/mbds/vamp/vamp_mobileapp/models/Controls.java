package com.mbds.vamp.vamp_mobileapp.models;

/**
 * Created by nour.benmoussa on 22/12/2017.
 */

public class Controls {

    boolean locked;
    boolean started;


    public Controls() {}

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
