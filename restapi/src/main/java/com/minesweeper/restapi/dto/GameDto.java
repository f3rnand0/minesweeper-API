package com.minesweeper.restapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDto {

    private Long id;
    private UserDto user;
    private Integer rows;
    private Integer columns;
    private Set<CellDto> cells;
    private Integer mines;
    private Timestamp dateStarted;
    private Timestamp dateFinished;
    private Time elapsedTime;
}
