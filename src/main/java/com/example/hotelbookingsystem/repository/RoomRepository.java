package com.example.hotelbookingsystem.repository;

import com.example.hotelbookingsystem.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {

}
