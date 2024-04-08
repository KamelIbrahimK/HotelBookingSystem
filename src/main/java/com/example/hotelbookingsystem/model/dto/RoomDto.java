package com.example.hotelbookingsystem.model.dto;

import com.example.hotelbookingsystem.model.enums.RoomBedsType;
import com.example.hotelbookingsystem.model.enums.RoomViewType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomDto {
    private Long hotelId;
    private Long userId;
    private double pricePerDay;
    private RoomViewType roomViewType;
    private RoomBedsType roomBedsType;
}
