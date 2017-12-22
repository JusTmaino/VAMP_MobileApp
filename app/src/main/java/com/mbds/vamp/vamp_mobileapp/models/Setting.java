package com.mbds.vamp.vamp_mobileapp.models;

public abstract class Setting {

    private String name;

    public Setting(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
