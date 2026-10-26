package com.salim.actora.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "username should not be empty")
    @Size(min=3,max = 15,message = "username should be 3 to 15 character long")
    private String username;

    @NotBlank(message = "password should not be empty")
    @Size(min = 8,max = 20,message = "password should be 8 to 20 character long")
    private String password;
}
