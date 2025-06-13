package com.ayurayush.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @Size(min = 10, max = 15)
    private String phone;  // Required for login

    private String whatsapp;  // Optional contact number for WhatsApp
}
