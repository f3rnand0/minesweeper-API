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

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardDto {

    private Boolean[][] visibleCells;
    private String[][] cells;
    private String endMessage;
    private Integer visibleCount;

    public BoardDto(Boolean[][] visibleCells, String[][] cells) {
        this.visibleCells = visibleCells;
        this.cells = cells;
    }
}
