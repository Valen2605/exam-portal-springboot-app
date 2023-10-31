package com.valentina.examportalspringbootapp.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Entity
@Table(name="student")
@Getter
@Setter
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @Email
    private String email;
    private String password;
    private boolean status;

}
