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
public class CellDto implements Comparable<CellDto> {

    private Integer row;
    private Integer column;
    private Boolean visible;
    private Boolean flagged;
    private String state;

    public CellDto(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public int compareTo(CellDto cellDto) {
        int c = row.compareTo(cellDto.getRow());
        if (c == 0)
            c = column.compareTo(cellDto.getColumn());
        return c;
    }
}
