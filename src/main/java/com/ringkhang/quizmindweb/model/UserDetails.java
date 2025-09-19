package com.ringkhang.quizmindweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_details")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password_hash" , nullable = false)
    private String pass;
    @Column(name = "email", nullable = false)
    private String email;

}
