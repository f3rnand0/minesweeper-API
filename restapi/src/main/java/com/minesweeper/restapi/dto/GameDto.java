package com.minesweeper.restapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.minesweeper.restapi.entity.GameTurn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDto {

    private Long id;
    private UserDto user;
    private Integer rows;
    private Integer columns;
    private Integer mines;
    private List<CellDto> cells;
    private GameTurn gameTurn;
    private CellDto selectedCell;
    private CellDto flaggedCell;
    private Timestamp dateStarted;
    private Timestamp dateFinished;
    private Time elapsedTime;
    private String endMessage;
}
