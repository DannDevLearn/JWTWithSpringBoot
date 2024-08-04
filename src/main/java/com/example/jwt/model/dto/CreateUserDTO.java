package com.example.jwt.model.dto;

import lombok.*;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {

    private String email; // Username en spring security
    private String password;
    private Set<String> roles;

}
