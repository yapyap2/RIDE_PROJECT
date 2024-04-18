package com.yap.ride_project.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BikeType {

    ROADBIKE, MTB, CX, MINIVELO, HYBRID, NONE;

    @JsonCreator
    public static BikeType from(String source){
        if(source.equals("R")) return BikeType.ROADBIKE;
        if(source.equals("M")) return BikeType.MTB;
        if(source.equals("V")) return BikeType.MINIVELO;
        if(source.equals("C")) return BikeType.CX;
        if(source.equals("H")) return BikeType.HYBRID;
        return BikeType.NONE;
    }

}
