package com.yap.ride_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rideId;
    @NotNull
    private String rideName;
    private String description;
    @NotNull
    private Double distance;
    private Double elevation;
    private String startLocationCode;
    @NotNull
    private LocalDateTime startDate;
    private Integer estimateTime;
    private Integer ftpLimit;
    private Integer ageLimit;
    private Integer participantLimit;
    private Integer participantMinimum;

    private boolean roadbike = false;
    private boolean mtb = false;
    private boolean minivelo = false;
    private boolean cx = false;
    private boolean none = false;
    private boolean hybrid = false;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "OWNER_USER_ID")
    private User ownerUser;

    @Column(name = "OWNER_USER_ID", insertable = false, updatable = false)
    private Long ownerUserId;

    @Builder
    public Ride(String rideName, String description, Double distance, Double elevation, String startLocationCode, LocalDateTime startDate, Integer estimateTime, Integer ftpLimit, Integer ageLimit, Integer participantLimit, Integer participantMinimum, List<String> bikeType, User ownerUser) {
        this.rideName = rideName;
        this.description = description;
        this.distance = distance;
        this.elevation = elevation;
        this.startLocationCode = startLocationCode;
        this.startDate = startDate;
        this.estimateTime = estimateTime;
        this.ftpLimit = ftpLimit;
        this.ageLimit = ageLimit;
        this.participantLimit = participantLimit;
        this.participantMinimum = participantMinimum;

        for (String s : bikeType) {
            if (s.equals("R")) roadbike = true;
            else if (s.equals("M")) mtb = true;
            else if (s.equals("V")) minivelo = true;
            else if (s.equals("H")) hybrid = true;
            else if (s.equals("N")) none = true;
            else if (s.equals("C")) cx = true;
        }

        this.ownerUser = ownerUser;
    }

    public Ride() {

    }
}
