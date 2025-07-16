package com.week5.SpringSecurity.dto;

import com.week5.SpringSecurity.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingUpDTO {
    private String name;
    private String email;
    private String password;
    private Set<Role> roles;
}
