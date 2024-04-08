package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.model.common.GenericException;
import com.example.hotelbookingsystem.model.common.GenericResponse;
import com.example.hotelbookingsystem.model.dto.SignUpDto;
import com.example.hotelbookingsystem.model.entity.User;
import com.example.hotelbookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/Account")
public class AccountController {
    @Autowired
    private UserService userService;
    @PostMapping(path = "/createUser")
    public GenericResponse  signup(@RequestBody SignUpDto signUpDto)throws GenericException {
        return userService.signUp(signUpDto);
    }
    @PutMapping(path = "/updateUser")
    public GenericResponse updateUser(@RequestBody SignUpDto signUpDto) throws GenericException {
        return userService.updateUser(signUpDto);
    }
    @DeleteMapping(path = "/deleteUser/{userId}")
    public GenericResponse deleteUser(@PathVariable("userId")Long userId) throws GenericException {
        return userService.deleteUser(userId);
    }
    @GetMapping(path = "/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/getUserById/{userId}")
    public GenericResponse getUserById(@PathVariable("userId")Long userId) throws GenericException {
        return userService.getUserById(userId);
    }
}
