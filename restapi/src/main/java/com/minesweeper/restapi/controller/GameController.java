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
     * Create game before start
     *
     * @return GameDto
     */
    @Operation(summary = "Create game before start")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the board",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameDto.class))})
    })
    @PostMapping("/add")
    public ResponseEntity<GameDto> addGame(@RequestBody GameDto gameDto) {
        return new ResponseEntity<GameDto>(gameService.addGame(gameDto), HttpStatus.CREATED);
    }
}
