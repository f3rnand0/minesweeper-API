package com.minesweeper.restapi.service;

import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.entity.User;
import com.minesweeper.restapi.exception.AlreadyExistsException;
import com.minesweeper.restapi.exception.UserNotFoundException;
import com.minesweeper.restapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public UserDto getUser(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent())
            return modelMapper.map(user.get(), UserDto.class);
        throw new UserNotFoundException("User not found");
    }

    public List<UserDto> getUserList() {
        List<UserDto>
                userList = userRepository.findAll().stream().map(user -> new UserDto(user.getName()))
                .collect(Collectors.toList());
        return userList;
    }

    public UserDto addUser(UserDto userDto) {
        Optional<User> user = userRepository.findByName(userDto.getName());
        if (!user.isPresent()) {
            User newUser = new User(userDto.getName());
            User userSaved = userRepository.save(newUser);
            return modelMapper.map(userSaved, UserDto.class);
        }
        throw new AlreadyExistsException("User already exist");
    }
}
