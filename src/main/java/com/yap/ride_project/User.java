package com.yap.ride_project;

import com.yap.ride_project.entity.Sex;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(unique = true)
    private String UID;
    private String name;
    private int age;
    private Sex sex;
    private float ftp;


    @Builder
    public User(String UID, String name, int age, float ftp,int sex) {
        this.UID = UID;
        this.name = name;
        this.age = age;
        this.sex = (sex == 1) ? Sex.MALE : Sex.FEMALE;
        this.ftp = ftp;
    }
}
