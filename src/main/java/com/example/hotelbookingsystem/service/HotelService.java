package com.example.hotelbookingsystem.service;

import com.example.hotelbookingsystem.model.common.Constants;
import com.example.hotelbookingsystem.model.common.GenericException;
import com.example.hotelbookingsystem.model.common.GenericResponse;
import com.example.hotelbookingsystem.model.dto.HotelDto;
import com.example.hotelbookingsystem.model.dto.RatingDto;
import com.example.hotelbookingsystem.model.entity.Hotel;
import com.example.hotelbookingsystem.model.entity.User;
import com.example.hotelbookingsystem.model.enums.UserType;
import com.example.hotelbookingsystem.repository.HotelRepository;
import com.example.hotelbookingsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private UserRepository userRepository;

    public GenericResponse addHotel(HotelDto hotelDto) throws GenericException {
        Optional<Hotel> hotel = hotelRepository.findByHotelName(hotelDto.getHotelName());
        if (hotel.isPresent()) {
            log.error("Hotel is already exist , can't added it again");
            throw new GenericException(Constants.DUPLICATE_HOTEL);
        } else {
            Hotel newHotel = new Hotel(hotelDto.getHotelName(), hotelDto.getHotelAdress());
            newHotel.setRoomsCountity(0);
            newHotel.setCreatedAt(LocalDateTime.now());
            newHotel.setUpdatedAt(LocalDateTime.now());
            newHotel.setRate(0);
            hotelRepository.save(newHotel);
            log.info("Hotel added successfully");
            return new GenericResponse(Constants.HOTEL_ADDED, newHotel);
        }
    }

    public GenericResponse updateHotel(HotelDto hotelDto) throws GenericException {
        Optional<Hotel> hotel = hotelRepository.findByHotelName(hotelDto.getHotelName());
        if (hotel.isPresent()) {
            hotel.get().setHotelName(hotelDto.getHotelName());
            hotel.get().setHotelAdress(hotelDto.getHotelAdress());
            hotel.get().setUpdatedAt(LocalDateTime.now());
            hotelRepository.save(hotel.get());
            log.info("hotel updated successfully");
            return new GenericResponse(Constants.HOTEL_UPDATED, hotel.get());
        } else {
            log.error("hotel not found");
            throw new GenericException(Constants.UN_AVAILABLE_HOTEL);
        }
    }

    public GenericResponse deleteHotel(Long hotelId) throws GenericException {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            hotelRepository.deleteById(hotelId);
            log.info("hotel deleted successfully");
            return new GenericResponse(Constants.DELETED_USER, hotel.get());
        } else {
            log.error("hotel not found");
            throw new GenericException(Constants.UN_AVAILABLE_HOTEL);
        }
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long hotelId) throws GenericException {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            return hotel.get();
        } else {
            log.error("User not found");
            throw new GenericException(Constants.UN_AVAILABLE_HOTEL);
        }
    }

    public GenericResponse rateTheHotelFrom5(RatingDto ratingDto) throws GenericException {
        Optional<User> user = userRepository.findById(ratingDto.getUserId());
        Optional<Hotel> hotel = hotelRepository.findById(ratingDto.getHotelId());
        if (user.isPresent() && user.get().getUserType().equals(UserType.USER)) {
            if (hotel.isPresent()) {
                hotel.get().setRate((ratingDto.getRateingFrom5()) + (hotel.get().getRate()));
                hotelRepository.save(hotel.get());
                log.info("rate successfully submitted");
                return new GenericResponse(Constants.RATE_SUCCESSFULLY, hotel.get());
            } else {
                log.error("hotel not found!");
                throw new GenericException(Constants.UN_AVAILABLE_HOTEL);
            }
        } else {
            log.error("User not found");
            throw new GenericException(Constants.UN_AVAILABLE_USER);
        }
    }
}
