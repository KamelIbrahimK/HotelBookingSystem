package com.example.hotelbookingsystem.service;

import com.example.hotelbookingsystem.model.common.Constants;
import com.example.hotelbookingsystem.model.common.GenericException;
import com.example.hotelbookingsystem.model.common.GenericResponse;
import com.example.hotelbookingsystem.model.dto.SignUpDto;
import com.example.hotelbookingsystem.model.entity.User;
import com.example.hotelbookingsystem.model.enums.UserType;
import com.example.hotelbookingsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public GenericResponse signUp(SignUpDto signUpDto)throws GenericException{
        Optional<User> user = userRepository.findByUserName(signUpDto.getUserName());
        if (user.isPresent()) {
            log.error("User is already exist , can't create it again");
            throw new GenericException(Constants.DUPLICATE_USER);
        } else {
            User newUser = new User(signUpDto.getUserName(), signUpDto.getPassword(),
                    signUpDto.getNationality(), signUpDto.getPhoneNumber(),signUpDto.getRole());
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setUpdatedInfoAt(LocalDateTime.now());
            userRepository.save(newUser);
            log.info("User added successfully");
            return new GenericResponse(Constants.USER_ADDED, newUser);
        }

    }
    public GenericResponse updateUser(SignUpDto signUpDto)throws GenericException{
        Optional<User> user = userRepository.findByUserName(signUpDto.getUserName());
        if (user.isPresent()){
            user.get().setUserName(signUpDto.getUserName());
            user.get().setPassword(signUpDto.getPassword());
            user.get().setUserNationality(signUpDto.getNationality());
            user.get().setPhoneNumber(signUpDto.getPhoneNumber());
            user.get().setUserType(signUpDto.getRole());
            user.get().setUpdatedInfoAt(LocalDateTime.now());
            userRepository.save(user.get());
            log.info("user updated successfully");
            return new GenericResponse(Constants.UPDATED_USER, user.get());
        }else {
            log.error("user not found");
            throw new GenericException(Constants.UN_AVAILABLE_USER);
        }
    }
    public GenericResponse deleteUser(Long userId) throws GenericException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()&&user.get().getUserType().equals(UserType.ADMIN)) {
            userRepository.deleteById(userId);
            log.info("user deleted successfully");
            return new GenericResponse(Constants.DELETED_USER, user.get());
        } else {
            log.error("user not found");
            throw new GenericException(Constants.UN_AVAILABLE_USER);
        }
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public GenericResponse getUserById(Long userId) throws GenericException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return new GenericResponse(Constants.AVAILABLE_USER,user.get());
        } else {
            log.error("User not found");
            throw new GenericException(Constants.UN_AVAILABLE_USER);
        }
    }

}
