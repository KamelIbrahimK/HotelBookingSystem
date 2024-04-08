package com.example.hotelbookingsystem.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private Long id;
    private String hotelName;
    private String hotelAdress;
    private int roomsCountity;
    private LocalDateTime createdAt;
    private LocalDateTime UpdatedAt;
    private int rate;

    @OneToMany(mappedBy = "hotel" , cascade = CascadeType.ALL )
    private List<Room> rooms;

    public Hotel(String hotelName, String hotelAdress) {
        this.hotelName=hotelName;
        this.hotelAdress=hotelAdress;
    }
}
