package com.example.hotelbookingsystem.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@NoArgsConstructor
public class RatingDto {
    private Long userId;
    private Long hotelId;
    private int rateingFrom5;
}
