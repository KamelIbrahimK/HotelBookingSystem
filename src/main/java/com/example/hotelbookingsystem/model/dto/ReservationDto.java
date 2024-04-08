package com.example.hotelbookingsystem.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationDto {
    private Long userId;
    private Long hotelId;
    private Long roomId;
    private LocalDateTime reservationStartingDate;
    private LocalDateTime reservationEndingDate;
}
