package com.example.hotelbookingsystem.model.entity;

import com.example.hotelbookingsystem.model.enums.RoomBedsType;
import com.example.hotelbookingsystem.model.enums.RoomViewType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoomViewType roomViewType;
    @Enumerated(EnumType.STRING)
    private RoomBedsType roomBedsType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    private double pricePerDay;
    private boolean available;



    public Room( RoomViewType roomViewType, RoomBedsType roomBedsType, Hotel hotel ,double pricePerDay) {
        this.roomViewType = roomViewType;
        this.roomBedsType = roomBedsType;
        this.pricePerDay = pricePerDay;
        this.hotel=hotel;
        this.available=true;
    }
}
