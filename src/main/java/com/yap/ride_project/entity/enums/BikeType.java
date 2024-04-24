package com.yap.ride_project.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yap.ride_project.exception.BikeTypeParsingException;

public enum BikeType {

    ROADBIKE, MTB, CX, MINIVELO, HYBRID, NONE;

    @JsonCreator
    public static BikeType from(String source){
        if(source.equals("R")) return BikeType.ROADBIKE;
        if(source.equals("M")) return BikeType.MTB;
        if(source.equals("V")) return BikeType.MINIVELO;
        if(source.equals("C")) return BikeType.CX;
        if(source.equals("H")) return BikeType.HYBRID;
        if(source.equals("N")) return BikeType.NONE;
        else throw new BikeTypeParsingException("해당하는 자전거 타입이 존재하지 않습니다. typeString: " + source);
    }

}
