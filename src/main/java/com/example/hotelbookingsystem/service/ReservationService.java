package com.example.hotelbookingsystem.service;

import com.example.hotelbookingsystem.model.common.Constants;
import com.example.hotelbookingsystem.model.common.GenericException;
import com.example.hotelbookingsystem.model.common.GenericResponse;
import com.example.hotelbookingsystem.model.dto.ReservationDto;
import com.example.hotelbookingsystem.model.dto.UpdateReservationDto;
import com.example.hotelbookingsystem.model.entity.Hotel;
import com.example.hotelbookingsystem.model.entity.Reservation;
import com.example.hotelbookingsystem.model.entity.Room;
import com.example.hotelbookingsystem.model.entity.User;
import com.example.hotelbookingsystem.model.enums.UserType;
import com.example.hotelbookingsystem.repository.HotelRepository;
import com.example.hotelbookingsystem.repository.ReservationRepository;
import com.example.hotelbookingsystem.repository.RoomRepository;
import com.example.hotelbookingsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReservationService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public GenericResponse bookARoom(ReservationDto ReservationDto) throws GenericException {
        Duration duration = Duration.between(ReservationDto.getReservationEndingDate(), ReservationDto.getReservationStartingDate());
        long durationInDays = duration.toDays();
        Optional<User> user = userRepository.findById(ReservationDto.getUserId());
        Optional<Hotel> hotel = hotelRepository.findById(ReservationDto.getHotelId());
        Optional<Room> room = roomRepository.findById(ReservationDto.getRoomId());


        if (user.isPresent() && user.get().getUserType().equals(UserType.USER)) {
            if (hotel.isPresent() && hotel.get().getRoomsCountity() > 0) {
                if (room.isPresent() && room.get().isAvailable()) {
                    Optional<Reservation> reservation = reservationRepository.findByHotelAndRoomAndUserAndReservationStartingDateAndReservationEndingDate
                            (hotel.get(), room.get(), user.get(), ReservationDto.getReservationStartingDate(), ReservationDto.getReservationEndingDate());
                    if (reservation.isPresent()) {
                        log.error("this reservation is already exist");
                        return new GenericResponse(Constants.DUPLICATE_RESERVATION, reservation);
                    } else {
                        Reservation newReservation = new Reservation(user.get(), hotel.get(), room.get(),
                                ReservationDto.getReservationStartingDate(), ReservationDto.getReservationEndingDate(),
                                (durationInDays * room.get().getPricePerDay()));
                        reservationRepository.save(newReservation);
                        room.get().setAvailable(false);
                        roomRepository.save(room.get());
                        log.info("reservation submitted successfully");
                        return new GenericResponse(Constants.RESERVATION_SUBMITTED, newReservation);
                    }
                } else {
                    log.error("room un available !!");
                    throw new GenericException(Constants.CANNOT_BOOK_THIS_ROOM);
                }
            } else {
                log.error("hotel un available !!");
                throw new GenericException(Constants.UN_AVAILABLE_HOTEL);
            }
        } else {
            log.error("user un available !!");
            throw new GenericException(Constants.UN_AVAILABLE_USER);
        }
    }

    public GenericResponse updateReservation(UpdateReservationDto updateReservationDto) throws GenericException {
        Duration duration = Duration.between(updateReservationDto.getReservationEndingDate(), updateReservationDto.getReservationStartingDate());
        long durationInDays = duration.toDays();
        Optional<User> user = userRepository.findById(updateReservationDto.getUserId());
        Optional<Hotel> hotel = hotelRepository.findById(updateReservationDto.getHotelId());
        Optional<Room> room = roomRepository.findById(updateReservationDto.getRoomId());
        Optional<Reservation> reservation = reservationRepository.findById(updateReservationDto.getReservationId());
        if (user.isPresent() && user.get().getId().equals(reservation.get().getUser().getId())) {
            if (hotel.isPresent() && hotel.get().getId().equals(reservation.get().getHotel().getId())) {
                if (room.isPresent() && room.get().getId().equals(reservation.get().getRoom().getId())) {
                    if (reservation.isPresent()){
                        reservation.get().setRoom(room.get());
                        reservation.get().setHotel(hotel.get());
                        reservation.get().setReservationStartingDate(updateReservationDto.getReservationStartingDate());
                        reservation.get().setReservationEndingDate(updateReservationDto.getReservationEndingDate());
                        reservation.get().setReservationPrice((durationInDays * room.get().getPricePerDay()));
                        reservationRepository.save(reservation.get());
                        log.info("reservation updated successfully");
                        return new GenericResponse(Constants.RESERVATION_UPDATED, reservation);
                    }else {
                        log.error("reservation un available !!");
                        throw new GenericException(Constants.UN_AVAILABLE_RESERVATION);
                    }
                }else {
                    log.error("room un available !!");
                    throw new GenericException(Constants.UN_AVAILABLE_ROOM);
                }
            }else {
                log.error("hotel un available !!");
                throw new GenericException(Constants.UN_AVAILABLE_HOTEL);
            }
        }else {
            log.error("user not found !!");
            throw new GenericException(Constants.UN_AVAILABLE_USER);
        }
    }
    public GenericResponse cancelMyReservation(Long reservationId) throws GenericException {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            reservationRepository.deleteById(reservationId);
            log.info("reservation deleted successfully");
            return new GenericResponse(Constants.DELETED_RESERVATION, reservation.get());
        } else {
            log.error("reservation not found");
            throw new GenericException(Constants.UN_AVAILABLE_RESERVATION);
        }
    }
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }


    public Reservation getReservationById(Long reservationId) throws GenericException {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            log.error("Reservation not found");
            throw new GenericException(Constants.UN_AVAILABLE_RESERVATION);
        }
    }
    public GenericResponse endTheReservationDate(Long reservationId)throws GenericException{
        Optional<Reservation>reservation=reservationRepository.findById(reservationId);
        if (reservation.isPresent()){
            reservation.get().setReservationEndingDate(LocalDateTime.now());
            reservation.get().getRoom().setAvailable(true);
            reservationRepository.save(reservation.get());
            log.info("reservation has been ended");
            return new GenericResponse(Constants.RESERVATION_ENDED, reservation.get());
        }else {
            log.error("Reservation not found");
            throw new GenericException(Constants.UN_AVAILABLE_RESERVATION);
        }
    }


}



