package com.example.hotelbookingsystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Reservation_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "hotel_hotel_id")
    private Hotel hotel;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    private LocalDateTime reservationStartingDate;
    private LocalDateTime reservationEndingDate;
    private double reservationPrice;


    public Reservation(User user, Hotel hotel, Room room, LocalDateTime reservationStartingDate, LocalDateTime reservationEndingDate, double reservationPrice) {
        this.user = user;
        this.hotel = hotel;
        this.room = room;
        this.reservationStartingDate = reservationStartingDate;
        this.reservationEndingDate = reservationEndingDate;
        this.reservationPrice = reservationPrice;
    }

}
