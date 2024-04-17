package com.yap.ride_project.dto;

import lombok.Data;

@Data
public class SignInRequestDTO {

    private String UID;
    private Long ID;
    private String name;
    private int age;
    private int sex;
    private float ftp;


}
