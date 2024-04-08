package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.model.common.GenericException;
import com.example.hotelbookingsystem.model.common.GenericResponse;
import com.example.hotelbookingsystem.model.dto.HotelDto;
import com.example.hotelbookingsystem.model.dto.RatingDto;
import com.example.hotelbookingsystem.model.entity.Hotel;
import com.example.hotelbookingsystem.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/Hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping(path = "/addHotel")
    public GenericResponse addHotel(@RequestBody HotelDto hotelDto)throws GenericException{
        return hotelService.addHotel(hotelDto);
    }
    @PutMapping(path = "/updateHotel")
    public GenericResponse updateHotel(@RequestBody HotelDto hotelDto) throws GenericException {
        return hotelService.updateHotel(hotelDto);
    }
    @DeleteMapping(path = "/deleteHotel/{hotelId}")
    public GenericResponse deleteHotel(@PathVariable("hotelId")Long hotelId) throws GenericException {
        return hotelService.deleteHotel(hotelId);
    }
    @GetMapping(path = "/getAllHotels")
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping(path = "/getHotelById/{hotelId}")
    public Hotel getHotelById(@PathVariable("hotelId")Long hotelId) throws GenericException {
        return hotelService.getHotelById(hotelId);
    }
    @PostMapping(path = "/rateTheHotelFrom5")
    public GenericResponse rateTheHotelFrom5(@RequestBody RatingDto ratingDto)throws GenericException{
        return hotelService.rateTheHotelFrom5(ratingDto);
    }
}
