package com.fitness.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.lang.annotation.Target;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Data
public class User {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role = UserRole.USER;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
