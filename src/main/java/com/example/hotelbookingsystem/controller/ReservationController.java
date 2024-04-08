package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.model.common.GenericException;
import com.example.hotelbookingsystem.model.common.GenericResponse;
import com.example.hotelbookingsystem.model.dto.ReservationDto;
import com.example.hotelbookingsystem.model.dto.UpdateReservationDto;
import com.example.hotelbookingsystem.model.entity.Reservation;
import com.example.hotelbookingsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/Reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping(path = "/BookARoom")
    public GenericResponse bookARoom(@RequestBody ReservationDto recervationDto)throws GenericException {
        return reservationService.bookARoom(recervationDto);
    }
    @PutMapping(path = "/updateMyReservation")
    public GenericResponse updateReservation(@RequestBody UpdateReservationDto recervationDto) throws GenericException {
        return reservationService.updateReservation(recervationDto);
    }
    @DeleteMapping(path = "/cancelMyReservation/{reservationId}")
    public GenericResponse cancelMyReservation(@PathVariable("reservationId")Long reservationId) throws GenericException {
        return reservationService.cancelMyReservation(reservationId);
    }
    @GetMapping(path = "/getAllReservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping(path = "/getReservationById/{reservationId}")
    public Reservation getReservationById(@PathVariable("reservationId")Long reservationId) throws GenericException {
        return reservationService.getReservationById(reservationId);
    }
    @PostMapping(path = "/endTheReservationDate")
    public GenericResponse endTheReservationDate(Long reservationId)throws GenericException{
        return reservationService.endTheReservationDate(reservationId);
    }

}
