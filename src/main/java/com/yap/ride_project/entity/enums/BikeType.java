package com.yap.ride_project.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yap.ride_project.exception.BikeTypeParsingException;

public enum BikeType {

    ROADBIKE("R"),
    MTB("M"),
    CX("C"),
    MINIVELO("V"),
    HYBRID("H"),
    NONE("N");

    private final String code;

    BikeType(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static BikeType from(String source){
        for (BikeType type : values()) {
            if (type.getCode().equals(source)) {
                return type;
            }
        }
        throw new BikeTypeParsingException("해당하는 자전거 타입이 존재하지 않습니다. typeString: " + source);
    }

}
