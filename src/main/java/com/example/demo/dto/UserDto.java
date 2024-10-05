package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Setter;
import lombok.Getter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    @NotBlank(message = "El nombre no debe estar vacío")
    private String name;

    @Email(message = "El formato de correo no es válido")
    private String email;

    private String password;

    private List<PhoneDto> phones;

    @Getter
    @Setter
    public static class PhoneDto {
        private String number;
        private String citycode;
        private String contrycode;
    }
}


