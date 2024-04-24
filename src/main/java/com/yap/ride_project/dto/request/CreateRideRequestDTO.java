package com.yap.ride_project.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yap.ride_project.entity.enums.BikeType;
import com.yap.ride_project.exception.BikeTypeParsingException;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class CreateRideRequestDTO {

    @JsonProperty("user_id")
    private long userId;
    @NotNull
    @Size(min = 2, max = 20)
    @JsonProperty("ride_name")
    private String rideName;
    private String description;
    @NotNull
    @JsonProperty("start_date")
    private String startDate;
    @NotNull
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "9999")
    private Double distance;
    @DecimalMin(value = "0")
    @DecimalMax(value = "99999")
    private Double elevation;
    @NotNull
    @JsonProperty("start_location_code")
    private String startLocationCode;

    @JsonProperty("estimate_time")
    private Integer estimateTime;
    @JsonProperty("ftp_limit")
    private Integer ftpLimit;
    @JsonProperty("age_limit")
    private Integer ageLimit;
    @JsonProperty("participant_limit")
    private Integer participantLimit;
    @JsonProperty("participant_minimum")
    private Integer participantMinimum;
    @NotEmpty
    @JsonProperty("bike_type")
    @JsonDeserialize(using = BikeTypeListDeserializer.class)
    private List<BikeType> bikeType;
}

class BikeTypeListDeserializer extends JsonDeserializer<List<BikeType>> {

    private static final List<String> allowBikeType = Arrays.asList("R","M", "V", "N", "H", "C");

    @Override
    public List<BikeType> deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {

        String[] values = p.readValueAs(String[].class);
        List<BikeType> list = new ArrayList<>();
        for (String type : values) {
            if(allowBikeType.contains(type)) list.add(BikeType.from(type));
            else throw new BikeTypeParsingException("해당하는 자전거 타입이 존재하지 않습니다. typeString: " + type);
        }


        return list;
    }
}
