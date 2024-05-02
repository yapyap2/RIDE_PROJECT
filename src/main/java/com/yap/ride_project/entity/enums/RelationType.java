package com.yap.ride_project.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yap.ride_project.exception.BikeTypeParsingException;
import com.yap.ride_project.exception.RelationTypeMappingException;

public enum RelationType {
    LIKE("LIKE"), SUMMIT("SUMMIT");
    private String code;
    public String getCode() {return code;}

    RelationType(String code) {this.code = code;}

    @JsonCreator
    public static RelationType from(String source){
        for (RelationType type : values()) {
            if (type.getCode().equals(source)) {
                return type;
            }
        }
        throw new RelationTypeMappingException("RelationType 맵핑실패. LIKE 또는 SUMMIT을 사용해주세요  RelationType: " + source);
    }
}
