package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.model.common.GenericException;
import com.example.hotelbookingsystem.model.common.GenericResponse;
import com.example.hotelbookingsystem.model.dto.RoomDto;
import com.example.hotelbookingsystem.model.dto.UpdateRoomDto;
import com.example.hotelbookingsystem.model.entity.Room;
import com.example.hotelbookingsystem.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/Room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping(path = "/addRoom")
    public GenericResponse addRoom(@RequestBody RoomDto roomDto) throws GenericException{
        return roomService.addRoom(roomDto);
    }
    @PutMapping(path = "/updateRoom")
    public GenericResponse updateRoom(@RequestBody UpdateRoomDto updateRoomDto) throws GenericException {
        return roomService.updateRoom(updateRoomDto);
    }
    @DeleteMapping(path = "/deleteRoom/{roomId}")
    public GenericResponse deleteRoom(@PathVariable("roomId")Long roomId) throws GenericException {
        return roomService.deleteRoom(roomId);
    }
    @GetMapping(path = "/getAllRoomsByHotelId/{hotelId}")
    public List<Room>getAllRoomsByHotelId(@PathVariable("hotelId")Long hotelId) throws GenericException {
        return roomService.getAllRoomsByHotelId(hotelId);
    }
}
