package com.minesweeper.restapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@ToString(exclude = "game")
@NoArgsConstructor
@Accessors(chain = true)
public class Cell {
    @Id
    @Column(name = "cell_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    private Integer row;

    private Integer column;

    private Boolean visible;

    private Boolean flagged;

    private String state;

    public Cell(Integer row, Integer column, Boolean visible, Boolean flagged, String state) {
        this.row = row;
        this.column = column;
        this.visible = visible;
        this.flagged = flagged;
        this.state = state;
    }
}
