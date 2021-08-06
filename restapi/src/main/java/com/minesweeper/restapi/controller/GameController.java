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
     * Adds a game
     *
     * @return GameDto
     */
    @Operation(summary = "Adds a game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description =
                    "Adds a game based on specific player name, rows, columns " +
                    "and mines",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameDto.class))})})
    @PostMapping("/add")
    public ResponseEntity<GameDto> addGame(@RequestBody GameDto gameDto) {
        return new ResponseEntity<>(gameService.addGame(gameDto), HttpStatus.CREATED);
    }

    /**
     * Starts a game (first turn)
     *
     * @return GameDto
     */
    @Operation(summary = "Starts a game based on the selected cell of the board. First turn of the player.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Starts a game based on the selected cell of " +
                                                             "the board. First turn of the player.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameDto.class))}),
            @ApiResponse(responseCode = "404", description = "Game not found",
                    content = @Content)})
    @PostMapping("/start")
    public ResponseEntity<GameDto> startGame(@RequestBody GameDto gameDto) {
        return new ResponseEntity<GameDto>(gameService.startGame(gameDto), HttpStatus.CREATED);
    }

    /**
     * Continues a game (later turns)
     *
     * @return GameDto
     */
    @Operation(summary = "Continues a game based on the selected cell of the board. Later turns of the " +
                         "player.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =
                    "Continues a game based on the selected cell of " +
                    "the board. Later turns of the player.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameDto.class))}),
            @ApiResponse(responseCode = "404", description = "Game not found",
                    content = @Content)})
    @PutMapping("/continue")
    public ResponseEntity<GameDto> continueGame(@RequestBody GameDto gameDto) {
        return new ResponseEntity<GameDto>(gameService.continueGame(gameDto), HttpStatus.OK);
    }

    /**
     * Flags a indicated cell of the board
     *
     * @return GameDto
     */
    @Operation(summary = "Flags a indicated cell of the board.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flags a indicated cell of the board.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameDto.class))}),
            @ApiResponse(responseCode = "404", description = "Game not found",
                    content = @Content)})
    @PatchMapping("/flag")
    public ResponseEntity<GameDto> flagCellGame(@RequestBody GameDto gameDto) {
        return new ResponseEntity<GameDto>(gameService.flagCellGame(gameDto), HttpStatus.OK);
    }
}
