package com.minesweeper.restapi.controller;

import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/minesweeper-api/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Get user by name
     *
     * @param name Name of the user
     * @return UserDto or message error
     */
    @Operation(summary = "Get user by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @GetMapping("/{name}")
    public ResponseEntity<UserDto> getUser(@PathVariable String name) {
        return new ResponseEntity<>(userService.getUser(name), HttpStatus.OK);
    }

    /**
     * Get list of users
     *
     * @return List of UserDto
     */
    @Operation(summary = "Get list of users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user list",
                    content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema =
                            @Schema(implementation = UserDto.class)))})})
    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> getUserList() {
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

    /**
     * Add a user
     *
     * @return UserDto or message error
     */
    @Operation(summary = "Add a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User added",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "User already exist",
                    content = @Content)})
    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

}
