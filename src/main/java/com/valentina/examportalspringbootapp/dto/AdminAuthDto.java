package com.valentina.examportalspringbootapp.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminAuthDto {

    private String username;
    private String password;

    public AdminAuthDto(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
