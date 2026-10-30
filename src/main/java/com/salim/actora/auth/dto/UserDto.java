package com.salim.actora.auth.dto;

import com.salim.actora.auth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String username;

    private String password;

    private Set<Role> roles;
}
