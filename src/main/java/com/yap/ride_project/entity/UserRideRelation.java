package com.yap.ride_project.entity;

import com.yap.ride_project.entity.enums.RelationType;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
public class UserRideRelation {
    public UserRideRelation(User user, Ride ride) {
        this.user = user;
        this.ride = ride;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private boolean summited;
    private boolean liked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    public void updateRelation(RelationType type){
        if(type == RelationType.LIKE) this.liked = true;
        else this.summited = true;
    }

    public UserRideRelation() {

    }
}
