package com.mbds.vamp.vamp_mobileapp.models;

/**
 * Created by nour.benmoussa on 22/12/2017.
 */

public class AirConditionning extends Setting {

    private double air_conditionning;

    public AirConditionning(String name) {
        super(name);
    }

    public double getAir_conditionning() {
        return air_conditionning;
    }

    public void setAir_conditionning(double air_conditionning) {
        this.air_conditionning = air_conditionning;
    }
}
