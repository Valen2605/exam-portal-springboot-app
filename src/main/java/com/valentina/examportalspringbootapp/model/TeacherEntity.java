package com.valentina.examportalspringbootapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Entity
@Table(name="teacher")
@Getter
@Setter
public class TeacherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Email
    private String email;
    private String password;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "id")
    private AdminEntity createdBy;

}
