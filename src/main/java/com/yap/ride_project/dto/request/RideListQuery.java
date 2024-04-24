package com.yap.ride_project.dto.request;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.yap.ride_project.entity.enums.BikeType;
import com.yap.ride_project.exception.BikeTypeParsingException;
import com.yap.ride_project.exception.RideQueryParameterException;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        public Builder(){
            this.builder = new BooleanBuilder();
        }

        public Builder name(String name){
            if(name == null) return this;
            if(name.equals("")) throw new RideQueryParameterException("name 필드 파싱 에러. 빈 문자열입니다.");
            builder.and(ride.rideName.contains(name));
            return this;
        }

        public Builder distanceUpperLimit(Double upperLimit){
            if(upperLimit == null) return this;
            builder.and(ride.distance.loe(upperLimit));
            return this;
        }

        public Builder distanceLowerLimit(Double lowerLimit){
            if(lowerLimit == null) return this;
            builder.and(ride.distance.goe(lowerLimit));
            return this;
        }

        public Builder startLocationCode(String locationCode){
            if(locationCode == null) return this;
            builder.and(ride.startLocationCode.eq(locationCode));
            return this;
        }

        public Builder startDateLeft(String left){
            if(left == null) left = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            try{
                builder.and(ride.startDate.after(LocalDateTime.parse(left+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            } catch (DateTimeParseException e){
                throw new RideQueryParameterException("start_date_left 필드 파싱 에러. yyyy-MM-dd 형식이 아닙니다. value: " + left);
            }
            return this;
        }

        public Builder startDateRight(String right){
            if(right == null) return this;
            try{
                builder.and(ride.startDate.before(LocalDateTime.parse(right+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            } catch (DateTimeParseException e){
                throw new RideQueryParameterException("start_date_right 필드 파싱 에러. yyyy-MM-dd 형식이 아닙니다. value: " + right);
            }
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
