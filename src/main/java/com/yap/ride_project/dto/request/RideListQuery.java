package com.yap.ride_project.dto.request;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.yap.ride_project.entity.enums.BikeType;
import com.yap.ride_project.exception.BikeTypeParsingException;
import com.yap.ride_project.exception.RideQueryParameterException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static com.yap.ride_project.entity.QRide.ride;

public class RideListQuery {
    public RideListQuery(BooleanBuilder builder) {
        this.builder = builder;
    }

    private BooleanBuilder builder;

    public BooleanBuilder getBuilder() {
        return builder;
    }


    public static class Builder{
        BooleanBuilder builder;
        StringBuilder stringBuilder;

        public Builder(){
            this.builder = new BooleanBuilder();
            this.stringBuilder = new StringBuilder();
        }

        public Builder name(String name){
            if(name == null || name.equals("")) return this;
            builder.and(ride.rideName.contains(name));
            return this;
        }

        public Builder distanceUpperLimit(Double upperLimit){
            if(upperLimit == null || upperLimit >= 700) return this;
            builder.and(ride.distance.loe(upperLimit));
            return this;
        }

        public Builder distanceLowerLimit(Double lowerLimit){
            if(lowerLimit == null) return this;
            builder.and(ride.distance.goe(lowerLimit));
            return this;
        }

        public Builder startLocationCode(String locationCode){ //로케이션 코드는 여러개 받을 수 있어야 함
            if(locationCode == null) return this;
            builder.and(ride.startLocationCode.eq(locationCode));
            return this;
        }

        public Builder dateRange(String range){
            builder.and(ride.startDate.after(LocalDateTime.now()));
            if(range == null) return this;

            if(range.equals("today")) builder.and(ride.startDate.before(LocalDateTime.of(LocalDate.now(), LocalTime.MAX)));
            else if(range.equals("half_month")) builder.and(ride.startDate.before(LocalDateTime.of(LocalDate.now().plusDays(15), LocalTime.MAX)));
            else if(range.equals("full_month")) builder.and(ride.startDate.before(LocalDateTime.of(LocalDate.now().plusDays(30), LocalTime.MAX)));
            else{ throw new RideQueryParameterException("dateRange 필드 파싱 에러. 규정 외 코드 입니다. dataRange:" + range);}
            return this;
        }

        public Builder bikeType(String bikeType){
            if(bikeType == null) return this;

            BooleanBuilder innerBuilder = new BooleanBuilder();

            String[] typeList = bikeType.split(",");
            for(String type : typeList){
                switch (type){
                    case "R" : innerBuilder.or(ride.roadbike.isTrue()); break;
                    case "M" : innerBuilder.or(ride.mtb.isTrue()); break;
                    case "H" : innerBuilder.or(ride.hybrid.isTrue()); break;
                    case "V" : innerBuilder.or(ride.minivelo.isTrue()); break;
                    case "N" : innerBuilder.or(ride.none.isTrue()); break;
                    case "C" : innerBuilder.or(ride.cx.isTrue()); break;
                    default: throw new RideQueryParameterException("bike_type 필드 파싱 에러. 잘못된 자전거 타입 코드 입니다. code: " + type);
                }
            }

            builder.and(innerBuilder);
            return this;
        }

        public RideListQuery build(){
            RideListQuery query = new RideListQuery(builder);
            return query;
        }

    }
}
