package com.minesweeper.restapi.service;

import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.entity.User;
import com.minesweeper.restapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDto getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return modelMapper.map(user.get(), UserDto.class);
        throw  new RuntimeException("User not found");
    }
}
