package com.minesweeper.restapi.service;

import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.entity.User;
import com.minesweeper.restapi.exception.AlreadyExistsException;
import com.minesweeper.restapi.exception.NotFoundException;
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

    /**
     * Get a user of the database
     *
     * @param name Name of the user
     * @return Dto of the user
     */
    public UserDto getUser(String name) {
        Optional<User> optionalUser = userRepository.findByName(name);
        User user = optionalUser.orElseThrow(() -> new NotFoundException("User not found"));
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * Get a list of all users
     *
     * @return List of sots of the users
     */
    public List<UserDto> getUserList() {
        List<UserDto>
                userList = userRepository.findAll().stream().map(user -> new UserDto(user.getName()))
                .collect(Collectors.toList());
        return userList;
    }

    /**
     * Saves a user in the database
     *
     * @param userDto Dto of the user
     * @return Dto of the user
     */
    public UserDto addUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByName(userDto.getName());
        // Throw an exception because there's an existing user
        optionalUser.ifPresent(u -> {
            throw new AlreadyExistsException("User already exist");
        });
        User user = new User(userDto.getName());
        User userSaved = userRepository.save(user);
        return modelMapper.map(userSaved, UserDto.class);
    }
}
