package com.example.hotelbookingsystem.model.dto;

import com.example.hotelbookingsystem.model.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpDto {
    private String userName;
    private String password;
    private String nationality;
    private String phoneNumber;
    private UserType role;

}
