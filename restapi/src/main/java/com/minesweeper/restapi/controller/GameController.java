package com.minesweeper.restapi.controller;

import com.minesweeper.restapi.dto.GameDto;
import com.minesweeper.restapi.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/minesweeper-api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    /**
     * Start a game
     *
     * @return GameDto
     */
    @Operation(summary = "Start a game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Start a game based on specific rows, columns " +
                                                             "and mines",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameDto.class))}),
            @ApiResponse(responseCode = "400", description = "User not found",
                    content = @Content)})
    @PostMapping("/add")
    public ResponseEntity<GameDto> addGame(@RequestBody GameDto gameDto) {
        return new ResponseEntity<GameDto>(gameService.addGame(gameDto), HttpStatus.CREATED);
    }

    /**
     * Modify cells depending the player turn
     *
     * @return GameDto
     */
    @Operation(summary = "Modify cells depending the player turn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Modify cells of the board depending the " +
                                                             "player turn",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameDto.class))}),
            @ApiResponse(responseCode = "400", description = "Game not found",
                    content = @Content)})
    @PostMapping("/modify")
    public ResponseEntity<GameDto> modifyGame(@RequestBody GameDto gameDto) {
        return new ResponseEntity<GameDto>(gameService.modifyGame(gameDto), HttpStatus.CREATED);
    }

}
