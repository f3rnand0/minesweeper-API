package com.minesweeper.restapi.controller;

import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/minesweeper-api/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Get user by id
     * @param name Name of the user
     * @return UserDto or error message
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String name) {
        return userService.getUser(name);
    }

    /**
     * Get list of users
     * @return List of UserDto
     */
    @GetMapping("/list")
    public ResponseEntity<Object> getUserList() {
        return userService.getUserList();
    }

    /**
     * Add user
     * @return UserDto or error message
     */
    @PostMapping("/add")
    public ResponseEntity<Object> getUserList(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

}
