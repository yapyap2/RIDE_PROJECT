package com.yap.ride_project.dto.request;

import com.querydsl.core.BooleanBuilder;
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
    private BooleanBuilder builder;

    public BooleanBuilder getBuilder() {
        return builder;
    }

    public RideListQuery(Map<String, String> queryMap){
        builder = new BooleanBuilder();

        if(!queryMap.containsKey("start_date_left")){
            queryMap.put("start_date_left", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        for(String key : queryMap.keySet()){
            String value = queryMap.get(key);
            try {
                switch (key){
                    case "name" : {
                        if(queryMap.get("name").equals("")) throw new RideQueryParameterException("name 필드 파싱 에러. 빈 문자열입니다.");
                        builder.and(ride.rideName.contains(value));
                    } break;
                    case "distance_upper_limit" : builder.and(ride.distance.loe(Double.parseDouble(value))); break;
                    case "distance_lower_limit" : builder.and(ride.distance.goe(Double.parseDouble(value))); break;
                    case "start_location_code" : builder.and(ride.startLocationCode.eq(value)); break;
                    case "start_date_left" : builder.and(ride.startDate.after(LocalDateTime.parse(value+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))); break;
                    case "start_date_right" :builder.and(ride.startDate.before(LocalDateTime.parse(value+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))); break;
                    case "bike_type" : {
                        String[] typeList = queryMap.get("bike_type").split(",");
                        for(String type : typeList){
                            switch (type){
                                case "R" : builder.and(ride.roadbike.isTrue()); break;
                                case "M" : builder.and(ride.mtb.isTrue()); break;
                                case "H" : builder.and(ride.hybrid.isTrue()); break;
                                case "V" : builder.and(ride.minivelo.isTrue()); break;
                                case "N" : builder.and(ride.none.isTrue()); break;
                                case "C" : builder.and(ride.cx.isTrue()); break;
                                default: throw new RideQueryParameterException("잘못된 bike_type type: " + type);
                            }
                        }
                        break;
                    }
                    default: throw new RideQueryParameterException("잘못된 쿼리파라미터 key: " + key + " / value: " + queryMap.get(key));
                }

            } catch (NumberFormatException e){
                throw new RideQueryParameterException("Distance 속성 파싱 에러. Double 형이 아닙니다. value: " + queryMap.get(key));
            } catch (DateTimeParseException e){
                throw new RideQueryParameterException("StartDate 속성 파싱 에러. yyyy-MM-dd 형식이 아닙니다. value: " + queryMap.get(key));
            }
        }
    }
}
