package com.example.hotelbookingsystem.service;

import com.example.hotelbookingsystem.model.common.Constants;
import com.example.hotelbookingsystem.model.common.GenericException;
import com.example.hotelbookingsystem.model.common.GenericResponse;
import com.example.hotelbookingsystem.model.dto.RoomDto;
import com.example.hotelbookingsystem.model.dto.UpdateRoomDto;
import com.example.hotelbookingsystem.model.entity.Hotel;
import com.example.hotelbookingsystem.model.entity.Room;
import com.example.hotelbookingsystem.model.entity.User;
import com.example.hotelbookingsystem.model.enums.UserType;
import com.example.hotelbookingsystem.repository.HotelRepository;
import com.example.hotelbookingsystem.repository.RoomRepository;
import com.example.hotelbookingsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HotelRepository hotelRepository;

    public GenericResponse addRoom(RoomDto roomDto) throws GenericException {
        Optional<User> user = userRepository.findById(roomDto.getUserId());
        Optional<Hotel> hotel = hotelRepository.findById(roomDto.getHotelId());
        if (user.isPresent() && user.get().getUserType().equals(UserType.ADMIN)) {
            if (hotel.isPresent()) {
                Room newRoom = new Room(roomDto.getRoomViewType(), roomDto.getRoomBedsType(), hotel.get() , roomDto.getPricePerDay());
                hotel.get().setRoomsCountity(hotel.get().getRoomsCountity()+1);
                hotel.get().getRooms().add(newRoom);
                hotelRepository.save(hotel.get());
                log.info("Room added successfully");
                return new GenericResponse(Constants.ROOM_ADDED, newRoom);
            } else {
                log.info("hotel not found ");
                throw new GenericException(Constants.UN_AVAILABLE_HOTEL);
            }
        } else {
            log.info("user not found");
            throw new GenericException(Constants.UN_AVAILABLE_USER);
        }
    }

    public GenericResponse updateRoom(UpdateRoomDto updateRoomDto) throws GenericException {
        Optional<User> user = userRepository.findById(updateRoomDto.getUserId());
        Optional<Hotel> hotel = hotelRepository.findById(updateRoomDto.getHotelId());
        Optional<Room> room = roomRepository.findById(updateRoomDto.getRoomId());
        if (user.isPresent() && user.get().getUserType().equals(UserType.ADMIN)) {
            if (hotel.isPresent() && hotel.get().getId().equals(updateRoomDto.getHotelId())) {
                if (room.isPresent() && room.get().getId().equals(updateRoomDto.getRoomId())) {
                    room.get().setRoomBedsType(updateRoomDto.getRoomBedsType());
                    room.get().setRoomViewType(updateRoomDto.getRoomViewType());
                    room.get().setPricePerDay(updateRoomDto.getPricePerDay());
                    roomRepository.save(room.get());
                    log.info("Room updated successfully");
                    return new GenericResponse(Constants.ROOM_UPDATED, room);
                } else {
                    log.info("room not found");
                    throw new GenericException(Constants.UN_AVAILABLE_ROOM);
                }
            } else {
                log.info("hotel not found");
                throw new GenericException(Constants.UN_AVAILABLE_HOTEL);
            }
        } else {
            log.info("user not found ");
            throw new GenericException(Constants.UN_AVAILABLE_USER);
        }
    }
    public GenericResponse deleteRoom(Long roomId) throws GenericException {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isPresent()) {
            roomRepository.deleteById(roomId);
            log.info("room deleted successfully");
            return new GenericResponse(Constants.DELETED_ROOM, room.get());
        } else {
            log.error("room not found");
            throw new GenericException(Constants.UN_AVAILABLE_ROOM);
        }
    }
    public List<Room>getAllRoomsByHotelId(Long hotelId)throws GenericException{
        Optional<Hotel>hotel=hotelRepository.findById(hotelId);
        if (hotel.isPresent()){

            log.info("rooms returned successfully");
           return hotel.get().getRooms();
        }else {
            log.info("no rooms founded");
            throw new GenericException(Constants.EMPETY_HOTEL);
        }
    }


}
