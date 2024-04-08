package com.example.hotelbookingsystem.model.common;

import com.example.hotelbookingsystem.model.entity.Hotel;
import com.example.hotelbookingsystem.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {
    private String message;
    private Object object;

}
