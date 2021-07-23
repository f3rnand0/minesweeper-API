package com.minesweeper.restapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardDto {

    private Boolean[][] visibleCells;
    private Boolean[][] flaggedCells;
    private String[][] cells;
    private String endMessage;
    private Integer visibleCount;

    public BoardDto(Boolean[][] visibleCells, Boolean[][] flaggedCells, String[][] cells) {
        this.visibleCells = visibleCells;
        this.flaggedCells = flaggedCells;
        this.cells = cells;
    }
}
