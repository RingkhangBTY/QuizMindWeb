package com.ringkhang.quizmindweb.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor @AllArgsConstructor
public class UserDetailsDTO {
    private int id;
    private String username;
    private String email;
}
