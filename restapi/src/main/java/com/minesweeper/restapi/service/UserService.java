package com.minesweeper.restapi.service;

import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.entity.User;
import com.minesweeper.restapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> getUser(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent())
            return new ResponseEntity(modelMapper.map(user.get(), UserDto.class), HttpStatus.OK);
        else
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> getUserList() {
        List<UserDto>
                userList = userRepository.findAll().stream().map(user -> new UserDto(user.getName())).collect(Collectors.toList());
        return new ResponseEntity(userList, HttpStatus.OK);
    }

    public ResponseEntity<Object> addUser(UserDto userDto) {
        Optional<User> user = userRepository.findByName(userDto.getName());
        if (user.isPresent())
            return new ResponseEntity("User already exist", HttpStatus.BAD_REQUEST);
        else {
            User newUser = new User();
            newUser.setName(userDto.getName());
            User userSaved = userRepository.save(newUser);
            return new ResponseEntity<>(modelMapper.map(userSaved, UserDto.class), HttpStatus.CREATED);
        }
    }
}
