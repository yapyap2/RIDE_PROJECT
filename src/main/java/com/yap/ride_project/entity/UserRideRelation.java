package com.yap.ride_project.entity;

import com.yap.ride_project.entity.enums.RelationType;
import com.yap.ride_project.exception.RelationUpdateException;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
public class UserRideRelation {
    public UserRideRelation(User user, Ride ride) {
        this.user = user;
        this.ride = ride;
        this.startAt = ride.getStartDate();
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private boolean summited;
    private boolean liked;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id")
    private Ride ride;
    private LocalDateTime startAt;
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
    public void updateRelation(RelationType type){
        if(type == RelationType.LIKE){
            if(liked) throw new RelationUpdateException("이미 좋아요 처리된 라이딩입니다.");
            liked = true;
        }
        else{
            if(summited) throw new RelationUpdateException("이미 참가 신청 처리된 라이딩입니다.");
            summited = true;
        }
    }

    public UserRideRelation() {

    }
}
