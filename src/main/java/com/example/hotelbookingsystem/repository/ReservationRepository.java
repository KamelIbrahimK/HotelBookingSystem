package com.example.hotelbookingsystem.repository;


import com.example.hotelbookingsystem.model.entity.Hotel;
import com.example.hotelbookingsystem.model.entity.Reservation;
import com.example.hotelbookingsystem.model.entity.Room;
import com.example.hotelbookingsystem.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByHotelAndRoomAndUserAndReservationStartingDateAndReservationEndingDate(Hotel hotel, Room room,
                                                                                                      User user, LocalDateTime startingDate,
                                                                                                      LocalDateTime endDate);

}
